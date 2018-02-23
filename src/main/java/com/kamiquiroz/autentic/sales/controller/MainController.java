package com.kamiquiroz.autentic.sales.controller;

import com.kamiquiroz.autentic.sales.entities.Product;
import com.kamiquiroz.autentic.sales.entities.Sale;
import com.kamiquiroz.autentic.sales.repositories.ProductRepository;
import com.kamiquiroz.autentic.sales.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/autentic") // This means URL's start with /demo (after Application path)
public class MainController {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductRepository productRepository;


    public Product findAProduct(Integer idproduct)
    {
        Long idproducto= new Long(idproduct);
        return productRepository.findOne(idproducto);
    }

    @GetMapping(path="/addOrder") // Map ONLY GET Requests
    public @ResponseBody String addNewOrder (@RequestParam Integer idProducto,
                                             @RequestParam Integer idVenta,
                                             @RequestParam int cantidad){
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Sale newSale = new Sale();
        Product product = findAProduct(idProducto);
        float totalValue = cantidad *  product.getUnitValue();
        newSale.setSaleId(idVenta);
        newSale.setProductId(idProducto);
        newSale.setTotalSale(totalValue);
        saleRepository.save(newSale);
        String salida = "Venta efectuada con éxito" +
                " Id de la factura:" + idVenta +
                " Valor en pesos:" + totalValue+
                " Lugar de despacho" + product.getProductLocation();
        return salida;
    }

    @GetMapping(path="/allSales")
    public @ResponseBody Iterable<Sale> getAllSales() {
        // This returns a JSON or XML with the users
        return saleRepository.findAll();
    }


}
