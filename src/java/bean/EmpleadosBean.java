package bean;

import dao.EmpleadosDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Empleados;


@ManagedBean
@ViewScoped

public class EmpleadosBean {
    
    private Empleados empleado = new Empleados();
    private List<Empleados> lstEmpleados;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.empleado.setIdEmpleado(0);
    }

    public List<Empleados> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleados> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }

    public Empleados getEmpleados() {
        return empleado;
    }

    public void setEmpleados(Empleados empleado) {
        this.empleado = empleado;
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
        EmpleadosDAO pdao;
        
        try {
            pdao = new EmpleadosDAO();
            pdao.registrar(empleado);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        EmpleadosDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new EmpleadosDAO();
                    lstEmpleados = pdao.listar();
                }
            } else {
                pdao = new EmpleadosDAO();
                lstEmpleados = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
     
    public void leerID(Empleados per) throws Exception {
        EmpleadosDAO pdao;
        Empleados temp;

        try {
            pdao = new EmpleadosDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.empleado = temp;
                this.accion = "Modificar Local";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        EmpleadosDAO proddao;

        try {
            proddao = new EmpleadosDAO();
            proddao.actualizar(empleado);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar(Empleados per) throws Exception {
        EmpleadosDAO pdao;

        try {
            pdao = new EmpleadosDAO();
            pdao.eliminar(per);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }
}
