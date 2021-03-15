package modelo;

import java.util.Date;

public class Cotizaciones_Ficha{
    private int idCotizacion_ficha;
    private String productoCotizacion_ficha;
    private String generoProdCotizacion_ficha;
    private String tallaProduCotizacion_ficha;
    private int cantidadCotizacion_ficha;
    private Date fechaCotizacion_ficha;
    private int idSatelite;
    private String descriCoti_ficha;
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.idCotizacion_ficha;
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
        final Cotizaciones_Ficha other = (Cotizaciones_Ficha) obj;
        if (this.idCotizacion_ficha != other.idCotizacion_ficha) {
            return false;
        }
        return true;
    }

       
    @Override
    public String toString() {
        //return "Producto{" + "codigo=" + codigo + '}';
        return String.format("%s[codigo=%d]", getClass().getSimpleName(), getIdCotizacion_ficha());
    }

    public int getIdCotizacion_ficha() {
        return idCotizacion_ficha;
    }

    public void setIdCotizacion_ficha(int idCotizacion_ficha) {
        this.idCotizacion_ficha = idCotizacion_ficha;
    }

    public String getProductoCotizacion_ficha() {
        return productoCotizacion_ficha;
    }

    public void setProductoCotizacion_ficha(String productoCotizacion_ficha) {
        this.productoCotizacion_ficha = productoCotizacion_ficha;
    }

    public String getGeneroProdCotizacion_ficha() {
        return generoProdCotizacion_ficha;
    }

    public void setGeneroProdCotizacion_ficha(String generoProdCotizacion_ficha) {
        this.generoProdCotizacion_ficha = generoProdCotizacion_ficha;
    }

    public String getTallaProduCotizacion_ficha() {
        return tallaProduCotizacion_ficha;
    }

    public void setTallaProduCotizacion_ficha(String tallaProduCotizacion_ficha) {
        this.tallaProduCotizacion_ficha = tallaProduCotizacion_ficha;
    }

    public int getCantidadCotizacion_ficha() {
        return cantidadCotizacion_ficha;
    }

    public void setCantidadCotizacion_ficha(int cantidadCotizacion_ficha) {
        this.cantidadCotizacion_ficha = cantidadCotizacion_ficha;
    }

    public Date getFechaCotizacion_ficha() {
        return fechaCotizacion_ficha;
    }

    public void setFechaCotizacion_ficha(Date fechaCotizacion_ficha) {
        this.fechaCotizacion_ficha = fechaCotizacion_ficha;
    }

    public int getIdSatelite() {
        return idSatelite;
    }

    public void setIdSatelite(int idSatelite) {
        this.idSatelite = idSatelite;
    }

    public String getDescriCoti_ficha() {
        return descriCoti_ficha;
    }

    public void setDescriCoti_ficha(String descriCoti_ficha) {
        this.descriCoti_ficha = descriCoti_ficha;
    }
    
}
