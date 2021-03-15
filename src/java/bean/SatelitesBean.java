package bean;

import dao.SatelitesDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Satelites;

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
public class SatelitesBean {

    private Satelites satelite = new Satelites();
    private List<Satelites> lstSatelites;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.satelite.setIdSatelite(1004);
        this.satelite.setNomSatelite("");
        this.satelite.setTeleSatelite(0);
        this.satelite.setDireccSatelite("");
        this.satelite.setCorreoSatelite("");  
        this.satelite.setFechaISatelite(new java.sql.Date(0));
    }

    public List<Satelites> getLstSatelites() {
        return lstSatelites;
    }

    public void setLstSatelites(List<Satelites> lstSatelites) {
        this.lstSatelites = lstSatelites;
    }

    public Satelites getSatelites() {
        return satelite;
    }

    public void setSatelites(Satelites satelite) {
        this.satelite = satelite;
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
        SatelitesDAO pdao;        
        try {
            pdao = new SatelitesDAO();
            pdao.registrar(satelite);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de satélite exitoso", "Registro de satélite exitoso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en registro del nuevo satélite", "Error en registro del nuevo satélite"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        SatelitesDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new SatelitesDAO();
                    lstSatelites = pdao.listar();
                }
            } else {
                pdao = new SatelitesDAO();
                lstSatelites = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
     
    public void leerID(Satelites per) throws Exception {
        SatelitesDAO pdao;
        Satelites temp;
        try {
            pdao = new SatelitesDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.satelite = temp;
                this.accion = "Modificar Satelite";
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void ver(Satelites per) throws Exception {
        SatelitesDAO pdao;
        Satelites temp;       
        try {
            pdao = new SatelitesDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.satelite = temp;
                this.accion = "Ver Satélite";
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    private void modificar() throws Exception {
        SatelitesDAO proddao;
        try {
            proddao = new SatelitesDAO();
            proddao.actualizar(satelite);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación de satélite exitosa", "Modificación de satélite exitosa"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación del satélite", "Error en la modificación del satélite"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void eliminar() throws Exception {
        SatelitesDAO pdao;
        try {
            pdao = new SatelitesDAO();
            pdao.eliminar(satelite);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación de satélite exitosa", "Eliminación de satélite exitosa"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación del satélite", "Error en la eliminación del satélite"));
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
                String query = "INSERT INTO satelites VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idSatelite - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //nomSatelite - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 3: //teleSatelite - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 4: //direccSatelite - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 5: //correoSatelite - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 6: //fechaISatelite - date
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
