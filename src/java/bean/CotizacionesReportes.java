package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.util.Date;

@ManagedBean
@ViewScoped

public class CotizacionesReportes {
    
    private Date fechaCotizacion;
    
    public CotizacionesReportes() {
    }

    public Date getFechaCotizacion() {
        return fechaCotizacion;
    }

    public void setFechaCotizacion(Date fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
    }

    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        CotizacionesDataSource rCliente = new CotizacionesDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizaciones.jasper");
       
        rCliente.getReportePdf(ruta, this.fechaCotizacion);
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteExcel() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
              
        CotizacionesDataSource rCliente = new CotizacionesDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizaciones.jasper");
       
        rCliente.getReporteExcel(ruta, this.fechaCotizacion);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteWord() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
           
        CotizacionesDataSource rCliente = new CotizacionesDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizaciones.jasper");
       
        rCliente.getReporteWord(ruta, this.fechaCotizacion);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReportePpt() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        CotizacionesDataSource rCliente = new CotizacionesDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizaciones.jasper");
       
        rCliente.getReportePPT(ruta, this.fechaCotizacion);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
