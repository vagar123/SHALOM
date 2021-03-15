package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import modelo.Inventarios_Productos_Tallas_Fichas;

public class Inventarios_Productos_Tallas_FichasDAO extends DAO {

    public void registrar(Inventarios_Productos_Tallas_Fichas inven) throws Exception {
        if (inven != null && inven.getIdProducto() != 0) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO inventarios_productos_tallas_fichas (idProducto, idInventario, idTalla, cantidadProducto, idFicha_produccio) values(?, ?, ?, ?, ?)");
                ps.setInt(1, inven.getIdProducto());
                ps.setInt(2, inven.getIdInventario());
                ps.setString(3, inven.getIdTalla());
                ps.setInt(4, inven.getCantidadProducto());
                ps.setInt(5, inven.getIdFicha_produccio());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Inventarios_Productos_Tallas_Fichas> listar() throws SQLException {
        List<Inventarios_Productos_Tallas_Fichas> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT productos.descriProducto, inventarios_productos_tallas_fichas.*\n"
                    + "FROM inventarios_productos_tallas_fichas inner join productos\n"
                    + "on inventarios_productos_tallas_fichas.idProducto = productos.idProducto;");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Inventarios_Productos_Tallas_Fichas l = new Inventarios_Productos_Tallas_Fichas();
                l.setDescriProducto(rs.getString("descriProducto"));
                l.setIdProducto(rs.getInt("idProducto"));
                l.setIdInventario(rs.getInt("idInventario"));
                l.setIdTalla(rs.getString("idTalla"));
                l.setCantidadProducto(rs.getInt("cantidadProducto"));
                l.setIdFicha_produccio(rs.getInt("idFicha_produccio"));
                lista.add(l);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Inventarios_Productos_Tallas_Fichas leedId(Inventarios_Productos_Tallas_Fichas inv) throws Exception {
        Inventarios_Productos_Tallas_Fichas inven;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM inventarios_productos_tallas_fichas WHERE idProducto = ?");
            ps.setInt(1, inv.getIdProducto());
            rs = ps.executeQuery();
            rs.next();
            inven = new Inventarios_Productos_Tallas_Fichas();
            inven.setIdProducto(rs.getInt("idProducto"));
            inven.setIdInventario(rs.getInt("idInventario"));
            inven.setIdTalla(rs.getString("idTalla"));
            inven.setCantidadProducto(rs.getInt("cantidadProducto"));
            inven.setIdFicha_produccio(rs.getInt("idFicha_produccio"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }

        return inven;
    }

    public void actualizar(Inventarios_Productos_Tallas_Fichas pro) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE inventarios_productos_tallas_fichas SET idInventario = ?, cantidadProducto =? WHERE idProducto = ?");
            ps.setInt(1, pro.getIdInventario());
            ps.setInt(2, pro.getCantidadProducto());
            ps.setInt(3, pro.getIdProducto());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Inventarios_Productos_Tallas_Fichas inv) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM inventarios_productos_tallas_fichas WHERE idProducto = ?");
            ps.setInt(1, inv.getIdProducto());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
