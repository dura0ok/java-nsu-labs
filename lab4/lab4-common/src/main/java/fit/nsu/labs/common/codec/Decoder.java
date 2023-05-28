package fit.nsu.labs.common.codec;

import fit.nsu.labs.common.exception.DecoderException;

import java.io.InputStream;

public interface Decoder<T> {

    T decode(InputStream inputStream) throws DecoderException;
}
