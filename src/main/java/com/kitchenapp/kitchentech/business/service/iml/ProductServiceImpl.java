package com.kitchenapp.kitchentech.business.service.iml;


import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.business.repository.ProductRepository;
import com.kitchenapp.kitchentech.business.service.ProductService;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProductsByRestaurantId(Long restaurantId){
        return productRepository.findByRestaurantId(restaurantId);
    }
    @Override
    public Product getProductByRestaurantProductId(Long id){
        return productRepository.findById(id).orElse(null);
    }
    @Override
    public Product createProduct(Product product){
        return productRepository.save(product);
    }
    @Override
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
    @Override
    public Product updateProduct(Product product){
        Product productToUpdate = productRepository.findById(product.getId()).orElse(null);
        if (productToUpdate != null){
            productToUpdate.setProductName(product.getProductName());
            productToUpdate.setCategory(product.getCategory());
            productToUpdate.setProductImageUrl(product.getProductImageUrl());
            productToUpdate.setProductPrice(product.getProductPrice());
            productToUpdate.setRestaurantId(product.getRestaurantId());
            productToUpdate.setSupplies(product.getSupplies());
            return productRepository.save(productToUpdate);
        }
        else {
            return null;
        }
    }
    @Override
    public List<Product> getProductByCategory(String category){
        if (category != null){
            return productRepository.findByCategory(category);
        }
        else {
            return null;
        }
    }

}
