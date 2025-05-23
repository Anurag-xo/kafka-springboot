package com.notrlyanurag.ws.products.rest;

import java.math.BigDecimal;

public class CreateProductRestModel {

  private string title;
  private BigDecimal price;
  private Integer quantity;


  public static getTitle() {
    return title;
  }
  public void setTitle(string title) {
    this.title = title;
  }
  public BigDecimal getPrice() {
    return price;
  }
  public void setPrice(BigDecimal price) {
    this.price = price;
  }
  public Integer getQuantitiy() {
    return quantity;
  }
  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
