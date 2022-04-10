package com.tiggersrwel.estore.ProductsService.query;

import com.tiggersrwel.estore.ProductsService.core.data.ProductEntity;
import com.tiggersrwel.estore.ProductsService.core.data.ProductsRepository;
import com.tiggersrwel.estore.ProductsService.core.events.ProductCreatedEvent;
import com.tiggersrwel.estore.core.events.ProductReservationCancelledEvent;
import com.tiggersrwel.estore.core.events.ProductReservedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
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

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent) {
        ProductEntity productEntity = productsRepository.findByProductId(productReservedEvent.getProductId());
        productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
        productsRepository.save(productEntity);
    }

    @EventHandler
    public void on(ProductReservationCancelledEvent productReservationCancelledEvent) {
        ProductEntity currentlyStoredProduct = productsRepository.findByProductId(productReservationCancelledEvent.getProductId());
//        currentlyStoredProduct.setQuantity(currentlyStoredProduct.getQuantity() + productReservationCancelledEvent.getQuantity());
        int newQuantity = currentlyStoredProduct.getQuantity() + productReservationCancelledEvent.getQuantity();
        currentlyStoredProduct.setQuantity(newQuantity);
        productsRepository.save(currentlyStoredProduct);
    }

    @ResetHandler
    public void reset() {
        productsRepository.deleteAll();
    }
}
