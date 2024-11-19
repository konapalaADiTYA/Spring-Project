package com.aditya.SpringProject.controller;

import com.aditya.SpringProject.model.Product;
import com.aditya.SpringProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        Product product = service.getProductById(id);
        if(product != null)
            return new ResponseEntity<>(product,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
       try {
           Product pro = service.addProduct(product, imageFile);
           return new ResponseEntity<>(pro,HttpStatus.CREATED);
       } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product = service.getProductById(productId);
        byte[] imageFile  = product.getImageDate();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> update(@PathVariable int id,@RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product pro = null;
        try {
            pro = service.updateProduct(id, product, imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
        }
        if(pro!=null)
            return new ResponseEntity<>("updated",HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product pro = service.getProductById(id);
        if(pro != null){
            service.deleteProduct(id);
            return  new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        System.out.println("Searching with :"+keyword);
        List<Product> pro = service.searchProducts(keyword);
        return new ResponseEntity<>(pro,HttpStatus.OK);
    }
}
