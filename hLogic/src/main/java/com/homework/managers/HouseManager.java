package com.homework.managers;



import com.homework.domain.Apartment;
import com.homework.domain.House;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class HouseManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/yours_houses";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "postgres";


    public House createHouse(String streetName) {
        House house = new House();
        house.setStreetName(streetName);
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            try {
                PreparedStatement preStmt = con.prepareStatement("insert into houses(address) values (?)");
                preStmt.setString(1, streetName);
                preStmt.executeUpdate();
                preStmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return house;
    }

    public boolean addToHouse(Apartment apartment, long houseId) {
        List<House> houses = getHouses();
        House house = null;
        for (House h : houses) {
            if (h.getId()==houseId){
                house = h;
            }
        }
        if (house==null){
            return false;
        }

        house.getApartments().add(apartment);
        return true;
    }

    public List<Apartment> getApartmentsInHouse(long houseId) {
        List<Apartment> apartments = new ArrayList<Apartment>();

        try {
            Class.forName("org.postgresql.Driver");
           Connection con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            try {
              PreparedStatement  preStmt = con.prepareStatement("select * from apartments where house_id = ?");
                preStmt.setLong(1, houseId);
               ResultSet rs = preStmt.executeQuery();

                while (rs.next()) {
                    String owner = rs.getString("owner");
                    long id = rs.getLong("id");
                    int aN = rs.getInt("apartmentnumber");
                    int area = rs.getInt("area");
                    int floor = rs.getInt("floor");
                    int nor = rs.getInt("numberofrooms");
                    int house_id = rs.getInt("house_id");
                    apartments.add(new Apartment(id, owner, aN, area, floor, nor, house_id));
                }
                preStmt.close();
                rs.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apartments;
    }

    public List<House> getHouses() {
        List<House> houses = new ArrayList<House>();

        try {
            Class.forName("org.postgresql.Driver");
           Connection con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            try {
              Statement  stmt = con.createStatement();
              ResultSet  rs = stmt.executeQuery("select * from houses");
                while (rs.next()) {
                    String address = rs.getString("address");
                    long id = rs.getLong("id");
                    List<Apartment> apartments = getApartmentsInHouse(id);
                    houses.add(new House(id, address, apartments));
                }
                stmt.close();
                rs.close();
            }finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return houses;
    }
}
