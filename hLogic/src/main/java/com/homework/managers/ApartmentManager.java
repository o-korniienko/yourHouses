package com.homework.managers;


import com.homework.domain.Apartment;
import com.homework.domain.House;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ApartmentManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/yours_houses";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "postgres";

    public Apartment creatApartment(String owner, int apartmentNumber, int area, int floor, int numberOfRooms,
                                    House house) {
        Apartment apartment = new Apartment();
        apartment.setApartmentNumber(apartmentNumber);
        apartment.setArea(area);
        apartment.setFloor(floor);
        apartment.setOwner(owner);
        apartment.setNumberOfRooms(numberOfRooms);
        apartment.setHouse_id((int) house.getId());

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            try {
                PreparedStatement preStmt = con.prepareStatement(
                        "insert into apartments(owner, apartmentnumber, area, floor, numberofrooms, house_id) values (?,?,?,?,?,?) ");
                preStmt.setString(1, owner);
                preStmt.setInt(2, apartmentNumber);
                preStmt.setInt(3, area);
                preStmt.setInt(4, floor);
                preStmt.setInt(5, numberOfRooms);
                preStmt.setInt(6, (int) house.getId());
                preStmt.executeUpdate();

                preStmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apartment;
    }

    public boolean delApartment(long id) {

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            try {
                PreparedStatement preStmt = con.prepareStatement("delete from apartments where id = ?");
                preStmt.setLong(1, id);
                preStmt.executeUpdate();
                preStmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public List<Apartment> getApartments() {
        List<Apartment> apartments = new ArrayList<Apartment>();
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from apartments");
                while (rs.next()) {
                    String owner = rs.getString("owner");
                    long id = rs.getLong("id");
                    int aN = rs.getInt("apartmentnumber");
                    int area = rs.getInt("area");
                    int floor = rs.getInt("floor");
                    int nor = rs.getInt("numberofrooms");
                    int houseId = rs.getInt("house_id");
                    apartments.add(new Apartment(id, owner, aN, area, floor, nor, houseId));
                }
                stmt.close();
                rs.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return apartments;
    }

}
