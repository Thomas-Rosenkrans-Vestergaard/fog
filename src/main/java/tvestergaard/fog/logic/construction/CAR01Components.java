package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.components.Component;

class CAR01Components
{

    private final Components components;

    public CAR01Components(Components components)
    {
        this.components = components;
    }

    public Component getGaragePosts()
    {
        return components.from("POST");
    }

    public Component getGarageStraps()
    {
        return components.from("STRAPS_GARAGE");
    }

    public Component getShedGableNogging()
    {
        return components.from("SHED_GABLE_NOGGING");
    }

    public Component getShedCladding()
    {
        return components.from("SHED_CLADDING");
    }

    public Component getDoorNogging()
    {
        return components.from("SHED_DOOR_NOGGING");
    }
}
