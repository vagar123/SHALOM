package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import modelo.Administradores;

public class AdministradoresDAO extends DAO {

    public void registrar(Administradores admin) throws Exception {
        if (admin != null && admin.getIdUsuario() != 0) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO administradores (idUsuario) values(?)");
                ps.setInt(1, admin.getIdUsuario());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Administradores> listar() throws SQLException {
        List<Administradores> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("select idAdmin, usuarios.idUsuario, nomUsuario, apellUsuario \n"
                    + "from administradores inner join usuarios\n"
                    + "on administradores.idUsuario = usuarios.idUsuario;");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Administradores a = new Administradores();
                a.setIdAdmin(rs.getInt("idAdmin"));
                a.setIdUsuario(rs.getInt("usuarios.idUsuario"));
                a.setNomUsuario(rs.getString("nomUsuario"));
                a.setApellUsuario(rs.getString("apellUsuario"));
                lista.add(a);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Administradores leedId(Administradores admin) throws Exception {
        Administradores administrador;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM administradores WHERE idUsuario = ?");
            ps.setInt(1, admin.getIdUsuario());
            rs = ps.executeQuery();
            rs.next();
            administrador = new Administradores();
            administrador.setIdUsuario(rs.getInt("idUsuario"));

        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }

        return administrador;
    }

    public void actualizar(Administradores administrador) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE administradores SET idUsuario = ? WHERE idUsuario = ?");
            ps.setInt(1, administrador.getIdUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Administradores administradores) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM administradores WHERE idUsuario = ?");
            ps.setInt(1, administradores.getIdUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
