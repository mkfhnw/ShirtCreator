package com.example.shirtcreator.ShirtCreator.Persistence;

import jakarta.persistence.*;

@Entity(name = "tblAddress")
public class Address {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer id;

    private String street;
    private Integer plz;
    private String location;

    public Address(String street, Integer plz, String location) {
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

    public void setPlz(Integer plz) {
        this.plz = plz;
    }

    // Getters & Setters
    public Integer getPlz() {
        return plz;
    }

    // TOSTRING
    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", plz=" + plz +
                ", location='" + location + '\'' +
                '}';
    }
}















