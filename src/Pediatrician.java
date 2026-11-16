import java.util.Set;

/**
 * Represents a paediatrician, a specialist doctor who cares for children.
 * A paediatrician records the age range of patients they are qualified to
 * treat.
 */
public class Pediatrician extends HealthProfessional {

    /** The minimum patient age this paediatrician treats. */
    private final int minAge;

    /** The maximum patient age this paediatrician treats. */
    private final int maxAge;

    /**
     * Constructs a paediatrician.
     *
     * @param id          the numeric ID
     * @param name        the full name
     * @param workingDays the days the paediatrician works
     * @param minAge      the minimum patient age treated
     * @param maxAge      the maximum patient age treated
     */
    public Pediatrician(String id, String name, Set<String> workingDays,
                        int minAge, int maxAge) {
        super(id, name, workingDays);
        if (minAge < 0 || maxAge < minAge) {
            throw new IllegalArgumentException("Invalid patient age range.");
        }
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpecialty() {
        return "Paediatrician";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDetails() {
        return getSpecialty() + " (treats ages " + minAge + "-" + maxAge + ")";
    }
}
