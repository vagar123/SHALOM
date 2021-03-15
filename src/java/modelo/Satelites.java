package modelo;

import java.util.Date;

public class Satelites{
    private int idSatelite;
    private String nomSatelite;
    private int teleSatelite;
    private String direccSatelite;
    private String correoSatelite;
    private Date fechaISatelite;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.idSatelite;
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
        final Satelites other = (Satelites) obj;
        if (this.idSatelite != other.idSatelite) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        //return "Producto{" + "codigo=" + codigo + '}';
        return String.format("%s[codigo=%d]", getClass().getSimpleName(), getIdSatelite());
    }
    
    public Satelites(){
        
    }

    public Satelites(int idSatelite, String nomSatelite) {
        this.idSatelite = idSatelite;
        this.nomSatelite = nomSatelite;
    }
    
    
    public int getIdSatelite() {
        return idSatelite;
    }

    public void setIdSatelite(int idSatelite) {
        this.idSatelite = idSatelite;
    }

    public String getNomSatelite() {
        return nomSatelite;
    }

    public void setNomSatelite(String nomSatelite) {
        this.nomSatelite = nomSatelite;
    }

    public int getTeleSatelite() {
        return teleSatelite;
    }

    public void setTeleSatelite(int teleSatelite) {
        this.teleSatelite = teleSatelite;
    }

    public String getDireccSatelite() {
        return direccSatelite;
    }

    public void setDireccSatelite(String direccSatelite) {
        this.direccSatelite = direccSatelite;
    }

    public String getCorreoSatelite() {
        return correoSatelite;
    }

    public void setCorreoSatelite(String correoSatelite) {
        this.correoSatelite = correoSatelite;
    }

    public Date getFechaISatelite() {
        return fechaISatelite;
    }

    public void setFechaISatelite(Date fechaSatelite) {
        this.fechaISatelite = fechaSatelite;
    }

}