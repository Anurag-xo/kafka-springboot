package com.notrlyanurag.ws.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired private ProductService productService;

  @PostMapping
  public String createProduct(@RequestBody CreateProductRestModel product) throws Exception {
    return productService.createProduct(product);
  }
}
