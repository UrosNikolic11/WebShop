package com.consulteer.webShop.services.impl;

import com.consulteer.webShop.dto.BuyDto;
import com.consulteer.webShop.dto.CartEntryDto;
import com.consulteer.webShop.dto.CartDto;
import com.consulteer.webShop.dto.ShowProductDto;
import com.consulteer.webShop.exception.BadRequestException;
import com.consulteer.webShop.model.Cart;
import com.consulteer.webShop.model.CartEntry;
import com.consulteer.webShop.model.CartProductPK;
import com.consulteer.webShop.model.Product;
import com.consulteer.webShop.repositories.CartEntryRepository;
import com.consulteer.webShop.repositories.ProductRepository;
import com.consulteer.webShop.repositories.impl.CartRepositoryImpl;
import com.consulteer.webShop.services.CartService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final CartRepositoryImpl cartRepository;
    private final CartEntryRepository cartEntryRepository;

    public CartServiceImpl(ProductRepository productRepository, CartRepositoryImpl cartRepository,
                           CartEntryRepository cartEntryRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartEntryRepository = cartEntryRepository;
    }

    @Override
    public void addProductToCart(CartEntryDto cartEntryDto) {
        Product product = productRepository.findById(cartEntryDto.cartId())
                .orElseThrow(() -> new BadRequestException("Product with given id does not exist!"));
        Cart cart = cartRepository.findById(cartEntryDto.cartId())
                .orElseThrow(() -> new BadRequestException("Cart with given id does not exist!"));

        cart.getCartProducts().stream()
                .filter(cartEntry -> cartEntry.getProduct().equals(product))
                .findFirst()
                .ifPresentOrElse(cartEntry -> {
                    if (cartEntry.getProduct().getNumberInStock() < cartEntryDto.amount())
                        throw new BadRequestException("Product out of stock!");

                    cartEntry.setAmount(cartEntry.getAmount() + cartEntryDto.amount());
                    cartEntryRepository.save(cartEntry);
                }, () -> {
                    if (product.getNumberInStock() < cartEntryDto.amount())
                        throw new BadRequestException("Product out of stock!");

                    CartEntry cartEntry = new CartEntry();
                    cartEntry.setCartProductPK(new CartProductPK(cartEntryDto.cartId(), cartEntryDto.productId()));
                    cartEntry.setAmount(cartEntryDto.amount());
                    cartEntry.setProduct(product);
                    cartEntry.setCart(cart);
                    cartEntryRepository.save(cartEntry);
                });
    }

    @Override
    public void removeProductFromCart(CartEntryDto cartEntryDto) {
        Cart cart = cartRepository.findById(cartEntryDto.cartId()).orElseThrow(() -> new BadRequestException("Cart with given id does not exist!"));

        cart.getCartProducts().stream()
                .filter(cartEntry -> cartEntry.getProduct().getId().equals(cartEntryDto.productId()))
                .findFirst()
                .ifPresentOrElse(cartEntry -> {
                            if (cartEntry.getAmount() - cartEntryDto.amount() <= 0) {
                                cartEntryRepository.delete(cartEntry);
                            } else {
                                cartEntry.setAmount(cartEntry.getAmount() - cartEntryDto.amount());
                                cartEntryRepository.save(cartEntry);
                            }
                        },
                        () -> {
                            throw new BadRequestException("This product is not in cart!");
                        });
    }

    @Override
    public CartDto showCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new BadRequestException("Cart with given id does not exist!"));
        var productsDto = cart.getCartProducts().stream().map(ShowProductDto::map).toList();
        return new CartDto(productsDto, cart.calculateTotalPrice());
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new BadRequestException("Cart with given id does not exist!"));
        cartEntryRepository.deleteAll(cart.getCartProducts());
    }

    @Override
    public BuyDto buy(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new BadRequestException("Cart with given id does not exist!"));
        BuyDto buyDto = new BuyDto("Your bill is: " + cart.calculateTotalPrice());
        cartEntryRepository.deleteAll(cart.getCartProducts());
        return buyDto;
    }
}
