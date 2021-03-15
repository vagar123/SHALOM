
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import modelo.Locales;

public class LocalesDAO extends DAO{
    public void registrar(Locales local) throws Exception {
        if (local != null && local.getIdLocal() != 0) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO locales (idLocal) values(?)");
                ps.setInt(1, local.getIdLocal());
                
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Locales> listar() throws SQLException {
        List<Locales> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM locales");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Locales l = new Locales();
                l.setIdLocal(rs.getInt("idLocal"));

                lista.add(l);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Locales leedId(Locales local) throws Exception {
        Locales locales;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM locales WHERE idLocal = ?");
            ps.setInt(1, local.getIdLocal());
            rs = ps.executeQuery();
            rs.next();
            locales = new Locales();
            locales.setIdLocal(rs.getInt("idLocal"));

            
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }

        return locales;
    }

    public void actualizar(Locales locales) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE locales SET idLocal = ? WHERE idLocal = ?");
            ps.setInt(1, locales.getIdLocal());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Locales locales) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM locales WHERE idLocal = ?");
            ps.setInt(1, locales.getIdLocal());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
