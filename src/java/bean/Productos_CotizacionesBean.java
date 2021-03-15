package bean;

import com.mysql.jdbc.Driver;
import dao.Productos_CotizacionesDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import modelo.Cotizacion;
import modelo.Producto_Cotizaciones;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;

@ManagedBean
@ViewScoped

public class Productos_CotizacionesBean {
    
    private Producto_Cotizaciones cotizaciones = new Producto_Cotizaciones();
    private List<Producto_Cotizaciones> lstCoti;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    public Producto_Cotizaciones getCotizaciones() {
        return cotizaciones;
    }

    public void setCotizaciones(Producto_Cotizaciones cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public List<Producto_Cotizaciones> getLstCoti() {
        return lstCoti;
    }

    public void setLstCoti(List<Producto_Cotizaciones> lstCoti) {
        this.lstCoti = lstCoti;
    }
 
    private void limpiar() {
        this.cotizaciones.setIdCotizacion(0);
        this.cotizaciones.setIdProducto(0);
        this.cotizaciones.setIdTalla("");
        this.cotizaciones.setCantidad(0);
        this.cotizaciones.setValorUnitario(0.0);
    }

    public void operar() throws Exception {
        switch (accion.charAt(0)) {
            case 'A':
                this.registrar();
                break;
            case 'M':
                this.modificar();
        }
    }
    
    private void registrar() throws Exception {
        Productos_CotizacionesDAO pdao;       
        try {
            pdao = new Productos_CotizacionesDAO();
            pdao.registrar(cotizaciones);
            this.listar(true);
        } catch (SQLException e) {
            throw e;
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        Productos_CotizacionesDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new Productos_CotizacionesDAO();
                    lstCoti = pdao.listar();
                }
            } else {
                pdao = new Productos_CotizacionesDAO();
                lstCoti = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public void leerID(Producto_Cotizaciones per) throws Exception {
        Productos_CotizacionesDAO pdao;
        Producto_Cotizaciones temp;

        try {
            pdao = new Productos_CotizacionesDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.cotizaciones = temp;
                this.accion = "Modificar";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        Productos_CotizacionesDAO pdao;
        try {
            pdao = new Productos_CotizacionesDAO();
            pdao.actualizar(cotizaciones);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar(Producto_Cotizaciones per) throws Exception {
        Productos_CotizacionesDAO pdao;
        try {
            pdao = new Productos_CotizacionesDAO();
            pdao.eliminar(per);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }
    
    Part excel;

    public Part getExcel() {
        return excel;
    }

    public void setExcel(Part excel) {
        this.excel = excel;
    }

    public void migrar() {
        try {
            Driver drv = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(drv);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shalom?user=root&password=&useSSL=false");

            Workbook libro = WorkbookFactory.create(excel.getInputStream()); //Crea libro de Excel
            XSSFSheet hoja = (XSSFSheet) libro.getSheetAt(0); //Hoja de Excel con los Datos

            Iterator<Row> itrFila = hoja.rowIterator(); //Para recorrer las Filas de Datos
            itrFila.next(); //Salta la Fila de Encabezados de la Tabla de Excel
            boolean guardar;
            while (itrFila.hasNext()) {
                guardar = true;
                Row fila = itrFila.next();

                Iterator<Cell> itrCelda = fila.cellIterator();
                int ncamp = 1;
                String query = "INSERT INTO productos_cotizaciones VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idCotizacion - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //idProducto - int  
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 3: //idTalla - String
                            query += ", '" + celda.getStringCellValue()+ "'";
                            break;
                        case 4: //cantidad - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 5: //valorUnitario - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                    }
                    ncamp++;
                }
                query += ");";

                if (guardar) {
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.executeUpdate();
                }
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Migración Correcta"));
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error Abriendo Archivo"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error en los datos suministrados"));
        } catch (InvalidFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error en Formato de Datos"));
        } catch (IllegalStateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en los datos, algunos valores no se encuentran en su formato (númerico / alfabetico)", "Error en los datos, algunos valores no se encuentran en su formato (númerico / alfabetico)"));
        }
    }
}
