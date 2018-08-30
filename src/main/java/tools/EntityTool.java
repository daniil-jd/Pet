package tools;

import models.Entity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

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

    private XMLInputFactory xmlInputFactory;

    public EntityTool(boolean isRead) {
        if (isRead) {
            initReader();
        }
        else {
            initSaver();
        }

    }

    private void initReader() {
        xmlInputFactory = XMLInputFactory.newInstance();
    }

    private void initSaver() {
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

    public Entity readEntityXML (File file) {
        if (file.length() > 0) {
            try {
                XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));
                Entity entity = null;
                int count = -1;
                while (xmlEventReader.hasNext()) {
                    XMLEvent xmlEvent = xmlEventReader.nextEvent();
                    if (xmlEvent.isStartElement()) {
                        StartElement startElement = xmlEvent.asStartElement();
                        if (startElement.getName().getLocalPart().equals("name")) {
                            count = 0;
                        } else if (startElement.getName().getLocalPart().equals("text")) {
                            count = 1;
                        }
                    }
                    if (xmlEvent.isCharacters()) {
                        Characters chrctrs = xmlEvent.asCharacters();
                        if (count == 0) {
                            entity = new Entity(chrctrs.getData());
                            count = -1;
                        } else if (count == 1) {
                            entity.setValue(chrctrs.getData());
                            count = -1;
                        }
                    }
                }
                return entity;
            } catch (XMLStreamException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Entity> loadEntities() {
        PropertyTool propertyTool = new PropertyTool();
        Set<Map.Entry<Object, Object>> set = propertyTool.getAllProperties();
        Iterator<Map.Entry<Object, Object>> it = set.iterator();

        List<Entity> result = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<Object, Object> item = it.next();
            if (!item.getKey().toString().equals("password")) {
                Entity entity = readEntityXML(new File(item.getValue().toString()));
                result.add(entity);
            }
        }

        return result;
    }
}
