import java.util.Set;

/**
 * Represents a general practitioner (GP), a doctor who provides primary
 * healthcare and can prescribe medication.
 */
public class GeneralPractitioner extends HealthProfessional {

    /** Whether this GP can currently prescribe medication. */
    private final boolean canPrescribe;

    /**
     * Constructs a general practitioner who can prescribe medication.
     *
     * @param id          the numeric ID
     * @param name        the full name
     * @param workingDays the days the GP works
     */
    public GeneralPractitioner(String id, String name, Set<String> workingDays) {
        this(id, name, workingDays, true);
    }

    /**
     * Constructs a general practitioner with an explicit prescribing status.
     *
     * @param id           the numeric ID
     * @param name         the full name
     * @param workingDays  the days the GP works
     * @param canPrescribe whether the GP can prescribe medication
     */
    public GeneralPractitioner(String id, String name, Set<String> workingDays,
                               boolean canPrescribe) {
        super(id, name, workingDays);
        this.canPrescribe = canPrescribe;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpecialty() {
        return "General Practitioner";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDetails() {
        return getSpecialty() + (canPrescribe
                ? " (can prescribe medication)"
                : " (cannot prescribe medication)");
    }
}
