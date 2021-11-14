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
    private static final Map<String, Configuration> profiles = Configuration.read(new File("configuration.xml"));
    private static Configuration profile;

    private final Map<String, String> values;

    private Configuration(Map<String, String> values) {
        this.values = values;
    }

    public String value(String key) {
        String value = this.values.get(key);
        if (value == null) {
            throw new ConfigurationException("unconfigured key: " + key);
        } else {
            return value;
        }
    }

    public static Configuration load(String profile) {
        Configuration.profile = Configuration.profiles.get(profile);
        return Configuration.profile;
    }

    public static void reset() {
        Configuration.profile = null;
    }

    public static Configuration load() {
        if (Configuration.profile == null) {
            return Configuration.load("");
        } else {
            return Configuration.profile;
        }
    }

    public static Map<String, Configuration> read(File file) {
        try {
            Document document = Configuration.createDocumentBuilder().parse(file);
            Node root = document.getDocumentElement();
            NodeList children = root.getChildNodes();

            HashMap<String, Configuration> profiles = new HashMap<>();
            for (int i = 0; i < children.getLength(); ++i) {
                Node childNode = children.item(i);
                if (childNode.getNodeName().equals("configuration")) {
                    Node childNameNode = childNode.getAttributes().getNamedItem("profile");
                    String name = childNameNode != null ? childNameNode.getTextContent() : "";

                    HashMap<String, String> values = new HashMap<>();
                    NodeList grandchildren = childNode.getChildNodes();
                    for (int j = 0; j < grandchildren.getLength(); ++j) {
                        Node grandchild = grandchildren.item(j);
                        if (grandchild.getNodeName().equals("value")) {
                            String key = grandchild.getAttributes().getNamedItem("key").getNodeValue();
                            String value = grandchild.getTextContent();
                            values.put(key, value);
                        }
                    }

                    profiles.put(name, new Configuration(values));
                }
            }

            return profiles;
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
