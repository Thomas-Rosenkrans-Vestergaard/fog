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
     * The document of the svg drawing.
     */
    private final Document document;

    /**
     * Creates a new {@link DocumentConstructionDrawing}.
     *
     * @param document The document containing the svg directives.
     */
    public DocumentConstructionDrawing(Document document)
    {
        this.document = document;
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
            SVGTranscoder t = new SVGTranscoder();

            TranscoderInput       input            = new TranscoderInput(document);
            ByteArrayOutputStream outputStream     = new ByteArrayOutputStream();
            OutputStreamWriter    writer           = new OutputStreamWriter(outputStream);
            TranscoderOutput      transcoderOutput = new TranscoderOutput(writer);

            t.transcode(input, transcoderOutput);
            writer.flush();
            writer.close();

            return new String(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
