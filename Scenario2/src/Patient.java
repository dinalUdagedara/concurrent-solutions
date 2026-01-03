/**
 * Represents a patient arriving at the A&E department.
 * 
 * Each patient requires a specific speciality consultant.
 * Thread-safe: Immutable after creation (only final fields).
 */
public class Patient {
    private final int id;
    private final long arrivalTime;
    private final Speciality requiredSpeciality;

    public Patient(int id, Speciality requiredSpeciality) {
        this.id = id;
        this.arrivalTime = System.currentTimeMillis();
        this.requiredSpeciality = requiredSpeciality;
    }

    public int getId() {
        return id;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public Speciality getRequiredSpeciality() {
        return requiredSpeciality;
    }

    /**
     * Calculates waiting time in milliseconds.
     */
    public long getWaitingTime() {
        return System.currentTimeMillis() - arrivalTime;
    }

    @Override
    public String toString() {
        return "Patient #" + id + " (Requires: " + requiredSpeciality + ")";
    }
}

