package bean;

import com.mysql.jdbc.Driver;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Administradores;
import dao.AdministradoresDAO;
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

public class AdministradoresBean {
    
    private Administradores admin = new Administradores();
    private List<Administradores> lstAdmin;
    private String accion;

    public Administradores getAdmin() {
        return admin;
    }

    public void setAdmin(Administradores admin) {
        this.admin = admin;
    }

    public List<Administradores> getLstAdmin() {
        return lstAdmin;
    }

    public void setLstAdmin(List<Administradores> lstAdmin) {
        this.lstAdmin = lstAdmin;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }
    
    private void limpiar() {
        this.admin.setIdAdmin(2);
        this.admin.setIdUsuario(0);
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
        AdministradoresDAO pdao;
        try {
            pdao = new AdministradoresDAO();
            pdao.registrar(admin);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de administrador exitoso", "Registro de administrador exitoso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en registro del administrador", "Error en registro del administrador"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }
    
    
    public void listar(boolean lis) throws SQLException {
        AdministradoresDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new AdministradoresDAO();
                    lstAdmin = pdao.listar();
                }
            } else {
                pdao = new AdministradoresDAO();
                lstAdmin = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    public void leerID(Administradores per) throws Exception {
        AdministradoresDAO pdao;
        Administradores temp;
        try {
            pdao = new AdministradoresDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.admin = temp;
                this.accion = "Modificar Registro";
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    
    private void modificar() throws Exception {
        AdministradoresDAO proddao;
        try {
            proddao = new AdministradoresDAO();
            proddao.actualizar(admin);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación de administrador", "Modificación de administraión"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación del administrador", "Error en la modificación del administrador"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    public void eliminar() throws Exception {
        AdministradoresDAO proddao;
        try {
            proddao = new AdministradoresDAO();
            proddao.eliminar(admin);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación del administrador correcta", "Eliminación del administrador correcta"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación del administrador", "Error en la eliminación del administrador"));
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
                String query = "INSERT INTO administradores VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idAdmin - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //idUsuario - int
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
