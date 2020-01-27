package com.example.calculatorapplication.Model;

import java.text.DecimalFormat;

public class DepthFieldCalculator {

    double COC;
    Lens lens;
    double subjectDistance;//unit mm
    double aperture; // unit mm
    double hyperFocalDistance; //unit mm
    double farFocal;//unit mm
    double nearFocal;//unit mm
    double dofFocal;//unit mm
    DecimalFormat format = new DecimalFormat("0.00");

    public DepthFieldCalculator(Lens lens, double subjectDistance, double aperture, double coc) {
        this.lens = lens;
        //transform m to mm
        this.subjectDistance = (subjectDistance * 1000);
        this.aperture = aperture;
        this.COC = coc;
        hyperFocalDistance = CalculatorHyperfocalDistance();
        farFocal = CalculatorFarFocal();
        nearFocal = CalculatorNearFocal();
        dofFocal = CalculatorDof();

    }

    /**
     * @return type{double} unit{mm}
     */
    public double CalculatorHyperfocalDistance() {
        double result = (lens.getFocalLength() * lens.getFocalLength()) / (aperture * COC);
        return result;
    }

    public double CalculatorNearFocal() {
        double result = (hyperFocalDistance * subjectDistance) / (hyperFocalDistance + (subjectDistance - lens.getFocalLength()));
        return result;
    }

    public double CalculatorFarFocal() {
        double result = (hyperFocalDistance * subjectDistance) / (hyperFocalDistance - (subjectDistance - lens.getFocalLength()));
        return result;
    }

    public double CalculatorDof() {
        return farFocal - nearFocal;
    }

    /**
     * @return type {String} unit m
     */
    public double getTransformHyperFocalDistance() {
        return hyperFocalDistance / 1000;
    }

    public double getTransformFarFocal() {
        return farFocal / 1000;
    }

    public double getTransformNearFocal() {
        return nearFocal / 1000;
    }

    public double getTransformDofFocal() {
        return dofFocal / 1000;
    }

    public double getCOC() {
        return COC;
    }

    public void setCOC(double COC) {
        this.COC = COC;
    }
}
