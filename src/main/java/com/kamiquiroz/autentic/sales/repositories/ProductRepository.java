package com.kamiquiroz.autentic.sales.repositories;

import com.kamiquiroz.autentic.sales.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {


}