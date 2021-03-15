package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.graficoUsuarios;

public class graficoUsuariosDAO extends DAO {

    public List<graficoUsuarios> listarGrafica() throws SQLException {
        List<graficoUsuarios> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("select usuarios.idUsuario, usuarios.nomUsuario, \n"
                    + "count(ordenes_pago.idOrdenpago), sum(ordenes_pago.valorTotalOrdenpago)\n"
                    + "from usuarios inner join ordenes_pago \n"
                    + "on usuarios.idUsuario = ordenes_pago.idUsuario\n"
                    + "inner join clientes\n"
                    + "on ordenes_pago.idCliente = clientes.idCliente\n"
                    + "inner join cotizaciones\n"
                    + "on clientes.idCliente = cotizaciones.idCliente group by usuarios.idUsuario;");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                graficoUsuarios p = new graficoUsuarios();
                p.setIdUsuario(rs.getInt("idUsuario"));
                p.setNomUsuario(rs.getString("nomUsuario"));
                p.setIdOrdenpago(rs.getInt("count(ordenes_pago.idOrdenpago)"));
                p.setValorTotalOrdenpago(rs.getDouble("sum(ordenes_pago.valorTotalOrdenpago)"));
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
