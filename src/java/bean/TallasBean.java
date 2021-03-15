package bean;

import dao.TallaDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Talla;

@ManagedBean
@ViewScoped

public class TallasBean {
    
    private Talla talla = new Talla();
    private List<Talla> lstTallas;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.talla.setIdTalla("");
    }

    public Talla getTalla() {
        return talla;
    }

    public void setTalla(Talla talla) {
        this.talla = talla;
    }

    public List<Talla> getLstTallas() {
        return lstTallas;
    }

    public void setLstTallas(List<Talla> lstTallas) {
        this.lstTallas = lstTallas;
    }

    public void operar() throws Exception {
        switch (accion.charAt(0)) {
            case 'A':
                this.registrar();
                break;
            case 'M':
                this.modificar();
        }
    }

    private void registrar() throws Exception {
        TallaDAO pdao;
        
        try {
            pdao = new TallaDAO();
            pdao.registrar(talla);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        TallaDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new TallaDAO();
                    lstTallas = pdao.listar();
                }
            } else {
                pdao = new TallaDAO();
                lstTallas = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
     
    public void leerID(Talla per) throws Exception {
        TallaDAO pdao;
        Talla temp;

        try {
            pdao = new TallaDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.talla = temp;
                this.accion = "Modificar Talla";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        TallaDAO proddao;

        try {
            proddao = new TallaDAO();
            proddao.actualizar(talla);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar(Talla per) throws Exception {
        TallaDAO pdao;

        try {
            pdao = new TallaDAO();
            pdao.eliminar(per);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }
}
