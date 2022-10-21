package com.consulteer.webShop.services.impl;

import com.consulteer.webShop.dto.CreateProductDto;
import com.consulteer.webShop.dto.ProductDto;
import com.consulteer.webShop.exception.BadRequestException;
import com.consulteer.webShop.exception.NotFoundException;
import com.consulteer.webShop.mappers.ProductMapper;
import com.consulteer.webShop.model.Product;
import com.consulteer.webShop.repositories.ProductRepository;
import com.consulteer.webShop.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto create(CreateProductDto createProductDto) {
        Product product = productMapper.map(createProductDto);
        productRepository.save(product);

        return productMapper.map(product);
    }

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::map);
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
        return productMapper.map(product);
    }

    @Override
    public ProductDto update(Long id, CreateProductDto createProductDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new BadRequestException("Product with given id does not exist!"));

        product.setName(createProductDto.getName());
        product.setPrice(createProductDto.getPrice());
        product.setNumberInStock(createProductDto.getNumberInStock());

        productRepository.save(product);

        return productMapper.map(product);
    }

    @Override
    public void remove(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new BadRequestException("Product with given id does not exist!"));
        productRepository.delete(product);
    }
}
