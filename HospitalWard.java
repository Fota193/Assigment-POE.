package com.mycompany.medicare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HospitalWard {

    private static final int ROWS = 4;
    private static final int COLS = 5;
    private static final int TOTAL_BEDS = ROWS * COLS;

    private ArrayList<Patient> patients;
    private Bed[] beds;
    private final String wardNumber = "Ward-1";

    public HospitalWard() {
        patients = new ArrayList<>();
        beds = new Bed[TOTAL_BEDS];
        for (int i = 0; i < TOTAL_BEDS; i++) {
            beds[i] = new Bed(String.format("B%02d", i + 1));
        }
    }

    // ---------------- Feature 1: Patient Management ----------------

    public boolean registerPatient(Patient patient) {
        if (findPatientById(patient.getPatientID()) != null) {
            return false; // duplicate Patient ID not allowed
        }
        patients.add(patient);
        return true;
    }

    public Patient findPatientById(String patientID) {
        for (Patient p : patients) {
            if (p.getPatientID().equalsIgnoreCase(patientID)) {
                return p;
            }
        }
        return null;
    }

    public boolean updatePatient(String patientID, String firstName, String lastName,
                                  int age, String gender, String medicalCondition) {
        Patient p = findPatientById(patientID);
        if (p == null) return false;
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setAge(age);
        p.setGender(gender);
        p.setMedicalCondition(medicalCondition);
        return true;
    }

    public boolean deletePatient(String patientID) {
        Patient p = findPatientById(patientID);
        if (p == null) return false;
        if (p instanceof Inpatient) {
            Inpatient ip = (Inpatient) p;
            if (ip.getBedNumber() != null) {
                releaseBed(ip.getBedNumber());
            }
        }
        patients.remove(p);
        return true;
    }

    public ArrayList<Patient> getAllPatients() {
        return patients;
    }

    // ---------------- Feature 2: Bed Management ----------------

    public Bed findBedByNumber(String bedNumber) {
        for (Bed b : beds) {
            if (b.getBedNumber().equalsIgnoreCase(bedNumber)) {
                return b;
            }
        }
        return null;
    }

    public String allocateBed(String patientID, String bedNumber) {
        Patient p = findPatientById(patientID);
        if (p == null) return "Patient not found.";
        if (!(p instanceof Inpatient)) return "Only Inpatients may be allocated a bed.";

        Inpatient ip = (Inpatient) p;
        if (ip.getBedNumber() != null) return "Patient already has a bed allocated: " + ip.getBedNumber();

        Bed bed = findBedByNumber(bedNumber);
        if (bed == null) return "Bed not found.";
        if (bed.isOccupied()) return "Bed " + bedNumber + " is already occupied.";

        bed.occupy(patientID);
        ip.setBedNumber(bedNumber);
        ip.setWardNumber(wardNumber);
        return "Bed " + bedNumber + " allocated to " + ip.getFirstName() + " " + ip.getLastName() + ".";
    }

    public String allocateNextAvailableBed(String patientID) {
        Bed available = null;
        for (Bed b : beds) {
            if (!b.isOccupied()) {
                available = b;
                break;
            }
        }
        if (available == null) return "No beds available.";
        return allocateBed(patientID, available.getBedNumber());
    }

    public String releaseBed(String bedNumber) {
        Bed bed = findBedByNumber(bedNumber);
        if (bed == null) return "Bed not found.";
        if (!bed.isOccupied()) return "Bed " + bedNumber + " is already available.";

        String patientID = bed.getPatientID();
        bed.release();

        Patient p = findPatientById(patientID);
        if (p instanceof Inpatient) {
            ((Inpatient) p).setBedNumber(null);
        }
        return "Bed " + bedNumber + " released.";
    }

    public void displayWardLayout() {
        System.out.println("\n--- Ward Layout (" + wardNumber + ") ---");
        int idx = 0;
        for (int r = 0; r < ROWS; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < COLS; c++) {
                Bed b = beds[idx++];
                sb.append(b.getBedNumber()).append(b.isOccupied() ? "[X] " : "[ ] ");
            }
            System.out.println(sb.toString().trim());
        }
        System.out.println("[X] = Occupied   [ ] = Available");
    }

    public ArrayList<Bed> getAvailableBeds() {
        ArrayList<Bed> list = new ArrayList<>();
        for (Bed b : beds) if (!b.isOccupied()) list.add(b);
        return list;
    }

    public ArrayList<Bed> getOccupiedBeds() {
        ArrayList<Bed> list = new ArrayList<>();
        for (Bed b : beds) if (b.isOccupied()) list.add(b);
        return list;
    }

    // ---------------- Feature 3: Reports ----------------

    public void displayAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        for (Patient p : patients) {
            p.displayDetails();
            System.out.println("-----------------------------");
        }
    }

    public void displayAvailableBeds() {
        ArrayList<Bed> available = getAvailableBeds();
        System.out.println("Available Beds (" + available.size() + "):");
        for (Bed b : available) System.out.println(" - " + b.getBedNumber());
    }

    public void displayOccupiedBeds() {
        ArrayList<Bed> occupied = getOccupiedBeds();
        System.out.println("Occupied Beds (" + occupied.size() + "):");
        for (Bed b : occupied) System.out.println(" - " + b.getBedNumber() + " (Patient: " + b.getPatientID() + ")");
    }

    public int totalRegisteredPatients() {
        return patients.size();
    }

    public int totalOccupiedBeds() {
        return getOccupiedBeds().size();
    }

    public double wardOccupancyPercentage() {
        return (totalOccupiedBeds() / (double) TOTAL_BEDS) * 100;
    }

    // ---------------- Sorting (Feature 5 support) ----------------

    public void sortPatientsBySurname() {
        Collections.sort(patients, Comparator.comparing(Patient::getLastName, String.CASE_INSENSITIVE_ORDER));
    }

    public void sortPatientsById() {
        Collections.sort(patients, Comparator.comparing(Patient::getPatientID, String.CASE_INSENSITIVE_ORDER));
    }
}
