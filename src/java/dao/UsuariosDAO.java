package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import modelo.Usuarios;

public class UsuariosDAO extends DAO {

    public void registrar(Usuarios per) throws Exception {
        if (per != null && !per.getNomUsuario().isEmpty()) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO usuarios (idUsuario, nomUsuario, apellUsuario, correoUsuario, teleUsuario, cargoUsuario, idLocal) values(?, ?, ?, ?, ?, ?, ?)");
                ps.setInt(1, per.getIdUsuario());
                ps.setString(2, per.getNomUsuario());
                ps.setString(3, per.getApellUsuario());
                ps.setString(4, per.getCorreoUsuario());
                ps.setInt(5, per.getTeleUsuario());
                ps.setString(6, per.getCargoUsuario());
                ps.setInt(7, per.getIdLocal());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Usuarios> listar() throws Exception {
        List<Usuarios> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM usuarios");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Usuarios p = new Usuarios();
                p.setIdUsuario(rs.getInt("idUsuario"));
                p.setNomUsuario(rs.getString("nomUsuario"));
                p.setApellUsuario(rs.getString("apellUsuario"));
                p.setCorreoUsuario(rs.getString("correoUsuario"));
                p.setTeleUsuario(rs.getInt("teleUsuario"));
                p.setCargoUsuario(rs.getString("cargoUsuario"));
                p.setIdLocal(rs.getInt("idLocal"));

                lista.add(p);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Usuarios leedId(Usuarios per) throws Exception {
        Usuarios pers;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM usuarios WHERE idUsuario = ?");
            ps.setInt(1, per.getIdUsuario());
            rs = ps.executeQuery();
            rs.next();
            pers = new Usuarios();
            pers.setIdUsuario(rs.getInt("idUsuario"));
            pers.setNomUsuario(rs.getString("nomUsuario"));
            pers.setApellUsuario(rs.getString("apellUsuario"));
            pers.setCorreoUsuario(rs.getString("correoUsuario"));
            pers.setTeleUsuario(rs.getInt("teleUsuario"));
            pers.setCargoUsuario(rs.getString("cargoUsuario"));
            pers.setIdLocal(rs.getInt("idLocal"));
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }

        return pers;
    }

    public void actualizar(Usuarios per) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE usuarios SET nomUsuario = ?, apellUsuario =?, correoUsuario =?, teleUsuario =?, cargoUsuario =?, idLocal =? WHERE idUsuario = ?");
            ps.setString(1, per.getNomUsuario());
            ps.setString(2, per.getApellUsuario());
            ps.setString(3, per.getCorreoUsuario());
            ps.setInt(4, per.getTeleUsuario());
            ps.setString(5, per.getCargoUsuario());
            ps.setInt(6, per.getIdLocal());
            ps.setInt(7, per.getIdUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Usuarios per) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM usuarios WHERE idUsuario = ?");
            ps.setInt(1, per.getIdUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}