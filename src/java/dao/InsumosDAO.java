package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Insumos;

public class InsumosDAO extends DAO{
    public void registrar(Insumos insu) throws Exception {
        if (insu != null && !insu.getNomInsumo().isEmpty()) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO insumos (idInsumo, nomInsumo, precioUniInsumo, categoriaInsumo, colorInsumo, descriInsumo) values(?, ?, ?, ?, ?, ?)");
                ps.setInt(1, insu.getIdInsumo());
                ps.setString(2, insu.getNomInsumo());
                ps.setDouble(3, insu.getPrecioUniInsumo());
                ps.setString(4, insu.getCategoriaInsumo());
                ps.setString(5, insu.getColorInsumo());
                ps.setString(6, insu.getDescriInsumo());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Insumos> listar() throws SQLException {
        List<Insumos> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM insumos");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Insumos in = new Insumos();
                in.setIdInsumo(rs.getInt("idInsumo"));
                in.setNomInsumo(rs.getString("nomInsumo"));
                in.setPrecioUniInsumo(rs.getDouble("precioUniInsumo"));
                in.setCategoriaInsumo(rs.getString("categoriaInsumo"));
                in.setColorInsumo(rs.getString("colorInsumo"));
                in.setDescriInsumo(rs.getString("descriInsumo"));
                lista.add(in);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Insumos leedId(Insumos insu) throws Exception {
        Insumos ins;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM insumos WHERE idInsumo = ?");
            ps.setInt(1, insu.getIdInsumo());
            rs = ps.executeQuery();
            rs.next();
            ins = new Insumos();
            ins.setIdInsumo(rs.getInt("idInsumo"));
            ins.setNomInsumo(rs.getString("nomInsumo"));
            ins.setPrecioUniInsumo(rs.getDouble("precioUniInsumo"));
            ins.setCategoriaInsumo(rs.getString("categoriaInsumo"));
            ins.setColorInsumo(rs.getString("colorInsumo"));
            ins.setDescriInsumo(rs.getString("descriInsumo"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }

        return ins;
    }

    public void actualizar(Insumos insu) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE insumos SET nomInsumo = ?, precioUniInsumo = ?, categoriaInsumo = ?, colorInsumo = ?, descriInsumo = ? WHERE idInsumo= ?");
            ps.setString(1, insu.getNomInsumo());
            ps.setDouble(2, insu.getPrecioUniInsumo());
            ps.setString(3, insu.getCategoriaInsumo());
            ps.setString(4, insu.getColorInsumo());
            ps.setString(5, insu.getDescriInsumo());
            ps.setInt(6, insu.getIdInsumo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Insumos insum) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM insumos WHERE idInsumo = ?");
            ps.setInt(1, insum.getIdInsumo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}