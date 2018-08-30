package tools;

import models.Entity;

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
     * Path to directory with the .properties and .xml.
     */
    private String path = "";

    /**
     * Constructor to initialize property, input and output steams.
     */
    public PropertyTool() {
        setPath();
        createFileIfNotExist();
        try {
            inputStream = new FileInputStream(path + "config.properties");
            properties.load(inputStream);
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
     * Checks that file exist.
     * @return true if exist
     */
    public boolean isFileExist() {
        File file = new File(path + "config.properties");
        return file.exists();
    }

    /**
     * Set path.
     */
    private void setPath() {
        File pathFile = new File("data");
        if (!pathFile.exists()) {
            pathFile.mkdir();
        }
        path = pathFile.getAbsolutePath() + "\\";
    }

    public String getPath() {
        return path;
    }

    /**
     * Create .properties if not exist.
     */
    private void createFileIfNotExist() {
        File file = new File(path + "config.properties");
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
        String res = properties.getProperty(name);
        if (res == null) {
            return "";
        }
        return res;
    }

    /**
     * Check that file exist property.
     * @param name property name
     * @return true if exist
     */
    public boolean isPropertyExist(String name) {
        try {
            if (properties.getProperty(name) != null
                    && properties.getProperty(name).length() > 0) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    /**
     * Saves new properties to hard disk.
     * @param propMap new properties map
     */
    public void saveProperties(Map<String,String> propMap) {
        try {
            outputStream = new FileOutputStream(path + "config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Set<Map.Entry<Object, Object>> set = properties.entrySet();
        Iterator<Map.Entry<Object, Object>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> en = it.next();
            String key = en.getKey().toString();
            String value = en.getValue().toString();
            propMap.put(key, value);
        }
        Iterator<Map.Entry<String, String>> iterator = propMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            properties.setProperty(entry.getKey(), entry.getValue());
        }
        try {
            properties.store(outputStream, "");
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
