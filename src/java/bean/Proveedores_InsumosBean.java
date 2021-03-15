package bean;

import dao.Proveedores_InsumosDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Proveedores_Insumos;

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

public class Proveedores_InsumosBean {

    private Proveedores_Insumos insumo = new Proveedores_Insumos();
    private List<Proveedores_Insumos> lstInsumo;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.insumo.setIdInsumo(0);
        this.insumo.setIdProveedor(0);
    }

    public Proveedores_Insumos getInsumo() {
        return insumo;
    }

    public void setInsumo(Proveedores_Insumos insumo) {
        this.insumo = insumo;
    }

    public List<Proveedores_Insumos> getLstInsumo() {
        return lstInsumo;
    }

    public void setLstInsumo(List<Proveedores_Insumos> lstInsumo) {
        this.lstInsumo = lstInsumo;
    }

    public void operar() throws Exception {
        switch (accion.charAt(0)) {
            case 'A':
                this.registrar();
                break;
            case 'M':
                this.modificar();
                break;
        }
    }

    private void registrar() throws Exception {
        Proveedores_InsumosDAO pdao;
        try {
            pdao = new Proveedores_InsumosDAO();
            pdao.registrar(insumo);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso", "Registro exitoso"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en registro nuevo", "Error en registro nuevo"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        Proveedores_InsumosDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new Proveedores_InsumosDAO();
                    lstInsumo = pdao.listar();
                }
            } else {
                pdao = new Proveedores_InsumosDAO();
                lstInsumo = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void leerID(Proveedores_Insumos per) throws Exception {
        Proveedores_InsumosDAO pdao;
        Proveedores_Insumos temp;

        try {
            pdao = new Proveedores_InsumosDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.insumo = temp;
                this.accion = "Modificar Registro";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        Proveedores_InsumosDAO proddao;
        try {
            proddao = new Proveedores_InsumosDAO();
            proddao.actualizar(insumo);
            this.listar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar() throws Exception {
        Proveedores_InsumosDAO pdao;
        try {
            pdao = new Proveedores_InsumosDAO();
            pdao.eliminar(insumo);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación exitosa", "Eliminación exitoso"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación", "Error en la eliminación"));
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
                String query = "INSERT INTO insumos_proveedores VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idInsumo- int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //idProveedor - String
                            query += ", '" + celda.getNumericCellValue()+ "'";
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
