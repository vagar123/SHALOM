
package modelo;

public class Locales {
    private int idLocal;
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.idLocal;
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
        final Locales other = (Locales) obj;
        if (this.idLocal != other.idLocal) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        //return "Producto{" + "codigo=" + codigo + '}';
        return String.format("%s[codigo=%d]", getClass().getSimpleName(), getIdLocal());
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }
    
}
