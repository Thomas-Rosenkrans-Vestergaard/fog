package tvestergaard.fog.logic;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.cladding.CladdingColumn;
import tvestergaard.fog.data.cladding.CladdingDAO;
import tvestergaard.fog.data.cladding.MysqlCladdingDAO;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public class CladdingFacade
{

    private final CladdingDAO dao;

    public CladdingFacade(CladdingDAO dao)
    {
        this.dao = dao;
    }

    public CladdingFacade()
    {
        this(new MysqlCladdingDAO(ProductionDataSource.getSource()));
    }

    /**
     * Returns the claddings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The complete list of the claddings in the data storage.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Cladding> get(Constraint<CladdingColumn>... constraints)
    {
        try {
            return dao.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
