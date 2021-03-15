package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Fichas_produccion;

public class graficoFichasDAO extends DAO{
    
    public List<Fichas_produccion> listarGrafica() throws SQLException {
        List<Fichas_produccion> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM fichas_produccion GROUP BY productFicha_producc;");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Fichas_produccion p = new Fichas_produccion();
                p.setIdFicha_produccion(rs.getInt("idFicha_produccion"));
                p.setProductFicha_producc(rs.getString("productFicha_producc"));
                p.setFechaIFicha_producc(rs.getDate("fechaIFicha_producc"));
                p.setFechaSFicha_producc(rs.getDate("fechaSFicha_producc"));
                p.setCantidadFicha_producc(rs.getInt("cantidadFicha_producc"));
                p.setTiempoFicha_producc(rs.getString("tiempoFicha_producc"));
                p.setTallaFicha_producc(rs.getString("tallaFicha_producc"));
                p.setValorTotalFicha_producc(rs.getInt("valorTotalFicha_producc"));
                p.setCategoriaFicha_producc(rs.getString("categoriaFicha_producc"));
                p.setGeneroFicha_producc(rs.getString("generoFicha_producc"));
                p.setColorFicha_producc(rs.getString("colorFicha_producc"));
                p.setDescripcionFicha_producc(rs.getString("descripcionFicha_producc"));
                p.setIdOrden_produccion(rs.getInt("idOrden_produccion"));
                p.setIdSatelite(rs.getInt("idSatelite"));
                p.setEstadoFicha_producc(rs.getString("estadoFicha_producc"));
                lista.add(p);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }
}
