package com.homework;


import com.homework.domain.Apartment;
import com.homework.domain.House;
import com.homework.managers.ApartmentManager;
import com.homework.managers.HouseManager;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named
@SessionScoped
public class HouseBean implements Serializable {
    private String streetName;
    private String owner;
    private int apartmentNumber;
    private int area;
    private int floor;
    private int numberOfRooms;
    private final String toAdd = "add_apartment_view.xhtml";
    private final String backToAll = "list_view.xhtml";
    private House house;


    private ApartmentManager apartmentsManagerBean = new ApartmentManager();

    private HouseManager housesManagerBean = new HouseManager();

    public HouseBean() {
            this.house = null;
    }

    public void createHouse() {
        housesManagerBean.createHouse(streetName);
    }

    public void goToAddApartmentView(House house) {
        this.house = house;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(toAdd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goMainView() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(backToAll);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addApartment() {
        if (house == null) {
            return;
        }
        Apartment apartment = apartmentsManagerBean.creatApartment(owner, apartmentNumber, area, floor, numberOfRooms,
                house);
        housesManagerBean.addToHouse(apartment, house.getId());
    }

    public List<Apartment> getApartmentsInHous() {
        if (house==null){
            return Collections.emptyList();
        }
        return housesManagerBean.getApartmentsInHouse(house.getId());
    }

    public void delApartment(Apartment apartment) {
        house.delApartment(apartment.getId());
        apartmentsManagerBean.delApartment(apartment.getId());
    }

    public List<House> getHouses() {
        System.out.println(housesManagerBean.getHouses());
        return housesManagerBean.getHouses();
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
