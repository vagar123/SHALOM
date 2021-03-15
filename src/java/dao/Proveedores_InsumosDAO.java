package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import modelo.Proveedores_Insumos;

public class Proveedores_InsumosDAO extends DAO {

    public void registrar(Proveedores_Insumos insumos) throws Exception {
        if (insumos != null && insumos.getIdInsumo() != 0) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO insumos_proveedores (idInsumo, idProveedor) values(?, ?)");
                ps.setInt(1, insumos.getIdInsumo());
                ps.setInt(2, insumos.getIdProveedor());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Proveedores_Insumos> listar() throws SQLException {
        List<Proveedores_Insumos> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("select insumos_proveedores.idProveedor, proveedores.nomProveedor, insumos_proveedores.idInsumo,\n"
                    + "insumos.nomInsumo, insumos_medidas_inventarios.nomMedida, insumos.descriInsumo, insumos_medidas_inventarios.cantidaInsumo\n"
                    + "FROM insumos_medidas_inventarios inner join insumos\n"
                    + "on insumos_medidas_inventarios.idInsumo = insumos.idInsumo \n"
                    + "inner join insumos_proveedores\n"
                    + "on insumos.idInsumo = insumos_proveedores.idInsumo\n"
                    + "inner join proveedores\n"
                    + "on insumos_proveedores.idProveedor = proveedores.idProveedor;");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Proveedores_Insumos l = new Proveedores_Insumos();
                l.setIdProveedor(rs.getInt("insumos_proveedores.idProveedor"));
                l.setNomProveedor(rs.getString("proveedores.nomProveedor"));
                l.setIdInsumo(rs.getInt("insumos_proveedores.idInsumo"));
                l.setNomInsumo(rs.getString("insumos.nomInsumo"));
                l.setNomMedida(rs.getString("insumos_medidas_inventarios.nomMedida"));
                l.setDescriInsumo(rs.getString("insumos.descriInsumo"));
                l.setCantidaInsumo(rs.getInt("insumos_medidas_inventarios.cantidaInsumo"));
                lista.add(l);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }
    
    public Proveedores_Insumos leedId(Proveedores_Insumos insumo) throws Exception {
        Proveedores_Insumos insumos;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM insumos_proveedores WHERE idInsumo = ?");
            ps.setInt(1, insumo.getIdInsumo());
            rs = ps.executeQuery();
            rs.next();
            insumos = new Proveedores_Insumos();
            insumos.setIdInsumo(rs.getInt("idInsumo"));
            insumos.setIdProveedor(rs.getInt("idProveedor"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return insumos;
    }
    
    public void actualizar(Proveedores_Insumos insumos) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE insumos_proveedores SET idInsumo = ?, idProveedor = ? WHERE idInsumo = ?");
            ps.setInt(1, insumos.getIdProveedor());           
            ps.setInt(2, insumos.getIdInsumo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
    
    public void eliminar(Proveedores_Insumos insumos) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM insumos_proveedores WHERE idInsumo = ?");
            ps.setInt(1, insumos.getIdInsumo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
