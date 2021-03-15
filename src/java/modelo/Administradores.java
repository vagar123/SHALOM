package modelo;

public class Administradores {
    
    private int idAdmin;
    private int idUsuario;
    private String nomUsuario;
    private String apellUsuario;

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

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

    public String getApellUsuario() {
        return apellUsuario;
    }

    public void setApellUsuario(String apellUsuario) {
        this.apellUsuario = apellUsuario;
    }
    
}
