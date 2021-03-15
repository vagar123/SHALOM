package modelo;

import java.util.Date;

public class Ordenes_produccion {

    private int idOrden_produccion;
    private String productoOrden_produccion;
    private Date fechaOrden_produccion;
    private String generoProdOrden_produccion;
    private String tallaOrden_produccion;
    private int cantidadOrden_produccion;
    private String colorOrden_produccion;
    private Double valorUnitarioOrden_produccion;
    private Double descuentoOrden_produccion;
    private Double valorTotalOrden_produccion;
    private String categoriaOrden_produccion;
    private String descriOrden_produccion;
    private int idSatelite;
    private int idCotizacion_ficha;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.idOrden_produccion;
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
        final Ordenes_produccion other = (Ordenes_produccion) obj;
        if (this.idOrden_produccion != other.idOrden_produccion) {
            return false;
        }
        return true;
    }

    public int getIdOrden_produccion() {
        return idOrden_produccion;
    }

    public void setIdOrden_produccion(int idOrden_produccion) {
        this.idOrden_produccion = idOrden_produccion;
    }

    public String getProductoOrden_produccion() {
        return productoOrden_produccion;
    }

    public void setProductoOrden_produccion(String productoOrden_produccion) {
        this.productoOrden_produccion = productoOrden_produccion;
    }

    public Date getFechaOrden_produccion() {
        return fechaOrden_produccion;
    }

    public void setFechaOrden_produccion(Date fechaOrden_produccion) {
        this.fechaOrden_produccion = fechaOrden_produccion;
    }

    public String getGeneroProdOrden_produccion() {
        return generoProdOrden_produccion;
    }

    public void setGeneroProdOrden_produccion(String generoProdOrden_produccion) {
        this.generoProdOrden_produccion = generoProdOrden_produccion;
    }

    public String getTallaOrden_produccion() {
        return tallaOrden_produccion;
    }

    public void setTallaOrden_produccion(String tallaOrden_produccion) {
        this.tallaOrden_produccion = tallaOrden_produccion;
    }

    public int getCantidadOrden_produccion() {
        return cantidadOrden_produccion;
    }

    public void setCantidadOrden_produccion(int cantidadOrden_produccion) {
        this.cantidadOrden_produccion = cantidadOrden_produccion;
    }

    public String getColorOrden_produccion() {
        return colorOrden_produccion;
    }

    public void setColorOrden_produccion(String colorOrden_produccion) {
        this.colorOrden_produccion = colorOrden_produccion;
    }

    public Double getValorUnitarioOrden_produccion() {
        return valorUnitarioOrden_produccion;
    }

    public void setValorUnitarioOrden_produccion(Double valorUnitarioOrden_produccion) {
        this.valorUnitarioOrden_produccion = valorUnitarioOrden_produccion;
    }

    public Double getDescuentoOrden_produccion() {
        return descuentoOrden_produccion;
    }

    public void setDescuentoOrden_produccion(Double descuentoOrden_produccion) {
        this.descuentoOrden_produccion = descuentoOrden_produccion;
    }

    public Double getValorTotalOrden_produccion() {
        return valorTotalOrden_produccion;
    }

    public void setValorTotalOrden_produccion(Double valorTotalOrden_produccion) {
        this.valorTotalOrden_produccion = valorTotalOrden_produccion;
    }

    public String getCategoriaOrden_produccion() {
        return categoriaOrden_produccion;
    }

    public void setCategoriaOrden_produccion(String categoriaOrden_produccion) {
        this.categoriaOrden_produccion = categoriaOrden_produccion;
    }

    public String getDescriOrden_produccion() {
        return descriOrden_produccion;
    }

    public void setDescriOrden_produccion(String descriOrden_produccion) {
        this.descriOrden_produccion = descriOrden_produccion;
    }

    public int getIdSatelite() {
        return idSatelite;
    }

    public void setIdSatelite(int idSatelite) {
        this.idSatelite = idSatelite;
    }

    public int getIdCotizacion_ficha() {
        return idCotizacion_ficha;
    }

    public void setIdCotizacion_ficha(int idCotizacion_ficha) {
        this.idCotizacion_ficha = idCotizacion_ficha;
    }
}
