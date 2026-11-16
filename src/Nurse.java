import java.util.Set;

/**
 * Represents a nurse. Unlike doctors, a nurse does not prescribe medication
 * but can take blood samples and is recorded with a registration level.
 */
public class Nurse extends HealthProfessional {

    /** The nurse's registration level (for example "Registered" or "Enrolled"). */
    private final String registrationLevel;

    /**
     * Constructs a nurse.
     *
     * @param id                the numeric ID
     * @param name              the full name
     * @param workingDays       the days the nurse works
     * @param registrationLevel the nurse's registration level
     */
    public Nurse(String id, String name, Set<String> workingDays,
                 String registrationLevel) {
        super(id, name, workingDays);
        if (registrationLevel == null || registrationLevel.isBlank()) {
            throw new IllegalArgumentException("Registration level must not be empty.");
        }
        this.registrationLevel = registrationLevel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpecialty() {
        return "Nurse";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDetails() {
        return getSpecialty() + " (" + registrationLevel
                + ", can take blood samples)";
    }
}
