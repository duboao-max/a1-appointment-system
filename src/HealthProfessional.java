import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Collections;

/**
 * Represents a generic health professional in the health service.
 *
 * <p>This is the base class for all specific health professional types
 * (such as general practitioners, specialists, and nurses). It captures the
 * information and behaviour that is common to every health professional,
 * regardless of their specialty.</p>
 *
 * <p>The class is {@code abstract} because it never makes sense to create a
 * "plain" health professional with no specialty. Every concrete type must
 * provide its own details through the {@link #getSpecialty()} method.</p>
 */
public abstract class HealthProfessional {

    /** The unique numeric identifier of the professional. */
    private final String id;

    /** The professional's full name. */
    private final String name;

    /**
     * The days of the week the professional works, stored as an
     * unmodifiable ordered set (for example "Monday", "Wednesday", "Friday").
     */
    private final Set<String> workingDays;

    /**
     * Constructs a health professional with a working schedule.
     *
     * @param id          the unique numeric identifier (must be digits only)
     * @param name        the professional's full name
     * @param workingDays the days the professional works each week
     * @throws IllegalArgumentException if {@code id} is not numeric,
     *         or if any argument is {@code null}/blank
     */
    protected HealthProfessional(String id, String name, Set<String> workingDays) {
        if (id == null || !id.matches("\\d+")) {
            throw new IllegalArgumentException("ID must contain digits only.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be empty.");
        }
        if (workingDays == null || workingDays.isEmpty()) {
            throw new IllegalArgumentException("Working days must not be empty.");
        }

        this.id = id;
        this.name = name;
        // Store an unmodifiable copy so external code cannot change the schedule.
        this.workingDays = Collections.unmodifiableSet(
                new LinkedHashSet<>(workingDays));
    }

    /**
     * Returns the professional's numeric ID.
     *
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the professional's full name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns an unmodifiable view of the days the professional works.
     *
     * @return the working days
     */
    public Set<String> getWorkingDays() {
        return workingDays;
    }

    /**
     * Returns a short description of this professional's specialty.
     * Each concrete type must implement this.
     *
     * @return the specialty description
     */
    public abstract String getSpecialty();

    /**
     * Returns the additional, type-specific detail used when presenting
     * this professional. The default implementation returns the specialty.
     * Subclasses may override it to add more information.
     *
     * @return the type-specific detail string
     */
    public String getDetails() {
        return getSpecialty();
    }

    /**
     * Returns a readable single-line summary of the professional, including
     * shared information and the type-specific details.
     *
     * @return a human-readable summary
     */
    @Override
    public String toString() {
        return String.format(
                "%s (ID %s) - %s | Works: %s",
                name, id, getDetails(), String.join(", ", workingDays));
    }

    /**
     * Compares this professional with another for equality based on the
     * unique numeric ID. Two professionals are equal when they share the
     * same ID, which is what "the same professional" means to the system.
     *
     * @param other the object to compare against
     * @return {@code true} if the IDs match
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        HealthProfessional that = (HealthProfessional) other;
        return id.equals(that.id);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return the hash code based on the ID
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
