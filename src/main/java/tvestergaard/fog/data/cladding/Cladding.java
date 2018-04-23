package tvestergaard.fog.data.cladding;

public interface Cladding
{

    /**
     * Returns the unique identifier of the {@link Cladding}.
     *
     * @return The unique identifier of the {@link Cladding}.
     */
    int getId();

    /**
     * Returns the name of the {@link Cladding}.
     *
     * @return The name of the {@link Cladding}.
     */
    String getName();

    /**
     * Returns the description of the {@link Cladding}.
     *
     * @return The description of the {@link Cladding}.
     */
    String getDescription();

    /**
     * Returns the price of the {@link Cladding} per square meter (in øre).
     *
     * @return the price of the {@link Cladding} per square meter (in øre).
     */
    int getPricePerSquareMeter();

    abstract boolean equals(Object other);
}
