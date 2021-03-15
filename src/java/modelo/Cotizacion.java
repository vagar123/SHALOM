package modelo;

import java.util.Date;

public class Cotizacion {
    private int idCotizacion;
    private int idCliente;
    private int idUsuario;
    private Date fechaCotizacion;
    private Double valortotalCotizacion;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.idCotizacion;
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
        final Cotizacion other = (Cotizacion) obj;
        if (this.idCotizacion != other.idCotizacion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s[codigo=%d]", getClass().getSimpleName(), getIdCotizacion());
    }
    
    public int getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(int idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaCotizacion() {
        return fechaCotizacion;
    }

    public void setFechaCotizacion(Date fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
    }

    public Double getValortotalCotizacion() {
        return valortotalCotizacion;
    }

    public void setValortotalCotizacion(Double valortotalCotizacion) {
        this.valortotalCotizacion = valortotalCotizacion;
    }
}
