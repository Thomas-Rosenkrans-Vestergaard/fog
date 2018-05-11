package tvestergaard.fog.logic.construction;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tvestergaard.fog.data.components.Component;

public class CAR01SkeletonConstructor implements SkeletonConstructor
{

    /**
     * Constructs the skeleton of the garage using the provided components.
     *
     * @param summary       The object containing information about the construction process.
     * @param specification The specifications that the skeleton must satisfy.
     * @param components    The components to use while constructing the skeleton.
     */
    @Override public void construct(MutableConstructionSummary summary, ConstructionSpecification specification, Components components)
    {
        MutableMaterialList materials = new MutableMaterialList("Skelet materialer");

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

        summary.add(materials);
        draw(summary, specification, components);
    }

    private void draw(MutableConstructionSummary summary, ConstructionSpecification specification, Components components)
    {
        DOMImplementation impl  = SVGDOMImplementation.getDOMImplementation();
        String            svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        Document          doc   = impl.createDocument(svgNS, "svg", null);

        Element svgRoot = doc.getDocumentElement();
        svgRoot.setAttributeNS(null, "width", Integer.toString(specification.getLength() * 10));
        svgRoot.setAttributeNS(null, "height", "450");

        Element rectangle = doc.createElementNS(svgNS, "rect");
        rectangle.setAttributeNS(null, "x", "10");
        rectangle.setAttributeNS(null, "y", "20");
        rectangle.setAttributeNS(null, "width", "100");
        rectangle.setAttributeNS(null, "height", "50");
        rectangle.setAttributeNS(null, "style", "fill:red");

        svgRoot.appendChild(rectangle);

        summary.add(new DocumentConstructionDrawing("Firkant", doc));
    }
}
