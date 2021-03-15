package bean;

import com.mysql.jdbc.Driver;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Usu_contra;
import dao.Usu_contraDAO;
import javax.faces.application.FacesMessage;


@ManagedBean
@ViewScoped

public class Usu_contraBean {
    
    private Usu_contra usu = new Usu_contra();
    private List<Usu_contra> lstUsu;
    private String accion;

    public Usu_contra getUsu() {
        return usu;
    }

    public void setUsu(Usu_contra usu) {
        this.usu = usu;
    }

    public List<Usu_contra> getLstUsu() {
        return lstUsu;
    }

    public void setLstUsu(List<Usu_contra> lstUsu) {
        this.lstUsu = lstUsu;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }
    
    private void limpiar() {
        this.usu.setUsu("");
        this.usu.setContra(0);
        this.usu.setTipo_usu("");
        this.usu.setUsuario(0);
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
        Usu_contraDAO pdao;
        try {
            pdao = new Usu_contraDAO();
            pdao.registrar(usu);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de contraseña y usuario exitoso", "Registro de contraseña y usuario exitoso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en registro del usuario exitoso", "Error en registro del usuario exitoso"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }
    
    
    public void listar(boolean lis) throws SQLException {
        Usu_contraDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new Usu_contraDAO();
                    lstUsu = pdao.listar();
                }
            } else {
                pdao = new Usu_contraDAO();
                lstUsu = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    public void leerID(Usu_contra per) throws Exception {
        Usu_contraDAO pdao;
        Usu_contra temp;
        try {
            pdao = new Usu_contraDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.usu = temp;
                this.accion = "Modificar Registro";
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    
    private void modificar() throws Exception {
        Usu_contraDAO proddao;
        try {
            proddao = new Usu_contraDAO();
            proddao.actualizar(usu);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación en el usuraio y/o contraseña", "Modificación en el usuraio y/o contraseña"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación del usuraio y/o contraseña", "Error en la modificación del usuraio y/o contraseña"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    
    public void eliminar() throws Exception {
        Usu_contraDAO proddao;
        try {
            proddao = new Usu_contraDAO();
            proddao.eliminar(usu);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se eliminó correctamente la información", "Se eliminó correctamente la información"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al eliminar", "Error al eliminar"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
}
