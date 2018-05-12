package ar.com.sustentate.mw.models;

import java.util.Date;

public class ElementosResponse {
    private String producto;
    private String condicion;
    private String comoHacerlo;
    private Date finalDate;
    private long id;

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) { this.producto = producto; }

    public String getCondicion() { return condicion; }

    public void setCondicon (String condicion) { this.condicion = condicion; }

    public String getComoHacerlo(){return comoHacerlo;}

    public void setComoHacerlo (String comoHacerlo) {this.comoHacerlo = comoHacerlo;}

    public Date getFinalDate(){return finalDate;}

    public void setFinalDate(Date dateFinal) {
        this.finalDate = finalDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
