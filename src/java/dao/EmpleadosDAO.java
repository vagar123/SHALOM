package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Empleados;

public class EmpleadosDAO extends DAO{
    
    public void registrar(Empleados empleado) throws Exception {
        if (empleado != null && empleado.getIdEmpleado()!= 0) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO empleados (idEmpleado) values(?)");
                ps.setInt(1, empleado.getIdEmpleado());
                
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Empleados> listar() throws SQLException {
        List<Empleados> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM empleados");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Empleados l = new Empleados();
                l.setIdEmpleado(rs.getInt("idEmpleado"));

                lista.add(l);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Empleados leedId(Empleados empleado) throws Exception {
        Empleados empleados;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM empleados WHERE idEmpleado = ?");
            ps.setInt(1, empleado.getIdEmpleado());
            rs = ps.executeQuery();
            rs.next();
            empleados = new Empleados();
            empleados.setIdEmpleado(rs.getInt("idEmpleado"));

            
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }

        return empleados;
    }

    public void actualizar(Empleados empleados) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE empleados SET idEmpleado = ? WHERE idEmpleado = ?");
            ps.setInt(1, empleados.getIdEmpleado());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Empleados empleados) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM empleados WHERE idEmpleado = ?");
            ps.setInt(1, empleados.getIdEmpleado());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
