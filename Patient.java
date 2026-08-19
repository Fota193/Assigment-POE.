package com.mycompany.medicare;

public class Patient {

    protected String patientID;
    protected String firstName;
    protected String lastName;
    protected int age;
    protected String gender;
    protected String medicalCondition;
    protected PatientCategory category;

    public Patient(String patientID, String firstName, String lastName, int age,
                   String gender, String medicalCondition, PatientCategory category) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    public String getPatientID() { return patientID; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getMedicalCondition() { return medicalCondition; }
    public void setMedicalCondition(String medicalCondition) { this.medicalCondition = medicalCondition; }

    public PatientCategory getCategory() { return category; }
    public void setCategory(PatientCategory category) { this.category = category; }

    public void displayDetails() {
        System.out.println("Patient ID     : " + patientID);
        System.out.println("Name           : " + firstName + " " + lastName);
        System.out.println("Age            : " + age);
        System.out.println("Gender         : " + gender);
        System.out.println("Condition      : " + medicalCondition);
        System.out.println("Category       : " + category);
    }
}
