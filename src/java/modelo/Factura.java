package modelo;

import java.util.Date;

public class Factura {
    private int idOrdenpago;
    private Date fechaOrdenpago;
    private int idCliente;
    private String nomCliente;
    private String apellCliente;
    private int idUsuario;
    private String nomUsuario;
    private String apellUsuario;
    private String tipopagoOrdenpago;
    private int idProducto;
    private String nomProducto;
    private String descriProducto;
    private String idTalla;
    private int cantidad;
    private int precioProducto;
    private double total;
    private double valorTotalOrdenpago;

    public int getIdOrdenpago() {
        return idOrdenpago;
    }

    public void setIdOrdenpago(int idOrdenpago) {
        this.idOrdenpago = idOrdenpago;
    }

    public Date getFechaOrdenpago() {
        return fechaOrdenpago;
    }

    public void setFechaOrdenpago(Date fechaOrdenpago) {
        this.fechaOrdenpago = fechaOrdenpago;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomCliente() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente = nomCliente;
    }

    public String getApellCliente() {
        return apellCliente;
    }

    public void setApellCliente(String apellCliente) {
        this.apellCliente = apellCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getApellUsuario() {
        return apellUsuario;
    }

    public void setApellUsuario(String apellUsuario) {
        this.apellUsuario = apellUsuario;
    }

    public String getTipopagoOrdenpago() {
        return tipopagoOrdenpago;
    }

    public void setTipopagoOrdenpago(String tipopagoOrdenpago) {
        this.tipopagoOrdenpago = tipopagoOrdenpago;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    public String getDescriProducto() {
        return descriProducto;
    }

    public void setDescriProducto(String descriProducto) {
        this.descriProducto = descriProducto;
    }

    public String getIdTalla() {
        return idTalla;
    }

    public void setIdTalla(String idTalla) {
        this.idTalla = idTalla;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(int precioProducto) {
        this.precioProducto = precioProducto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getValorTotalOrdenpago() {
        return valorTotalOrdenpago;
    }

    public void setValorTotalOrdenpago(double valorTotalOrdenpago) {
        this.valorTotalOrdenpago = valorTotalOrdenpago;
    }
    
    
}
