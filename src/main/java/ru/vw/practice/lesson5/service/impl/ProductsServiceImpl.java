package ru.vw.practice.lesson5.service.impl;

import org.springframework.stereotype.Service;
import ru.vw.practice.lesson5.dto.Product;
import ru.vw.practice.lesson5.repository.ProductsRepository;
import ru.vw.practice.lesson5.service.ProductsService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {
  private final ProductsRepository productsRepository;

  public ProductsServiceImpl(ProductsRepository productsRepository) {
    this.productsRepository = productsRepository;
  }

  @Override
  public List<Product> getByUserId(long userId) {
    return productsRepository.getByUserId(userId);
  }

  @Override
  public Optional<Product> getByProductId(long productId) {
    return productsRepository.getByProductId(productId);
  }
}
