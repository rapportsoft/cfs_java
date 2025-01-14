package com.cwms.entities;

public class ContainerCountDTO {
    private String name;
    private int count20;
    private int count40;
    private int tuesCount;

    // Constructor
    public ContainerCountDTO(String name, int count20, int count40, int tuesCount) {
        this.name = name;
        this.count20 = count20;
        this.count40 = count40;
        this.tuesCount = tuesCount;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount20() {
        return count20;
    }

    public void setCount20(int count20) {
        this.count20 = count20;
    }

    public int getCount40() {
        return count40;
    }

    public void setCount40(int count40) {
        this.count40 = count40;
    }

    public int getTuesCount() {
        return tuesCount;
    }

    public void setTuesCount(int tuesCount) {
        this.tuesCount = tuesCount;
    }
}
