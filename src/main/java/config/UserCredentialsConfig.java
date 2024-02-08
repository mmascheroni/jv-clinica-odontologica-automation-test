package config;

import exceptions.MissingPropertyException;

import java.io.IOException;
import java.util.Base64;

public class UserCredentialsConfig {

    private ConfigProperties configProperties = new ConfigProperties();

    public UserCredentialsConfig(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    private String getConfigProperty(String property, String propFileName) throws IOException, MissingPropertyException {
        configProperties.loadProperties(propFileName);

        return configProperties.getProp(property);
    }

    public String getUserBase64Credentials(String role, String passRole, String fileNameProps) throws MissingPropertyException, IOException {
        String user = getConfigProperty(role, fileNameProps);

        String pass = getConfigProperty(passRole, fileNameProps);

        String credentialsUser = user + ":" + pass;
        String base64CredentialsUser = Base64.getEncoder().encodeToString(credentialsUser.getBytes());

        return base64CredentialsUser;
    }
}
