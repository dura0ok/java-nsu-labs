package fit.nsu.labs.common.codec;

import fit.nsu.labs.common.exception.DecoderException;
import fit.nsu.labs.common.exception.EncoderException;

import java.io.*;

public interface ObjectCodecUtil {

    static byte[] encode(Object object) throws EncoderException {
        try {
            //noinspection SpellCheckingInspection
            var baos = new ByteArrayOutputStream();
            var oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new EncoderException(e);
        }
    }

    static Object decoder(InputStream inputStream) throws DecoderException {
        try {
            var ois = new ObjectInputStream(inputStream);
            return ois.readObject();
        } catch (Exception e) {
            throw new DecoderException(e);
        }
    }
}
