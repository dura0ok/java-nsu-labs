package fit.nsu.labs.common;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.IOException;

import static fit.nsu.labs.Utils.getBytes;

public abstract class SocketMessage {
    public static byte[] serialize(XStream xStream, SocketMessage message) throws IOException {
        switch (System.getProperty("PROTOCOL")) {
            case "XML" -> {
                var msg = xStream.toXML(message);
                msg = msg.length() + msg;
                System.out.println(msg);
                return msg.getBytes();
            }
            case "SERIALIZATION" -> {
                return getBytes(message);
            }

            default -> throw new RuntimeException("invalid protocol");
        }
    }
}
