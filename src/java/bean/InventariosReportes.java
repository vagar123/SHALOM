package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class InventariosReportes {
    
    private int idInventario;
    
    public InventariosReportes() {
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }
    
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        InventariosDataSource rCliente = new InventariosDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportInventarios.jasper");
       
        rCliente.getReportePdf(ruta, this.idInventario);
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteExcel() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
     
        InventariosDataSource rCliente = new InventariosDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportInventarios.jasper");
       
        rCliente.getReporteExcel(ruta, this.idInventario);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteWord() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
           
        InventariosDataSource rCliente = new InventariosDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportInventarios.jasper");
       
        rCliente.getReporteWord(ruta, this.idInventario);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReportePpt() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
      
        InventariosDataSource rCliente = new InventariosDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportInventarios.jasper");
       
        rCliente.getReportePPT(ruta, this.idInventario);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
