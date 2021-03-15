package bean;

import dao.Ordenes_produccionDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Ordenes_produccion;

//Librerias para la migración
import com.mysql.jdbc.Driver;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.servlet.http.Part;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;

@ManagedBean
@ViewScoped
public class Ordenes_produccionBean {
    
    private Ordenes_produccion OrdenProducccion = new Ordenes_produccion();
    private List<Ordenes_produccion> lstOrdenes_produccion;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.OrdenProducccion.setIdOrden_produccion(5120);
        this.OrdenProducccion.setProductoOrden_produccion("");
        this.OrdenProducccion.setFechaOrden_produccion(new java.sql.Date(0));
        this.OrdenProducccion.setGeneroProdOrden_produccion("");
        this.OrdenProducccion.setTallaOrden_produccion("");
        this.OrdenProducccion.setCantidadOrden_produccion(0);
        this.OrdenProducccion.setColorOrden_produccion("");
        this.OrdenProducccion.setValorUnitarioOrden_produccion(0.0);
        this.OrdenProducccion.setDescuentoOrden_produccion(0.0);
        this.OrdenProducccion.setValorTotalOrden_produccion(0.0);
        this.OrdenProducccion.setCategoriaOrden_produccion("");
        this.OrdenProducccion.setDescriOrden_produccion("");
        this.OrdenProducccion.setIdSatelite(0);
        this.OrdenProducccion.setIdCotizacion_ficha(0);
    }

    public List<Ordenes_produccion> getLstOrdenes_produccion() {
        return lstOrdenes_produccion;
    }

    public void setLstOrdenes_produccion(List<Ordenes_produccion> lstOrdenes_produccion) {
        this.lstOrdenes_produccion = lstOrdenes_produccion;
    }

    public Ordenes_produccion getOrdenes_produccion() {
        return OrdenProducccion;
    }

    public void setOrdenes_produccion(Ordenes_produccion OrdenProducccion) {
        this.OrdenProducccion = OrdenProducccion;
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

    public void registrar() throws Exception{
        Ordenes_produccionDAO pdao;
        try {
            pdao = new Ordenes_produccionDAO();
            pdao.registrar(OrdenProducccion);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Orden de producción exitoso", "Registro de Orden de producción exitoso"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en Registro de Orden de producción", "Error en Registro de Orden de producción"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        Ordenes_produccionDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new Ordenes_produccionDAO();
                    lstOrdenes_produccion = pdao.listar();
                }
            } else {
                pdao = new Ordenes_produccionDAO();
                lstOrdenes_produccion = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
     
    public void leerID(Ordenes_produccion per) throws Exception {
        Ordenes_produccionDAO pdao;
        Ordenes_produccion temp;
        try {
            pdao = new Ordenes_produccionDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.OrdenProducccion = temp;
                this.accion = "Modificar Orden de producción";
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void ver(Ordenes_produccion  per) throws Exception {
        Ordenes_produccionDAO pdao;
        Ordenes_produccion temp;        
        try {
            pdao = new Ordenes_produccionDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.OrdenProducccion = temp;
                this.accion = "Ver Orden de producción";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        Ordenes_produccionDAO proddao;
        try {
            proddao = new Ordenes_produccionDAO();
            proddao.actualizar(OrdenProducccion);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modificó correctamente la orden de producción", "Se modificó correctamente la orden de producción"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación de la orden de producción", "Error en la modificación de la orden de producción"));
        } 
    }

    public void eliminar() throws Exception {
        Ordenes_produccionDAO pdao;
        try {
            pdao = new Ordenes_produccionDAO();
            pdao.eliminar(OrdenProducccion);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se eliminó correctamente la orden de producción", "Se eliminó correctamente la orden de producción"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación de la orden de producción", "Error en la eliminación de la orden de producción"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
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
                String query = "INSERT INTO ordenes_produccion VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idOrden_produccion - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //productoOrden_produccion - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 3: //fechaOrden_produccion - date
                            Date d = celda.getDateCellValue();//Convertimos d (util.Date) a fehchain (sql.Date)
                            java.sql.Date fechain = new java.sql.Date(d.getTime());
                            query += ", '" + fechain + "'";
                            break;
                        case 4: //generoProdOrden_produccion - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 5: //tallaOrden_produccion - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 6: //cantidadOrden_produccion - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 7: //colorOrden_produccion - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 8: //valorUnitarioOrden_produccion - Decimal
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 9: //descuentoOrden_produccion - Decimal
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 10: //valorTotalOrden_produccion - Decimal
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 11: //categoriaOrden_produccion - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 12: //descriOrden_produccion - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 13: //idSatelite - String
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 14: //idCotizacion_ficha - int
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
            
            this.listar(true);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Migración Correcta", "Migración Correcta"));
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Abriendo Archivo", "Error Abriendo Archivo"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en los datos suministrados", "Error en los datos suministrados"));
        } catch (InvalidFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en Formato de Datos", "Error en Formato de Datos"));
        } catch (IllegalStateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en los datos, algunos valores no se encuentran en su formato (númerico / alfabetico)", "Error en los datos, algunos valores no se encuentran en su formato (númerico / alfabetico)"));
        }
    }
}
