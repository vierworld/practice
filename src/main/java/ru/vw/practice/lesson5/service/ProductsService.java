package ru.vw.practice.lesson5.service;

import ru.vw.practice.lesson5.dto.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsService {
  List<Product> getByUserId(long userId);

  Optional<Product> getByProductId(long productId);
}
