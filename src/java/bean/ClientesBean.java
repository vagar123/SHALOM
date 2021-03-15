package bean;

import dao.ClientesDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Clientes;

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
public class ClientesBean {

    private Clientes cliente = new Clientes();
    private List<Clientes> lstClientes;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.cliente.setIdCliente(0);
        this.cliente.setNomCliente("");
        this.cliente.setApellCliente("");
        this.cliente.setTelefCliente(0);
        this.cliente.setCorreoCliente("");
        this.cliente.setFechaNacimiento(new java.sql.Date(0));
    }

    public List<Clientes> getLstClientes() {
        return lstClientes;
    }

    public void setLstClientes(List<Clientes> lstClientes) {
        this.lstClientes = lstClientes;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
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
        ClientesDAO pdao;       
        try {
            pdao = new ClientesDAO();
            pdao.registrar(cliente);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de cliente exitoso", "Registro de cliente exitoso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en registro del nuevo cliente", "Error en registro del nuevo cliente"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        ClientesDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new ClientesDAO();
                    lstClientes = pdao.listar();
                }
            } else {
                pdao = new ClientesDAO();
                lstClientes = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
     
    public void leerID(Clientes per) throws Exception {
        ClientesDAO pdao;
        Clientes temp;
        try {
            pdao = new ClientesDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.cliente = temp;
                this.accion = "Modificar Cliente";
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void ver(Clientes per) throws Exception {
        ClientesDAO pdao;
        Clientes temp;
        try {
            pdao = new ClientesDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.cliente = temp;
                this.accion = "Ver Cliente";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        ClientesDAO proddao;
        try {
            proddao = new ClientesDAO();
            proddao.actualizar(cliente);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación de cliente exitosa", "Modificación de cliente exitosa"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación del cliente", "Error en la modificación del cliente"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void eliminar() throws Exception {
        ClientesDAO pdao;
        try {
            pdao = new ClientesDAO();
            pdao.eliminar(cliente);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación de cliente exitosa", "Eliminación de cliente exitosa"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación del cliente", "Error en la eliminación del cliente"));
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
                String query = "INSERT INTO clientes VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idCliente - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //nomCliente - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 3: //apellCliente - int
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 4: //telefCliente - String
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 5: //correoCliente - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 6: //fechaNacimiento - date
                            Date d = celda.getDateCellValue();//Convertimos d (util.Date) a fehchain (sql.Date)
                            java.sql.Date fechain = new java.sql.Date(d.getTime());
                            query += ", '" + fechain + "'";
                            break;
                        case 7: //generoCliente - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en SQL", "Error en SQL"));
        } catch (InvalidFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en Formato de Datos", "Error en Formato de Datos"));
        } catch (IllegalStateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en los datos, algunos valores no se encuentran en su formato (númerico / alfabetico)", "Error en los datos, algunos valores no se encuentran en su formato (númerico / alfabetico)"));
        }
    }
}