package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class Ordenes_pagoReportes1 {
    
    private String categoriProducto;
    
    public Ordenes_pagoReportes1() {
    }

    public String getCategoriProducto() {
        return categoriProducto;
    }

    public void setCategoriProducto(String categoriProducto) {
        this.categoriProducto = categoriProducto;
    }
    
    
    //Metodo para invocar el reporte y enviarle los parametros si es que necesita
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        //Instancia hacia la clase reporteClientes        
        Ordenes_pagoDataSource1 rCliente = new Ordenes_pagoDataSource1();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportVentas.jasper");
       
        rCliente.getReportePdf(ruta, this.categoriProducto);
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteExcel() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        //Instancia hacia la clase reporteClientes        
        Ordenes_pagoDataSource1 rCliente = new Ordenes_pagoDataSource1();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportVentas.jasper");
       
        rCliente.getReporteExcel(ruta, this.categoriProducto);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteWord() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        //Instancia hacia la clase reporteClientes        
        Ordenes_pagoDataSource1 rCliente = new Ordenes_pagoDataSource1();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportVentas.jasper");
       
        rCliente.getReporteWord(ruta, this.categoriProducto);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReportePpt() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        //Instancia hacia la clase reporteClientes        
        Ordenes_pagoDataSource1 rCliente = new Ordenes_pagoDataSource1();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportVentas.jasper");
       
        rCliente.getReportePPT(ruta, this.categoriProducto);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
