package com.msarch.ProductService.controller;


import com.msarch.ProductService.entity.Product;
import com.msarch.ProductService.modal.ProductRequest;
import com.msarch.ProductService.modal.ProductResponse;
import com.msarch.ProductService.service.IProductService;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
        long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getProductById(@PathVariable("id") long productId){
        ProductResponse productResponse = productService.getProductById(productId);
        return  new ResponseEntity<ProductResponse>(productResponse,HttpStatus.OK);
    }

    @PutMapping("/reduceQty/{id}")
        public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity){
                productService.reduceQuantity(productId,quantity);
                return new ResponseEntity<>(HttpStatus.OK);

        }
}
