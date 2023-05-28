package fit.nsu.labs.common.codec;

import fit.nsu.labs.common.exception.DecoderException;
import fit.nsu.labs.common.exception.EncoderException;
import fit.nsu.labs.common.message.client.ClientMessage;

import java.io.InputStream;

public class ObjectClientCodec implements Encoder<ClientMessage>, Decoder<ClientMessage> {

    @Override
    public byte[] encode(ClientMessage object) throws EncoderException {
        System.out.printf("[DEBUG] [client.codec] outgoing message %s\n", object.getClass().getSimpleName());
        return ObjectCodecUtil.encode(object);
    }

    @Override
    public ClientMessage decode(InputStream inputStream) throws DecoderException {
        var result = (ClientMessage) ObjectCodecUtil.decoder(inputStream);
        System.out.printf("[DEBUG] [client.codec] input message %s\n", result.getClass().getSimpleName());
        return result;
    }
}
