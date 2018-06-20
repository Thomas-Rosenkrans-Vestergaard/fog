package tvestergaard.fog.logic.construction;

import org.junit.Test;
import tvestergaard.fog.data.materials.Material;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tvestergaard.fog.Helpers.randomString;

public class MutableMaterialsTest
{
    @Test
    public void addGetLines() throws Exception
    {
        MutableMaterials instance = new MutableMaterials();

        assertEquals(0, instance.getLines().size());

        Material material = mock(Material.class);
        when(material.getPrice()).thenReturn(1000);
        when(material.getUnit()).thenReturn(5);
        String expectedNotes = randomString();
        instance.add(material, 10, expectedNotes);

        assertEquals(1, instance.getLines().size());
        MaterialLine line = instance.getLines().get(0);

        assertEquals(material, line.getMaterial());
        assertEquals(2, line.getAmount());
        assertEquals(2000, line.getTotal());
        assertEquals(expectedNotes, line.getNotes());
    }

    @Test
    public void getTotal() throws Exception
    {
        MutableMaterials instance = new MutableMaterials();

        Material materialOne = mock(Material.class);
        when(materialOne.getPrice()).thenReturn(1000);
        when(materialOne.getUnit()).thenReturn(1);
        Material materialTwo = mock(Material.class);
        when(materialTwo.getPrice()).thenReturn(1001);
        when(materialTwo.getUnit()).thenReturn(5);

        instance.add(materialOne, 1, "");
        assertEquals(1000, instance.getTotal());

        instance.add(materialOne, 1, "");
        assertEquals(2000, instance.getTotal());

        instance.add(materialOne, 5, "");
        assertEquals(7000, instance.getTotal());

        instance.add(materialTwo, 1, "");
        assertEquals(8001, instance.getTotal());

        instance.add(materialTwo, 6, "");
        assertEquals(10003, instance.getTotal());
    }
}