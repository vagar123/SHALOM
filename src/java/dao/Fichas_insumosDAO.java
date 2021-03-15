package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Fichas_Insumos;

public class Fichas_insumosDAO extends DAO {

    public void registrar(Fichas_Insumos ficha) throws Exception {
        if (ficha != null && ficha.getIdFicha_produccion() != 0) {
            try {
                this.conectar();

                //Valida nombre
                PreparedStatement ps = this.getConn().prepareStatement("select nomInsumo from insumos where idInsumo = ?");
                ps.setInt(1, ficha.getIdInsumo());
                ResultSet rs = ps.executeQuery();
                rs.next();
                String nombre = rs.getString("nomInsumo");

                //Valida cantidad 
                ps = this.getConn().prepareStatement("select insumos_medidas_inventarios.cantidaInsumo\n"
                        + "from insumos_medidas_inventarios\n"
                        + "where insumos_medidas_inventarios.idInsumo = ?");
                ps.setInt(1, ficha.getIdInsumo());
                rs = ps.executeQuery();
                rs.next();
                int valida = rs.getInt("insumos_medidas_inventarios.cantidaInsumo");

                ps = this.getConn().prepareStatement("INSERT INTO fichas_insumos (idFicha_produccion, idInsumo, cantidad) values(?, ?, ?)");
                ps.setInt(1, ficha.getIdFicha_produccion());
                ps.setInt(2, ficha.getIdInsumo());
                if (ficha.getCantidad() <= valida) {
                    ps.setInt(3, ficha.getCantidad());
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "La cantidad solicitada no esta disponible del insumo: " + nombre, "La cantidad solicitada no esta disponible del insumo: " + nombre));
                }
                ps.executeUpdate();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de nuevo insumo a ficha de producción exitoso", "Registro de nuevo insumo a ficha de producción exitoso"));

            } catch (SQLException e) {
                System.out.println("error inresando insumosa ficha");
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Fichas_Insumos> listar() throws SQLException {
        List<Fichas_Insumos> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("select fichas_insumos.idFicha_produccion, fichas_produccion.descripcionFicha_producc, fichas_produccion.idSatelite,\n"
                    + "satelites.nomSatelite, fichas_insumos.idInsumo, insumos.nomInsumo, fichas_insumos.cantidad, fichas_insumos.valorInsumos,\n"
                    + "fichas_produccion.valorTotalFicha_producc\n"
                    + "FROM insumos inner join fichas_insumos\n"
                    + "on insumos.idInsumo = fichas_insumos.idInsumo \n"
                    + "inner join fichas_produccion\n"
                    + "on fichas_insumos.idFicha_produccion = fichas_produccion.idFicha_produccion\n"
                    + "inner join satelites\n"
                    + "on fichas_produccion.idSatelite = satelites.idSatelite;");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Fichas_Insumos f = new Fichas_Insumos();
                f.setIdFicha_produccion(rs.getInt("idFicha_produccion"));
                f.setDescripcionFicha_producc(rs.getString("descripcionFicha_producc"));
                f.setIdSatelite(rs.getInt("idSatelite"));
                f.setNomSatelite(rs.getString("nomSatelite"));
                f.setIdInsumo(rs.getInt("idInsumo"));
                f.setNomInsumo(rs.getString("nomInsumo"));
                f.setCantidad(rs.getInt("cantidad"));
                f.setValorInsumos(rs.getDouble("valorInsumos"));
                f.setValorTotalFicha_producc(rs.getDouble("valorTotalFicha_producc"));
                lista.add(f);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Fichas_Insumos leedId(Fichas_Insumos fichas) throws Exception {
        Fichas_Insumos ficha;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM fichas_insumos WHERE idFicha_produccion = ?");
            ps.setInt(1, fichas.getIdFicha_produccion());
            rs = ps.executeQuery();
            rs.next();
            ficha = new Fichas_Insumos();
            ficha.setIdFicha_produccion(rs.getInt("idFicha_produccion"));
            ficha.setIdInsumo(rs.getInt("idInsumo"));
            ficha.setCantidad(rs.getInt("cantidad"));
            ficha.setValorInsumos(rs.getDouble("valorInsumos"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return ficha;
    }

    public void actualizar(Fichas_Insumos pro) throws Exception {
        try {
            this.conectar();

            //Valida nombre
            PreparedStatement ps = this.getConn().prepareStatement("select nomInsumo from insumos where idInsumo = ?");
            ps.setInt(1, pro.getIdInsumo());
            ResultSet rs = ps.executeQuery();
            rs.next();
            String nombre = rs.getString("nomInsumo");

            //Valida cantidad 
            ps = this.getConn().prepareStatement("select insumos_medidas_inventarios.cantidaInsumo\n"
                    + "from insumos_medidas_inventarios\n"
                    + "where insumos_medidas_inventarios.idInsumo = ?");
            ps.setInt(1, pro.getIdInsumo());
            rs = ps.executeQuery();
            rs.next();
            int valida = rs.getInt("insumos_medidas_inventarios.cantidaInsumo");

            ps = this.getConn().prepareStatement("UPDATE fichas_insumos SET cantidad =? WHERE idFicha_produccion = ?");
            if (pro.getCantidad() <= valida) {
                ps.setInt(1, pro.getCantidad());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "La cantidad solicitada no esta disponible del insumo: " + nombre, "La cantidad solicitada no esta disponible del insumo: " + nombre));
            }
            ps.setInt(2, pro.getIdFicha_produccion());
            ps.executeUpdate();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación de detalles de la ficha producción exitoso", "Modificación de detalles de la ficha producción exitoso"));
        } catch (SQLException e) {
            System.out.println("error modificando insumos a ficha");
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Fichas_Insumos ficha) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM fichas_insumos WHERE idFicha_produccion = ?");
            ps.setInt(1, ficha.getIdFicha_produccion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
