package bean;

import dao.Cotizaciones_FichaDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Cotizaciones_Ficha;

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
public class Cotizaciones_FichaBean {

    private Cotizaciones_Ficha cotizacion_ficha = new Cotizaciones_Ficha();
    private List<Cotizaciones_Ficha> lstCotizacion_ficha;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.cotizacion_ficha.setProductoCotizacion_ficha("");
        this.cotizacion_ficha.setGeneroProdCotizacion_ficha("");
        this.cotizacion_ficha.setTallaProduCotizacion_ficha("");
        this.cotizacion_ficha.setCantidadCotizacion_ficha(0);
        this.cotizacion_ficha.setFechaCotizacion_ficha(new java.sql.Date(0));
        this.cotizacion_ficha.setIdSatelite(0);
        this.cotizacion_ficha.setDescriCoti_ficha("");
    }

    public List<Cotizaciones_Ficha> getLstCotizaciones_ficha() {
        return lstCotizacion_ficha;
    }

    public void setLstCotizaciones_ficha(List<Cotizaciones_Ficha> lstCotizacion_ficha) {
        this.lstCotizacion_ficha = lstCotizacion_ficha;
    }

    public Cotizaciones_Ficha getCotizaciones_ficha() {
        return cotizacion_ficha;
    }

    public void setCotizaciones_ficha(Cotizaciones_Ficha cotizacion_ficha) {
        this.cotizacion_ficha = cotizacion_ficha;
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

    public void registrar() throws Exception {
        Cotizaciones_FichaDAO pdao;
        try {
            pdao = new Cotizaciones_FichaDAO();
            pdao.registrar(cotizacion_ficha);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cotización de producción exitoso", "Registro de Cotización de producción exitoso"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en Registro de Cotización de producción", "Error en Registro de Cotización de producción"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        Cotizaciones_FichaDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new Cotizaciones_FichaDAO();
                    lstCotizacion_ficha = pdao.listar();
                }
            } else {
                pdao = new Cotizaciones_FichaDAO();
                lstCotizacion_ficha = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void leerID(Cotizaciones_Ficha per) throws Exception {
        Cotizaciones_FichaDAO pdao;
        Cotizaciones_Ficha temp;

        try {
            pdao = new Cotizaciones_FichaDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.cotizacion_ficha = temp;
                this.accion = "Modificar Cotizacion de producción";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void ver(Cotizaciones_Ficha per) throws Exception {
        Cotizaciones_FichaDAO pdao;
        Cotizaciones_Ficha temp;
        try {
            pdao = new Cotizaciones_FichaDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.cotizacion_ficha = temp;
                this.accion = "Ver Cotización de producción";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        Cotizaciones_FichaDAO proddao;
        try {
            proddao = new Cotizaciones_FichaDAO();
            proddao.actualizar(cotizacion_ficha);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modificó correctamente la cotización de producción", "Se modificó correctamente la cotización de producción"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación de la cotización de producción", "Error en la modificación de la cotización de producción"));
        }
    }

    public void eliminar() throws Exception {
        Cotizaciones_FichaDAO pdao;
        try {
            pdao = new Cotizaciones_FichaDAO();
            pdao.eliminar(cotizacion_ficha);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se eliminó correctamente la cotización de producción", "Se eliminó correctamente la cotización de producción"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación de la cotización de producción", "Error en la eliminación de la cotización de producción"));
        }
    }

    //Carga masiva desde EXCEL
    
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
                String query = "INSERT INTO cotizaciones_fichas VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idCotizacion_ficha - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //productoCotizacion_ficha - String  
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 3: //generoProdCotizacion_ficha - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 4: //tallaProduCotizacion_ficha - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 5: //cantidadCotizacion_ficha - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 6://fechaCotizacion_ficha - date
                            Date d = celda.getDateCellValue();//Convertimos d (util.Date) a fehchain (sql.Date)
                            java.sql.Date fechain = new java.sql.Date(d.getTime());
                            query += ", '" + fechain + "'";
                            break;
                        case 7: //idSatelite - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 8: //descriCoti_ficha - String
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
