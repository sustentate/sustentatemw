package ar.com.sustentate.mw.models;

public class ElementosModel {
    private String producto;
    private String condicion;
    private String comoHacerlo;
    private long _id;

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) { this.producto = producto; }

    public String getCondicion() { return condicion; }

    public void setCondicon (String condicion) { this.condicion = condicion; }

    public String getComoHacerlo(){return comoHacerlo;}

    public void setComoHacerlo (String comoHacerlo) {this.comoHacerlo = comoHacerlo;}

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        this._id = id;
    }
}
