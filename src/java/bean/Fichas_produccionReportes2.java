package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class Fichas_produccionReportes2 {
    
    private int idSatelite;
    private String estadoFicha_producc;
    
    public Fichas_produccionReportes2() {
    }

    public int getIdSatelite() {
        return idSatelite;
    }

    public void setIdSatelite(int idSatelite) {
        this.idSatelite = idSatelite;
    }

    public String getEstadoFicha_producc() {
        return estadoFicha_producc;
    }

    public void setEstadoFicha_producc(String estadoFicha_producc) {
        this.estadoFicha_producc = estadoFicha_producc;
    }
    
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Fichas_produccionDataSource2 rCliente = new Fichas_produccionDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportFicha_produccion2.jasper");
       
        rCliente.getReportePdf(ruta, this.idSatelite, this.estadoFicha_producc);
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteExcel() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
              
        Fichas_produccionDataSource2 rCliente = new Fichas_produccionDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportFicha_produccion2.jasper");
       
        rCliente.getReporteExcel(ruta, this.idSatelite, this.estadoFicha_producc);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteWord() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
           
        Fichas_produccionDataSource2 rCliente = new Fichas_produccionDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportFicha_produccion2.jasper");
       
        rCliente.getReporteWord(ruta, this.idSatelite, this.estadoFicha_producc);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReportePpt() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Fichas_produccionDataSource2 rCliente = new Fichas_produccionDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportFicha_produccion2.jasper");
       
        rCliente.getReportePPT(ruta, this.idSatelite, this.estadoFicha_producc);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
