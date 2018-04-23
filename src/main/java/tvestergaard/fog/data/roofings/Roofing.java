package tvestergaard.fog.data.roofings;

public interface Roofing
{

    /**
     * Returns the unique identifier of the {@link Roofing}.
     *
     * @return The unique identifier of the {@link Roofing}.
     */
    int getId();

    /**
     * Returns the name of the {@link Roofing}.
     *
     * @return The name of the {@link Roofing}.
     */
    String getName();

    /**
     * Returns the description of the {@link Roofing}.
     *
     * @return The description of the {@link Roofing}.
     */
    String getDescription();

    /**
     * Returns the minimum slope the {@link Roofing} must have.
     *
     * @return The minimum slope the {@link Roofing} must have. Returns an integer between 0 and 90 (exclusive).
     */
    int getMinimumSlope();

    /**
     * Returns the maximum slope the {@link Roofing} must have.
     *
     * @return The maximum slope the {@link Roofing} must have. Returns an integer between 0 and 90 (exclusive).
     */
    int getMaximumSlope();

    /**
     * Returns the price of the {@link Roofing} per square meter (in øre).
     *
     * @return The price of the {@link Roofing} per square meter (in øre).
     */
    int getPricePerSquareMeter();
}
