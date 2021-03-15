package bean;

import dao.UsuariosDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Usuarios;

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
public class UsuariosBean {

    private Usuarios persona = new Usuarios();
    private List<Usuarios> lstPersonas;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.persona.setIdUsuario(0);
        this.persona.setNomUsuario("");
        this.persona.setApellUsuario("");
        this.persona.setCorreoUsuario("");
        this.persona.setTeleUsuario(0);
        this.persona.setCargoUsuario("");
        this.persona.setIdLocal(0);
    }

    public List<Usuarios> getLstPersonas() {
        return lstPersonas;
    }

    public void setLstPersonas(List<Usuarios> lstPersonas) {
        this.lstPersonas = lstPersonas;
    }

    public Usuarios getPersona() {
        return persona;
    }

    public void setPersona(Usuarios persona) {
        this.persona = persona;
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
        UsuariosDAO pdao;
        try {
            pdao = new UsuariosDAO();
            pdao.registrar(persona);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de usuario exitoso", "Registro de usuario exitoso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en registro del nuevo usuario", "Error en registro del nuevo usuario"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws Exception {
        UsuariosDAO pdao;

        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new UsuariosDAO();
                    lstPersonas = pdao.listar();
                }
            } else {
                pdao = new UsuariosDAO();
                lstPersonas = pdao.listar();
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void leerID(Usuarios per) throws Exception {
        UsuariosDAO pdao;
        Usuarios temp;

        try {
            pdao = new UsuariosDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.persona = temp;
                this.accion = "Modificar";
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
     public void ver(Usuarios per) throws Exception {
        UsuariosDAO pdao;
        Usuarios temp;
        try {
            pdao = new UsuariosDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.persona = temp;
                this.accion = "Ver usuario";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        UsuariosDAO pdao;
        try {
            pdao = new UsuariosDAO();
            pdao.actualizar(persona);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación de usuario exitosa", "Modificación de usuario exitosa"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación del usuario", "Error en la modificación del usuario"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void eliminar() throws Exception {
        UsuariosDAO pdao;
        try {
            pdao = new UsuariosDAO();
            pdao.eliminar(persona);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación de usuario exitosa", "Eliminación de usuario exitosa"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación del usuario", "Error en la eliminación del usuario"));
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
                String query = "INSERT INTO usuarios VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idUsuario - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //nomUsuario - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 3: //apellUsuario - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 4: //correoUsuario - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 5: //teleUsuario - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 6: //cargoUsuario - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 7: //idLocal - int
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error en SQL"));
        } catch (InvalidFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error en Formato de Datos"));
        } catch (IllegalStateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en los datos, algunos valores no se encuentran en su formato (númerico / alfabetico)", "Error en los datos, algunos valores no se encuentran en su formato (númerico / alfabetico)"));
        }
    }
}
