package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import modelo.Cotizaciones_Ficha;

public class Cotizaciones_FichaDAO extends DAO {

    public void registrar(Cotizaciones_Ficha cotiF) throws Exception {
        if (cotiF != null && !cotiF.getProductoCotizacion_ficha().isEmpty()) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO cotizaciones_fichas (productoCotizacion_ficha, generoProdCotizacion_ficha, tallaProduCotizacion_ficha, cantidadCotizacion_ficha, fechaCotizacion_ficha, idSatelite, descriCoti_ficha) values(?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, cotiF.getProductoCotizacion_ficha());
                ps.setString(2, cotiF.getGeneroProdCotizacion_ficha());
                ps.setString(3, cotiF.getTallaProduCotizacion_ficha());
                ps.setInt(4, cotiF.getCantidadCotizacion_ficha());
                Calendar c = Calendar.getInstance();
                c.setTime(cotiF.getFechaCotizacion_ficha());
                java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
                ps.setDate(5, sqlDate);
                ps.setInt(6, cotiF.getIdSatelite());
                ps.setString(7, cotiF.getDescriCoti_ficha());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Cotizaciones_Ficha> listar() throws SQLException {
        List<Cotizaciones_Ficha> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM cotizaciones_fichas");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Cotizaciones_Ficha cf = new Cotizaciones_Ficha();
                cf.setIdCotizacion_ficha(rs.getInt("idCotizacion_ficha"));
                cf.setProductoCotizacion_ficha(rs.getString("productoCotizacion_ficha"));
                cf.setGeneroProdCotizacion_ficha(rs.getString("generoProdCotizacion_ficha"));
                cf.setTallaProduCotizacion_ficha(rs.getString("tallaProduCotizacion_ficha"));
                cf.setCantidadCotizacion_ficha(rs.getInt("cantidadCotizacion_ficha"));
                cf.setFechaCotizacion_ficha(rs.getDate("fechaCotizacion_ficha"));
                cf.setIdSatelite(rs.getInt("idSatelite"));
                cf.setDescriCoti_ficha(rs.getString("descriCoti_ficha"));
                lista.add(cf);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Cotizaciones_Ficha leedId(Cotizaciones_Ficha cotiF) throws Exception {
        Cotizaciones_Ficha cotiFi;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM cotizaciones_fichas WHERE idCotizacion_ficha = ?");
            ps.setInt(1, cotiF.getIdCotizacion_ficha());
            rs = ps.executeQuery();
            rs.next();
            cotiFi = new Cotizaciones_Ficha();
            cotiFi.setIdCotizacion_ficha(rs.getInt("idCotizacion_ficha"));
            cotiFi.setProductoCotizacion_ficha(rs.getString("productoCotizacion_ficha"));
            cotiFi.setGeneroProdCotizacion_ficha(rs.getString("generoProdCotizacion_ficha"));
            cotiFi.setTallaProduCotizacion_ficha(rs.getString("tallaProduCotizacion_ficha"));
            cotiFi.setCantidadCotizacion_ficha(rs.getInt("cantidadCotizacion_ficha"));
            cotiFi.setFechaCotizacion_ficha(rs.getDate("fechaCotizacion_ficha"));
            cotiFi.setIdSatelite(rs.getInt("idSatelite"));
            cotiFi.setDescriCoti_ficha(rs.getString("descriCoti_ficha"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        
        return cotiFi;
    }

    public void actualizar(Cotizaciones_Ficha cotiF) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE cotizaciones_fichas SET productoCotizacion_ficha = ?, generoProdCotizacion_ficha = ?, tallaProduCotizacion_ficha = ?, cantidadCotizacion_ficha = ?, fechaCotizacion_ficha = ?, idSatelite = ?, descriCoti_ficha = ?  WHERE idCotizacion_ficha = ?");
            ps.setString(1, cotiF.getProductoCotizacion_ficha());
            ps.setString(2, cotiF.getGeneroProdCotizacion_ficha());
            ps.setString(3, cotiF.getTallaProduCotizacion_ficha());
            ps.setInt(4, cotiF.getCantidadCotizacion_ficha());
            Calendar c = Calendar.getInstance();
            c.setTime(cotiF.getFechaCotizacion_ficha());
            java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
            ps.setDate(5, sqlDate);
            ps.setInt(6, cotiF.getIdSatelite());
            ps.setString(7, cotiF.getDescriCoti_ficha());
            ps.setInt(8, cotiF.getIdCotizacion_ficha());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Cotizaciones_Ficha cotiF) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM cotizaciones_fichas WHERE idCotizacion_ficha = ?");
            ps.setInt(1, cotiF.getIdCotizacion_ficha());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
