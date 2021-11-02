package csci310;

import csci310.exception.ConfigurationException;
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
        this.document = document;
    }

    public Map<String, String> values(String... path) {
        Node cursor = this.document.getDocumentElement();
        for (String part : path) {
            NodeList children = cursor.getChildNodes();

            boolean found = false;
            for (int i = 0; i < children.getLength(); ++i) {
                Node child = children.item(i);
                if (child.getNodeName().equals(part)) {
                    cursor = child;
                    found = true;
                }
            }

            if (!found) {
                throw new ConfigurationException("Couldn't find values for path " + String.join("/", path) + "!");
            }
        }

        HashMap<String, String> result = new HashMap<>();
        NodeList children = cursor.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            if (child.getNodeName().equals("value")) {
                String key = child.getAttributes().getNamedItem("key").getNodeValue();
                String value = child.getTextContent();
                result.put(key, value);
            }
        }

        return result;
    }

    public static Configuration load() {
        if (Configuration.instance == null) {
            Configuration.instance = Configuration.read(new File("configuration.xml"));
        }
        return Configuration.instance;
    }

    public static Configuration read(File file) {
        try {
            return new Configuration(Configuration.createDocumentBuilder().parse(file));
        } catch (SAXException exception) {
            throw new ConfigurationException("Invalid XML in settings file!");
        } catch (IOException exception) {
            throw new ConfigurationException("Error reading settings file!");
        }
    }

    public static DocumentBuilderFactory createDocumentBuilderFactory() {
        return DocumentBuilderFactory.newInstance();
    }

    public static DocumentBuilder createDocumentBuilder(DocumentBuilderFactory documentBuilderFactory) {
        try {
            return documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException exception) {
            throw new ConfigurationException("Failed to initialize the settings parser!");
        }
    }

    public static DocumentBuilder createDocumentBuilder() {
        return Configuration.createDocumentBuilder(Configuration.createDocumentBuilderFactory());
    }
}
