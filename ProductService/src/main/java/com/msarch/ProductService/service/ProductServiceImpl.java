package com.msarch.ProductService.service;

import com.msarch.ProductService.entity.Product;
import com.msarch.ProductService.exception.ProductServiceCustomException;
import com.msarch.ProductService.modal.ProductRequest;
import com.msarch.ProductService.modal.ProductResponse;
import com.msarch.ProductService.repository.IProductRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImpl implements  IProductService{

    @Autowired
    private IProductRepo productRepo;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product..");
        Product product = Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepo.save(product);
        log.info("Product created");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
       Product product = productRepo.findById(productId).orElseThrow(
               () -> new ProductServiceCustomException("product not present with provided id","PRODUCT_NOT_FOUND")
       );

       ProductResponse productResponse = new ProductResponse();

        copyProperties(product,productResponse);

        return productResponse;

    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reducing quantity {} of the product id {}",quantity,productId);

        Product product = productRepo.findById(productId).orElseThrow(()->new ProductServiceCustomException("Product Not Found with id "+ productId,"PRODUCT_NOT_FOUND"));

        if(product.getQuantity() < quantity){
            throw new ProductServiceCustomException("Product does not have sufficient quantity","INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepo.save(product);

    }
}
