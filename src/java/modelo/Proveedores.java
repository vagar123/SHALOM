package modelo;

import java.util.Date;

public class Proveedores{
    private int idProveedor;
    private String nomProveedor;
    private int telefProveedor;
    private String correoProveedor;
    private String direccProveedor;
    private Date fechaEntrada;
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.idProveedor;
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
        final Proveedores other = (Proveedores) obj;
        if (this.idProveedor != other.idProveedor) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        //return "Producto{" + "codigo=" + codigo + '}';
        return String.format("%s[codigo=%d]", getClass().getSimpleName(), getIdProveedor());
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNomProveedor() {
        return nomProveedor;
    }

    public void setNomProveedor(String nomProveedor) {
        this.nomProveedor = nomProveedor;
    }

    public int getTelefProveedor() {
        return telefProveedor;
    }

    public void setTelefProveedor(int telefProveedor) {
        this.telefProveedor = telefProveedor;
    }

    public String getCorreoProveedor() {
        return correoProveedor;
    }

    public void setCorreoProveedor(String correoProveedor) {
        this.correoProveedor = correoProveedor;
    }

    public String getDireccProveedor() {
        return direccProveedor;
    }

    public void setDireccProveedor(String direccProveedor) {
        this.direccProveedor = direccProveedor;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

}