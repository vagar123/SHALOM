package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class Cotizaciones_FichaReportes3 {
    
    private String productoCotizacion_ficha;
    
    public Cotizaciones_FichaReportes3() {
    }

    public String getProductoCotizacion_ficha() {
        return productoCotizacion_ficha;
    }

    public void setProductoCotizacion_ficha(String productoCotizacion_ficha) {
        this.productoCotizacion_ficha = productoCotizacion_ficha;
    }
    
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Cotizaciones_FichaDataSource3 rCliente = new Cotizaciones_FichaDataSource3();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizacionesProduccion3.jasper");
       
        rCliente.getReportePdf(ruta, this.productoCotizacion_ficha);
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteExcel() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
              
        Cotizaciones_FichaDataSource3 rCliente = new Cotizaciones_FichaDataSource3();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizacionesProduccion3.jasper");
       
        rCliente.getReporteExcel(ruta, this.productoCotizacion_ficha);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteWord() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
           
        Cotizaciones_FichaDataSource3 rCliente = new Cotizaciones_FichaDataSource3();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizacionesProduccion3.jasper");
       
        rCliente.getReporteWord(ruta, this.productoCotizacion_ficha);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReportePpt() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Cotizaciones_FichaDataSource3 rCliente = new Cotizaciones_FichaDataSource3();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizacionesProduccion3.jasper");
       
        rCliente.getReportePPT(ruta, this.productoCotizacion_ficha);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
