package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Ordenes_pago;
import modelo.Producto_Ordenes;
import modelo.Factura;

public class Ordenes_pagoDAO extends DAO {

    //Método para registrar las Ordenes de pago
    public void registrar(Ordenes_pago ordenPago, List<Producto_Ordenes> lista) throws SQLException {
        if (ordenPago != null && lista != null) {
            try {
                this.conectar();
                this.getConn().setAutoCommit(false);
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO ordenes_pago (tipopagoOrdenpago, idUsuario, idCliente, fechaOrdenpago, valorTotalOrdenpago) VALUES(?, ?, ?, ?, ?)");
                ps.setString(1, ordenPago.getTipopagoOrdenpago());
                ps.setInt(2, ordenPago.getIdUsuario());
                ps.setInt(3, ordenPago.getIdCliente());
                Calendar c = Calendar.getInstance();
                c.setTime(ordenPago.getFechaOrdenpago());
                java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
                ps.setDate(4, sqlDate);
                ps.setInt(5, ordenPago.getValorTotalOrdenpago());
                ps.executeUpdate();

                int codv;
                ps = this.getConn().prepareStatement("SELECT LAST_INSERT_ID() FROM ordenes_pago");
                ResultSet rs = ps.executeQuery();
                rs.next();
                codv = rs.getInt(1);

                for (Producto_Ordenes det : lista) {

                    //Validación nombre
                    ps = this.getConn().prepareStatement("select nomProducto, descriProducto\n"
                            + "from productos \n"
                            + "where idProducto = ?");
                    ps.setInt(1, det.getProducto().getIdProducto());
                    rs = ps.executeQuery();
                    rs.next();
                    String nombre = rs.getString("nomProducto") + ". " + rs.getString("descriProducto");

                    //Validación cantidad
                    ps = this.getConn().prepareStatement("select inventarios_productos_tallas_fichas.cantidadProducto\n"
                            + "from inventarios_productos_tallas_fichas\n"
                            + "where inventarios_productos_tallas_fichas.idProducto = ? and inventarios_productos_tallas_fichas.idTalla = ?;");
                    ps.setInt(1, det.getProducto().getIdProducto());
                    ps.setString(2, det.getIdTalla());
                    rs = ps.executeQuery();
                    rs.next();
                    int valida = rs.getInt("inventarios_productos_tallas_fichas.cantidadProducto");

                    ps = this.getConn().prepareStatement("INSERT INTO productos_ordenes(idOrdenpago, idProducto, idTalla, cantidad, total) VALUES (?, ?, ?, ?, ?)");
                    ps.setInt(1, codv);
                    ps.setInt(2, det.getProducto().getIdProducto());
                    ps.setString(3, det.getIdTalla());
                    if (det.getCantidad() <= valida) {
                        ps.setInt(4, det.getCantidad());
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "La cantidad solicitada no esta disponible del producto: "+nombre, "La cantidad solicitada no esta disponible del producto: "+nombre));
                    }
                    ps.setDouble(5, det.getTotal());
                    ps.executeUpdate();
                }
                this.getConn().commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Venta exitoso", "Registro de Venta exitoso"));
            } catch (SQLException e) {
                this.getConn().rollback();
                System.out.println("error generando orden de pago");
            }
        }
    }

    //Método para listar todas las ordenes de pago
    public List<Ordenes_pago> listar() throws SQLException {
        List<Ordenes_pago> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM ordenes_pago");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Ordenes_pago o = new Ordenes_pago();
                o.setIdOrdenpago(rs.getInt("idOrdenpago"));
                o.setTipopagoOrdenpago(rs.getString("tipopagoOrdenpago"));
                o.setIdUsuario(rs.getInt("idUsuario"));
                o.setIdCliente(rs.getInt("idCliente"));
                o.setFechaOrdenpago(rs.getDate("fechaOrdenpago"));
                o.setValorTotalOrdenpago(rs.getInt("valorTotalOrdenpago"));
                lista.add(o);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    //Método para listar los productos de una Orden de pago por el ID
    public List<Factura> listarFactura(Ordenes_pago orden) throws SQLException {
        List<Factura> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("select ordenes_pago.fechaOrdenpago, ordenes_pago.idOrdenpago, clientes.idCliente,\n"
                    + "clientes.nomCliente ,clientes.apellCliente,\n"
                    + "ordenes_pago.tipopagoOrdenpago, productos.idProducto, productos.nomProducto, productos.descriProducto,\n"
                    + "productos_ordenes.idTalla, productos_ordenes.cantidad, productos.precioProducto, productos_ordenes.total, \n"
                    + "ordenes_pago.valorTotalOrdenpago\n"
                    + "from clientes inner join ordenes_pago on\n"
                    + "clientes.idCliente = ordenes_pago.idCliente\n"
                    + "inner join productos_ordenes on\n"
                    + "ordenes_pago.idOrdenpago = productos_ordenes.idOrdenpago\n"
                    + "inner join productos\n"
                    + "on productos_ordenes.idProducto = productos.idProducto"
                    + "where ordenes_pago.idOrdenpago = ?");
            ps.setInt(1, orden.getIdOrdenpago());
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Factura factura = new Factura();
                factura.setIdOrdenpago(rs.getInt("idOrdenpago"));
                factura.setFechaOrdenpago(rs.getDate("fechaOrdenpago"));
                factura.setIdCliente(rs.getInt("idCliente"));
                factura.setNomCliente(rs.getString("nomCliente"));
                factura.setApellCliente(rs.getString("apellCliente"));
                factura.setTipopagoOrdenpago(rs.getString("tipopagoOrdenpago"));
                factura.setIdProducto(rs.getInt("idProducto"));
                factura.setNomProducto(rs.getString("nomProducto"));
                factura.setDescriProducto(rs.getString("descriProducto"));
                factura.setIdTalla(rs.getString("idTalla"));
                factura.setCantidad(rs.getInt("cantidad"));
                factura.setPrecioProducto(rs.getInt("precioProducto"));
                factura.setTotal(rs.getDouble("precioProducto"));
                factura.setValorTotalOrdenpago(rs.getDouble("valorTotalOrdenpago"));
                lista.add(factura);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    //Método para ver los datos de una orden de pago de acuerdo a un ID
    public Factura leedId(Ordenes_pago orden) throws Exception {
        Factura factura;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("select ordenes_pago.fechaOrdenpago, ordenes_pago.idOrdenpago, clientes.idCliente,\n"
                    + "clientes.nomCliente ,clientes.apellCliente,\n"
                    + "ordenes_pago.tipopagoOrdenpago, productos.idProducto, productos.nomProducto, productos.descriProducto,\n"
                    + "productos_ordenes.idTalla, productos_ordenes.cantidad, productos.precioProducto, productos_ordenes.total, \n"
                    + "ordenes_pago.valorTotalOrdenpago\n"
                    + "from clientes inner join ordenes_pago on\n"
                    + "clientes.idCliente = ordenes_pago.idCliente\n"
                    + "inner join productos_ordenes on\n"
                    + "ordenes_pago.idOrdenpago = productos_ordenes.idOrdenpago\n"
                    + "inner join productos\n"
                    + "on productos_ordenes.idProducto = productos.idProducto  \n"
                    + "where ordenes_pago.idOrdenpago = ?");
            ps.setInt(1, orden.getIdOrdenpago());
            rs = ps.executeQuery();
            rs.next();
            factura = new Factura();
            factura.setIdOrdenpago(rs.getInt("idOrdenpago"));
            factura.setFechaOrdenpago(rs.getDate("fechaOrdenpago"));
            factura.setIdCliente(rs.getInt("idCliente"));
            factura.setNomCliente(rs.getString("nomCliente"));
            factura.setApellCliente(rs.getString("apellCliente"));
            factura.setTipopagoOrdenpago(rs.getString("tipopagoOrdenpago"));
            factura.setIdProducto(rs.getInt("idProducto"));
            factura.setNomProducto(rs.getString("nomProducto"));
            factura.setDescriProducto(rs.getString("descriProducto"));
            factura.setIdTalla(rs.getString("idTalla"));
            factura.setCantidad(rs.getInt("cantidad"));
            factura.setPrecioProducto(rs.getInt("precioProducto"));
            factura.setTotal(rs.getDouble("precioProducto"));
            factura.setValorTotalOrdenpago(rs.getDouble("valorTotalOrdenpago"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return factura;
    }

    //Método para actualizar una orden de pago -- No usado por ahora
    public void actualizar(Ordenes_pago orden) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE ordenes_pago SET tipopagoOrdenpago = ?, idUsuario = ?, idCliente = ?, fechaOrdenpago = ?, valorTotalOrdenpago = ?, Iva = ?  WHERE idOrdenpago = ?");

            ps.setString(1, orden.getTipopagoOrdenpago());
            ps.setInt(2, orden.getIdUsuario());
            ps.setInt(3, orden.getIdCliente());
            Calendar c = Calendar.getInstance();
            c.setTime(orden.getFechaOrdenpago());
            java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
            ps.setDate(4, sqlDate);
            ps.setDouble(5, orden.getValorTotalOrdenpago());
            ps.setInt(7, orden.getIdOrdenpago());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    //Método para eliminar una orden de pago
    public void eliminar(Ordenes_pago orden) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM ordenes_pago WHERE idOrdenpago = ?");
            ps.setInt(1, orden.getIdOrdenpago());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
