package modelo;

import java.util.Date;

public class Producto_Cotizaciones {
    
    private int idCotizacion;
    private Productos producto;
    private String idTalla;
    private int cantidad;
    private double valorUnitario;
    
    //Variables para la información completa de las cotizaciones
    private int idCliente;
    private String nomCliente;
    private String apellCliente;
    private int idUsuario;
    private Date fechaCotización;
    private Double valortotalCotizacion;
    private int idProducto;
    private String nomProducto;

    public int getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(int idCotizacion) {
        this.idCotizacion = idCotizacion;
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

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    } 
    
    //Getters y Setters para la información completa de las cotizaciones

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

    public Date getFechaCotización() {
        return fechaCotización;
    }

    public void setFechaCotización(Date fechaCotización) {
        this.fechaCotización = fechaCotización;
    }

    public Double getValortotalCotizacion() {
        return valortotalCotizacion;
    }

    public void setValortotalCotizacion(Double valortotalCotizacion) {
        this.valortotalCotizacion = valortotalCotizacion;
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
    
    
}
