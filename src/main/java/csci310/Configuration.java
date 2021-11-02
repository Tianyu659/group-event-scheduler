package csci310;

import csci310.exception.ConfigurationException;
import csci310.exception.NotImplementedError;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private static Configuration instance = Configuration.load();
    private final Document document;

    private Configuration(Document document) {
        throw new NotImplementedError();
    }

    public Map<String, String> values(String... path) {
        throw new NotImplementedError();
    }

    public static Configuration load() {
        throw new NotImplementedError();
    }

    public static Configuration read(File file) {
        throw new NotImplementedError();
    }

    public static DocumentBuilderFactory createDocumentBuilderFactory() {
        throw new NotImplementedError();
    }

    public static DocumentBuilder createDocumentBuilder(DocumentBuilderFactory documentBuilderFactory) {
        throw new NotImplementedError();
    }

    public static DocumentBuilder createDocumentBuilder() {
        throw new NotImplementedError();
    }
}
