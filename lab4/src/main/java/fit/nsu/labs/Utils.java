package fit.nsu.labs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Utils {
    public static byte[] serializeMessage(Object dataToSend) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(dataToSend);
        oos.flush();
        return bos.toByteArray();
    }
}
