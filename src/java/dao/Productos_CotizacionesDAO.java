package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import modelo.Producto_Cotizaciones;

public class Productos_CotizacionesDAO extends DAO {

    public void registrar(Producto_Cotizaciones coti) throws Exception {
        if (coti != null && coti.getIdCotizacion() != 0) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO productos_cotizaciones (idCotizacion, idProducto, idTalla, cantidad, valorUnitario) values(?, ?, ?, ?, ?)");
                ps.setInt(1, coti.getIdCotizacion());
                ps.setInt(2, coti.getProducto().getIdProducto());
                ps.setString(3, coti.getIdTalla());
                ps.setInt(4, coti.getCantidad());
                ps.setDouble(5, coti.getValorUnitario());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Producto_Cotizaciones> listar() throws SQLException {
        List<Producto_Cotizaciones> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("select cotizaciones.idCotizacion, cotizaciones.idCliente, clientes.nomCliente, clientes.apellCliente, cotizaciones.idUsuario, \n"
                    + "cotizaciones.fechaCotizacion, productos_cotizaciones.idProducto, productos.nomProducto, productos_cotizaciones.idTalla, \n"
                    + "productos_cotizaciones.cantidad, productos_cotizaciones.valorUnitario, cotizaciones.valortotalCotizacion\n"
                    + "from clientes inner join cotizaciones\n"
                    + "on clientes.idCliente = cotizaciones.idCliente\n"
                    + "inner join productos_cotizaciones \n"
                    + "on cotizaciones.idCotizacion = productos_cotizaciones.idCotizacion\n"
                    + "inner join productos\n"
                    + "on productos_cotizaciones.idProducto = productos.idProducto;");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Producto_Cotizaciones l = new Producto_Cotizaciones();
                l.setIdCotizacion(rs.getInt("cotizaciones.idCotizacion"));
                l.setIdCliente(rs.getInt("cotizaciones.idCliente"));
                l.setNomCliente(rs.getString("clientes.nomCliente"));
                l.setApellCliente(rs.getString("clientes.apellCliente"));
                l.setIdUsuario(rs.getInt("cotizaciones.idUsuario"));
                l.setFechaCotización(rs.getDate("cotizaciones.fechaCotizacion"));
                l.setIdProducto(rs.getInt("productos_cotizaciones.idProducto"));
                l.setNomProducto(rs.getString("productos.nomProducto"));
                l.setIdTalla(rs.getString("productos_cotizaciones.idTalla"));
                l.setCantidad(rs.getInt("productos_cotizaciones.cantidad"));
                l.setValorUnitario(rs.getDouble("productos_cotizaciones.valorUnitario"));
                l.setValortotalCotizacion(rs.getDouble("cotizaciones.valortotalCotizacion"));
                lista.add(l);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Producto_Cotizaciones leedId(Producto_Cotizaciones coti) throws Exception {
        Producto_Cotizaciones cotizacion;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM productos_cotizaciones WHERE idCotizacion = ?");
            ps.setInt(1, coti.getIdCotizacion());
            rs = ps.executeQuery();
            rs.next();
            cotizacion = new Producto_Cotizaciones();
            cotizacion.setIdCotizacion(rs.getInt("idCotizacion"));
            cotizacion.setIdProducto(rs.getInt("idProducto"));
            cotizacion.setIdTalla(rs.getString("idTalla"));
            cotizacion.setCantidad(rs.getInt("cantidad"));
            cotizacion.setValorUnitario(rs.getDouble("valorUnitario"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return cotizacion;
    }

    public void actualizar(Producto_Cotizaciones pro) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE productos_cotizaciones SET idCotizacion = ?, idProducto = ?, idTalla = ?, cantidad = ?, valorUnitario = ? WHERE idCotizacion = ?");
            ps.setInt(1, pro.getIdCotizacion());
            ps.setInt(2, pro.getIdProducto());
            ps.setString(3, pro.getIdTalla());
            ps.setInt(4, pro.getCantidad());
            ps.setDouble(5, pro.getValorUnitario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Producto_Cotizaciones coti) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM productos_cotizaciones WHERE idCotizacion = ?");
            ps.setInt(1, coti.getIdCotizacion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
