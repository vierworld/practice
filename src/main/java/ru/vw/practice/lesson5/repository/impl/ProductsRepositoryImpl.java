package ru.vw.practice.lesson5.repository.impl;

import org.springframework.stereotype.Repository;
import ru.vw.practice.lesson5.dto.Product;
import ru.vw.practice.lesson5.repository.ProductsRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ProductsRepositoryImpl implements ProductsRepository {
  private final List<Product> products;

  public ProductsRepositoryImpl() {
    products = new ArrayList<>(List.of(
            Product.builder().productId(1L).userId(1L).billNumber("9909123845342776")
                    .balance(BigDecimal.valueOf(12345.345)).productType(Product.ProductType.BILL).build(),
            Product.builder().productId(2L).userId(2L).billNumber("9909556788456432")
                    .balance(BigDecimal.valueOf(678.344)).productType(Product.ProductType.BILL).build(),
            Product.builder().productId(3L).userId(123L).billNumber("4456678678567456")
                    .balance(BigDecimal.valueOf(999.99)).productType(Product.ProductType.BILL).build(),
            Product.builder().productId(4L).userId(1L).billNumber("2345467678654534")
                    .balance(BigDecimal.valueOf(1236678.111)).productType(Product.ProductType.CARD).build(),
            Product.builder().productId(5L).userId(123L).billNumber("9786746345632453")
                    .balance(BigDecimal.valueOf(7777.777)).productType(Product.ProductType.BILL).build(),
            Product.builder().productId(6L).userId(123L).billNumber("4564562414254778")
                    .balance(BigDecimal.valueOf(99988.888)).productType(Product.ProductType.BILL).build(),
            Product.builder().productId(7L).userId(123L).billNumber("3456567867867342")
                    .balance(BigDecimal.valueOf(777744)).productType(Product.ProductType.CARD).build(),
            Product.builder().productId(8L).userId(2L).billNumber("8877234534545365")
                    .balance(BigDecimal.valueOf(123443)).productType(Product.ProductType.BILL).build(),
            Product.builder().productId(9L).userId(2L).billNumber("99093425987865634")
                    .balance(BigDecimal.valueOf(6654323)).productType(Product.ProductType.CARD).build(),
            Product.builder().productId(10L).userId(123L).billNumber("3295489676623445")
                    .balance(BigDecimal.valueOf(345667)).productType(Product.ProductType.BILL).build()
    ));
  }

  @Override
  public List<Product> getByUserId(long userId) {
    return products.stream().filter(a -> Objects.equals(a.getUserId(), userId)).toList();
  }

  @Override
  public Optional<Product> getByProductId(long productId) {
    return products.stream().filter(a -> Objects.equals(a.getProductId(), productId)).findAny();
  }
}
