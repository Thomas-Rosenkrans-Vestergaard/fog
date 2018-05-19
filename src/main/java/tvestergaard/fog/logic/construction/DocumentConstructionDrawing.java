package tvestergaard.fog.logic.construction;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.svg2svg.SVGTranscoder;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

public class DocumentConstructionDrawing implements ConstructionDrawing
{

    /**
     * The title of the construction drawing.
     */
    private final String title;

    /**
     * The document of the svg drawing.
     */
    private final Document document;

    /**
     * Creates a new {@link DocumentConstructionDrawing}.
     *
     * @param title    The title of the document.
     * @param document The document containing the svg directives.
     */
    public DocumentConstructionDrawing(String title, Document document)
    {
        this.title = title;
        this.document = document;
    }

    /**
     * Returns the title of the drawing.
     *
     * @return The title of the drawing.
     */
    @Override public String getTitle()
    {
        return title;
    }

    /**
     * Returns the document containing the svg contents.
     *
     * @return The document containing the svg contents.
     */
    @Override public Document getDocument()
    {
        return document;
    }

    /**
     * Returns the SVG XML contents.
     *
     * @return The SVG XML contents.
     */
    @Override public String getXML()
    {
        try {
            SVGTranscoder transcoder = new SVGTranscoder();

            TranscoderInput       input            = new TranscoderInput(document);
            ByteArrayOutputStream outputStream     = new ByteArrayOutputStream();
            OutputStreamWriter    writer           = new OutputStreamWriter(outputStream);
            TranscoderOutput      transcoderOutput = new TranscoderOutput(writer);

            transcoder.transcode(input, transcoderOutput);
            writer.flush();
            writer.close();

            return new String(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
