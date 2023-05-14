package fit.nsu.labs.common;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

class UserConverter implements Converter {
    public boolean canConvert(Class clazz) {
        return clazz == User.class;
    }

    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        User user = (User) value;
        writer.startNode("name");
        writer.setValue(user.name());
        writer.endNode();
        writer.startNode("sessionID");
        writer.setValue(user.sessionID().toString());
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        reader.moveDown();
        String name = reader.getValue();
        reader.moveUp();
        reader.moveDown();
        int sessionID = Integer.parseInt(reader.getValue());
        reader.moveUp();
        return new User(name, sessionID);
    }
}

