package ui;

import config.ConfigProperties;
import exceptions.MissingPropertyException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class TurnoTest {

    public WebDriver driver;

    private ConfigProperties configProperties = new ConfigProperties();

    private String getConfigProperty(String property, String propFileName) throws IOException, MissingPropertyException {
        configProperties.loadProperties(propFileName);

        return configProperties.getProp(property);
    }
}
