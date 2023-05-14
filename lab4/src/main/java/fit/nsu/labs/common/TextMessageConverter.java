package fit.nsu.labs.common;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TextMessageConverter implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        TextMessage message = (TextMessage) o;
        writer.startNode("name");
        writer.setValue(message.name());
        writer.endNode();
        writer.startNode("text");
        writer.setValue(message.text());
        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String name = null;
        String text = null;
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String nodeName = reader.getNodeName();
            if ("name".equals(nodeName)) {
                name = reader.getValue();
            } else if ("text".equals(nodeName)) {
                text = reader.getValue();
            }
            reader.moveUp();
        }
        return new TextMessage(name, text);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz == TextMessage.class;
    }
}