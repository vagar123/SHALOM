package bean;

import dao.InsumosDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Insumos;

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
public class InsumosBean {

    private Insumos insumos = new Insumos();
    private List<Insumos> lstInsumos;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.insumos.setIdInsumo(0);
        this.insumos.setNomInsumo("");
        this.insumos.setPrecioUniInsumo(0.0);
        this.insumos.setCategoriaInsumo("");
        this.insumos.setColorInsumo("");
        this.insumos.setDescriInsumo("");
    }

    public List<Insumos> getLstInsumos() {
        return lstInsumos;
    }

    public void setLstInsumos(List<Insumos> lstInsumos) {
        this.lstInsumos = lstInsumos;
    }

    public Insumos getInsumos() {
        return insumos;
    }

    public void setInsumos(Insumos insumo) {
        this.insumos = insumo;
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
        InsumosDAO pdao;
        try {
            pdao = new InsumosDAO();
            pdao.registrar(insumos);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de insumo exitoso", "Registro de insumo exitoso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en registro del nuevo insumo", "Error en registro del nuevo insumo"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        InsumosDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new InsumosDAO();
                    lstInsumos = pdao.listar();
                }
            } else {
                pdao = new InsumosDAO();
                lstInsumos = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void leerID(Insumos per) throws Exception {
        InsumosDAO pdao;
        Insumos temp;

        try {
            pdao = new InsumosDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.insumos = temp;
                this.accion = "Modificar Insumo";
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        InsumosDAO proddao;
        try {
            proddao = new InsumosDAO();
            proddao.actualizar(insumos);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación del insumo exitosa", "Modificación del insumo exitosa"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación del insumo", "Error en la modificación del insumo"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void eliminar() throws Exception {
        InsumosDAO pdao;
        try {
            pdao = new InsumosDAO();
            pdao.eliminar(insumos);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación del insumo exitosa", "Eliminación del insumo exitosa"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación del insumo", "Error en la eliminación del insumo"));
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
                String query = "INSERT INTO insumos VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idInsumo - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //nomInsumo - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 3: //precioUniInsumo - decimal
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 4: //categoriaInsumo - String 
                            query += ", '" + celda.getStringCellValue() + "'";
                            break;
                        case 5: //colorInsumo - String
                            query += ", '" + celda.getStringCellValue() + "'";
                            break;
                        case 6: //descriInsumo - String
                            query += ", '" + celda.getStringCellValue() + "'";
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
