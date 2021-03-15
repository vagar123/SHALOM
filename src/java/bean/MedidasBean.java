package bean;

import dao.MedidasDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Medidas;

@ManagedBean
@ViewScoped
public class MedidasBean {

    private Medidas medida = new Medidas();
    private List<Medidas> lstMedidas;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.medida.setNomMedida("");
    }

    public Medidas getMedida() {
        return medida;
    }

    public void setMedida(Medidas medida) {
        this.medida = medida;
    }

    public List<Medidas> getLstMedidas() {
        return lstMedidas;
    }

    public void setLstMedidas(List<Medidas> lstMedidas) {
        this.lstMedidas = lstMedidas;
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
        MedidasDAO pdao;
        try {
            pdao = new MedidasDAO();
            pdao.registrar(medida);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        MedidasDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new MedidasDAO();
                    lstMedidas = pdao.listar();
                }
            } else {
                pdao = new MedidasDAO();
                lstMedidas = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void leerID(Medidas per) throws Exception {
        MedidasDAO pdao;
        Medidas temp;
        try {
            pdao = new MedidasDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.medida = temp;
                this.accion = "Modificar Medida";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        MedidasDAO proddao;
        try {
            proddao = new MedidasDAO();
            proddao.actualizar(medida);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar(Medidas per) throws Exception {
        MedidasDAO pdao;
        try {
            pdao = new MedidasDAO();
            pdao.eliminar(per);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }
}
