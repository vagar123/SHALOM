package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import modelo.Ordenes_produccion;

public class Ordenes_produccionDAO extends DAO{
    
    public void registrar(Ordenes_produccion OrdenP) throws Exception {
        if (OrdenP != null && !OrdenP.getProductoOrden_produccion().isEmpty()) {
            try {
                this.conectar();
                
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO ordenes_produccion (productoOrden_produccion, fechaOrden_produccion, generoProdOrden_produccion, tallaOrden_produccion, cantidadOrden_produccion, colorOrden_produccion, valorUnitarioOrden_produccion, descuentoOrden_produccion, valorTotalOrden_produccion, categoriaOrden_produccion, descriOrden_produccion, idSatelite, idCotizacion_ficha) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, OrdenP.getProductoOrden_produccion());
                Calendar c = Calendar.getInstance();
                c.setTime(OrdenP.getFechaOrden_produccion());
                java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
                ps.setDate(2, sqlDate);
                ps.setString(3, OrdenP.getGeneroProdOrden_produccion());
                ps.setString(4, OrdenP.getTallaOrden_produccion());
                ps.setInt(5, OrdenP.getCantidadOrden_produccion());
                ps.setString(6, OrdenP.getColorOrden_produccion());
                ps.setDouble(7, OrdenP.getValorUnitarioOrden_produccion());
                ps.setDouble(8, OrdenP.getDescuentoOrden_produccion());
                ps.setDouble(9, OrdenP.getValorTotalOrden_produccion());
                ps.setString(10, OrdenP.getCategoriaOrden_produccion());
                ps.setString(11, OrdenP.getDescriOrden_produccion());
                ps.setInt(12, OrdenP.getIdSatelite());
                ps.setInt(13, OrdenP.getIdCotizacion_ficha());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Ordenes_produccion> listar() throws SQLException {
        List<Ordenes_produccion> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM ordenes_produccion");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Ordenes_produccion o = new Ordenes_produccion();
                o.setIdOrden_produccion(rs.getInt("idOrden_produccion"));
                o.setProductoOrden_produccion(rs.getString("productoOrden_produccion"));
                o.setFechaOrden_produccion(rs.getDate("fechaOrden_produccion"));
                o.setGeneroProdOrden_produccion(rs.getString("generoProdOrden_produccion"));
                o.setTallaOrden_produccion(rs.getString("tallaOrden_produccion"));
                o.setCantidadOrden_produccion(rs.getInt("cantidadOrden_produccion"));
                o.setColorOrden_produccion(rs.getString("colorOrden_produccion"));
                o.setValorUnitarioOrden_produccion(rs.getDouble("valorUnitarioOrden_produccion"));
                o.setDescuentoOrden_produccion(rs.getDouble("descuentoOrden_produccion"));
                o.setValorTotalOrden_produccion(rs.getDouble("valorTotalOrden_produccion"));
                o.setCategoriaOrden_produccion(rs.getString("categoriaOrden_produccion"));
                o.setDescriOrden_produccion(rs.getString("descriOrden_produccion"));
                o.setIdSatelite(rs.getInt("idSatelite"));
                o.setIdCotizacion_ficha(rs.getInt("idCotizacion_ficha"));
                lista.add(o);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Ordenes_produccion leedId(Ordenes_produccion OrdenP) throws Exception {
        Ordenes_produccion OrdenPr;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM ordenes_produccion WHERE idOrden_produccion = ?");
            ps.setInt(1, OrdenP.getIdOrden_produccion());
            rs = ps.executeQuery();
            rs.next();
            OrdenPr = new Ordenes_produccion();
            OrdenPr.setIdOrden_produccion(rs.getInt("idOrden_produccion"));
            OrdenPr.setProductoOrden_produccion(rs.getString("productoOrden_produccion"));
            OrdenPr.setFechaOrden_produccion(rs.getDate("fechaOrden_produccion"));
            OrdenPr.setGeneroProdOrden_produccion(rs.getString("generoProdOrden_produccion"));
            OrdenPr.setTallaOrden_produccion(rs.getString("tallaOrden_produccion"));
            OrdenPr.setCantidadOrden_produccion(rs.getInt("cantidadOrden_produccion"));
            OrdenPr.setColorOrden_produccion(rs.getString("colorOrden_produccion"));
            OrdenPr.setValorUnitarioOrden_produccion(rs.getDouble("valorUnitarioOrden_produccion"));
            OrdenPr.setDescuentoOrden_produccion(rs.getDouble("descuentoOrden_produccion"));
            OrdenPr.setValorTotalOrden_produccion(rs.getDouble("valorTotalOrden_produccion"));
            OrdenPr.setCategoriaOrden_produccion(rs.getString("categoriaOrden_produccion"));
            OrdenPr.setDescriOrden_produccion(rs.getString("descriOrden_produccion"));
            OrdenPr.setIdSatelite(rs.getInt("idSatelite"));
            OrdenPr.setIdCotizacion_ficha(rs.getInt("idCotizacion_ficha"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return OrdenPr;
    }

    public void actualizar(Ordenes_produccion OrdenP) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE ordenes_produccion SET productoOrden_produccion = ?, fechaOrden_produccion = ?, generoProdOrden_produccion = ?, tallaOrden_produccion = ?, cantidadOrden_produccion = ?, colorOrden_produccion = ?, valorUnitarioOrden_produccion = ?, descuentoOrden_produccion = ?, valorTotalOrden_produccion = ?, categoriaOrden_produccion = ?, descriOrden_produccion = ?, idSatelite = ?, idCotizacion_ficha = ? WHERE idOrden_produccion = ?");
            ps.setString(1, OrdenP.getProductoOrden_produccion());
            Calendar c = Calendar.getInstance();
            c.setTime(OrdenP.getFechaOrden_produccion());
            java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
            ps.setDate(2, sqlDate);
            ps.setString(3, OrdenP.getGeneroProdOrden_produccion());
            ps.setString(4, OrdenP.getTallaOrden_produccion());
            ps.setInt(5, OrdenP.getCantidadOrden_produccion());
            ps.setString(6, OrdenP.getColorOrden_produccion());
            ps.setDouble(7, OrdenP.getValorUnitarioOrden_produccion());
            ps.setDouble(8, OrdenP.getDescuentoOrden_produccion());
            ps.setDouble(9, OrdenP.getValorTotalOrden_produccion());
            ps.setString(10, OrdenP.getCategoriaOrden_produccion());
            ps.setString(11, OrdenP.getDescriOrden_produccion());
            ps.setInt(12, OrdenP.getIdSatelite());
            ps.setInt(13, OrdenP.getIdCotizacion_ficha());
            ps.setInt(14, OrdenP.getIdOrden_produccion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Ordenes_produccion OrdenP) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM ordenes_produccion WHERE idOrden_produccion = ?");
            ps.setInt(1, OrdenP.getIdOrden_produccion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
    
}