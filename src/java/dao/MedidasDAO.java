package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import modelo.Medidas;

public class MedidasDAO extends DAO{
    
    public void registrar(Medidas medida) throws Exception {
        if (medida != null && !medida.getNomMedida().isEmpty()) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO medidas (nomMedida) values(?)");
                ps.setString(1, medida.getNomMedida());
                
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }
    
    public List<Medidas> listar() throws SQLException {
        List<Medidas> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM medidas");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Medidas l = new Medidas();
                l.setNomMedida(rs.getString("nomMedida"));

                lista.add(l);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }
    
    public Medidas leedId(Medidas medida) throws Exception {
        Medidas medidas;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM medidas WHERE nomMedida = ?");
            ps.setString(1, medida.getNomMedida());
            rs = ps.executeQuery();
            rs.next();
            medidas = new Medidas();
            medidas.setNomMedida(rs.getString("nomMedida"));           
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return medidas;
    }
    
    public void actualizar(Medidas medidas) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE medidas SET nomMedida = ? WHERE nomMedida = ?");
            ps.setString(1, medidas.getNomMedida());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Medidas medidas) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM medidas WHERE nomMedida = ?");
            ps.setString(1, medidas.getNomMedida());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
