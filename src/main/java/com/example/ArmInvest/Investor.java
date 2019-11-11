package com.example.ArmInvest;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class Investor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String password;
    private String phoneNumber;


    public Investor(String email, String password) { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return userEmail;
    }

    public void setEmail(String email) {
        this.userEmail = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public Map<String, Object> toDTO1(){
        return new LinkedHashMap<String, Object>(){{
            put("id", id);
            put("email", userEmail);
            put("name", firstName);
        }};
    }
}