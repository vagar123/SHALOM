package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class Cotizacion_VentaReporte {
    
    private int idCotizacion;
    
    public Cotizacion_VentaReporte() {
    }

    public int getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(int idCotizacion) {
        this.idCotizacion = idCotizacion;
    }
    
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Cotizacion_VentaDataSource cotizacion = new Cotizacion_VentaDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/cotizacion.jasper");
       
        cotizacion.getReporte(ruta, this.idCotizacion);
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
