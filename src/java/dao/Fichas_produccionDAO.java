package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Fichas_produccion;
import modelo.Fichas_Insumos;

public class Fichas_produccionDAO extends DAO {

    //Método para registrar las Ordenes de pago
    public void registrar(Fichas_produccion ficha, List<Fichas_Insumos> lista) throws SQLException {
        if (ficha != null && lista != null) {
            try {
                this.conectar();
                this.getConn().setAutoCommit(false);
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO fichas_produccion (productFicha_producc, fechaIFicha_producc, fechaSFicha_producc, cantidadFicha_producc, tiempoFicha_producc, tallaFicha_producc, valorTotalFicha_producc, categoriaFicha_producc, generoFicha_producc, colorFicha_producc, descripcionFicha_producc, idOrden_produccion, idSatelite, estadoFicha_producc) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, ficha.getProductFicha_producc());
                Calendar c = Calendar.getInstance();
                c.setTime(ficha.getFechaIFicha_producc());
                java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
                ps.setDate(2, sqlDate);
                Calendar c2 = Calendar.getInstance();
                c.setTime(ficha.getFechaSFicha_producc());
                java.sql.Date sqlDate2 = new java.sql.Date(c2.getTime().getTime());
                ps.setDate(3, sqlDate2);
                ps.setInt(4, ficha.getCantidadFicha_producc());
                ps.setString(5, ficha.getTiempoFicha_producc());
                ps.setString(6, ficha.getTallaFicha_producc());
                ps.setInt(7, ficha.getValorTotalFicha_producc());
                ps.setString(8, ficha.getCategoriaFicha_producc());
                ps.setString(9, ficha.getGeneroFicha_producc());
                ps.setString(10, ficha.getColorFicha_producc());
                ps.setString(11, ficha.getDescripcionFicha_producc());
                ps.setInt(12, ficha.getIdOrden_produccion());
                ps.setInt(13, ficha.getIdSatelite());
                ps.setString(14, ficha.getEstadoFicha_producc());
                ps.executeUpdate();
                
                int codf;
                ps = this.getConn().prepareStatement("SELECT LAST_INSERT_ID() FROM fichas_produccion");
                ResultSet rs = ps.executeQuery();
                rs.next();
                codf = rs.getInt(1);
                
                for (Fichas_Insumos det : lista) {

                    //Valida nombre
                    ps = this.getConn().prepareStatement("select nomInsumo from insumos where idInsumo = ?");
                    ps.setInt(1, det.getInsumo().getIdInsumo());
                    rs = ps.executeQuery();
                    rs.next();
                    String nombre = rs.getString("nomInsumo");

                    //Valida cantidad 
                    ps = this.getConn().prepareStatement("select insumos_medidas_inventarios.cantidaInsumo\n"
                            + "from insumos_medidas_inventarios\n"
                            + "where insumos_medidas_inventarios.idInsumo = ?");
                    ps.setInt(1, det.getInsumo().getIdInsumo());
                    rs = ps.executeQuery();
                    rs.next();
                    int valida = rs.getInt("insumos_medidas_inventarios.cantidaInsumo");
                    
                    ps = this.getConn().prepareStatement("INSERT INTO fichas_insumos(idFicha_produccion, idInsumo, cantidad, valorInsumos) VALUES (?, ?, ?, ?)");
                    ps.setInt(1, codf);
                    ps.setInt(2, det.getInsumo().getIdInsumo());
                    if(det.getCantidad()<= valida){
                        ps.setInt(3, det.getCantidad());
                    } else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "La cantidad solicitada no esta disponible del insumo: "+nombre, "La cantidad solicitada no esta disponible del insumo: "+nombre));
                    }                   
                    ps.setDouble(4, det.getValorInsumos());
                    ps.executeUpdate();
                }
                this.getConn().commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de la Ficha de producción exitoso", "Registro de la Ficha de producción exitoso"));
            } catch (SQLException e) {
                this.getConn().rollback();
                System.out.println("error generando ficha de produccion");
            }
        }
    }
    
    public List<Fichas_produccion> listar() throws SQLException {
        List<Fichas_produccion> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM fichas_produccion");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Fichas_produccion f = new Fichas_produccion();
                f.setIdFicha_produccion(rs.getInt("idFicha_produccion"));
                f.setProductFicha_producc(rs.getString("productFicha_producc"));
                f.setFechaIFicha_producc(rs.getDate("fechaIFicha_producc"));
                f.setFechaSFicha_producc(rs.getDate("fechaSFicha_producc"));
                f.setCantidadFicha_producc(rs.getInt("cantidadFicha_producc"));
                f.setTiempoFicha_producc(rs.getString("tiempoFicha_producc"));
                f.setTallaFicha_producc(rs.getString("tallaFicha_producc"));
                f.setValorTotalFicha_producc(rs.getInt("valorTotalFicha_producc"));
                f.setCategoriaFicha_producc(rs.getString("categoriaFicha_producc"));
                f.setGeneroFicha_producc(rs.getString("generoFicha_producc"));
                f.setColorFicha_producc(rs.getString("colorFicha_producc"));
                f.setDescripcionFicha_producc(rs.getString("descripcionFicha_producc"));
                f.setIdOrden_produccion(rs.getInt("idOrden_produccion"));
                f.setIdSatelite(rs.getInt("idSatelite"));
                f.setEstadoFicha_producc(rs.getString("estadoFicha_producc"));
                lista.add(f);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }
    
    public Fichas_produccion leedId(Fichas_produccion Ficha) throws Exception {
        Fichas_produccion Fichas;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM fichas_produccion WHERE idFicha_produccion = ?");
            ps.setInt(1, Ficha.getIdFicha_produccion());
            rs = ps.executeQuery();
            rs.next();
            Fichas = new Fichas_produccion();
            Fichas.setIdFicha_produccion(rs.getInt("idFicha_produccion"));
            Fichas.setProductFicha_producc(rs.getString("productFicha_producc"));
            Fichas.setFechaIFicha_producc(rs.getDate("fechaIFicha_producc"));
            Fichas.setFechaSFicha_producc(rs.getDate("fechaSFicha_producc"));
            Fichas.setCantidadFicha_producc(rs.getInt("cantidadFicha_producc"));
            Fichas.setTiempoFicha_producc(rs.getString("tiempoFicha_producc"));
            Fichas.setTallaFicha_producc(rs.getString("tallaFicha_producc"));
            Fichas.setValorTotalFicha_producc(rs.getInt("valorTotalFicha_producc"));
            Fichas.setCategoriaFicha_producc(rs.getString("categoriaFicha_producc"));
            Fichas.setGeneroFicha_producc(rs.getString("generoFicha_producc"));
            Fichas.setColorFicha_producc(rs.getString("colorFicha_producc"));
            Fichas.setDescripcionFicha_producc(rs.getString("descripcionFicha_producc"));
            Fichas.setIdOrden_produccion(rs.getInt("idOrden_produccion"));
            Fichas.setIdSatelite(rs.getInt("idSatelite"));
            Fichas.setEstadoFicha_producc(rs.getString("estadoFicha_producc"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        
        return Fichas;
    }
    
    public void actualizar(Fichas_produccion Ficha) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE fichas_produccion SET productFicha_producc = ?, fechaIFicha_producc = ?, fechaSFicha_producc = ?, cantidadFicha_producc = ?, tiempoFicha_producc = ?, tallaFicha_producc = ?,  valorTotalFicha_producc = ?, categoriaFicha_producc = ?, generoFicha_producc = ?, colorFicha_producc = ?, descripcionFicha_producc = ?, idOrden_produccion = ?, idSatelite = ?, estadoFicha_producc = ?  WHERE idFicha_produccion = ?");
            ps.setString(1, Ficha.getProductFicha_producc());
            Calendar c = Calendar.getInstance();
            c.setTime(Ficha.getFechaIFicha_producc());
            java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
            ps.setDate(2, sqlDate);
            Calendar ca = Calendar.getInstance();
            ca.setTime(Ficha.getFechaSFicha_producc());
            java.sql.Date sqlDateS = new java.sql.Date(ca.getTime().getTime());
            ps.setDate(3, sqlDateS);
            ps.setInt(4, Ficha.getCantidadFicha_producc());
            ps.setString(5, Ficha.getTiempoFicha_producc());
            ps.setString(6, Ficha.getTallaFicha_producc());
            ps.setInt(7, Ficha.getValorTotalFicha_producc());
            ps.setString(8, Ficha.getCategoriaFicha_producc());
            ps.setString(9, Ficha.getGeneroFicha_producc());
            ps.setString(10, Ficha.getColorFicha_producc());
            ps.setString(11, Ficha.getDescripcionFicha_producc());
            ps.setInt(12, Ficha.getIdOrden_produccion());
            ps.setInt(13, Ficha.getIdSatelite());
            ps.setString(14, Ficha.getEstadoFicha_producc());
            ps.setInt(15, Ficha.getIdFicha_produccion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
    
    public void eliminar(Fichas_produccion Ficha) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM fichas_produccion WHERE idFicha_produccion = ?");
            ps.setInt(1, Ficha.getIdFicha_produccion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
    
}
