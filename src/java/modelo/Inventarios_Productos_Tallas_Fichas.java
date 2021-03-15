package modelo;

public class Inventarios_Productos_Tallas_Fichas {
    
    private String descriProducto;
    private int  idProducto;
    private int idInventario;
    private String idTalla;
    private int cantidadProducto;
    private int idFicha_produccio;

    public String getDescriProducto() {
        return descriProducto;
    }

    public void setDescriProducto(String descriProducto) {
        this.descriProducto = descriProducto;
    }

    
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public String getIdTalla() {
        return idTalla;
    }

    public void setIdTalla(String idTalla) {
        this.idTalla = idTalla;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public int getIdFicha_produccio() {
        return idFicha_produccio;
    }

    public void setIdFicha_produccio(int idFicha_produccio) {
        this.idFicha_produccio = idFicha_produccio;
    }
}
