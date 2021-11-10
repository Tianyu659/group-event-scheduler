package csci310;

import csci310.exception.ConfigurationException;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ConfigurationTest {
    private File createTemporaryFile(String contents) throws IOException {
        File file = File.createTempFile("configuration", "xml");
        FileWriter writer = new FileWriter(file);
        writer.write(contents);
        writer.close();
        file.deleteOnExit();
        return file;
    }

    @Test
    public void testCreateDocumentBuilder() throws ConfigurationException {
        DocumentBuilder documentBuilder = Configuration.createDocumentBuilder();
        Assert.assertNotNull(documentBuilder);
    }

    @Test
    public void testCreateDocumentBuilderFactory() throws ConfigurationException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = EasyMock.mock(DocumentBuilderFactory.class);
        EasyMock.expect(documentBuilderFactory.newDocumentBuilder()).andThrow(new ParserConfigurationException());
        EasyMock.replay(documentBuilderFactory);

        try {
            Configuration.createDocumentBuilder(documentBuilderFactory);
            Assert.fail();
        } catch (ConfigurationException exception) {
            Assert.assertNotNull(exception);
        }
    }

    @Test
    public void testLoad() {
        Assert.assertNotNull(Configuration.load());
    }

    @Test
    public void testReadConfiguration() throws IOException, ConfigurationException {
        File file = createTemporaryFile("<configuration><test><value key=\"a\">hello</value><value key=\"b\">world</value></test></configuration>");
        Configuration configuration = Configuration.read(file);
        Assert.assertNotNull(configuration);
    }

    @Test
    public void testReadConfigurationInvalid() throws IOException, ConfigurationException {
        File file = createTemporaryFile("<");
        try {
            Configuration.read(file);
            Assert.fail();
        } catch (ConfigurationException exception) {
            Assert.assertNotNull(exception);
        }
    }

    @Test
    public void testReadConfigurationMissing() throws IOException, ConfigurationException {
        File file = new File("?");
        try {
            Configuration.read(file);
            Assert.fail();
        } catch (ConfigurationException exception) {
            Assert.assertNotNull(exception);
        }
    }

    @Test
    public void testValues() throws IOException, ConfigurationException {
        File file = createTemporaryFile("<configuration><test><value key=\"a\">hello</value><value key=\"b\">world</value></test></configuration>");
        Configuration configuration = Configuration.read(file);
        Map<String, String> values = configuration.values("test");
        Assert.assertEquals(2, values.size());
    }

    @Test
    public void testValuesNested() throws IOException, ConfigurationException {
        File file = createTemporaryFile("<configuration><parent><test><value key=\"a\">hello</value><value key=\"b\">world</value></test></parent></configuration>");
        Configuration configuration = Configuration.read(file);
        Map<String, String> values = configuration.values("parent", "test");
        Assert.assertEquals(2, values.size());
    }

    @Test
    public void testValuesNestedMissing() throws IOException, ConfigurationException {
        File file = createTemporaryFile("<configuration><parent><test><value key=\"a\">hello</value><value key=\"b\">world</value></test></parent></configuration>");
        Configuration configuration = Configuration.read(file);

        try {
            configuration.values("parent", "missing");
            Assert.fail();
        } catch (ConfigurationException exception) {
            Assert.assertNotNull(exception);
        }
    }
}
