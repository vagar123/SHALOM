package modelo;

import java.util.Date;

public class Fichas_produccion extends dao.DAO{

    private int idFicha_produccion;
    private String productFicha_producc;
    private Date fechaIFicha_producc;
    private Date fechaSFicha_producc;
    private int cantidadFicha_producc;
    private String tiempoFicha_producc;
    private String tallaFicha_producc;
    private int valorTotalFicha_producc;
    private String categoriaFicha_producc;
    private String generoFicha_producc;
    private String colorFicha_producc;
    private String descripcionFicha_producc;
    private int idOrden_produccion;
    private int idSatelite;
    private String estadoFicha_producc;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.idFicha_produccion;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fichas_produccion other = (Fichas_produccion) obj;
        if (this.idFicha_produccion != other.idFicha_produccion) {
            return false;
        }
        return true;
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

    public int getCantidadFicha_producc() {
        return cantidadFicha_producc;
    }

    public void setCantidadFicha_producc(int cantidadFicha_producc) {
        this.cantidadFicha_producc = cantidadFicha_producc;
    }

    public String getTiempoFicha_producc() {
        return tiempoFicha_producc;
    }

    public void setTiempoFicha_producc(String tiempoFicha_producc) {
        this.tiempoFicha_producc = tiempoFicha_producc;
    }

    public String getTallaFicha_producc() {
        return tallaFicha_producc;
    }

    public void setTallaFicha_producc(String tallaFicha_producc) {
        this.tallaFicha_producc = tallaFicha_producc;
    }

    public int getValorTotalFicha_producc() {
        return valorTotalFicha_producc;
    }

    public void setValorTotalFicha_producc(int valorTotalFicha_producc) {
        this.valorTotalFicha_producc = valorTotalFicha_producc;
    }

    public String getCategoriaFicha_producc() {
        return categoriaFicha_producc;
    }

    public void setCategoriaFicha_producc(String categoriaFicha_producc) {
        this.categoriaFicha_producc = categoriaFicha_producc;
    }

    public String getGeneroFicha_producc() {
        return generoFicha_producc;
    }

    public void setGeneroFicha_producc(String generoFicha_producc) {
        this.generoFicha_producc = generoFicha_producc;
    }

    public String getColorFicha_producc() {
        return colorFicha_producc;
    }

    public void setColorFicha_producc(String colorFicha_producc) {
        this.colorFicha_producc = colorFicha_producc;
    }

    public String getDescripcionFicha_producc() {
        return descripcionFicha_producc;
    }

    public void setDescripcionFicha_producc(String descripcionFicha_producc) {
        this.descripcionFicha_producc = descripcionFicha_producc;
    }

    public int getIdOrden_produccion() {
        return idOrden_produccion;
    }

    public void setIdOrden_produccion(int idOrden_produccion) {
        this.idOrden_produccion = idOrden_produccion;
    }

    public int getIdSatelite() {
        return idSatelite;
    }

    public void setIdSatelite(int idSatelite) {
        this.idSatelite = idSatelite;
    }

    public String getEstadoFicha_producc() {
        return estadoFicha_producc;
    }

    public void setEstadoFicha_producc(String estadoFicha_producc) {
        this.estadoFicha_producc = estadoFicha_producc;
    }
}