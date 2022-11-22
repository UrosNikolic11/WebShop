package com.consulteer.webShop.services;

import com.consulteer.webShop.dto.CreateProductDto;
import com.consulteer.webShop.dto.ProductDto;
import com.consulteer.webShop.dto.ReportDto;
import com.consulteer.webShop.dto.TopProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDto create(CreateProductDto createProductDto);
    Page<ProductDto> findAll(Pageable pageable);
    ProductDto findById(Long id);
    ProductDto update(Long id, CreateProductDto createProductDto);
    List<ProductDto> findNew();
    List<TopProductDto> findTopProducts();

    ReportDto getReport(Long id);
    void remove(Long id);
}
