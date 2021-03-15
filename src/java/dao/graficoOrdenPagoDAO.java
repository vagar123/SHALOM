package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.graficoOrdenPago;

public class graficoOrdenPagoDAO extends DAO {

    public List<graficoOrdenPago> listarGrafica() throws SQLException {
        List<graficoOrdenPago> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("select ordenes_pago.idOrdenpago, sum(productos_ordenes.cantidad),\n"
                    + "productos.nomProducto\n"
                    + "from ordenes_pago inner join productos_ordenes\n"
                    + "on ordenes_pago.idOrdenpago = productos_ordenes.idOrdenpago\n"
                    + "inner join productos \n"
                    + "on productos_ordenes.idProducto = productos.idProducto\n"
                    + "group by productos.nomProducto;");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                graficoOrdenPago p = new graficoOrdenPago();
                p.setIdOrdenpago(rs.getInt("ordenes_pago.idOrdenpago"));
                p.setCantidad(rs.getInt("sum(productos_ordenes.cantidad)"));
                p.setNomProducto(rs.getString("productos.nomProducto"));
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
