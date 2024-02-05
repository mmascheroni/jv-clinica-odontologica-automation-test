package config;


import exceptions.MissingPropertyException;
import org.eclipse.sisu.containers.Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class ConfigProperties {

    public ConfigProperties() {
    }

    HashMap props;

    public void loadProperties(String propertiesFileName) throws IOException {
        Properties properties = new Properties();
        InputStream entry = null;

            try
            {
                // entry = new FileInputStream("config.properties");
                entry = Main.class.getClassLoader().getResourceAsStream(propertiesFileName + ".properties");

                properties.load(entry);

                props = new HashMap(properties);
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                entry.close();
            }
    }

    public String getProp(String propertyName) throws MissingPropertyException {
        String value = (String) props.get(propertyName);
        if (value == null)
            throw new MissingPropertyException(propertyName);

        return value;
    }


}
