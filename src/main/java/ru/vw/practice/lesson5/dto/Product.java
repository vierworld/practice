package ru.vw.practice.lesson5.dto;

import java.math.BigDecimal;

public class Product {
  private Long productId;
  private Long userId;
  private String billNumber;
  private BigDecimal balance;
  private ProductType productType;

  public Product(Long productId,
                 Long userId,
                 String billNumber,
                 BigDecimal balance,
                 ProductType productType) {
    this.productId = productId;
    this.userId = userId;
    this.billNumber = billNumber;
    this.balance = balance;
    this.productType = productType;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getBillNumber() {
    return billNumber;
  }

  public void setBillNumber(String billNumber) {
    this.billNumber = billNumber;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public ProductType getProductType() {
    return productType;
  }

  public void setProductType(ProductType productType) {
    this.productType = productType;
  }

  public static ProductBuilder builder() {
    return new ProductBuilder();
  }

  public static class ProductBuilder {
    private Long productId;
    private Long userId;
    private String billNumber;
    private BigDecimal balance;
    private ProductType productType;

    public ProductBuilder productId(Long productId) {
      this.productId = productId;
      return this;
    }

    public ProductBuilder userId(Long userId) {
      this.userId = userId;
      return this;
    }

    public ProductBuilder billNumber(String billNumber) {
      this.billNumber = billNumber;
      return this;
    }

    public ProductBuilder balance(BigDecimal balance) {
      this.balance = balance;
      return this;
    }

    public ProductBuilder productType(ProductType productType) {
      this.productType = productType;
      return this;
    }


    public Product build() {
      return new Product(productId,
              userId,
              billNumber,
              balance,
              productType);
    }
  }

  public enum ProductType {
    BILL(1),
    CARD(2);

    private int code;

    ProductType(int code) {
      this.code = code;
    }

    public int getCode() {
      return code;
    }
  }
}
