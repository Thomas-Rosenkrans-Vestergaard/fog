package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.materials.categories.CategoryMaterial;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Bridges some distance using multiple materials of the same type.
 *
 * @param <T> The category of the materials to build the bridge from.
 */
public class Bridger<T extends CategoryMaterial>
{

    /**
     * The possible materials there are to construct the bridge.
     */
    private final List<T> materials;

    /**
     * The function that extracts the distance from the materials the bridge is constructed from.
     */
    private final Function<T, Integer> function;

    /**
     * A function that is called when the bridge is constructed.
     */
    private final BiConsumer<List<T>, MutableMaterials> end;

    /**
     * Creates a mew {@link Bridger}.
     *
     * @param materials The possible materials there are to construct the bridge.
     * @param function  The function that extracts the distance from the materials the bridge is constructed from.
     * @param end       A function that is called when the bridge is constructed.
     */
    public Bridger(List<T> materials, Function<T, Integer> function, BiConsumer<List<T>, MutableMaterials> end)
    {
        this.materials = materials;
        Collections.sort(materials, Comparator.comparingInt(function::apply));
        this.function = function;
        this.end = end;
    }

    /**
     * Creates a mew {@link Bridger}.
     *
     * @param materials The possible materials there are to construct the bridge.
     * @param function  The function that extracts the distance from the materials the bridge is constructed from.
     */
    public Bridger(List<T> materials, Function<T, Integer> function)
    {
        this(materials, function, null);
    }

    /**
     * Constructs the bridge for the provided distance, adding the required materials to the provided
     * {@link MutableMaterials} instance.
     *
     * @param targetDistance The distance the bridge must cover.
     * @param destination    The instance to add the required materials to.
     */
    public void bridge(int targetDistance, MutableMaterials destination)
    {
        if (materials.size() == 0)
            throw new IllegalStateException("Cannot bridge with no materials.");

        int distanceCovered = 0;
        while (distanceCovered < targetDistance) {
            T best = largest(targetDistance - distanceCovered);
            distanceCovered += function.apply(best);
        }
    }

    private T largest(int distance)
    {
        T largest = materials.get(0);

        return largest;
    }
}
