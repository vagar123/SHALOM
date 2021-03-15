package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class Cotizacion_ProduccionReporte {
    
    private int idCotizacion_ficha;
    
    public Cotizacion_ProduccionReporte(){        
    }

    public int getIdCotizacion_ficha() {
        return idCotizacion_ficha;
    }

    public void setIdCotizacion_ficha(int idCotizacion_ficha) {
        this.idCotizacion_ficha = idCotizacion_ficha;
    }
    
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Cotizacion_ProduccionDataSource cotizacion = new Cotizacion_ProduccionDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/cotizacion_produccion.jasper");
       
        cotizacion.getReporte(ruta, this.idCotizacion_ficha);
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
