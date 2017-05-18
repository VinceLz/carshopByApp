package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/11/17.
 */

public class SelectDataList {
    private List<String> country;
    private List<String> output;
    private List<String> drive;
    private List<String> fuel;
    private List<String> transmission;
    private List<String> produce;
    private List<String> structure;
    private List<String> seat;
    private List<String> level;
    private double minprice;
    private double maxprice;
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public double getMinprice() {
        return minprice;
    }

    public void setMinprice(double minprice) {
        this.minprice = minprice;
    }

    public double getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(double maxprice) {
        this.maxprice = maxprice;
    }

    public SelectDataList(List<String> country, List<String> output, List<String> drive, List<String> fuel, List<String> transmission, List<String> produce, List<String> structure, List<String> seat, List<String> level) {
        this.country = country;
        this.output = output;
        this.drive = drive;
        this.fuel = fuel;
        this.transmission = transmission;
        this.produce = produce;
        this.structure = structure;
        this.seat = seat;
        this.level = level;
    }

    public List<String> getLevel() {
        return level;
    }

    public void setLevel(List<String> level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "SelectDataList{" +
                "country=" + country +
                ", output=" + output +
                ", drive=" + drive +
                ", fuel=" + fuel +
                ", transmission=" + transmission +
                ", produce=" + produce +
                ", structure=" + structure +
                ", seat=" + seat +
                ", level=" + level +
                '}';
    }

    public SelectDataList() {

    }

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> country) {
        this.country = country;
    }

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(List<String> output) {
        this.output = output;
    }

    public List<String> getDrive() {
        return drive;
    }

    public void setDrive(List<String> drive) {
        this.drive = drive;
    }

    public List<String> getFuel() {
        return fuel;
    }

    public void setFuel(List<String> fuel) {
        this.fuel = fuel;
    }

    public List<String> getTransmission() {
        return transmission;
    }

    public void setTransmission(List<String> transmission) {
        this.transmission = transmission;
    }

    public List<String> getProduce() {
        return produce;
    }

    public void setProduce(List<String> produce) {
        this.produce = produce;
    }

    public List<String> getStructure() {
        return structure;
    }

    public void setStructure(List<String> structure) {
        this.structure = structure;
    }

    public List<String> getSeat() {
        return seat;
    }

    public void setSeat(List<String> seat) {
        this.seat = seat;
    }
}
