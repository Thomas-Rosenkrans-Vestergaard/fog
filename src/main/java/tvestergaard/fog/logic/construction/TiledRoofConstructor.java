package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.Component;

public class TiledRoofConstructor implements RoofConstructor
{
//
//    private static final String TILE                   = "TILE";
//    private static final String RIDGE_TILE             = "RIDGE_TILE";
//    private static final String RIDGE_TILE_BRACKET     = "RIDGE_TILE_BRACKET";
//    private static final String TOP_HOLDER             = "TOP_HOLDER";
//    private static final String TILE_BINDERS_AND_HOOKS = "TILE_BINDERS_AND_HOOKS";
//
//    /**
//     * Constructs the roof using the provided components while updating the provided summary.
//     *
//     * @param summary                   The summary being updated with the materials needed to construct the roof.
//     * @param constructionSpecification The constructionSpecification being constructed.
//     * @param components                The components to use while constructing the roof.
//     */
//    @Override public void construct(MaterialsBill summary, ConstructionSpecification constructionSpecification, RoofingComponentCollection components)
//    {
//        int length    = constructionSpecification.getLength();
//        int width     = constructionSpecification.getWidth();
//        int widthHalf = width / 2;
//
//        int roofHeight = up(Math.tan(Math.toRadians(constructionSpecification.getRoofingSlope())) * widthHalf);
//        int hypotenuse = up(Math.sqrt((widthHalf * widthHalf) + (roofHeight * roofHeight)));
//
//        Component tile = components.get(TILE);
//
//        int tileRows      = up(hypotenuse / cm(tile.getInt("HEIGHT_MM"))) * 2;
//        int tileColumns   = up(length / cm(tile.getInt("WIDTH_MM")));
//        int numberOfTiles = tileRows * tileColumns;
//        summary.add(new MaterialsBillLineImpl(tile.getMaterial(), numberOfTiles, tile.getNotes()));
//
//        calculateRidgeTiles(summary, constructionSpecification, components);
//        calculateTopLatheHolder(summary, constructionSpecification, components);
//        calculateBindersHooks(summary, components, tileRows, tileColumns);
//    }
//
//    private int up(double v)
//    {
//        return (int) Math.ceil(v);
//    }
//
//    private double cm(int mm)
//    {
//        return (double) mm / 10;
//    }
//
//    private void calculateRidgeTiles(MaterialsBill summary, ConstructionSpecification constructionSpecification, RoofingComponentCollection components)
//    {
//        int       length             = constructionSpecification.getLength();
//        Component ridgeTile          = components.get(RIDGE_TILE);
//        int       numberOfRidgeTiles = up(length / cm(ridgeTile.getInt("LENGTH_MM")));
//
//        summary.add(new MaterialsBillLineImpl(ridgeTile.getMaterial(), numberOfRidgeTiles, ridgeTile.getNotes()));
//        Component ridgeTileBracket = components.get(RIDGE_TILE_BRACKET);
//        summary.add(new MaterialsBillLineImpl(ridgeTileBracket.getMaterial(), numberOfRidgeTiles, ridgeTileBracket.getNotes()));
//    }
//
//    private void calculateTopLatheHolder(MaterialsBill summary, ConstructionSpecification constructionSpecification, RoofingComponentCollection components)
//    {
//        Component component = components.get(TOP_HOLDER);
//        int       length    = constructionSpecification.getLength();
//        int       amount    = up(length / cm(component.getInt("USE_WIDTH_MM")));
//        summary.add(new MaterialsBillLineImpl(component.getMaterial(), amount, component.getNotes()));
//    }
//
//    private void calculateBindersHooks(MaterialsBill summary, RoofingComponentCollection components, int tileRows, int tileColumns)
//    {
//        int outer = tileRows * 2 + tileColumns * 2;
//        int inner = (tileRows * tileColumns - outer) / 2;
//
//        Component component = components.get(TILE_BINDERS_AND_HOOKS);
//        summary.add(new MaterialsBillLineImpl(component.getMaterial(), outer + inner, component.getNotes()));
//    }
}
