import java.util.Set;

/**
 * Represents a dietitian, a health professional who advises on nutrition.
 * A dietitian records an area of specialisation.
 */
public class Dietitian extends HealthProfessional {

    /** The dietitian's area of specialisation (for example "Sports nutrition"). */
    private final String specialisation;

    /**
     * Constructs a dietitian.
     *
     * @param id             the numeric ID
     * @param name           the full name
     * @param workingDays    the days the dietitian works
     * @param specialisation the dietitian's specialisation area
     */
    public Dietitian(String id, String name, Set<String> workingDays,
                     String specialisation) {
        super(id, name, workingDays);
        if (specialisation == null || specialisation.isBlank()) {
            throw new IllegalArgumentException("Specialisation must not be empty.");
        }
        this.specialisation = specialisation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpecialty() {
        return "Dietitian";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDetails() {
        return getSpecialty() + " (specialises in " + specialisation + ")";
    }
}
