package modelo;

import java.util.Date;

public class Clientes {
    private int idCliente;
    private String nomCliente;
    private String apellCliente;
    private int telefCliente;
    private String correoCliente;
    private Date fechaNacimiento;
    private String generoCliente;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.idCliente;
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
        final Clientes other = (Clientes) obj;
        if (this.idCliente != other.idCliente) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        //return "Producto{" + "codigo=" + codigo + '}';
        return String.format("%s[codigo=%d]", getClass().getSimpleName(), getIdCliente());
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

    public int getTelefCliente() {
        return telefCliente;
    }

    public void setTelefCliente(int telefCliente) {
        this.telefCliente = telefCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGeneroCliente() {
        return generoCliente;
    }

    public void setGeneroCliente(String generoCliente) {
        this.generoCliente = generoCliente;
    }  
}
