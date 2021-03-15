package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import modelo.Productos;
import modelo.graficoProductos;


public class ProductosDAO extends DAO {

    public void registrar(Productos pro) throws Exception {
        if (pro != null && !pro.getNomProducto().isEmpty()) {
            try {
                this.conectar();
                
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO productos (idProducto, nomProducto, precioProducto, generoProducto, colorProducto, categoriaProducto, descriProducto) values(?, ?, ?, ?, ?, ?, ?)");
                ps.setInt(1, pro.getIdProducto());
                ps.setString(2, pro.getNomProducto());
                ps.setDouble(3, pro.getPrecioProducto());
                ps.setString(4, pro.getGeneroProducto());
                ps.setString(5, pro.getColorProducto());
                ps.setString(6, pro.getCategoriaProducto());
                ps.setString(7, pro.getDescriProducto());
                ps.executeUpdate();
               
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Productos> listar() throws SQLException {
        List<Productos> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM productos");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Productos p = new Productos();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNomProducto(rs.getString("nomProducto"));
                p.setPrecioProducto(rs.getDouble("precioProducto"));
                p.setGeneroProducto(rs.getString("generoProducto"));
                p.setColorProducto(rs.getString("colorProducto"));
                p.setCategoriaProducto(rs.getString("categoriaProducto"));
                p.setDescriProducto(rs.getString("descriProducto"));

                lista.add(p);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Productos leedId(Productos pro) throws Exception {
        Productos prod;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM productos WHERE idProducto = ?");
            ps.setInt(1, pro.getIdProducto());
            rs = ps.executeQuery();
            rs.next();
            prod = new Productos();
            prod.setIdProducto(rs.getInt("idProducto"));
            prod.setNomProducto(rs.getString("nomProducto"));
            prod.setPrecioProducto(rs.getDouble("precioProducto"));
            prod.setGeneroProducto(rs.getString("generoProducto"));
            prod.setColorProducto(rs.getString("colorProducto"));
            prod.setCategoriaProducto(rs.getString("categoriaProducto"));
            prod.setDescriProducto(rs.getString("descriProducto"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }

        return prod;
    }

    public void actualizar(Productos pro) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE productos SET nomProducto = ?, precioProducto =?, generoProducto =?, colorProducto =?, categoriaProducto =?, descriProducto=? WHERE idProducto = ?");
            ps.setString(1, pro.getNomProducto());
            ps.setDouble(2, pro.getPrecioProducto());
            ps.setString(3, pro.getGeneroProducto());
            ps.setString(4, pro.getColorProducto());
            ps.setString(5, pro.getCategoriaProducto());
            ps.setString(6, pro.getDescriProducto());
            ps.setInt(7, pro.getIdProducto());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Productos pro) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM productos WHERE idProducto = ?");
            ps.setInt(1, pro.getIdProducto());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
    
}
