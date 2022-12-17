package com.example.shirtcreator.ShirtCreator.Persistence;


import jakarta.persistence.*;

@Entity
@Table (name = "CUSTOMER_TABLE")
public class Customer {
    //public static int globalCount = 0;
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DELETED")
    private boolean deleted;

    @ManyToOne
    private Address address;

    // CONSTRUCTOR
    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        //this.id = globalCount++;
    }

    public Customer() {
        //this.id = globalCount++;
    }

    // GETTERS & SETTERS
    public int getid() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Address getAddress(){return address; }

    public void setAddress(Address address) { this.address = address; }
}
