package com.consulteer.webShop.services.impl;

import com.consulteer.webShop.dto.*;
import com.consulteer.webShop.exception.BadRequestException;
import com.consulteer.webShop.model.*;
import com.consulteer.webShop.repositories.CartEntryRepository;
import com.consulteer.webShop.repositories.OrderEntryRepository;
import com.consulteer.webShop.repositories.OrderRepository;
import com.consulteer.webShop.repositories.ProductRepository;
import com.consulteer.webShop.repositories.impl.CartRepositoryImpl;
import com.consulteer.webShop.services.CartService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final CartRepositoryImpl cartRepository;
    private final CartEntryRepository cartEntryRepository;
    private final OrderRepository orderRepository;
    private final OrderEntryRepository orderEntryRepository;

    public CartServiceImpl(ProductRepository productRepository, CartRepositoryImpl cartRepository,
                           CartEntryRepository cartEntryRepository, OrderRepository orderRepository, OrderEntryRepository orderEntryRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartEntryRepository = cartEntryRepository;
        this.orderRepository = orderRepository;
        this.orderEntryRepository = orderEntryRepository;
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
    public BuyDtoResponse buy(BuyDto buyDto) {
        Cart cart = cartRepository.findById(buyDto.cartId()).orElseThrow(() -> new BadRequestException("Cart with given id does not exist!"));

        Set<CartEntry> products = cart.getCartProducts();

        if(products.size() == 0){
            throw new BadRequestException("Cart is empty");
        }

        Order order = new Order();
        order.setAddress(buyDto.address());
        order.setCity(buyDto.city());
        order.setNumber(buyDto.number());
        order.setTotalPrice(cart.calculateTotalPrice());
        order.setUser(cart.getUser());
        orderRepository.save(order);

        products.forEach(cartEntry -> {
            OrderEntry orderEntry = new OrderEntry();
            orderEntry.setAmount(cartEntry.getAmount());
            orderEntry.setOrder(order);
            orderEntry.setProduct(cartEntry.getProduct());
            orderEntry.setOrderProductPK(new OrderProductPK(order.getId(), cartEntry.getProduct().getId()));
            orderEntryRepository.save(orderEntry);
            Product product = cartEntry.getProduct();
            product.setNumberInStock(product.getNumberInStock() - cartEntry.getAmount());
            productRepository.save(product);
        });

        BuyDtoResponse buyDtoResponse = new BuyDtoResponse("Your bill is: " + cart.calculateTotalPrice());

        products.forEach(cartEntry -> {
            products.remove(cartEntry);
            cartEntryRepository.delete(cartEntry);
        });

        return buyDtoResponse;
    }
}
