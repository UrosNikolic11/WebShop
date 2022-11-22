package com.consulteer.webShop.controllers;

import com.consulteer.webShop.dto.CreateProductDto;
import com.consulteer.webShop.dto.ProductDto;
import com.consulteer.webShop.dto.ReportDto;
import com.consulteer.webShop.dto.TopProductDto;
import com.consulteer.webShop.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody @Valid CreateProductDto createProductDto) {
        return new ResponseEntity<>(productService.create(createProductDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable("id") Long id
            , @RequestBody @Valid CreateProductDto createProductDto) {
        return new ResponseEntity<>(productService.update(id, createProductDto), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll(Pageable pageable) {
        return new ResponseEntity<>(productService.findAll(pageable), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/newest")
    public ResponseEntity<List<ProductDto>> findNew() {
        return new ResponseEntity<>(productService.findNew(), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductDto>> getTopProducts() {
        return new ResponseEntity<>(productService.findTopProducts(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/report/{id}")
    public ResponseEntity<ReportDto> getReport(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.getReport(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        productService.remove(id);
    }
}
