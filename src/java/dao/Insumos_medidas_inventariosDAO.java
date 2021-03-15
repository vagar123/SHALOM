package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import modelo.Insumos_medidas_inventarios;

public class Insumos_medidas_inventariosDAO extends DAO {

    public void registrar(Insumos_medidas_inventarios insumos) throws Exception {
        if (insumos != null && insumos.getIdInsumo() != 0) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO insumos_medidas_inventarios (idInsumo, nomMedida, idInventario, cantidaInsumo) values(?, ?, ?, ?)");
                ps.setInt(1, insumos.getIdInsumo());
                ps.setString(2, insumos.getNomMedida());
                ps.setInt(3, insumos.getIdInventario());
                ps.setInt(4, insumos.getCantidaInsumo());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Insumos_medidas_inventarios> listar() throws SQLException {
        List<Insumos_medidas_inventarios> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT insumos.nomInsumo, insumos_medidas_inventarios.* \n"
                    + "FROM insumos_medidas_inventarios inner join insumos\n"
                    + "on insumos_medidas_inventarios.idInsumo = insumos.idInsumo;");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Insumos_medidas_inventarios l = new Insumos_medidas_inventarios();
                l.setNomInsumo(rs.getString("insumos.nomInsumo"));
                l.setIdInsumo(rs.getInt("idInsumo"));
                l.setNomMedida(rs.getString("nomMedida"));
                l.setIdInventario(rs.getInt("idInventario"));
                l.setCantidaInsumo(rs.getInt("cantidaInsumo"));
                lista.add(l);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Insumos_medidas_inventarios leedId(Insumos_medidas_inventarios insumo) throws Exception {
        Insumos_medidas_inventarios insumos;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM Insumos_medidas_inventarios WHERE idInsumo = ?");
            ps.setInt(1, insumo.getIdInsumo());
            rs = ps.executeQuery();
            rs.next();
            insumos = new Insumos_medidas_inventarios();
            insumos.setIdInsumo(rs.getInt("idInsumo"));
            insumos.setNomMedida(rs.getString("nomMedida"));
            insumos.setIdInventario(rs.getInt("idInventario"));
            insumos.setCantidaInsumo(rs.getInt("cantidaInsumo"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return insumos;
    }

    public void actualizar(Insumos_medidas_inventarios insumos) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE insumos_medidas_inventarios SET idInventario = ?, cantidaInsumo = ? WHERE idInsumo = ?");
            ps.setInt(1, insumos.getIdInventario());
            ps.setInt(2, insumos.getCantidaInsumo());           
            ps.setInt(3, insumos.getIdInsumo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Insumos_medidas_inventarios insumos) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM Insumos_medidas_inventarios WHERE idInsumo = ?");
            ps.setInt(1, insumos.getIdInsumo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
