package com.kamiquiroz.autentic.sales.repositories;


import com.kamiquiroz.autentic.sales.entities.Product;
import com.kamiquiroz.autentic.sales.entities.Sale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends CrudRepository<Sale, Long> {

}

