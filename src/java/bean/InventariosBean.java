package bean;

import dao.InventariosDAO;
import java.util.Date;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Inventarios;

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
public class InventariosBean {

    private Inventarios inventario = new Inventarios();
    private List<Inventarios> lstInventarios;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.inventario.setNomInventario("");
        this.inventario.setExistInventario(0);
        this.inventario.setFechaInventario(new java.sql.Date(0));
        this.inventario.setIdAdmin(0);
        this.inventario.setIdLocal(0);
    }

    public List<Inventarios> getLstInventarios() {
        return lstInventarios;
    }

    public void setLstInventarios(List<Inventarios> lstInventarios) {
        this.lstInventarios = lstInventarios;
    }

    public Inventarios getInventarios() {
        return inventario;
    }

    public void setInventarios(Inventarios inventario) {
        this.inventario = inventario;
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
        InventariosDAO pdao;
        try {
            pdao = new InventariosDAO();
            pdao.registrar(inventario);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de inventario exitoso", "Registro de inventario exitoso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en registro del nuevo inventario", "Error en registro del nuevo inventario"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        InventariosDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new InventariosDAO();
                    lstInventarios = pdao.listar();
                }
            } else {
                pdao = new InventariosDAO();
                lstInventarios = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
     
    public void leerID(Inventarios per) throws Exception {
        InventariosDAO pdao;
        Inventarios temp;

        try {
            pdao = new InventariosDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.inventario = temp;
                this.accion = "Modificar Inventario";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        InventariosDAO proddao;
        try {
            proddao = new InventariosDAO();
            proddao.actualizar(inventario);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación de inventario exitosa", "Modificación de inventario exitosa"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación del inventario", "Error en la modificación del inventario"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void eliminar() throws Exception {
        InventariosDAO pdao;
        try {
            pdao = new InventariosDAO();
            pdao.eliminar(inventario);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación de inventario exitosa", "Eliminación de inventario exitosa"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación del inventario", "Error en la eliminación del inventario"));
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
                String query = "INSERT INTO inventarios VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idInventario - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //nomInventario - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 3: //existInventario - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 4: //FechaInven - date
                            java.util.Date d = celda.getDateCellValue();//Convertimos d (util.Date) a fechain (sql.Date)
                            java.sql.Date fechain = new java.sql.Date(d.getTime());
                            query += ", '" + fechain + "'";
                            break;
                        case 5: //idAdmin - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 6: //idLocal - int
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

