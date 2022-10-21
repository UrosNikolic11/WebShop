package com.consulteer.webShop.mappers;

import com.consulteer.webShop.dto.CreateProductDto;
import com.consulteer.webShop.dto.ProductDto;
import com.consulteer.webShop.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductMapper() {
    }

    public ProductDto map(Product product){
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getNumberInStock());
    }

    public Product map(CreateProductDto createProductDto){
        return new Product(null, createProductDto.getName(), createProductDto.getPrice(), createProductDto.getNumberInStock());
    }
}
