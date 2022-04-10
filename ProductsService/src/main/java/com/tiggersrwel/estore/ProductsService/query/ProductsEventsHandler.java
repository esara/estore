package com.tiggersrwel.estore.ProductsService.query;

import com.tiggersrwel.estore.ProductsService.core.data.ProductEntity;
import com.tiggersrwel.estore.ProductsService.core.data.ProductsRepository;
import com.tiggersrwel.estore.ProductsService.core.events.ProductCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductsEventsHandler {

    private final ProductsRepository productsRepository;

    public ProductsEventsHandler(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @ExceptionHandler(resultType=IllegalStateException.class)
    public void handle(IllegalArgumentException exception) {
        // Log error message
    }

    @ExceptionHandler(resultType=Exception.class)
    public void handle(Exception exception) throws Exception {
        throw exception;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);
        productsRepository.save(productEntity);
    }
}
