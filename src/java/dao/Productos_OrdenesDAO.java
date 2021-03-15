package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import modelo.Producto_Ordenes;

public class Productos_OrdenesDAO extends DAO {

    public void registrar(Producto_Ordenes orden) throws Exception {
        if (orden != null && orden.getIdOrdenpago() != 0) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO productos_ordenes (idOrdenpago, idProducto, idTalla, cantidad, total) values(?, ?, ?, ?, ?)");
                ps.setInt(1, orden.getIdOrdenpago());
                ps.setInt(2, orden.getProducto().getIdProducto());
                ps.setString(3, orden.getIdTalla());
                ps.setInt(4, orden.getCantidad());
                ps.setDouble(5, orden.getTotal());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Producto_Ordenes> listar() throws SQLException {
        List<Producto_Ordenes> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("select ordenes_pago.idOrdenpago, ordenes_pago.fechaOrdenpago, ordenes_pago.tipopagoOrdenpago, clientes.idCliente,\n"
                    + "clientes.nomCliente, clientes.apellCliente, productos.idProducto, productos.nomProducto, productos.descriProducto,\n"
                    + "productos_ordenes.idTalla, productos_ordenes.cantidad, productos.precioProducto, productos_ordenes.total, \n"
                    + "ordenes_pago.valorTotalOrdenpago\n"
                    + "from clientes inner join ordenes_pago  \n"
                    + "on clientes.idCliente = ordenes_pago.idCliente\n"
                    + "inner join productos_ordenes on \n"
                    + "ordenes_pago.idOrdenpago = productos_ordenes.idOrdenpago\n"
                    + "inner join productos on\n"
                    + "productos_ordenes.idProducto = productos.idProducto;");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Producto_Ordenes l = new Producto_Ordenes();               
                l.setIdOrdenpago(rs.getInt("ordenes_pago.idOrdenpago"));
                l.setFechaOrdenpago(rs.getDate("ordenes_pago.fechaOrdenpago"));
                l.setTipopagoOrdenpago(rs.getString("ordenes_pago.tipopagoOrdenpago"));
                l.setIdCliente(rs.getInt("clientes.idCliente"));
                l.setNomCliente(rs.getString("clientes.nomCliente"));
                l.setApellCliente(rs.getString("clientes.apellCliente"));
                l.setIdProducto(rs.getInt("productos.idProducto"));
                l.setNomProducto(rs.getString("productos.nomProducto"));
                l.setDescriProducto(rs.getString("productos.descriProducto"));
                l.setIdTalla(rs.getString("productos_ordenes.idTalla"));
                l.setCantidad(rs.getInt("productos_ordenes.cantidad"));
                l.setPrecioProducto(rs.getDouble("productos.precioProducto"));
                l.setTotal(rs.getDouble("productos_ordenes.total"));
                l.setValorTotalOrdenpago(rs.getDouble("ordenes_pago.valorTotalOrdenpago"));
                lista.add(l);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Producto_Ordenes leedId(Producto_Ordenes ord) throws Exception {
        Producto_Ordenes orden;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM productos_ordenes WHERE idOrdenpago = ?");
            ps.setInt(1, ord.getIdOrdenpago());
            rs = ps.executeQuery();
            rs.next();
            orden = new Producto_Ordenes();
            orden.setIdOrdenpago(rs.getInt("idOrdenpago"));
            orden.setIdProducto(rs.getInt("idProducto"));
            orden.setIdTalla(rs.getString("idTalla"));
            orden.setCantidad(rs.getInt("cantidad"));
            orden.setTotal(rs.getDouble("total"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return orden;
    }

    public void actualizar(Producto_Ordenes pro) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE productos_ordenes SET idOrdenpago = ?, idProducto = ?, idTalla = ?, cantidad = ?, total = ? WHERE idOrdenpago = ?");
            ps.setInt(1, pro.getIdOrdenpago());
            ps.setInt(2, pro.getIdProducto());
            ps.setString(3, pro.getIdTalla());
            ps.setInt(4, pro.getCantidad());
            ps.setDouble(5, pro.getTotal());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Producto_Ordenes coti) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM productos_ordenes WHERE idOrdenpago = ?");
            ps.setInt(1, coti.getIdOrdenpago());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
