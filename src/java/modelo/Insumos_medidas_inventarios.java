package modelo;

public class Insumos_medidas_inventarios {
    
    private int idInsumo;
    private String nomInsumo;
    private String nomMedida;
    private int idInventario;
    private int cantidaInsumo;

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
    
    public String getNomMedida() {
        return nomMedida;
    }

    public void setNomMedida(String nomMedida) {
        this.nomMedida = nomMedida;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getCantidaInsumo() {
        return cantidaInsumo;
    }

    public void setCantidaInsumo(int cantidaInsumo) {
        this.cantidaInsumo = cantidaInsumo;
    }

}
