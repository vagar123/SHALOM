package modelo;

public class graficoUsuarios {
    
    private int idUsuario;
    private String nomUsuario;
    private int idOrdenpago;
    private double valorTotalOrdenpago;
    private double valortotalCotizacion;

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

    public int getIdOrdenpago() {
        return idOrdenpago;
    }

    public void setIdOrdenpago(int idOrdenpago) {
        this.idOrdenpago = idOrdenpago;
    }

    public double getValorTotalOrdenpago() {
        return valorTotalOrdenpago;
    }

    public void setValorTotalOrdenpago(double valorTotalOrdenpago) {
        this.valorTotalOrdenpago = valorTotalOrdenpago;
    }

    public double getValortotalCotizacion() {
        return valortotalCotizacion;
    }

    public void setValortotalCotizacion(double valortotalCotizacion) {
        this.valortotalCotizacion = valortotalCotizacion;
    }

    
}
