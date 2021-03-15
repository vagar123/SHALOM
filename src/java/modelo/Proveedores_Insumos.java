package modelo;

public class Proveedores_Insumos {
    
    private int idProveedor;
    private String nomProveedor;
    private int idInsumo;
    private String nomInsumo;
    private String nomMedida;
    private String descriInsumo;
    private int cantidaInsumo;

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

    public String getDescriInsumo() {
        return descriInsumo;
    }

    public void setDescriInsumo(String descriInsumo) {
        this.descriInsumo = descriInsumo;
    }

    public int getCantidaInsumo() {
        return cantidaInsumo;
    }

    public void setCantidaInsumo(int cantidaInsumo) {
        this.cantidaInsumo = cantidaInsumo;
    }  
}
