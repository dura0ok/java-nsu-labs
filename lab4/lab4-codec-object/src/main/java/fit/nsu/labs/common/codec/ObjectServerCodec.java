package fit.nsu.labs.common.codec;

import fit.nsu.labs.common.exception.DecoderException;
import fit.nsu.labs.common.exception.EncoderException;
import fit.nsu.labs.common.message.server.ServerMessage;

import java.io.InputStream;

public class ObjectServerCodec implements Encoder<ServerMessage>, Decoder<ServerMessage> {

    @Override
    public byte[] encode(ServerMessage object) throws EncoderException {
        System.out.printf("[DEBUG] [server.codec] outgoing message %s\n", object.getClass().getSimpleName());
        return ObjectCodecUtil.encode(object);
    }

    @Override
    public ServerMessage decode(InputStream inputStream) throws DecoderException {
        var result = (ServerMessage) ObjectCodecUtil.decoder(inputStream);
        System.out.printf("[DEBUG] [server.codec] input message %s\n", result.getClass().getSimpleName());
        return result;
    }
}
