package fit.nsu.labs.common.codec;

import fit.nsu.labs.common.exception.EncoderException;

public interface Encoder<T> {

    byte[] encode(T object) throws EncoderException;
}
