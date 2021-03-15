package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class Orden_ProduccionReporte {
    
    private int idOrden_produccion;
    
    public Orden_ProduccionReporte(){
    }

    public int getIdOrden_produccion() {
        return idOrden_produccion;
    }

    public void setIdOrden_produccion(int idOrden_produccion) {
        this.idOrden_produccion = idOrden_produccion;
    }
    
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Orden_ProduccionDataSource orden = new Orden_ProduccionDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/orden_produccion.jasper");
       
        orden.getReporte(ruta, this.idOrden_produccion);
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
