package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class FichaReporte {
    
    private int idFicha_produccion;
    
    public FichaReporte(){
    }

    public int getIdFicha_produccion() {
        return idFicha_produccion;
    }

    public void setIdFicha_produccion(int idFicha_produccion) {
        this.idFicha_produccion = idFicha_produccion;
    }

    
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        FichaDataSource ficha = new FichaDataSource();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/ficha_produccion.jasper");
       
        ficha.getReporte(ruta, this.idFicha_produccion);
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
