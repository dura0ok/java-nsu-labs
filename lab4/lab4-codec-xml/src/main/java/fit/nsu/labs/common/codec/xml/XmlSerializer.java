package fit.nsu.labs.common.codec.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface XmlSerializer<T> {
    void serialize(T message, Document document, Element rootElement);
    T deserialize(Class<T> classMessage, Element rootElement);
}
