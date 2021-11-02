package csci310.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import csci310.exception.NotImplementedError;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class Endpoint {
    public static <T> void list(List<T> querySet, Writer writer) throws IOException {
        throw new NotImplementedError();
    }
}
