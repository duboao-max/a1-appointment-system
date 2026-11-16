import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Manages the collection of appointments for the health service.
 *
 * <p>This class provides operations to add, view, filter, sort, and cancel
 * appointments. It also enforces the rule that a health professional must
 * never be double-booked in the same time slot.</p>
 *
 * <p>The clinic runs on a fixed daily schedule of time slots from 08:00 to
 * 16:00 in 30-minute intervals. The schedule is expressed in terms of an
 * opening time, a closing time, and an interval, so it can be changed in one
 * place without touching the rest of the booking logic.</p>
 */
public class AppointmentManager {

    /** The clinic opening time in minutes since midnight (08:00). */
    public static final int OPENING_TIME = 8 * 60;

    /** The clinic closing time in minutes since midnight (16:00). */
    public static final int CLOSING_TIME = 16 * 60;

    /** The interval between appointment slots in minutes. */
    public static final int SLOT_INTERVAL = 30;

    /** The collection of appointments currently managed by this manager. */
    private final List<Appointment> appointments;

    /**
     * Constructs an empty appointment manager.
     */
    public AppointmentManager() {
        appointments = new ArrayList<>();
    }

    /**
     * Returns whether the given time falls on a valid clinic time slot.
     *
     * @param timeInMinutes the time to check, in minutes since midnight
     * @return {@code true} if the time is a valid slot within opening hours
     */
    private boolean isValidSlot(int timeInMinutes) {
        if (timeInMinutes < OPENING_TIME || timeInMinutes >= CLOSING_TIME) {
            return false;
        }
        return (timeInMinutes - OPENING_TIME) % SLOT_INTERVAL == 0;
    }

    /**
     * Adds a new appointment, rejecting any booking that would double-book a
     * health professional or fall outside the clinic's time slots.
     *
     * @param patient       the patient (must not be {@code null})
     * @param professional  the health professional (must not be {@code null})
     * @param timeInMinutes the appointment time in minutes since midnight
     * @return a success or warning message describing the outcome
     */
    public String addAppointment(Patient patient, HealthProfessional professional,
                                 int timeInMinutes) {
        if (patient == null || professional == null) {
            return "Warning: appointment requires both a patient and a professional.";
        }

        if (!isValidSlot(timeInMinutes)) {
            return "Warning: time must be a valid slot between 08:00 and 16:00 "
                    + "in 30-minute intervals.";
        }

        for (Appointment existing : appointments) {
            if (existing.getProfessional().equals(professional)
                    && existing.getTimeInMinutes() == timeInMinutes) {
                return "Warning: " + professional.getName()
                        + " is already booked at "
                        + existing.getTime() + ".";
            }
        }

        appointments.add(new Appointment(patient, professional, timeInMinutes));
        return "Appointment added: " + patient.getName() + " with "
                + professional.getName() + " at " + new Appointment(
                        patient, professional, timeInMinutes).getTime() + ".";
    }

    /**
     * Returns all appointments sorted from earliest to latest.
     *
     * @return a sorted list of all appointments
     */
    public List<Appointment> getAppointments() {
        List<Appointment> sorted = new ArrayList<>(appointments);
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Returns the appointments for a specific health professional, identified
     * by ID, sorted by time.
     *
     * @param professionalId the numeric ID of the professional
     * @return a sorted list of matching appointments
     */
    public List<Appointment> filterByProfessional(String professionalId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getProfessional().getId().equals(professionalId)) {
                result.add(appointment);
            }
        }
        Collections.sort(result);
        return result;
    }

    /**
     * Returns the appointments for a specific patient, identified by phone
     * number, sorted by time.
     *
     * @param phoneNumber the patient's phone number
     * @return a sorted list of matching appointments
     */
    public List<Appointment> filterByPatientPhone(String phoneNumber) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().getPhoneNumber().equals(phoneNumber)) {
                result.add(appointment);
            }
        }
        Collections.sort(result);
        return result;
    }

    /**
     * Cancels the appointment for a given professional at a given time.
     *
     * @param professionalId the numeric ID of the professional
     * @param timeInMinutes  the appointment time in minutes since midnight
     * @return a success or warning message describing the outcome
     */
    public String cancelByProfessional(String professionalId, int timeInMinutes) {
        Appointment target = null;
        for (Appointment appointment : appointments) {
            if (appointment.getProfessional().getId().equals(professionalId)
                    && appointment.getTimeInMinutes() == timeInMinutes) {
                target = appointment;
                break;
            }
        }
        if (target == null) {
            return "Warning: no appointment found for professional ID "
                    + professionalId + " at time " + minutesToTime(timeInMinutes) + ".";
        }
        appointments.remove(target);
        return "Appointment cancelled for " + target.getProfessional().getName()
                + " at " + target.getTime() + ".";
    }

    /**
     * Cancels the appointments for a given patient, identified by phone number.
     *
     * @param phoneNumber the patient's phone number
     * @return a success or warning message describing the outcome
     */
    public String cancelByPatientPhone(String phoneNumber) {
        int removed = 0;
        for (int i = appointments.size() - 1; i >= 0; i--) {
            if (appointments.get(i).getPatient().getPhoneNumber().equals(phoneNumber)) {
                appointments.remove(i);
                removed++;
            }
        }
        if (removed == 0) {
            return "Warning: no appointments found for patient phone number "
                    + phoneNumber + ".";
        }
        return "Cancelled " + removed + " appointment(s) for patient phone number "
                + phoneNumber + ".";
    }

    /**
     * Formats a time in minutes since midnight as {@code HH:mm}.
     *
     * @param timeInMinutes the time in minutes
     * @return the formatted time
     */
    private String minutesToTime(int timeInMinutes) {
        return String.format("%02d:%02d", timeInMinutes / 60, timeInMinutes % 60);
    }
}
