package com.mycompany.medicare;

public enum PatientCategory {
    INPATIENT,
    OUTPATIENT,
    EMERGENCY;

    public boolean requiresBed() {
        return this == INPATIENT;
    }
}
