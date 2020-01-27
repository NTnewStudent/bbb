package com.example.calculatorapplication.Model;

public class Lens {

    String make;
    double maximumAperture;
    Integer focalLength; // unit mm

    public Lens(String make, double maximumAperture, Integer focalLength) {
        this.make = make;
        this.maximumAperture = maximumAperture;
        this.focalLength = focalLength;
    }

    @Override
    public String toString() {
        return "" +
                "" + make + '\'' +
                " " + focalLength +"mm"+
                " F" + maximumAperture;
    }

    public Lens() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public double getMaximumAperture() {
        return maximumAperture;
    }

    public void setMaximumAperture(double maximumAperture) {
        this.maximumAperture = maximumAperture;
    }

    public Integer getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(Integer focalLength) {
        this.focalLength = focalLength;
    }
}
