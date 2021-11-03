package csci310.forms;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import csci310.exception.RequestException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Reader;

public class Form {
    public static <T extends Form> T parse(Reader reader, Class<T> tClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
        return objectMapper.readValue(reader, tClass);
    }

    public static <T extends Form> T read(HttpServletRequest request, Class<T> tClass) throws IOException, RequestException {
        try {
            return Form.parse(request.getReader(), tClass);
        } catch (MismatchedInputException exception) {
            throw new RequestException(400, "form data did not conform to schema!");
        }
    }
}
