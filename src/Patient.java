/**
 * Represents a patient who books appointments with the health service.
 * A patient is identified by their name and mobile phone number.
 */
public class Patient {

    /** The patient's full name. */
    private final String name;

    /** The patient's mobile phone number. */
    private final String phoneNumber;

    /**
     * Constructs a patient.
     *
     * @param name        the patient's full name
     * @param phoneNumber the patient's mobile phone number
     * @throws IllegalArgumentException if either argument is {@code null}/blank
     */
    public Patient(String name, String phoneNumber) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Patient name must not be empty.");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Patient phone number must not be empty.");
        }
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the patient's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the patient's phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns a readable summary of the patient.
     *
     * @return a human-readable summary
     */
    @Override
    public String toString() {
        return name + " (" + phoneNumber + ")";
    }
}
