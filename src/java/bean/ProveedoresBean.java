package bean;

import dao.ProveedoresDAO;
import java.util.Date;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Proveedores;

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
public class ProveedoresBean {

    private Proveedores proveedor = new Proveedores();
    private List<Proveedores> lstProveedores;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.proveedor.setIdProveedor(0);
        this.proveedor.setNomProveedor("");
        this.proveedor.setTelefProveedor(0);
        this.proveedor.setCorreoProveedor("");
        this.proveedor.setDireccProveedor("");
        this.proveedor.setFechaEntrada(new java.sql.Date(0));
    }

    public List<Proveedores> getLstProveedores() {
        return lstProveedores;
    }

    public void setLstProveedores(List<Proveedores> lstProveedores) {
        this.lstProveedores = lstProveedores;
    }

    public Proveedores getProveedores() {
        return proveedor;
    }

    public void setProveedores(Proveedores proveedor) {
        this.proveedor = proveedor;
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
        ProveedoresDAO pdao;       
        try {
            pdao = new ProveedoresDAO();
            pdao.registrar(proveedor);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de proveedor exitoso", "Registro de proveedor exitoso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en registro del nuevo proveedor", "Error en registro del nuevo proveedor"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        ProveedoresDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new ProveedoresDAO();
                    lstProveedores = pdao.listar();
                }
            } else {
                pdao = new ProveedoresDAO();
                lstProveedores = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
     
    public void leerID(Proveedores per) throws Exception {
        ProveedoresDAO pdao;
        Proveedores temp;
        try {
            pdao = new ProveedoresDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.proveedor = temp;
                this.accion = "Modificar Proveedor";
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void ver(Proveedores per) throws Exception {
        ProveedoresDAO pdao;
        Proveedores temp;       
        try {
            pdao = new ProveedoresDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.proveedor = temp;
                this.accion = "Ver Proveedor";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        ProveedoresDAO proddao;
        try {
            proddao = new ProveedoresDAO();
            proddao.actualizar(proveedor);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación de proveedor exitosa", "Modificación de proveedor exitosa"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación del proveedor", "Error en la modificación del proveedor"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void eliminar() throws Exception {
        ProveedoresDAO pdao;
        try {
            pdao = new ProveedoresDAO();
            pdao.eliminar(proveedor);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación de proveedor exitosa", "Eliminación de proveedor exitosa"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación del proveedor", "Error en la eliminación del proveedor"));
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
                String query = "INSERT INTO proveedores VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idProveedor- int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //nomProveedor - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 3: //telefProveedor - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 4: //correoProveedor - String
                            query += ", '" + celda.getRichStringCellValue()+ "'";
                            break;
                        case 5: //direccProveedor - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 6: //fechaEntrada - Date
                            Date d = celda.getDateCellValue();//Convertimos d (util.Date) a fehchain (sql.Date)
                            java.sql.Date fechain = new java.sql.Date(d.getTime());
                            query += ", '" + fechain + "'";
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
