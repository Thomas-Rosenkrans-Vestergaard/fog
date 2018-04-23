package tvestergaard.fog.data.flooring;

public interface Flooring
{

    /**
     * Returns the unique identifier of the {@link Flooring}.
     *
     * @return The unique identifier of the {@link Flooring}.
     */
    int getId();

    /**
     * Returns the name of the {@link Flooring}.
     *
     * @return The name of the {@link Flooring}.
     */
    String getName();

    /**
     * Returns the description of the {@link Flooring}.
     *
     * @return The description of the {@link Flooring}.
     */
    String getDescription();

    /**
     * Returns the price of the {@link Flooring} per square meter (in øre).
     *
     * @return the price of the {@link Flooring} per square meter (in øre).
     */
    int getPricePerSquareMeter();

    abstract boolean equals(Object other);
}
