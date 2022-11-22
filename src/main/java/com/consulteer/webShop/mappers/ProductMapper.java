package com.consulteer.webShop.mappers;

import com.consulteer.webShop.dto.CreateProductDto;
import com.consulteer.webShop.dto.ProductDto;
import com.consulteer.webShop.dto.TopProductDto;
import com.consulteer.webShop.enums.ProductStockStatus;
import com.consulteer.webShop.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductMapper() {
    }

    public ProductDto map(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getNumberInStock());
    }

    public Product map(CreateProductDto createProductDto) {
        return new Product(null, createProductDto.getName(), createProductDto.getPrice(), createProductDto.getNumberInStock());
    }

    public TopProductDto mapTopProduct(Product product){
        return new TopProductDto(product.getName(), product.getPrice(), labelStock(product));
    }

    private ProductStockStatus labelStock(Product product){
        if (product.getNumberInStock() == 0) return ProductStockStatus.OUT_OF_STOCK;
        return ProductStockStatus.IN_STOCK;
    }
}
