package bean;

import dao.Insumos_medidas_inventariosDAO;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Insumos_medidas_inventarios;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

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

public class Insumos_medidas_inventariosBean {

    private Insumos_medidas_inventarios insumo = new Insumos_medidas_inventarios();
    private List<Insumos_medidas_inventarios> lstInsumo;
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
        this.insumo.setNomMedida("");
        this.insumo.setIdInventario(0);
        this.insumo.setCantidaInsumo(0);
    }

    public Insumos_medidas_inventarios getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumos_medidas_inventarios insumo) {
        this.insumo = insumo;
    }

    public List<Insumos_medidas_inventarios> getLstInsumo() {
        return lstInsumo;
    }

    public void setLstInsumo(List<Insumos_medidas_inventarios> lstInsumo) {
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
        Insumos_medidas_inventariosDAO pdao;
        try {
            pdao = new Insumos_medidas_inventariosDAO();
            pdao.registrar(insumo);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de insumo al inventario exitoso", "Registro de insumo al inventario exitoso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en registro del insumo al inventario", "Error en registro del insumo al inventario"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        Insumos_medidas_inventariosDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new Insumos_medidas_inventariosDAO();
                    lstInsumo = pdao.listar();
                }
            } else {
                pdao = new Insumos_medidas_inventariosDAO();
                lstInsumo = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void leerID(Insumos_medidas_inventarios per) throws Exception {
        Insumos_medidas_inventariosDAO pdao;
        Insumos_medidas_inventarios temp;
        try {
            pdao = new Insumos_medidas_inventariosDAO();
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
        Insumos_medidas_inventariosDAO proddao;
        try {
            proddao = new Insumos_medidas_inventariosDAO();
            proddao.actualizar(insumo);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación de la cantidad/inventario exitosa", "Modificación de la cantidad/inventario exitosa"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación de la cantidad/inventario", "Error en la modificación de la cantidad/inventario"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void eliminar() throws Exception {
        Insumos_medidas_inventariosDAO pdao;
        try {
            pdao = new Insumos_medidas_inventariosDAO();
            pdao.eliminar(insumo);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación del insumo en inventario exitosa", "Eliminación del insumo en inventario exitosa"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación del insumo en inventario", "Error en la eliminación del insumo en inventario"));
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
                String query = "INSERT INTO insumos_medidas_inventarios VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idInsumo - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //nomMedida - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 3: //idInventario - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 4: //cantidadInsumo - int
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
