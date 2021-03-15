package bean;

import dao.ProductosDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Productos;

//Librerias para la migración
import com.mysql.jdbc.Driver;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class ProductosBean {

    private Productos producto = new Productos();
    private List<Productos> lstProductos;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.producto.setIdProducto(0);
        this.producto.setNomProducto("");
        this.producto.setPrecioProducto(0.0);
        this.producto.setGeneroProducto("");
        this.producto.setColorProducto("");
        this.producto.setCategoriaProducto("");
        this.producto.setDescriProducto("");
    }

    public List<Productos> getLstProductos() {
        return lstProductos;
    }

    public void setLstProductos(List<Productos> lstProductos) {
        this.lstProductos = lstProductos;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
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
        ProductosDAO pdao;
        try {
            pdao = new ProductosDAO();
            pdao.registrar(producto);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de producto exitoso", "Registro de producto exitoso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en registro del nuevo producto", "Error en registro del nuevo producto"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        ProductosDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new ProductosDAO();
                    lstProductos = pdao.listar();
                }
            } else {
                pdao = new ProductosDAO();
                lstProductos = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
     
    public void leerID(Productos per) throws Exception {
        ProductosDAO pdao;
        Productos temp;

        try {
            pdao = new ProductosDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.producto = temp;
                this.accion = "Modificar Producto";
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void ver(Productos per) throws Exception {
        ProductosDAO pdao;
        Productos temp;    
        try {
            pdao = new ProductosDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.producto = temp;
                this.accion = "Ver Producto";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        ProductosDAO proddao;
        try {
            proddao = new ProductosDAO();
            proddao.actualizar(producto);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación del producto exitosa", "Modificación del producto exitosa"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación del producto", "Error en la modificación del producto"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void eliminar() throws Exception {
        ProductosDAO pdao;
        try {
            pdao = new ProductosDAO();
            pdao.eliminar(producto);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación del producto exitosa", "Eliminación del producto exitosa"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación del producto", "Error en la eliminación del producto"));
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
                String query = "INSERT INTO productos VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idProducto - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //nomProducto - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 3: //precioProducto- decimal
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 4: //generoProducto - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 5: //colorProducto - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 6: //categoriaProducto - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 7: //descriProducto - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                    }
                    ncamp++;

                }
                query += ");";

                //Logica de Persistencia - Guardar informacion de la Fila en la Base de Datos
                if (guardar) {
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.executeUpdate();
                }

                if (guardar) {
                         System.out.println(query);  
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