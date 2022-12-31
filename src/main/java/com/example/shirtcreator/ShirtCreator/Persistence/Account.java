package com.example.shirtcreator.ShirtCreator.Persistence;

import jakarta.persistence.*;

@Entity(name="tblAccount")
public class Account {

    // Fields
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;

    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;

    private String password;
    private String token;

    // Constructor
    public Account() {
    }

    // Getters & Setters
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
