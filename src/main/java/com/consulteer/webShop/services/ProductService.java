package com.consulteer.webShop.services;

import com.consulteer.webShop.dto.CreateProductDto;
import com.consulteer.webShop.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductDto create(CreateProductDto createProductDto);
    Page<ProductDto> findAll(Pageable pageable);
    ProductDto findById(Long id);
    ProductDto update(Long id, CreateProductDto createProductDto);
    void remove(Long id);
}
