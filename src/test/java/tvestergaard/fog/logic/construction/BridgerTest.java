package tvestergaard.fog.logic.construction;

import org.junit.Test;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.materials.categories.AbstractCategoryMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BridgerTest
{

    private abstract class TestCategory extends AbstractCategoryMaterial implements Component
    {

        public TestCategory(Material material)
        {
            super(material);
        }

        abstract int getMeasurement();
    }

    @Test
    public void bridgeReturnsSingle() throws Exception
    {
        List<TestCategory> materials = new ArrayList<>();

        TestCategory material = mock(TestCategory.class);
        when(material.getMeasurement()).thenReturn(5);
        when(material.getPrice()).thenReturn(10);
        when(material.getUnit()).thenReturn(1);
        materials.add(material);

        BiConsumer<List<TestCategory>, MutableMaterials> end = mock(BiConsumer.class);

        Bridger<TestCategory> instance    = new Bridger<>(materials, TestCategory::getMeasurement, "Notes", end);
        MutableMaterials      destination = new MutableMaterials();
        instance.bridge(5, destination);

        assertEquals(10, destination.getTotal());
        assertEquals(1, destination.getLines().size());
        assertEquals(material, destination.getLines().get(0).getMaterial());
        verify(end, times(1)).accept(any(), same(destination));
    }

    @Test
    public void bridgeExact() throws Exception
    {

        List<TestCategory> materials = new ArrayList<>();

        TestCategory material1 = mock(TestCategory.class);
        when(material1.getMeasurement()).thenReturn(5);
        when(material1.getUnit()).thenReturn(1);
        when(material1.getPrice()).thenReturn(1);
        materials.add(material1);

        TestCategory material2 = mock(TestCategory.class);
        when(material2.getMeasurement()).thenReturn(2);
        when(material2.getUnit()).thenReturn(2);
        when(material2.getPrice()).thenReturn(2);
        materials.add(material2);

        Bridger<TestCategory> instance    = new Bridger<>(materials, TestCategory::getMeasurement, "Notes");
        MutableMaterials      destination = new MutableMaterials();
        instance.bridge(12, destination);

        assertEquals(2, destination.getLines().size());
    }

    @Test
    public void bridgeExtra() throws Exception
    {
        List<TestCategory> materials = new ArrayList<>();

        TestCategory material1 = mock(TestCategory.class);
        when(material1.getMeasurement()).thenReturn(5);
        when(material1.getUnit()).thenReturn(1);
        when(material1.getPrice()).thenReturn(1);
        materials.add(material1);

        TestCategory material2 = mock(TestCategory.class);
        when(material2.getMeasurement()).thenReturn(2);
        when(material2.getUnit()).thenReturn(2);
        when(material2.getPrice()).thenReturn(2);
        materials.add(material2);

        Bridger<TestCategory> instance    = new Bridger<>(materials, TestCategory::getMeasurement, "Notes");
        MutableMaterials      destination = new MutableMaterials();
        instance.bridge(16, destination);

        assertEquals(2, destination.getLines().size());
        assertEquals(3, destination.getLines().get(0).getAmount());
        assertEquals(1, destination.getLines().get(1).getAmount());
    }
}