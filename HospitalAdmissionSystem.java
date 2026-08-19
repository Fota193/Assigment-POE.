package com.mycompany.medicare;

import java.util.Scanner;

public class HospitalAdmissionSystem {

    private static HospitalWard ward = new HospitalWard();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: patientManagementMenu(); break;
                case 2: bedManagementMenu(); break;
                case 3: reportsMenu(); break;
                case 0: running = false; System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice.");
            }
        }
        sc.close();
    }

    private static void printMainMenu() {
        System.out.println("\n===== MediCare Hospital Admission System =====");
        System.out.println("1. Patient Management");
        System.out.println("2. Bed Management");
        System.out.println("3. Reports");
        System.out.println("0. Exit");
    }

    // ---------------- Patient Management ----------------

    private static void patientManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Patient Management ---");
            System.out.println("1. Register new patient");
            System.out.println("2. Search patient by ID");
            System.out.println("3. Update patient details");
            System.out.println("4. Delete patient");
            System.out.println("5. Sort patients by surname");
            System.out.println("6. Sort patients by Patient ID");
            System.out.println("0. Back");
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: registerPatient(); break;
                case 2: searchPatient(); break;
                case 3: updatePatient(); break;
                case 4: deletePatient(); break;
                case 5: ward.sortPatientsBySurname(); ward.displayAllPatients(); break;
                case 6: ward.sortPatientsById(); ward.displayAllPatients(); break;
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void registerPatient() {
        System.out.print("Patient ID: ");
        String id = sc.nextLine();
        System.out.print("First Name: ");
        String first = sc.nextLine();
        System.out.print("Last Name: ");
        String last = sc.nextLine();
        int age = readInt("Age: ");
        System.out.print("Gender: ");
        String gender = sc.nextLine();
        System.out.print("Medical Condition: ");
        String condition = sc.nextLine();

        PatientCategory category = readCategory();

        Patient patient;
        if (category == PatientCategory.INPATIENT) {
            patient = new Inpatient(id, first, last, age, gender, condition, "Ward-1", null);
        } else {
            patient = new Patient(id, first, last, age, gender, condition, category);
        }

        boolean success = ward.registerPatient(patient);
        System.out.println(success ? "Patient registered successfully." : "Patient ID already exists.");
    }

    private static PatientCategory readCategory() {
        System.out.println("Category: 1) Inpatient  2) Outpatient  3) Emergency");
        int c = readInt("Choose: ");
        switch (c) {
            case 1: return PatientCategory.INPATIENT;
            case 2: return PatientCategory.OUTPATIENT;
            case 3: return PatientCategory.EMERGENCY;
            default:
                System.out.println("Invalid, defaulting to Outpatient.");
                return PatientCategory.OUTPATIENT;
        }
    }

    private static void searchPatient() {
        System.out.print("Enter Patient ID: ");
        String id = sc.nextLine();
        Patient p = ward.findPatientById(id);
        if (p == null) System.out.println("Patient not found.");
        else p.displayDetails();
    }

    private static void updatePatient() {
        System.out.print("Enter Patient ID to update: ");
        String id = sc.nextLine();
        Patient existing = ward.findPatientById(id);
        if (existing == null) { System.out.println("Patient not found."); return; }

        System.out.print("New First Name (" + existing.getFirstName() + "): ");
        String first = sc.nextLine();
        System.out.print("New Last Name (" + existing.getLastName() + "): ");
        String last = sc.nextLine();
        int age = readInt("New Age (" + existing.getAge() + "): ");
        System.out.print("New Gender (" + existing.getGender() + "): ");
        String gender = sc.nextLine();
        System.out.print("New Medical Condition (" + existing.getMedicalCondition() + "): ");
        String condition = sc.nextLine();

        boolean success = ward.updatePatient(id, first, last, age, gender, condition);
        System.out.println(success ? "Patient updated." : "Update failed.");
    }

    private static void deletePatient() {
        System.out.print("Enter Patient ID to delete: ");
        String id = sc.nextLine();
        boolean success = ward.deletePatient(id);
        System.out.println(success ? "Patient deleted." : "Patient not found.");
    }

    // ---------------- Bed Management ----------------

    private static void bedManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Bed Management ---");
            System.out.println("1. Allocate bed to inpatient");
            System.out.println("2. Release bed");
            System.out.println("3. Display ward layout");
            System.out.println("4. Display available beds");
            System.out.println("5. Display occupied beds");
            System.out.println("0. Back");
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: allocateBed(); break;
                case 2: releaseBed(); break;
                case 3: ward.displayWardLayout(); break;
                case 4: ward.displayAvailableBeds(); break;
                case 5: ward.displayOccupiedBeds(); break;
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void allocateBed() {
        System.out.print("Enter Patient ID: ");
        String pid = sc.nextLine();
        System.out.print("Enter Bed Number (e.g. B01) or leave blank for next available: ");
        String bed = sc.nextLine();
        String result = bed.isEmpty() ? ward.allocateNextAvailableBed(pid) : ward.allocateBed(pid, bed);
        System.out.println(result);
    }

    private static void releaseBed() {
        System.out.print("Enter Bed Number: ");
        String bed = sc.nextLine();
        System.out.println(ward.releaseBed(bed));
    }

    // ---------------- Reports ----------------

    private static void reportsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Reports ---");
            System.out.println("1. All registered patients");
            System.out.println("2. All available beds");
            System.out.println("3. All occupied beds");
            System.out.println("4. Total registered patients");
            System.out.println("5. Total occupied beds");
            System.out.println("6. Ward occupancy percentage");
            System.out.println("0. Back");
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: ward.displayAllPatients(); break;
                case 2: ward.displayAvailableBeds(); break;
                case 3: ward.displayOccupiedBeds(); break;
                case 4: System.out.println("Total registered patients: " + ward.totalRegisteredPatients()); break;
                case 5: System.out.println("Total occupied beds: " + ward.totalOccupiedBeds()); break;
                case 6: System.out.printf("Ward occupancy: %.2f%%%n", ward.wardOccupancyPercentage()); break;
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // ---------------- Utility ----------------

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a number: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine(); // consume leftover newline
        return val;
    }
}
