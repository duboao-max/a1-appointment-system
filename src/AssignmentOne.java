import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * The entry point for Assessment 1. This class demonstrates the complete
 * appointment system end-to-end:
 *
 * <ul>
 *   <li>Part 3: creating and displaying several health professionals of
 *       different types, processed together through the common base type.</li>
 *   <li>Part 6: demonstrating the appointment system, including normal and
 *       error cases for adding, viewing, filtering, sorting, and cancelling
 *       appointments.</li>
 * </ul>
 */
public class AssignmentOne {

    /**
     * Creates the health professionals and runs the appointment demonstration.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // ---- Part 3: creating and working with health professionals ----
        List<HealthProfessional> professionals = createProfessionals();

        System.out.println("=== HEALTH PROFESSIONALS ===");
        for (HealthProfessional professional : professionals) {
            System.out.println(professional);
        }
        System.out.println();

        // ---- Part 6: demonstrating the appointment system ----
        AppointmentManager manager = new AppointmentManager();

        Patient alice = new Patient("Alice Smith", "0412345678");
        Patient bob = new Patient("Bob Jones", "0423456789");
        Patient carol = new Patient("Carol Lee", "0434567890");

        GeneralPractitioner gp = findType(professionals, GeneralPractitioner.class, 0);
        GeneralPractitioner gp2 = findType(professionals, GeneralPractitioner.class, 1);
        Pediatrician paed = findType(professionals, Pediatrician.class, 0);
        Nurse nurse = findType(professionals, Nurse.class, 0);
        Dietitian dietitian = findType(professionals, Dietitian.class, 0);

        System.out.println("=== ADDING APPOINTMENTS ===");
        System.out.println(manager.addAppointment(alice, gp, 8 * 60 + 0));        // 08:00
        System.out.println(manager.addAppointment(bob, gp, 8 * 60 + 30));         // 08:30
        System.out.println(manager.addAppointment(carol, gp2, 8 * 60 + 0));       // 08:00 (different GP)
        System.out.println(manager.addAppointment(alice, paed, 9 * 60 + 0));      // 09:00
        System.out.println(manager.addAppointment(bob, nurse, 9 * 60 + 30));      // 09:30
        System.out.println(manager.addAppointment(carol, dietitian, 10 * 60 + 0)); // 10:00
        System.out.println();

        // Error case: double-booking the same professional at the same time.
        System.out.println("=== DOUBLE-BOOKING ATTEMPT ===");
        System.out.println(manager.addAppointment(carol, gp, 8 * 60 + 0));        // 08:00 again
        System.out.println();

        // Error case: an invalid time slot.
        System.out.println("=== INVALID TIME ATTEMPT ===");
        System.out.println(manager.addAppointment(carol, gp, 8 * 60 + 15));       // 08:15
        System.out.println();

        // Display all appointments, sorted by time.
        System.out.println("=== ALL APPOINTMENTS (sorted by time) ===");
        printAppointments(manager.getAppointments());
        System.out.println();

        // Filter by professional ID.
        System.out.println("=== FILTERED BY PROFESSIONAL (GP, ID 1001) ===");
        printAppointments(manager.filterByProfessional("1001"));
        System.out.println();

        // Filter by patient phone number.
        System.out.println("=== FILTERED BY PATIENT PHONE (0412345678) ===");
        printAppointments(manager.filterByPatientPhone("0412345678"));
        System.out.println();

        // Cancel by professional and time.
        System.out.println("=== CANCELLING APPOINTMENT ===");
        System.out.println(manager.cancelByProfessional("1001", 8 * 60 + 0));
        System.out.println();

        // Error case: cancelling an appointment that does not exist.
        System.out.println("=== CANCELLING NON-EXISTENT APPOINTMENT ===");
        System.out.println(manager.cancelByProfessional("1001", 15 * 60 + 30));
        System.out.println();

        // Cancel by patient phone number.
        System.out.println("=== CANCELLING BY PATIENT PHONE ===");
        System.out.println(manager.cancelByPatientPhone("0423456789"));
        System.out.println();

        // Error case: cancelling a patient with no appointments.
        System.out.println("=== CANCELLING UNKNOWN PATIENT ===");
        System.out.println(manager.cancelByPatientPhone("0499999999"));
        System.out.println();

        // Display the updated collection.
        System.out.println("=== UPDATED APPOINTMENTS ===");
        printAppointments(manager.getAppointments());
    }

    /**
     * Creates a realistic mix of health professionals of the different types.
     *
     * @return a list of health professionals
     */
    private static List<HealthProfessional> createProfessionals() {
        List<HealthProfessional> professionals =
                new java.util.ArrayList<>();

        professionals.add(new GeneralPractitioner("1001", "Dr. Amy Chen",
                days("Monday", "Wednesday", "Friday")));
        professionals.add(new GeneralPractitioner("1002", "Dr. David Lee",
                days("Tuesday", "Thursday")));
        professionals.add(new GeneralPractitioner("1003", "Dr. Sarah Patel",
                days("Monday", "Tuesday", "Friday"), false));
        professionals.add(new Pediatrician("2001", "Dr. Emma Wilson",
                days("Monday", "Thursday"), 0, 17));
        professionals.add(new Pediatrician("2002", "Dr. Mark Taylor",
                days("Wednesday", "Friday"), 0, 12));
        professionals.add(new Nurse("3001", "Nurse Jane Brown",
                days("Monday", "Wednesday", "Friday"), "Registered"));
        professionals.add(new Nurse("3002", "Nurse Tom Green",
                days("Tuesday", "Thursday"), "Enrolled"));
        professionals.add(new Dietitian("4001", "Dietitian Lisa White",
                days("Monday", "Wednesday"), "Sports nutrition"));
        professionals.add(new Dietitian("4002", "Dietitian Peter Black",
                days("Thursday", "Friday"), "Paediatric nutrition"));

        return professionals;
    }

    /**
     * Finds the nth professional of a given concrete type within the list.
     *
     * @param <T>           the concrete professional type
     * @param professionals the full list of professionals
     * @param type          the class of the type to find
     * @param index         the zero-based occurrence to return
     * @return the matching professional
     */
    private static <T extends HealthProfessional> T findType(
            List<HealthProfessional> professionals, Class<T> type, int index) {
        int found = 0;
        for (HealthProfessional professional : professionals) {
            if (type.isInstance(professional)) {
                if (found == index) {
                    return type.cast(professional);
                }
                found++;
            }
        }
        throw new IllegalArgumentException("Not enough " + type.getSimpleName()
                + " professionals in the list.");
    }

    /**
     * Builds an ordered set of working days from the given values.
     *
     * @param days the days to include
     * @return an ordered set of the given days
     */
    private static Set<String> days(String... days) {
        Set<String> set = new LinkedHashSet<>();
        for (String day : days) {
            set.add(day);
        }
        return set;
    }

    /**
     * Prints a list of appointments, one per line, or a note when empty.
     *
     * @param appointments the appointments to print
     */
    private static void printAppointments(List<Appointment> appointments) {
        if (appointments.isEmpty()) {
            System.out.println("  (no appointments)");
            return;
        }
        for (Appointment appointment : appointments) {
            System.out.println("  " + appointment);
        }
    }
}
