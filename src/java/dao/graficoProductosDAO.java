package dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.graficoProductos;

public class graficoProductosDAO {

    public List<graficoProductos> listarGrafica() {
        List<graficoProductos> lista = null;
        ResultSet rs;

        try {
            Driver drv = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(drv);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shalom?user=root&password=&useSSL=false");

            PreparedStatement ps = con.prepareStatement("select productos.categoriaProducto, sum(inventarios_productos_tallas_fichas.cantidadProducto)\n"
                    + "from productos inner join inventarios_productos_tallas_fichas\n"
                    + "on productos.idProducto = inventarios_productos_tallas_fichas.idProducto\n"
                    + "group by productos.categoriaProducto;");
            rs = ps.executeQuery();

            lista = new ArrayList();

            while (rs.next()) {
                graficoProductos p = new graficoProductos();
                p.setCategoriaProducto(rs.getString("productos.categoriaProducto"));
                p.setCantidadProducto(rs.getInt("sum(inventarios_productos_tallas_fichas.cantidadProducto)"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error conexión");
        }
        return lista;
    }
}
