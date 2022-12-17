package com.example.shirtcreator.ShirtCreator.Persistence;

import jakarta.persistence.*;

@Entity(name = "tblAddress")
public class Address {

    @Id
    @GeneratedValue
    private int id;

    private String street;
    private int plz;
    private String location;

    public Address(String street, int plz, String location) {
        this.street = street;
        this.plz = plz;
        this.location = location;
    }

    public Address() {

    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    // Getters & Setters
    public int getPlz() {
        return plz;
    }
}















