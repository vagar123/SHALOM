package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import modelo.Cotizacion;
import modelo.Producto_Cotizaciones;

public class CotizacionDAO extends DAO {

    public void registrar(Cotizacion cotizacion, List<Producto_Cotizaciones> lista) throws SQLException {
        if (cotizacion != null && lista != null) {
            try {
                this.conectar();
                this.getConn().setAutoCommit(false);
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO cotizaciones (idCliente, idUsuario, fechaCotizacion, valortotalCotizacion) VALUES(?, ?, ?, ?)");
                ps.setInt(1, cotizacion.getIdCliente());
                ps.setInt(2, cotizacion.getIdUsuario());
                Calendar c = Calendar.getInstance();
                c.setTime(cotizacion.getFechaCotizacion());
                java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
                ps.setDate(3, sqlDate);
                ps.setDouble(4, cotizacion.getValortotalCotizacion());
                ps.executeUpdate();

                int codv;
                ps = this.getConn().prepareStatement("SELECT LAST_INSERT_ID() FROM cotizaciones");
                ResultSet rs = ps.executeQuery();
                rs.next();
                codv = rs.getInt(1);

                for (Producto_Cotizaciones det : lista) {
                    ps = this.getConn().prepareStatement("INSERT INTO productos_cotizaciones(idCotizacion, idProducto, idTalla, cantidad, valorUnitario) VALUES (?, ?, ?, ?, ?)");
                    ps.setInt(1, codv);
                    ps.setInt(2, det.getProducto().getIdProducto());
                    ps.setString(3, det.getIdTalla());
                    ps.setInt(4, det.getCantidad());
                    ps.setDouble(5, det.getValorUnitario());
                    ps.executeUpdate();
                }
                this.getConn().commit();
            } catch (SQLException e) {
                this.getConn().rollback();
            }
        }
    }

    public List<Cotizacion> listar() throws SQLException {
        List<Cotizacion> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM cotizaciones");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Cotizacion c = new Cotizacion();
                c.setIdCotizacion(rs.getInt("idCotizacion"));
                c.setIdCliente(rs.getInt("idCliente"));
                c.setIdUsuario(rs.getInt("idUsuario"));
                c.setFechaCotizacion(rs.getDate("fechaCotizacion"));
                c.setValortotalCotizacion(rs.getDouble("valortotalCotizacion"));
                lista.add(c);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Cotizacion leedId(Cotizacion coti) throws Exception {
        Cotizacion cotiza;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM cotizaciones WHERE idCotizacion = ?");
            ps.setInt(1, coti.getIdCotizacion());
            rs = ps.executeQuery();
            rs.next();
            cotiza = new Cotizacion();
            cotiza.setIdCotizacion(rs.getInt("idCotizacion"));
            cotiza.setIdCliente(rs.getInt("idCliente"));
            cotiza.setIdUsuario(rs.getInt("idUsuario"));
            cotiza.setFechaCotizacion(rs.getDate("fechaCotizacion"));
            cotiza.setValortotalCotizacion(rs.getDouble("valortotalCotizacion"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }

        return cotiza;
    }

    public void actualizar(Cotizacion coti) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE cotizaciones SET idCliente = ?, idUsuario = ?, fechaCotizacion = ?, valortotalCotizacion = ? WHERE idCotizacion = ?");
           
            ps.setInt(1, coti.getIdCliente());
            ps.setInt(2, coti.getIdUsuario());
            Calendar c = Calendar.getInstance();
            c.setTime(coti.getFechaCotizacion());
            java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
            ps.setDate(3, sqlDate);
            ps.setDouble(4, coti.getValortotalCotizacion());
            ps.setInt(5, coti.getIdCotizacion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Cotizacion coti) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM cotizaciones WHERE idCotizacion = ?");
            ps.setInt(1, coti.getIdCotizacion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}