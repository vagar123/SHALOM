package modelo;

import java.util.Date;

public class Ordenes_pago {
    private int idOrdenpago;
    private String tipopagoOrdenpago;
    private int idUsuario;
    private int idCliente;
    private Date fechaOrdenpago;
    private int valorTotalOrdenpago;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.idOrdenpago;
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
        final Ordenes_pago other = (Ordenes_pago) obj;
        if (this.idOrdenpago != other.idOrdenpago) {
            return false;
        }
        return true;
    }
    
    public int getIdOrdenpago() {
        return idOrdenpago;
    }

    public void setIdOrdenpago(int idOrdenpago) {
        this.idOrdenpago = idOrdenpago;
    }

    public String getTipopagoOrdenpago() {
        return tipopagoOrdenpago;
    }

    public void setTipopagoOrdenpago(String tipopagoOrdenpago) {
        this.tipopagoOrdenpago = tipopagoOrdenpago;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaOrdenpago() {
        return fechaOrdenpago;
    }

    public void setFechaOrdenpago(Date fechaOrdenpago) {
        this.fechaOrdenpago = fechaOrdenpago;
    }

    public int getValorTotalOrdenpago() {
        return valorTotalOrdenpago;
    }

    public void setValorTotalOrdenpago(int valorTotalOrdenpago) {
        this.valorTotalOrdenpago = valorTotalOrdenpago;
    }
}
