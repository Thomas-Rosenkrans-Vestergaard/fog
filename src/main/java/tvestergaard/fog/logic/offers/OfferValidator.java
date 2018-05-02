package tvestergaard.fog.logic.offers;

import java.util.HashSet;
import java.util.Set;

public class OfferValidator
{

    public Set<OfferError> validate(int price)
    {
        Set<OfferError> errors = new HashSet<>();

        if (price < 0)
            errors.add(OfferError.NEGATIVE_PRICE);

        return errors;
    }
}
