package com.example.demo.Controller;

import com.example.demo.Entity.Product;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController2 {
    private final List<Product> productDB = new ArrayList<>();

    @PostConstruct
    private void initDB() {
        productDB.add(new Product("B0001", "Android Development (Java)", 380));
        productDB.add(new Product("B0002", "Android Development (Kotlin)", 420));
        productDB.add(new Product("B0003", "Data Structure (Java)", 250));
        productDB.add(new Product("B0004", "Finance Management", 450));
        productDB.add(new Product("B0005", "Human Resource Management", 330));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {
        Optional<Product> productOp = productDB.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (productOp.isPresent()) {
            Product product = productOp.get();
            return ResponseEntity.ok().body(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(value = "keyword", defaultValue = "") String name) {
        List<Product> products = productDB.stream()
                .filter(p -> p.getName().toUpperCase().contains(name.toUpperCase()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(products);
    }

//    @RequestMapping("/products")
//    public ResponseEntity<Product> createProduct(@RequestBody Product request) {
//        boolean isIdDuplicated = productDB.stream()
//                .anyMatch(p -> p.getId().equals(request.getId()));
//        if (isIdDuplicated) {
//            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
//        }
//
//        Product product = new Product();
//        product.setId(request.getId());
//        product.setName(request.getName());
//        product.setPrice(request.getPrice());
//        productDB.add(product);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(product.getId())
//                .toUri();
//
//        return ResponseEntity.created(location).body(product);
//    }


}