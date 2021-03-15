package bean;

import com.mysql.jdbc.Driver;
import dao.Productos_OrdenesDAO;
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
import modelo.Producto_Ordenes;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;

@ManagedBean
@ViewScoped

public class Productos_OrdenesBean {
    
    private Producto_Ordenes ordenes = new Producto_Ordenes();
    private List<Producto_Ordenes> lstOrden;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    public Producto_Ordenes getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(Producto_Ordenes ordenes) {
        this.ordenes = ordenes;
    }

    public List<Producto_Ordenes> getLstOrden() {
        return lstOrden;
    }

    public void setLstOrden(List<Producto_Ordenes> lstOrden) {
        this.lstOrden = lstOrden;
    }   
 
    private void limpiar() {
        this.ordenes.setIdOrdenpago(0);
        this.ordenes.setIdProducto(0);
        this.ordenes.setIdTalla("");
        this.ordenes.setCantidad(0);
        this.ordenes.setTotal(0.0);
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
        Productos_OrdenesDAO pdao;       
        try {
            pdao = new Productos_OrdenesDAO();
            pdao.registrar(ordenes);
            this.listar(true);
        } catch (SQLException e) {
            throw e;
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        Productos_OrdenesDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new Productos_OrdenesDAO();
                    lstOrden = pdao.listar();
                }
            } else {
                pdao = new Productos_OrdenesDAO();
                lstOrden = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public void leerID(Producto_Ordenes per) throws Exception {
        Productos_OrdenesDAO pdao;
        Producto_Ordenes temp;

        try {
            pdao = new Productos_OrdenesDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.ordenes = temp;
                this.accion = "Modificar";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        Productos_OrdenesDAO pdao;
        try {
            pdao = new Productos_OrdenesDAO();
            pdao.actualizar(ordenes);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar(Producto_Ordenes per) throws Exception {
        Productos_OrdenesDAO pdao;
        try {
            pdao = new Productos_OrdenesDAO();
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

            Workbook libro = WorkbookFactory.create(excel.getInputStream()); 
            XSSFSheet hoja = (XSSFSheet) libro.getSheetAt(0); 

            Iterator<Row> itrFila = hoja.rowIterator(); 
            itrFila.next();
            boolean guardar;
            while (itrFila.hasNext()) {
                guardar = true;
                Row fila = itrFila.next();

                Iterator<Cell> itrCelda = fila.cellIterator();
                int ncamp = 1;
                String query = "INSERT INTO productos_ordenes VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idOrdenpago - int
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
                        case 5: //total - int
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
