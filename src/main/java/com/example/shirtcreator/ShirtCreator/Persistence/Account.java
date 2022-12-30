package com.example.shirtcreator.ShirtCreator.Persistence;

import com.example.shirtcreator.ShirtCreator.Services.Account.MessageNewAccount;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity(name="tblAccount")
public class Account {

    // Fields
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Customer customer;

    private String password;

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
}
