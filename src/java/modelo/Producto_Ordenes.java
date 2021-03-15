package modelo;

import java.util.Date;

public class Producto_Ordenes {
    private int idOrdenpago;
    private Productos producto;
    private String idTalla;
    private int cantidad;
    private double total;
    
    //Variables para la información completa de las ordenes de pago
    private int idCliente;
    private String nomCliente;
    private String apellCliente;
    private Date fechaOrdenpago;
    private Double valorTotalOrdenpago;
    private int idProducto;
    private String nomProducto;
    private String descriProducto;
    private Double precioProducto;
    private String tipopagoOrdenpago;

    public int getIdOrdenpago() {
        return idOrdenpago;
    }

    public void setIdOrdenpago(int idOrdenpago) {
        this.idOrdenpago = idOrdenpago;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
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

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
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

    public Date getFechaOrdenpago() {
        return fechaOrdenpago;
    }

    public void setFechaOrdenpago(Date fechaOrdenpago) {
        this.fechaOrdenpago = fechaOrdenpago;
    }

    public Double getValorTotalOrdenpago() {
        return valorTotalOrdenpago;
    }

    public void setValorTotalOrdenpago(Double valorTotalOrdenpago) {
        this.valorTotalOrdenpago = valorTotalOrdenpago;
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

    public String getTipopagoOrdenpago() {
        return tipopagoOrdenpago;
    }

    public void setTipopagoOrdenpago(String tipopagoOrdenpago) {
        this.tipopagoOrdenpago = tipopagoOrdenpago;
    }

    public Double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(Double precioProducto) {
        this.precioProducto = precioProducto;
    }

}
