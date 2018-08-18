package tools;

import models.Entity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class EntityTool {

    public static boolean saveEntity(final Entity entity) {
        System.out.println("save " + entity + " " + entity.getValue());
        return true;
    }

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;
    private TransformerFactory transformerFactory;
    private Transformer transformer;

    public EntityTool() {

        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.newDocument();

            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
    }

    public boolean saveEntityXML(final Entity entity) {
        try {
            Element rootElement = document.createElement("entity");
            document.appendChild(rootElement);

            Element nameElement = document.createElement("name");
            nameElement.appendChild(document.createTextNode(entity.getName()));
            rootElement.appendChild(nameElement);

            Element textElement = document.createElement("text");
            textElement.appendChild(document.createTextNode(entity.getValue()));
            rootElement.appendChild(textElement);

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(entity.getName() + ".xml"));
            transformer.transform(source, result);
            /*StreamResult result =  new StreamResult(System.out);
            transformer.transform(source, result);*/
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
