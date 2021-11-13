package csci310;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Resources {
    public static String read(String path) throws IOException {
        return new String(Files.readAllBytes(Path.of("src/test/resources/" + path)));
    }
}
