package bean;

import dao.LocalesDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Locales;


@ManagedBean
@ViewScoped
public class LocalesBean {
    
    private Locales local = new Locales();
    private List<Locales> lstLocales;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.local.setIdLocal(0);
    }

    public List<Locales> getLstLocales() {
        return lstLocales;
    }

    public void setLstLocales(List<Locales> lstLocales) {
        this.lstLocales = lstLocales;
    }

    public Locales getLocales() {
        return local;
    }

    public void setLocales(Locales local) {
        this.local = local;
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
        LocalesDAO pdao;
        
        try {
            pdao = new LocalesDAO();
            pdao.registrar(local);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        LocalesDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new LocalesDAO();
                    lstLocales = pdao.listar();
                }
            } else {
                pdao = new LocalesDAO();
                lstLocales = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
     
    public void leerID(Locales per) throws Exception {
        LocalesDAO pdao;
        Locales temp;

        try {
            pdao = new LocalesDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.local = temp;
                this.accion = "Modificar Local";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        LocalesDAO proddao;

        try {
            proddao = new LocalesDAO();
            proddao.actualizar(local);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar(Locales per) throws Exception {
        LocalesDAO pdao;

        try {
            pdao = new LocalesDAO();
            pdao.eliminar(per);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }
}
