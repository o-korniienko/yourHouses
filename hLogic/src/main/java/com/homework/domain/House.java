package com.homework.domain;

import java.util.List;

public class House {


    private long id;
    private String streetName;

    private List<Apartment> apartments;

    public House(long id, String streetName, List<Apartment> apartments) {
        this.id = id;
        this.streetName = streetName;
        this.apartments = apartments;
    }

    public House() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

    public void delApartment(long id){
        for (Apartment a : apartments) {
            if (a.getId()==id){
                apartments.remove(a);
            }
        }
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", streetName='" + streetName + '\'' +
                '}';
    }
}
