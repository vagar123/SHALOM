package modelo;

import java.util.Date;

public class Inventarios{
    private int idInventario;
    private String nomInventario;
    private int existInventario;
    private Date fechaInventario;
    private int idAdmin;
    private int idLocal;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.idInventario;
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
        final Inventarios other = (Inventarios) obj;
        if (this.idInventario != other.idInventario) {
            return false;
        }
        return true;
    }

   
    @Override
    public String toString() {
        //return "Producto{" + "codigo=" + codigo + '}';
        return String.format("%s[codigo=%d]", getClass().getSimpleName(), getIdInventario());
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public String getNomInventario() {
        return nomInventario;
    }

    public void setNomInventario(String nomInventario) {
        this.nomInventario = nomInventario;
    }

    public int getExistInventario() {
        return existInventario;
    }

    public void setExistInventario(int existInventario) {
        this.existInventario = existInventario;
    }

    public Date getFechaInventario() {
        return fechaInventario;
    }

    public void setFechaInventario(Date fechaInventario) {
        this.fechaInventario = fechaInventario;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

}