package org.daily.bill.web.controller;

import org.daily.bill.api.service.ProductService;
import org.daily.bill.domain.Product;
import org.daily.bill.web.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.daily.bill.web.utils.WebConstants.*;
import static org.daily.bill.web.utils.WebConstants.ERROR_CODE;
import static org.daily.bill.web.utils.WebConstants.ERROR_STATUS;

/**
 * Created by vano on 5.11.17.
 */
@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:9000")
public class ProductRest {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.PUT)
    public Response create(@RequestBody Product product) {
        try {
            productService.create(product);
            return new Response(OK_CODE, OK_STATUS, "Product created", product);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage(), e);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Response findById(@PathVariable Long id) {
        try {
            Product product = productService.findById(id);
            return new Response(OK_CODE, OK_STATUS, "Product", product);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage(), e);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public Response findAll() {
        try {
            List<Product> shops = productService.findAll();
            return new Response(OK_CODE, OK_STATUS, "Product list", shops);
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage(), e);
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    public Response updateProduct(@RequestBody Product product) {
        try {
            productService.update(product);
            return new Response(OK_CODE, OK_STATUS, "Updated");
        } catch (Exception e) {
            return new Response(ERROR_CODE, ERROR_STATUS, e.getMessage(), e);
        }

    }


}
