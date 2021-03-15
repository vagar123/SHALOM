package modelo;

public class graficoOrdenPago {
    
    private int idOrdenpago;
    private int cantidad;
    private String nomProducto;

    public int getIdOrdenpago() {
        return idOrdenpago;
    }

    public void setIdOrdenpago(int idOrdenpago) {
        this.idOrdenpago = idOrdenpago;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

}
