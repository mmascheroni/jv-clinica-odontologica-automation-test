package exceptions;

@SuppressWarnings("serial")
public class NoSuchPropertyFileException extends Exception {

    private String propFile;

    public NoSuchPropertyFileException(String fileName) {
        super("Archivo .properties no encontrado: '" + fileName + "'");
        this.setPropFile(fileName);
    }

    public String getPropFile() {
        return propFile;
    }

    public void setPropFile(String propFile) {
        this.propFile = propFile;
    }

}
