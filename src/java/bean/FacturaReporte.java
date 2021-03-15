package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class FacturaReporte {
    
    private int idOrdenpago;
    
    public FacturaReporte() {
    }

    public int getIdOrdenpago() {
        return idOrdenpago;
    }

    public void setIdOrdenpago(int idOrdenpago) {
        this.idOrdenpago = idOrdenpago;
    }
    
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        FacturaDataSource factura = new FacturaDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/factura.jasper");
       
        factura.getReporte(ruta, this.idOrdenpago);
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
