package modelo;

public class Insumos {

    private int idInsumo;
    private String nomInsumo;
    private Double precioUniInsumo;
    private String categoriaInsumo;
    private String colorInsumo;
    private String descriInsumo;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.idInsumo;
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
        final Insumos other = (Insumos) obj;
        if (this.idInsumo != other.idInsumo) {
            return false;
        }
        return true;
    }
    
     @Override
    public String toString() {
        //return "Producto{" + "codigo=" + codigo + '}';
        return String.format("%s[codigo=%d]", getClass().getSimpleName(), getIdInsumo());
    }
    

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getNomInsumo() {
        return nomInsumo;
    }

    public void setNomInsumo(String nomInsumo) {
        this.nomInsumo = nomInsumo;
    }

    public Double getPrecioUniInsumo() {
        return precioUniInsumo;
    }

    public void setPrecioUniInsumo(Double precioUniInsumo) {
        this.precioUniInsumo = precioUniInsumo;
    }

    public String getCategoriaInsumo() {
        return categoriaInsumo;
    }

    public void setCategoriaInsumo(String categoriaInsumo) {
        this.categoriaInsumo = categoriaInsumo;
    }

    public String getColorInsumo() {
        return colorInsumo;
    }

    public void setColorInsumo(String colorInsumo) {
        this.colorInsumo = colorInsumo;
    }

    public String getDescriInsumo() {
        return descriInsumo;
    }

    public void setDescriInsumo(String descriInsumo) {
        this.descriInsumo = descriInsumo;
    }
    
    
}