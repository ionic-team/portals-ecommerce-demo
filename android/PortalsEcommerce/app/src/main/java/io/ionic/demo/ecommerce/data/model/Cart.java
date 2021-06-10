package io.ionic.demo.ecommerce.data.model;

import java.util.ArrayList;

public class Cart {
    public int id;
    public float subTotal = 0;
    public ArrayList<CartItem> basket = new ArrayList<CartItem>();
}
