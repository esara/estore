package com.tiggersrwel.estore.ProductsService.command.rest;

import com.tiggersrwel.estore.ProductsService.command.CreateProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/products") // http://localhost:8080/products
public class ProductsCommandController {

//    @Autowired
//    private Environment env;
    private final Environment env;
    private final CommandGateway commandGateway;

    @Autowired
    public ProductsCommandController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

//    @GetMapping
//    public String getProduct() {
//        return "HTTP GET handled" + env.getProperty("local.server.port");
//    }

    @PostMapping
    public String createProduct(@Valid @RequestBody CreateProductRestModel createProductRestModel) {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(createProductRestModel.getPrice())
                .quantity(createProductRestModel.getQuantity())
                .title(createProductRestModel.getTitle())
                .productId(UUID.randomUUID().toString()).build();

        String returnValue;
        returnValue = commandGateway.sendAndWait(createProductCommand);

//        try {
//            returnValue = commandGateway.sendAndWait(createProductCommand);
//        } catch (Exception ex) {
//            returnValue = ex.getLocalizedMessage();
//        }
        return "HTTP POST for " + createProductRestModel.toString() + " with return: " + returnValue;
    }

//    @PutMapping
//    public String updateProduct() {
//        return "HTTP PUT handled";
//    }
//
//    @DeleteMapping
//    public String deleteProduct() {
//        return "HTTP DELETE handled";
//    }
}
