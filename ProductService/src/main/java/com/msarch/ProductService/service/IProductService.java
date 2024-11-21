package com.msarch.ProductService.service;

import com.msarch.ProductService.modal.ProductRequest;
import com.msarch.ProductService.modal.ProductResponse;

public interface IProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
