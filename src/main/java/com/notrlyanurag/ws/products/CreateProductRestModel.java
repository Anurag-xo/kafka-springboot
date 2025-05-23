package com.notrlyanurag.ws.products;

import java.math.BigDecimal;

public class CreateProductRestModel {

  private String title;
  private BigDecimal price;
  private int quantity;


  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public BigDecimal getPrice() {
    return price;
  }
  public void setPrice(BigDecimal price) {
    this.price = price;
  }
  public int getQuantity() {
    return quantity;
  }
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
