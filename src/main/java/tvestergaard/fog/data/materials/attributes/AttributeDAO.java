package tvestergaard.fog.data.materials.attributes;

import com.google.common.collect.Multimap;
import tvestergaard.fog.data.DataAccessException;

public interface AttributeDAO
{

    /**
     * Returns the attributes for the provided materials.
     *
     * @param materials The materials to return the attributes of.
     * @return The multimap containing the attributes mapped to their material.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Multimap<Integer, Attribute> get(int... materials) throws DataAccessException;
}
