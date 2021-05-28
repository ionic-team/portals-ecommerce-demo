package io.ionic.demo.ecommerce.data.model;

import java.util.ArrayList;

public class User {
    public int id;
    public String firstName;
    public String lastName;
    public String email;
    public String image;
    public ArrayList<Address> addresses;
    public ArrayList<CreditCard> creditCards;
}
