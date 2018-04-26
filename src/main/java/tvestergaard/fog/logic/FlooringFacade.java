package tvestergaard.fog.logic;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.flooring.Flooring;
import tvestergaard.fog.data.flooring.FlooringColumn;
import tvestergaard.fog.data.flooring.FlooringDAO;
import tvestergaard.fog.data.flooring.MysqlFlooringDAO;

import java.util.List;

public class FlooringFacade
{

    private final FlooringDAO dao;

    public FlooringFacade(FlooringDAO dao)
    {
        this.dao = dao;
    }

    public FlooringFacade()
    {
        this(new MysqlFlooringDAO(ProductionDataSource.getSource()));
    }

    /**
     * Returns the floorings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The complete list of the floorings in the data storage.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Flooring> get(Constraint<FlooringColumn>... constraints)
    {
        try {
            return dao.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
