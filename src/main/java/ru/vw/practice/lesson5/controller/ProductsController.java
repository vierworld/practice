package ru.vw.practice.lesson5.controller;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.vw.practice.lesson5.dto.Product;
import ru.vw.practice.lesson5.exception.CustomNotFoundException;
import ru.vw.practice.lesson5.service.ProductsService;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductsController {
  private final ProductsService productsService;

  public ProductsController(ProductsService productsService) {
    this.productsService = productsService;
  }

  @GetMapping("/products/{productId}")
  public Product getProductById(@PathVariable long productId) {
    Optional<Product> result = productsService.getByProductId(productId);
    return result.orElseThrow(CustomNotFoundException::new);
  }

  @GetMapping("/products/user/{userId}")
  public List<Product> getProductsByUserId(@PathVariable long userId) {
    List<Product> result = productsService.getByUserId(userId);
    if (CollectionUtils.isEmpty(result)) {
      throw new CustomNotFoundException();
    }
    return productsService.getByUserId(userId);
  }

}
