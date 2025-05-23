package com.notrlyanurag.ws.products;

import java.math.BigDecimal;

public class CreateProductRestModel {

  private String title;
  private double price;
  private int quantity;


  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public double getPrice() {
    return price;
  }
  public void setPrice(double price) {
    this.price = price;
  }
  public int getQuantitiy() {
    return quantity;
  }
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
