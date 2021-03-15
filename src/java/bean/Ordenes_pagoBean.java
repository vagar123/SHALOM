package bean;

import dao.Ordenes_pagoDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Ordenes_pago;
import modelo.Factura;
import modelo.Productos;
import modelo.Producto_Ordenes;

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
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.primefaces.event.RowEditEvent;


@ManagedBean
@ViewScoped
public class Ordenes_pagoBean {

    private Ordenes_pago ordenpago = new Ordenes_pago();
    private Producto_Ordenes detalleOrden = new Producto_Ordenes();
    private Productos producto = new Productos();
    private Factura factura = new Factura();
    private String talla;
    private int cantidad;
    private List<Ordenes_pago> lstOrdenes_pago;
    private List<Producto_Ordenes> lista = new ArrayList();
    private List<Factura> listaDetalle = new ArrayList();
    private String accion;

    public Ordenes_pago getOrdenpago() {
        return ordenpago;
    }
    public void setOrdenpago(Ordenes_pago ordenpago) {
        this.ordenpago = ordenpago;
    }

    public Producto_Ordenes getDetalleOrden() {
        return detalleOrden;
    }
    public void setDetalleOrden(Producto_Ordenes detalleOrden) {
        this.detalleOrden = detalleOrden;
    }


    public Productos getProducto() {
        return producto;
    }
    public void setProducto(Productos producto) {
        this.producto = producto;
    }


    public Factura getFactura() {
        return factura;
    }
    public void setFactura(Factura factura) {
        this.factura = factura;
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

    
    public List<Ordenes_pago> getLstOrdenes_pago() {
        return lstOrdenes_pago;
    }
    public void setLstOrdenes_pago(List<Ordenes_pago> lstOrdenes_pago) {
        this.lstOrdenes_pago = lstOrdenes_pago;
    }

    
    public List<Producto_Ordenes> getLista() {
        return lista;
    }
    public void setLista(List<Producto_Ordenes> lista) {
        this.lista = lista;
    }


    public List<Factura> getListaDetalle() {
        return listaDetalle;
    }
    public void setListaDetalle(List<Factura> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    
    public String getAccion() {
        return accion;
    }
    
    
    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.ordenpago.setIdOrdenpago(0);
        this.ordenpago.setTipopagoOrdenpago("");
        this.ordenpago.setFechaOrdenpago(new java.sql.Date(0));
        this.ordenpago.setValorTotalOrdenpago(0);
    }

    public Ordenes_pago getOrdenes_pago() {
        return ordenpago;
    }

    public void setOrdenes_pago(Ordenes_pago ordenpago) {
        this.ordenpago = ordenpago;
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
        Producto_Ordenes detv = new Producto_Ordenes();
        detv.setProducto(producto);
        detv.setCantidad(cantidad);
        detv.setIdTalla(talla);
        detv.setTotal(detv.getCantidad() * detv.getProducto().getPrecioProducto());
        this.lista.add(detv);
        this.totalFactura();
    }

    public void totalFactura() {
        int valorTotalOrdenpago = 0;
        try {
            for (Producto_Ordenes det : lista) {
                valorTotalOrdenpago += det.getProducto().getPrecioProducto() * det.getCantidad();
            }
            this.ordenpago.setValorTotalOrdenpago(valorTotalOrdenpago);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void totalProducto() {
        double total = 0;
        try {
            for (Producto_Ordenes det : lista) {
                total = det.getProducto().getPrecioProducto() * det.getCantidad();
            }
            this.detalleOrden.setTotal(total);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void quitarProducto(int idProducto) {
        try {
            int i = 0;
            for (Producto_Ordenes item : this.lista) {
                if (item.getProducto().equals(idProducto)) {
                } else {
                    this.lista.remove(i);
                    break;
                }
                i++;
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Se retiró correctamente el producto de la factura"));
            this.totalFactura();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", e.getMessage()));
        }
    }

    public void registrar() {
        Ordenes_pagoDAO dao;
        int total = 0;
        for (Producto_Ordenes det : lista) {
            total += det.getProducto().getPrecioProducto() * det.getCantidad();
        }
        try {
            dao = new Ordenes_pagoDAO();
            ordenpago.setValorTotalOrdenpago(total);
            dao.registrar(ordenpago, lista);
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en Registro de Venta", "Error en Registro de Venta"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
   
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        Ordenes_pagoDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new Ordenes_pagoDAO();
                    lstOrdenes_pago = pdao.listar();
                }
            } else {
                pdao = new Ordenes_pagoDAO();
                lstOrdenes_pago = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

  
    public void listarDetalle(boolean lis, Ordenes_pago orden) throws SQLException {
        Ordenes_pagoDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new Ordenes_pagoDAO();
                    listaDetalle = pdao.listarFactura(orden);
                }
            } else {
                pdao = new Ordenes_pagoDAO();
                listaDetalle = pdao.listarFactura(orden);
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void leerID(Ordenes_pago ord) throws Exception {
        Ordenes_pagoDAO pdao;
        Factura temp;
        try {
            pdao = new Ordenes_pagoDAO();
            temp = pdao.leedId(ord);
            if (temp != null) {
                this.factura = temp;
                this.accion = "Modificar Orden";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        Ordenes_pagoDAO proddao;
        try {
            proddao = new Ordenes_pagoDAO();
            proddao.actualizar(ordenpago);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar() throws Exception {
        Ordenes_pagoDAO pdao;
        try {
            pdao = new Ordenes_pagoDAO();
            pdao.eliminar(ordenpago);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se eliminó correctamente la orden de pago", "Se eliminó correctamente la orden de pago"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la eliminación de la orden de pago", "Error en la eliminación de la orden de pago"));
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
                String query = "INSERT INTO ordenes_pago VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idOrden_pago - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //tipoPago
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 3: //idEmpleado - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 4: //idCliente - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 5: //fechaOrdenpago - date
                            Date d = celda.getDateCellValue();//Convertimos d (util.Date) a fehchain (sql.Date)
                            java.sql.Date fechain = new java.sql.Date(d.getTime());
                            query += ", '" + fechain + "'";
                            break;
                        case 6: //valorTotalOrdenpago - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 7: //iva
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Migracion Correcta"));
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error Abriendo Archivo"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error en SQL"));
        } catch (InvalidFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error en Formato de Datos"));
        } catch (IllegalStateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en los datos, algunos valores no se encuentran en su formato (númerico / alfabetico)", "Error en los datos, algunos valores no se encuentran en su formato (númerico / alfabetico)"));
        }
    }
    
}
