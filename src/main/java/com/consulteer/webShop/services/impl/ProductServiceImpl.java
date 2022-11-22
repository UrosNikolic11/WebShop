package com.consulteer.webShop.services.impl;

import com.consulteer.webShop.dto.*;
import com.consulteer.webShop.exception.BadRequestException;
import com.consulteer.webShop.exception.NotFoundException;
import com.consulteer.webShop.mappers.CityMapper;
import com.consulteer.webShop.mappers.ProductMapper;
import com.consulteer.webShop.mappers.ReportMapper;
import com.consulteer.webShop.model.Product;
import com.consulteer.webShop.model.ReportProduct;
import com.consulteer.webShop.repositories.ProductRepository;
import com.consulteer.webShop.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CityMapper cityMapper;
    private final ReportMapper reportMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CityMapper cityMapper, ReportMapper reportMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.cityMapper = cityMapper;
        this.reportMapper = reportMapper;
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
    public List<ProductDto> findNew() {
        return productRepository.findNewProduct().stream().map(productMapper::map).collect(Collectors.toList());
    }

    @Override
    public List<TopProductDto> findTopProducts() {
        return productRepository.getTopProducts().stream().map(productMapper::mapTopProduct).collect(Collectors.toList());
    }

    @Override
    public ReportDto getReport(Long id) {
        ReportProduct reportProduct = productRepository.getReportProduct(id).orElseThrow(() -> new BadRequestException("Product not found!"));
        List<CityDto> cityDtoList = productRepository.getReportCity(id).stream()
                .map(reportCity -> cityMapper.map(reportCity, reportProduct.getProductPrice())).toList();

        return reportMapper.map(cityDtoList, reportProduct);
    }

    @Override
    public void remove(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new BadRequestException("Product with given id does not exist!"));
        productRepository.delete(product);
    }
}
