package fit.nsu.labs.common.codec;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public abstract class AbstractXmlCodec {
    private final Transformer transformer;

    public AbstractXmlCodec() {
        try {
            var transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
        } catch (Exception e) {
            throw new RuntimeException("ЧЕГО!!?? КАК ЭТО СЛУЧИЛОСЬ???", e);
        }
    }

    protected Document createDocument() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
    }

    protected byte[] toBytes(Document document) throws TransformerException, IOException {
        var source = new DOMSource(document);
        //noinspection SpellCheckingInspection
        var baos = new ByteArrayOutputStream();
        var streamResult = new StreamResult(new OutputStreamWriter(baos));

        transformer.transform(source, streamResult);
        byte[] bytes = baos.toByteArray();

        baos = new ByteArrayOutputStream();
        var dos = new DataOutputStream(baos);
        dos.writeInt(bytes.length);
        dos.write(bytes);
        dos.flush();

        return baos.toByteArray();
    }

    protected Document parseFromStream(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        var dis = new DataInputStream(inputStream);
        var length = dis.readInt();
        var xmlBytes = dis.readNBytes(length);

        DumpUtil.dumpPacket(xmlBytes, "Dump incoming XML packet without first 4 bytes (length)");

        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xmlBytes));
    }
}
