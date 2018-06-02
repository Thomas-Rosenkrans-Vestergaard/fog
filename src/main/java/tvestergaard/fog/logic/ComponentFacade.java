package tvestergaard.fog.logic;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.components.ComponentDAO;
import tvestergaard.fog.data.components.ComponentDefinition;

import java.util.List;

public class ComponentFacade
{

    /**
     * The data access object to use.
     */
    private final ComponentDAO componentDAO;

    /**
     * Creates a new {@link ComponentFacade}.
     *
     * @param componentDAO The data access object to use.
     */
    public ComponentFacade(ComponentDAO componentDAO)
    {
        this.componentDAO = componentDAO;
    }

    /**
     * Updates some component definitions.
     *
     * @param definitions The definitions to update.
     * @return {@code true} if the component definitions was successfully updated.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public boolean update(List<ComponentDefinition> definitions)
    {
        try {
            return componentDAO.update(definitions);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
