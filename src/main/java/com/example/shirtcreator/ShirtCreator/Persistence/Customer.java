package com.example.shirtcreator.ShirtCreator.Persistence;


import jakarta.persistence.*;

@Entity(name = "tblCustomer")
public class Customer {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private boolean deleted;

    @ManyToOne
    @JoinColumn (name = "address")
    private Address address;

    // CONSTRUCTOR
    public Customer(String firstName, String lastName, String email, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    public Customer() {
    }

    // GETTERS & SETTERS
    public Integer getid() {
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

    // TOSTRING
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", deleted=" + deleted +
                ", address=" + address +
                '}';
    }
}
