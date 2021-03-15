package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class Ordenes_pagoReportes3 {
    
    private String fechaOrdenpago;
    private String categoriaProducto;
    
    public Ordenes_pagoReportes3() {
    }

    public String getFechaOrdenpago() {
        return fechaOrdenpago;
    }

    public void setFechaOrdenpago(String fechaOrdenpago) {
        this.fechaOrdenpago = fechaOrdenpago;
    }

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }
    
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Ordenes_pagoDataSource3 rCliente = new Ordenes_pagoDataSource3();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportOrdenPago2.jasper");
       
        rCliente.getReportePdf(ruta, this.fechaOrdenpago, this.categoriaProducto);
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteExcel() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
              
        Ordenes_pagoDataSource3 rCliente = new Ordenes_pagoDataSource3();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportOrdenPago2.jasper");
       
        rCliente.getReporteExcel(ruta, this.fechaOrdenpago, this.categoriaProducto);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteWord() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
           
        Ordenes_pagoDataSource3 rCliente = new Ordenes_pagoDataSource3();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportOrdenPago2.jasper");
       
        rCliente.getReporteWord(ruta, this.fechaOrdenpago, this.categoriaProducto);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReportePpt() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        Ordenes_pagoDataSource3 rCliente = new Ordenes_pagoDataSource3();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportOrdenPago2.jasper");
       
        rCliente.getReportePPT(ruta, this.fechaOrdenpago, this.categoriaProducto);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
