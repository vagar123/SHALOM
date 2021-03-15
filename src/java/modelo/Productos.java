package modelo;

public class Productos{
    private int idProducto;
    private String nomProducto;
    private double precioProducto;
    private String generoProducto;
    private String colorProducto;
    private String categoriaProducto;
    private String descriProducto;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.idProducto;
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
        final Productos other = (Productos) obj;
        if (this.idProducto != other.idProducto) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        //return "Producto{" + "codigo=" + codigo + '}';
        return String.format("%s[codigo=%d]", getClass().getSimpleName(), getIdProducto());
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

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getGeneroProducto() {
        return generoProducto;
    }

    public void setGeneroProducto(String generoProducto) {
        this.generoProducto = generoProducto;
    }

    public String getColorProducto() {
        return colorProducto;
    }

    public void setColorProducto(String colorProducto) {
        this.colorProducto = colorProducto;
    }

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public String getDescriProducto() {
        return descriProducto;
    }

    public void setDescriProducto(String descriProducto) {
        this.descriProducto = descriProducto;
    } 
}