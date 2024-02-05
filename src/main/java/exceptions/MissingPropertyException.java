package exceptions;

public class MissingPropertyException extends Exception
{

    private String propVal;

    public MissingPropertyException(String parName) {
        super("Parametro de configuracion no encontrado: '" + parName + "'");
        this.setPropVal(parName);
    }

    public String getPropVal() {
        return propVal;
    }

    public void setPropVal(String propVal) {
        this.propVal = propVal;
    }

}
