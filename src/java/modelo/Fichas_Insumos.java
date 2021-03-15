package modelo;

public class Fichas_Insumos {
   
    private int idFicha_produccion;
    private Insumos Insumo;
    private int cantidad;
    private double valorInsumos;

    //Variables usadas para el ingreso individual a la tabla fichas_insumos
    private String descripcionFicha_producc;
    private int idSatelite;
    private String nomSatelite;
    private int idInsumo; 
    private String nomInsumo;
    private double valorTotalFicha_producc;
    
    public int getIdFicha_produccion() {
        return idFicha_produccion;
    }

    public void setIdFicha_produccion(int idFicha_produccion) {
        this.idFicha_produccion = idFicha_produccion;
    }

    public Insumos getInsumo() {
        return Insumo;
    }

    public void setInsumo(Insumos Insumo) {
        this.Insumo = Insumo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValorInsumos() {
        return valorInsumos;
    }

    public void setValorInsumos(double valorInsumos) {
        this.valorInsumos = valorInsumos;
    }

    //Variables usadas para el ingreso individual a la tabla fichas_insumos

    public String getDescripcionFicha_producc() {
        return descripcionFicha_producc;
    }

    public void setDescripcionFicha_producc(String descripcionFicha_producc) {
        this.descripcionFicha_producc = descripcionFicha_producc;
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

    public double getValorTotalFicha_producc() {
        return valorTotalFicha_producc;
    }

    public void setValorTotalFicha_producc(double valorTotalFicha_producc) {
        this.valorTotalFicha_producc = valorTotalFicha_producc;
    }
}
