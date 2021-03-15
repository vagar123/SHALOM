package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.graficoCotizaciones_Ficha;

public class graficoCotizaciones_FichaDAO extends DAO {

    public List<graficoCotizaciones_Ficha> listarGrafica() throws SQLException {
        List<graficoCotizaciones_Ficha> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("select sum(cotizaciones_fichas.cantidadCotizacion_ficha), cotizaciones_fichas.productoCotizacion_ficha\n"
                    + "from cotizaciones_fichas group by cotizaciones_fichas.productoCotizacion_ficha ;");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                graficoCotizaciones_Ficha p = new graficoCotizaciones_Ficha();
                p.setCantidadCotizacion_ficha(rs.getInt("sum(cotizaciones_fichas.cantidadCotizacion_ficha)"));
                p.setProductoCotizacion_ficha(rs.getString("cotizaciones_fichas.productoCotizacion_ficha"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }
}
