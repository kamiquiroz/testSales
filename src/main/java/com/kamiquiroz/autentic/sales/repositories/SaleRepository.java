package com.kamiquiroz.autentic.sales.repositories;


import com.kamiquiroz.autentic.sales.entities.Product;
import com.kamiquiroz.autentic.sales.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.List;

@Repository
public interface SaleRepository extends CrudRepository<Sale,Integer> {

    @Query("SELECT s.productId, s.saleId,s.totalSale FROM Sale s where s.productId= :prodId")
    List<Sale> getSaleByProduct(@Param("prodId") Integer prodI);




}

