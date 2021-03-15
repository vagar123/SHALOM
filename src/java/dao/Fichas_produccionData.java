
package dao;

import java.util.Date;


public class Fichas_produccionData extends DAO {
    
    private int idFicha_produccion;
    private String productFicha_producc;
    private Date fechaIFicha_producc;
    private Date fechaSFicha_producc;
    private int idSatelite;
    private String nomSatelite;
    private String etadoFicha_producc;
    
    public Fichas_produccionData() {
    }

    public Fichas_produccionData(int idFicha_produccion, String productFicha_producc, Date fechaIFicha_producc, Date fechaSFicha_producc, int idSatelite, String nomSatelite) {
        this.idFicha_produccion = idFicha_produccion;
        this.productFicha_producc = productFicha_producc;
        this.fechaIFicha_producc = fechaIFicha_producc;
        this.fechaSFicha_producc = fechaSFicha_producc;
        this.idSatelite = idSatelite;
        this.nomSatelite = nomSatelite;
    }

    public int getIdFicha_produccion() {
        return idFicha_produccion;
    }

    public void setIdFicha_produccion(int idFicha_produccion) {
        this.idFicha_produccion = idFicha_produccion;
    }

    public String getProductFicha_producc() {
        return productFicha_producc;
    }

    public void setProductFicha_producc(String productFicha_producc) {
        this.productFicha_producc = productFicha_producc;
    }

    public Date getFechaIFicha_producc() {
        return fechaIFicha_producc;
    }

    public void setFechaIFicha_producc(Date fechaIFicha_producc) {
        this.fechaIFicha_producc = fechaIFicha_producc;
    }

    public Date getFechaSFicha_producc() {
        return fechaSFicha_producc;
    }

    public void setFechaSFicha_producc(Date fechaSFicha_producc) {
        this.fechaSFicha_producc = fechaSFicha_producc;
    }

    public int getIdSatelite() {
        return idSatelite;
    }

    public void setIdSatelite(int idSatelite) {
        this.idSatelite = idSatelite;
    }

    public String getNomSatelite() {
        return nomSatelite;
    }

    public void setNomSatelite(String nomSatelite) {
        this.nomSatelite = nomSatelite;
    }

    public String getEtadoFicha_producc() {
        return etadoFicha_producc;
    }

    public void setEtadoFicha_producc(String etadoFicha_producc) {
        this.etadoFicha_producc = etadoFicha_producc;
    }
    
    
            
}
