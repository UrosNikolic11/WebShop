package com.consulteer.webShop.repositories;

import com.consulteer.webShop.model.Product;
import com.consulteer.webShop.model.ReportCity;
import com.consulteer.webShop.model.ReportProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, CrudRepository<Product, Long> {
    @Query(value = "SELECT * FROM webshop.item i WHERE created_at BETWEEN DATE_SUB(now(),INTERVAL 1 WEEK) AND NOW()ORDER BY created_at DESC LIMIT 5 ",
    nativeQuery = true)
    List<Product> findNewProduct();

    @Query(value = "SELECT i.*" +
            "FROM order_product op JOIN orders o ON (op.order_id = o.id) " +
            "JOIN item i ON (i.id = op.product_id) " +
            "WHERE o.created_at BETWEEN DATE_SUB(now(),INTERVAL 2 WEEK) AND NOW() " +
            "GROUP BY op.product_id " +
            "ORDER BY sum(op.amount) desc " +
            "LIMIT 10", nativeQuery = true)
    List<Product> getTopProducts();

    @Query(value = "SELECT SUM(op.amount) as totalAmount, o.city as cityName " +
            "From orders o " +
            "join order_product op ON (o.id = op.order_id) " +
            "WHERE op.product_id = :id " +
            "GROUP BY o.city",
            nativeQuery = true)
    List<ReportCity> getReportCity(Long id);

    @Query(value = "SELECT i.name as productName, i.price as productPrice from item i where i.id = :id", nativeQuery = true)
    Optional<ReportProduct> getReportProduct(Long id);
}
