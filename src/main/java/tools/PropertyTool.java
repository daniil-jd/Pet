package tools;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * The class is designed to simplify the interaction with files .properties. *
 */
public class PropertyTool {

    /**
     * Properties variable.
     */
    private Properties properties = new Properties();

    /**
     * Properties input stream.
     */
    private InputStream inputStream;

    /**
     * Properties output stream.
     */
    private OutputStream outputStream;

    /**
     * Constructor to initialize property, input and output steams.
     */
    public PropertyTool() {
        createFileIfNotExist();
        try {
            inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);

            outputStream = new FileOutputStream("config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Create .properties if not exist.
     */
    private void createFileIfNotExist() {
        File file = new File("config.properties");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns property by name.
     * @param name name
     * @return property value
     */
    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    /**
     * Saves new properties to hard disk.
     * @param propMap new properties map
     */
    public void saveProperties(Map<String,String> propMap) {
        Iterator<Map.Entry<String, String>> iterator = propMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            properties.setProperty(entry.getKey(), entry.getValue());
        }
        try {
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load all properties.
     * @return list of properties
     */
    public Set<Map.Entry<Object, Object>> getAllProperties() {
        return properties.entrySet();
    }

}
