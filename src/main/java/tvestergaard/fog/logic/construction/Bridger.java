package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.materials.Material;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Bridges some distance using multiple materials of the same type.
 *
 * @param <T> The category of the materials to build the bridge from.
 */
public class Bridger<T extends Material>
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
     * The notes on the components.
     */
    private final String notes;

    /**
     * Creates a mew {@link Bridger}.
     *
     * @param materials The possible materials there are to construct the bridge.
     * @param function  The function that extracts the distance from the materials the bridge is constructed from.
     * @param notes     The notes on the components.
     * @param end       A function that is called when the bridge is constructed.
     */
    public Bridger(List<T> materials, Function<T, Integer> function, String notes, BiConsumer<List<T>, MutableMaterials> end)
    {
        this.materials = materials;
        Collections.sort(materials, (o1, o2) -> function.apply(o2) - function.apply(o1));
        this.function = function;
        this.notes = notes;
        this.end = end;
    }

    /**
     * Creates a mew {@link Bridger}.
     *
     * @param materials The possible materials there are to construct the bridge.
     * @param function  The function that extracts the distance from the materials the bridge is constructed from.
     * @param notes     The notes on the components.
     */
    public Bridger(List<T> materials, Function<T, Integer> function, String notes)
    {
        this(materials, function, notes, null);
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
        Map<T, Integer> amounts = new HashMap<>();

        if (materials.size() == 0)
            throw new IllegalStateException("Cannot bridge with no materials.");

        List<T> endList         = new ArrayList<>();
        int     distanceCovered = 0;
        while (distanceCovered < targetDistance) {
            T best = findOne(targetDistance - distanceCovered);
            distanceCovered += function.apply(best);
            amounts.put(best, amounts.getOrDefault(best, 0) + 1);
            endList.add(best);
        }

        for (Map.Entry<T, Integer> entry : amounts.entrySet())
            destination.add(entry.getKey(), entry.getValue(), notes);
        if (end != null)
            end.accept(endList, destination);
    }

    /**
     * Finds the largest, best fit for the provided distance.
     *
     * @param distance The distance the best fit must cover.
     * @return The resulting best fit.
     */
    private T findOne(int distance)
    {
        T largest = materials.get(0);

        for (int x = 1; x < materials.size(); x++) {
            T current = materials.get(x);
            if (function.apply(current) < distance)
                return largest;
            largest = current;
        }

        return largest;
    }
}
