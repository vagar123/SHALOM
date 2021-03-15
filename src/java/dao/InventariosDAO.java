package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import modelo.Inventarios;

public class InventariosDAO extends DAO {

    public void registrar(Inventarios inven) throws Exception {
        if (inven != null && !inven.getNomInventario().isEmpty()) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO inventarios (nomInventario, existInventario, fechaInventario, idAdmin, idLocal) values(?, ?, ?, ?, ?)");
                ps.setString(1, inven.getNomInventario());
                ps.setInt(2, inven.getExistInventario());
                Calendar c = Calendar.getInstance();
                c.setTime(inven.getFechaInventario());
                java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
                ps.setDate(3, sqlDate);
                ps.setInt(4, inven.getIdAdmin());
                ps.setInt(5, inven.getIdLocal());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Inventarios> listar() throws SQLException {
        List<Inventarios> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM inventarios");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Inventarios i = new Inventarios();
                i.setIdInventario(rs.getInt("idInventario"));
                i.setNomInventario(rs.getString("nomInventario"));
                i.setExistInventario(rs.getInt("existInventario"));
                i.setFechaInventario(rs.getDate("fechaInventario"));
                i.setIdAdmin(rs.getInt("idAdmin"));
                i.setIdLocal(rs.getInt("idLocal"));

                lista.add(i);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Inventarios leedId(Inventarios inven) throws Exception {
        Inventarios invent;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM inventarios WHERE idInventario = ?");
            ps.setInt(1, inven.getIdInventario());
            rs = ps.executeQuery();
            rs.next();
            invent = new Inventarios();
            invent.setIdInventario(rs.getInt("idInventario"));
            invent.setNomInventario(rs.getString("nomInventario"));
            invent.setExistInventario(rs.getInt("existInventario"));
            invent.setFechaInventario(rs.getDate("fechaInventario"));
            invent.setIdAdmin(rs.getInt("idAdmin"));
            invent.setIdLocal(rs.getInt("idLocal"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }

        return invent;
    }

    public void actualizar(Inventarios inven) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE inventarios SET nomInventario = ?, existInventario = ?, fechaInventario = ?, idAdmin = ?, idLocal = ?  WHERE idInventario = ?");
            ps.setString(1, inven.getNomInventario());
            ps.setInt(2, inven.getExistInventario());
            Calendar c = Calendar.getInstance();
            c.setTime(inven.getFechaInventario());
            java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
            ps.setDate(3, sqlDate);
            ps.setInt(4, inven.getIdAdmin());
            ps.setInt(5, inven.getIdLocal());
            ps.setInt(6, inven.getIdInventario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Inventarios inven) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM inventarios WHERE idInventario = ?");
            ps.setInt(1, inven.getIdInventario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
