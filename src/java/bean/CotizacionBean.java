package bean;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Cotizacion;
import modelo.Producto_Cotizaciones;
import modelo.Productos;
import dao.CotizacionDAO;
import java.util.List;

//Librerias para la migración
import com.mysql.jdbc.Driver;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
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
public class CotizacionBean {
        
    private Cotizacion cotizaciones = new Cotizacion();
    private Producto_Cotizaciones detalleCotizacion = new Producto_Cotizaciones();
    private Productos producto = new Productos();
    private String talla;
    private int cantidad;
    private List<Cotizacion> lstCotizacion;
    private List<Producto_Cotizaciones> lista = new ArrayList();
    private String accion;

    
    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    public List<Cotizacion> getLstcotizaciones() {
        return lstCotizacion;
    }

    public void setLstCotizacion(List<Cotizacion> lstCotizacion) {
        this.lstCotizacion = lstCotizacion;
    }

    public Cotizacion getCotizacion() {
        return cotizaciones;
    }

    public void setCotizacion(Cotizacion cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public Cotizacion getCotizaciones() {
        return cotizaciones;
    }

    public void setCotizaciones(Cotizacion cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public Producto_Cotizaciones getDetalleCotizacion() {
        return detalleCotizacion;
    }

    public void setDetalleCotizacion(Producto_Cotizaciones detalleCotizacion) {
        this.detalleCotizacion = detalleCotizacion;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List<Producto_Cotizaciones> getLista() {
        return lista;
    }

    public void setLista(List<Producto_Cotizaciones> lista) {
        this.lista = lista;
    }

    
    private void limpiar() {
        this.cotizaciones.setIdCotizacion(0);
        this.cotizaciones.setIdCliente(0);
        this.cotizaciones.setIdUsuario(0);
        this.cotizaciones.setFechaCotizacion(new java.sql.Date(0));
        this.cotizaciones.setValortotalCotizacion(0.0);
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
    
    public void agregar() {
        Producto_Cotizaciones detv = new Producto_Cotizaciones();
        detv.setProducto(producto);
        detv.setIdTalla(talla);
        detv.setCantidad(cantidad);
        detv.setValorUnitario(detv.getCantidad() * detv.getProducto().getPrecioProducto());
        this.lista.add(detv);
        this.totalCotizacion();
    }
    
    public void totalCotizacion() {
        double valortotalCotizacion = 0;
        try {
            for (Producto_Cotizaciones det : lista) {
                valortotalCotizacion += det.getProducto().getPrecioProducto() * det.getCantidad();
            }
            this.cotizaciones.setValortotalCotizacion(valortotalCotizacion);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void totalProducto() {
        double valorUnitario = 0;
        try {
            for (Producto_Cotizaciones det : lista) {
                valorUnitario = det.getProducto().getPrecioProducto() * det.getCantidad();
            }
            this.detalleCotizacion.setValorUnitario(valorUnitario);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void quitarProducto(int idProducto) {
        try {
            int i = 0;
            for (Producto_Cotizaciones item : this.lista) {
                if (item.getProducto().equals(idProducto)) {
                } else {
                    this.lista.remove(i);
                    break;
                }
                i++;
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Se retiró correctamente el producto de la cotización"));
            this.totalCotizacion();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", e.getMessage()));
        }
    }

    public void registrar() {
        CotizacionDAO dao;
        double valorUnitario = 0;
        for (Producto_Cotizaciones det : lista) {
            valorUnitario += det.getProducto().getPrecioProducto() * det.getCantidad();
        }
        try {
            dao = new CotizacionDAO();
            cotizaciones.setValortotalCotizacion(valorUnitario);
            dao.registrar(cotizaciones, lista);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cotización exitoso", "Registro de Cotización exitoso"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en Registro de Cotización", "Error en Registro de Cotización"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        CotizacionDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new CotizacionDAO();
                    lstCotizacion = pdao.listar();
                }
            } else {
                pdao = new CotizacionDAO();
                lstCotizacion = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public void leerID(Cotizacion per) throws Exception {
        CotizacionDAO pdao;
        Cotizacion temp;
        try {
            pdao = new CotizacionDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.cotizaciones = temp;
                this.accion = "Modificar";
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
     public void ver(Cotizacion per) throws Exception {
        CotizacionDAO pdao;
        Cotizacion temp;
        try {
            pdao = new CotizacionDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.cotizaciones = temp;
                this.accion = "Ver Cotización";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        CotizacionDAO pdao;
        try {
            pdao = new CotizacionDAO();
            pdao.actualizar(cotizaciones);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar() throws Exception {
        CotizacionDAO pdao;
        try {
            pdao = new CotizacionDAO();
            pdao.eliminar(cotizaciones);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se eliminó correctamente la cotización", "Se eliminó correctamente la cotización"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación de la cotización", "Error en la eliminación de la cotización"));
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
                String query = "INSERT INTO cotizaciones VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idCotizacion - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //idCliente - int  
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 3: //idUsuario - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 4: //fechaCotizacion - fecha
                            Date d = celda.getDateCellValue();//Convertimos d (util.Date) a fehchain (sql.Date)
                            java.sql.Date fechain = new java.sql.Date(d.getTime());
                            query += ", '" + fechain + "'";
                            break;
                        case 5: //valortotalCotizacion - int
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

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Migración Correcta"));
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error Abriendo Archivo"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error en los datos suministrados"));
        } catch (InvalidFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error en Formato de Datos"));
        }
    }
    
    public void migrarProductos_Cotizaciones() {
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

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Migracion Correcta"));
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
