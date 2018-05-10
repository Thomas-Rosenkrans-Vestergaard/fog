package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.components.Component;

public class CAR01SkeletonConstructor implements SkeletonConstructor
{

    /**
     * Constructs the skeleton of the garage using the provided components.
     *
     * @param materials     The summary being updated with the materials needed to construct the skeleton.
     * @param components    The components to use while constructing the skeleton.
     * @param specification The specifications that the skeleton must satisfy.
     */
    @Override public void construct(MutableMaterialList materials, Components components, ConstructionSpecification specification)
    {
        Component post         = components.from("POST");
        Component straps       = components.from("STRAPS_GARAGE");
        Component loesHolter   = components.from("LOESHOLTER_SHED_GABLE");
        Component shedCladding = components.from("SHED_CLADDING");
        Component zDoor        = components.from("Z_DOOR");

        materials.add(post.getMaterial(), 8, post.getNotes());
        materials.add(straps.getMaterial(), 2, straps.getNotes());
        materials.add(zDoor.getMaterial(), 1, zDoor.getNotes());
        materials.add(loesHolter.getMaterial(), 20, loesHolter.getNotes());
        materials.add(shedCladding.getMaterial(), 12, shedCladding.getNotes());
    }
}
