package tvestergaard.fog.data.materials;

public interface SimpleMaterial extends MaterialUpdater
{

    /**
     * Checks if the material is currently active.
     *
     * @return {@code true} if the material is currently active. Or {@code false} or has been replaced with a newer version.
     */
    boolean isActive();
}
