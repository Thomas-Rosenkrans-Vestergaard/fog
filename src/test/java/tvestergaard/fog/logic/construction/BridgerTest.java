package tvestergaard.fog.logic.construction;

import org.junit.Test;
import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.materials.categories.AbstractCategoryMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BridgerTest
{

    private class TestCategory extends AbstractCategoryMaterial
    {

        public TestCategory(Material material)
        {
            super(material);
        }

        public int getMeasurement()
        {
            return material.getId();
        }
    }

    @Test
    public void bridge() throws Exception
    {

        List<TestCategory> materials = new ArrayList<>();

        Material material1 = mock(Material.class);
        when(material1.getId()).thenReturn(1);
        when(material1.getUnit()).thenReturn(1);
        when(material1.getPrice()).thenReturn(1);
        materials.add(new TestCategory(material1));

        Material material2 = mock(Material.class);
        when(material2.getId()).thenReturn(2);
        when(material2.getUnit()).thenReturn(1);
        when(material2.getId()).thenReturn(1);

        BiConsumer<>

        Bridger<TestCategory> instance    = new Bridger<TestCategory>(materials, material -> material.getMeasurement());
        MutableMaterials      destination = new MutableMaterials();
        instance.bridge(5, destination);

        assertEquals(5, destination.getLines().size());
    }
}