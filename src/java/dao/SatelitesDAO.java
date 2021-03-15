package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import modelo.Satelites;

public class SatelitesDAO extends DAO {

    public void registrar(Satelites sate) throws Exception {
        if (sate != null && !sate.getNomSatelite().isEmpty()) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO satelites (nomSatelite, teleSatelite, direccSatelite, correoSatelite, fechaISatelite) values(?, ?, ?, ?, ?)");
                ps.setString(1, sate.getNomSatelite());
                ps.setInt(2, sate.getTeleSatelite());
                ps.setString(3, sate.getDireccSatelite());
                ps.setString(4, sate.getCorreoSatelite());
                Calendar c = Calendar.getInstance();
                c.setTime(sate.getFechaISatelite());
                java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
                ps.setDate(5, sqlDate);
                
                
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Satelites> listar() throws SQLException {
        List<Satelites> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM satelites");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Satelites s = new Satelites();
                s.setIdSatelite(rs.getInt("idSatelite"));
                s.setNomSatelite(rs.getString("nomSatelite"));
                s.setTeleSatelite(rs.getInt("teleSatelite"));
                s.setDireccSatelite(rs.getString("direccSatelite"));
                s.setCorreoSatelite(rs.getString("correoSatelite"));
                s.setFechaISatelite(rs.getDate("fechaISatelite"));
                lista.add(s);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Satelites leedId(Satelites sateli) throws Exception {
        Satelites satelit;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM satelites WHERE idSatelite = ?");
            ps.setInt(1, sateli.getIdSatelite());
            rs = ps.executeQuery();
            rs.next();
            satelit = new Satelites();
            satelit.setIdSatelite(rs.getInt("idSatelite"));
            satelit.setNomSatelite(rs.getString("nomSatelite"));
            satelit.setTeleSatelite(rs.getInt("teleSatelite"));
            satelit.setDireccSatelite(rs.getString("direccSatelite"));
            satelit.setCorreoSatelite(rs.getString("correoSatelite"));
            satelit.setFechaISatelite(rs.getDate("fechaISatelite"));
            
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }

        return satelit;
    }

    public void actualizar(Satelites sateli) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE satelites SET nomSatelite = ?, teleSatelite = ?, direccSatelite = ?, correoSatelite = ?, fechaISatelite = ?  WHERE idSatelite = ?");
            ps.setString(1, sateli.getNomSatelite());
            ps.setInt(2, sateli.getTeleSatelite());
            ps.setString(3, sateli.getDireccSatelite());
            ps.setString(4, sateli.getCorreoSatelite());
            Calendar c = Calendar.getInstance();
            c.setTime(sateli.getFechaISatelite());
            java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
            ps.setDate(5, sqlDate);
            ps.setInt(6, sateli.getIdSatelite());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Satelites sateli) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM satelites WHERE idSatelite = ?");
            ps.setInt(1, sateli.getIdSatelite());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
