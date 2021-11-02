package csci310.forms;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import csci310.exception.NotImplementedError;
import csci310.exception.RequestException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Reader;

public class Form {
    public static <T extends Form> T parse(Reader reader, Class<T> tClass) throws IOException {
        throw new NotImplementedError();
    }

    public static <T extends Form> T read(HttpServletRequest request, Class<T> tClass) throws IOException, RequestException {
        throw new NotImplementedError();
    }
}
