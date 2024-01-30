package models;

public class Domicilio {

    private Long id;
    private String calle;
    private int numero;
    private String localidad;

    public Domicilio() {}

    public Domicilio(String calle, int numero, String localidad) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
    }


    public Long getId() {
        return id;
    }


    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    @Override
    public String toString() {
        return "Domicilio{" +
                "id=" + id +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                ", localidad='" + localidad + '\'' +
                '}';
    }
}
