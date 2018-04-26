package tvestergaard.fog.logic;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.roofing.MysqlRoofingDAO;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.roofing.RoofingColumn;
import tvestergaard.fog.data.roofing.RoofingDAO;
import tvestergaard.fog.logic.ApplicationException;

import java.util.List;

public class RoofingFacade
{

    private final RoofingDAO dao;

    public RoofingFacade(RoofingDAO dao)
    {
        this.dao = dao;
    }

    public RoofingFacade()
    {
        this(new MysqlRoofingDAO(ProductionDataSource.getSource()));
    }

    /**
     * Returns the roofings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The complete list of the roofings in the data storage.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Roofing> get(Constraint<RoofingColumn>... constraints)
    {
        try {
            return dao.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
