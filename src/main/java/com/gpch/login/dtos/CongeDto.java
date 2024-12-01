package com.gpch.login.dtos;

public class CongeDto {


    private String name;
    private double rest;


    public CongeDto(String name, double rest) {
        this.name = name;
        this.rest = rest;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }
}
