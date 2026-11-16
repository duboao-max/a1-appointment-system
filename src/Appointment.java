/**
 * Represents a single appointment linking a patient to a chosen health
 * professional at a specific time of the current day.
 *
 * <p>An appointment is {@link Comparable} so that a collection of
 * appointments can be sorted from earliest to latest by time.</p>
 *
 * <p>The appointment time is stored as minutes since midnight. This keeps the
 * value as a single integer that orders correctly as a natural number,
 * avoiding the pitfalls of storing times as strings.</p>
 */
public class Appointment implements Comparable<Appointment> {

    /** The patient booking the appointment. */
    private final Patient patient;

    /** The health professional the patient will see. */
    private final HealthProfessional professional;

    /**
     * The appointment time in minutes since midnight.
     * For example 08:30 is stored as 510.
     */
    private final int timeInMinutes;

    /**
     * Constructs an appointment.
     *
     * @param patient          the patient (must not be {@code null})
     * @param professional     the health professional (must not be {@code null})
     * @param timeInMinutes    the time in minutes since midnight (0-1439)
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Appointment(Patient patient, HealthProfessional professional,
                       int timeInMinutes) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient must not be null.");
        }
        if (professional == null) {
            throw new IllegalArgumentException("Professional must not be null.");
        }
        if (timeInMinutes < 0 || timeInMinutes > 23 * 60 + 59) {
            throw new IllegalArgumentException("Time must be within a single day.");
        }
        this.patient = patient;
        this.professional = professional;
        this.timeInMinutes = timeInMinutes;
    }

    /**
     * Returns the patient.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the health professional.
     *
     * @return the health professional
     */
    public HealthProfessional getProfessional() {
        return professional;
    }

    /**
     * Returns the appointment time in minutes since midnight.
     *
     * @return the time in minutes
     */
    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    /**
     * Returns the appointment time formatted as {@code HH:mm}.
     *
     * @return the formatted time
     */
    public String getTime() {
        int hours = timeInMinutes / 60;
        int minutes = timeInMinutes % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    /**
     * Compares this appointment with another by time, so appointments can be
     * sorted from earliest to latest.
     *
     * @param other the appointment to compare against
     * @return a negative, zero, or positive value
     */
    @Override
    public int compareTo(Appointment other) {
        return Integer.compare(this.timeInMinutes, other.timeInMinutes);
    }

    /**
     * Returns a readable summary of the appointment, including the patient and
     * the chosen health professional's details.
     *
     * @return a human-readable summary
     */
    @Override
    public String toString() {
        return String.format("%s | Patient: %s | Professional: %s",
                getTime(), patient, professional);
    }
}
