package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class Cotizaciones_FichaReportes2 {
    
    private int idSatelite;
    
    public Cotizaciones_FichaReportes2() {
    }

    public int getIdSatelite() {
        return idSatelite;
    }

    public void setIdSatelite(int idSatelite) {
        this.idSatelite = idSatelite;
    }
    
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Cotizaciones_FichaDataSource2 rCliente = new Cotizaciones_FichaDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizacionesProduccion2.jasper");
       
        rCliente.getReportePdf(ruta, this.idSatelite);
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteExcel() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
              
        Cotizaciones_FichaDataSource2 rCliente = new Cotizaciones_FichaDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizacionesProduccion2.jasper");
       
        rCliente.getReporteExcel(ruta, this.idSatelite);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteWord() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
           
        Cotizaciones_FichaDataSource2 rCliente = new Cotizaciones_FichaDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizacionesProduccion2.jasper");
       
        rCliente.getReporteWord(ruta, this.idSatelite);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReportePpt() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Cotizaciones_FichaDataSource2 rCliente = new Cotizaciones_FichaDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizacionesProduccion2.jasper");
       
        rCliente.getReportePPT(ruta, this.idSatelite);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
