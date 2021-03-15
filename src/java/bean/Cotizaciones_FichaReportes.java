package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class Cotizaciones_FichaReportes {
    
    private int idSatelite;
    private String productoCotizacion_ficha;
    
    public Cotizaciones_FichaReportes() {
    }

    public int getIdSatelite() {
        return idSatelite;
    }

    public void setIdSatelite(int idSatelite) {
        this.idSatelite = idSatelite;
    }

    public String getProductoCotizacion_ficha() {
        return productoCotizacion_ficha;
    }

    public void setProductoCotizacion_ficha(String productoCotizacion_ficha) {
        this.productoCotizacion_ficha = productoCotizacion_ficha;
    }
    
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Cotizaciones_FichaDataSource rCliente = new Cotizaciones_FichaDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizacionesProduccion.jasper");
       
        rCliente.getReportePdf(ruta, this.idSatelite, this.productoCotizacion_ficha);
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteExcel() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
              
        Cotizaciones_FichaDataSource rCliente = new Cotizaciones_FichaDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizacionesProduccion.jasper");
       
        rCliente.getReporteExcel(ruta, this.idSatelite, this.productoCotizacion_ficha);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteWord() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
           
        Cotizaciones_FichaDataSource rCliente = new Cotizaciones_FichaDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizacionesProduccion.jasper");
       
        rCliente.getReporteWord(ruta, this.idSatelite, this.productoCotizacion_ficha);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReportePpt() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Cotizaciones_FichaDataSource rCliente = new Cotizaciones_FichaDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportCotizacionesProduccion.jasper");
       
        rCliente.getReportePPT(ruta, this.idSatelite, this.productoCotizacion_ficha);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
