package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped

public class ProductosReportes2 {
    
    private int cantidadProducto;
    private int idInventario;
    
    public ProductosReportes2() {
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }
    
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        ProductosDataSource2 rCliente = new ProductosDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportProductos2.jasper");
       
        rCliente.getReportePdf(ruta, this.cantidadProducto, this.idInventario);
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteExcel() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
              
        ProductosDataSource2 rCliente = new ProductosDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportProductos2.jasper");
       
        rCliente.getReporteExcel(ruta, this.cantidadProducto, this.idInventario);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReporteWord() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
           
        ProductosDataSource2 rCliente = new ProductosDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportProductos2.jasper");
       
        rCliente.getReporteWord(ruta, this.cantidadProducto, this.idInventario);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
    
    public void verReportePpt() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
             
        ProductosDataSource2 rCliente = new ProductosDataSource2();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportProductos2.jasper");
       
        rCliente.getReportePPT(ruta, this.cantidadProducto, this.idInventario);        
        FacesContext.getCurrentInstance().responseComplete();               
    }
}
