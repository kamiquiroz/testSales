package com.kamiquiroz.autentic.sales.controller;

import com.kamiquiroz.autentic.sales.entities.Product;
import com.kamiquiroz.autentic.sales.entities.Sale;
import com.kamiquiroz.autentic.sales.repositories.ProductRepository;
import com.kamiquiroz.autentic.sales.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller    // This means that this class is a Controller
@RequestMapping(path = "/autentic") // This means URL's start with /demo (after Application path)
public class MainController {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path = "/sales")
    public @ResponseBody
    Iterable<Sale> getAllSales() {
        // This returns a JSON or XML with the users
        return saleRepository.findAll();
    }



    @GetMapping(path = "/sales/find/{orderId}")
    public Sale findASale(@PathVariable Integer orderId) {
        return saleRepository.findOne(orderId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sales/add")
    public @ResponseBody
    String addNewOrder(@RequestParam(value = "idProducto") Integer idProducto,
                       @RequestParam(value = "idVenta") Integer idVenta,
                       @RequestParam(value = "cantidad") Integer cantidad) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        Sale newSale = new Sale();
        Product product = findAProduct(idProducto);
        String salida;
        if (product != null) {
            Integer totalValue = cantidad * product.getUnitValue();
            newSale.setSaleId(idVenta);
            newSale.setProductId(idProducto);
            newSale.setAmount(cantidad);
            newSale.setTotalSale(totalValue);
            saleRepository.save(newSale);
            salida = "Venta efectuada con éxito" +
                    " Id de la factura:" + idVenta +
                    " Valor en pesos:" + totalValue +
                    " Lugar de despacho:" + product.getProductLocation();

        } else {
            salida = "El producto solicitado no existe";
        }
        return salida;
    }

    @RequestMapping(value = "/orders/delete",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String  deleteOrder(@RequestParam(value = "saleId") Integer saleId) {
        if(saleRepository.findOne(saleId)!=null){
            saleRepository.delete(saleId);
            return "Venta Eliminada";
        }else{
            return "No existe el id ingresado";
        }
    }

    @RequestMapping(value = "/orders/update",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String updateOrder(@RequestParam(value = "productId") Integer productId,
                       @RequestParam(value = "saleId") Integer saleId,
                       @RequestParam(value = "amount") Integer amount) {
        Product product = findAProduct(productId);
        String output;
        Sale sale = findASale(saleId);
        if (sale != null) {
            if (product != null) {
                Integer totalValue = amount * product.getUnitValue();
                sale.setProductId(productId);
                sale.setAmount(amount);
                sale.setTotalSale(totalValue);
                saleRepository.save(sale);
                output = "Venta actualizada con éxito" +
                        " Id de la factura:" + saleId +
                        " Valor en pesos:" + totalValue +
                        " Lugar de despacho:" + product.getProductLocation();
            } else {
                output = "No se encontró un producto con ese ID";
            }
        } else {
            output = "No se encontró una venta con ese ID";
        }
        return output;
    }


    @GetMapping(path = "/products/find/{productId}")
    public Product findAProduct(@PathVariable Integer productId) {
        return productRepository.findOne(productId);
    }

    @RequestMapping(value = "/products/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String addNewProduct(@RequestBody Product newProduct) {
        Product prod = new Product();
        prod.setProductId(newProduct.getProductId());
        prod.setProductName(newProduct.getProductName());
        prod.setUnitValue(newProduct.getUnitValue());
        prod.setProductLocation(newProduct.getProductLocation());
        productRepository.save(prod);
        return "succesfull";
    }


    @GetMapping(path = "/products")
    public @ResponseBody
    Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    @RequestMapping(value = "/products/update",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String updateProduct(@RequestBody Product productToUpdate) {
        Product prod = findAProduct(productToUpdate.getProductId());
        String output;
        if (prod != null) {
            prod.setProductName(productToUpdate.getProductName());
            prod.setUnitValue(productToUpdate.getUnitValue());
            prod.setProductLocation(productToUpdate.getProductLocation());
            productRepository.save(prod);
            output = "Producto Actualizado";
        } else {
            output = "No se encontró un producto con ese ID";
        }
        return output;
    }

    @RequestMapping(value = "/products/delete",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String  deleteProduct(@RequestParam("productId") Integer productId) {
            String output;
            List <Sale> salesByProductId =saleRepository.getSaleByProduct(productId);
            if (salesByProductId.size() > 0) {
                    output = "El producto tiene ordenes asociadas";
            } else {
                if (productRepository.findOne(productId) != null) {
                    productRepository.delete(productId);
                    output = "Producto Eliminado";
                } else {
                    output = "No existe el producto que desea eliminar";
                }

            }
            return output;
    }
}
