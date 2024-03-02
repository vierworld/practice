package ru.vw.practice.lesson5.repository;

import ru.vw.practice.lesson5.dto.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository {
  List<Product> getByUserId(long userId);

  Optional<Product> getByProductId(long productId);
}
