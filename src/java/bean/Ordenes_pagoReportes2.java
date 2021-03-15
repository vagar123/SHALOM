package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class Ordenes_pagoReportes2 {
    
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Ordenes_pagoDataSource2 rCliente = new Ordenes_pagoDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportOrdenPago.jasper");
       
        rCliente.getReportePdf(ruta);
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
     public void verReporteExcel() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
              
        Ordenes_pagoDataSource2 rCliente = new Ordenes_pagoDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportOrdenPago.jasper");
       
        rCliente.getReporteExcel(ruta);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteWord() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
           
        Ordenes_pagoDataSource2 rCliente = new Ordenes_pagoDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportOrdenPago.jasper");
       
        rCliente.getReporteWord(ruta);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReportePpt() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Ordenes_pagoDataSource2 rCliente = new Ordenes_pagoDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportOrdenPago.jasper");
       
        rCliente.getReportePPT(ruta);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
