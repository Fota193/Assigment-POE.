package com.mycompany.medicare;

public class Bed {

    private String bedNumber;
    private boolean occupied;
    private String patientID;

    public Bed(String bedNumber) {
        this.bedNumber = bedNumber;
        this.occupied = false;
        this.patientID = null;
    }

    public String getBedNumber() { return bedNumber; }
    public boolean isOccupied() { return occupied; }
    public String getPatientID() { return patientID; }

    public void occupy(String patientID) {
        this.occupied = true;
        this.patientID = patientID;
    }

    public void release() {
        this.occupied = false;
        this.patientID = null;
    }
}
