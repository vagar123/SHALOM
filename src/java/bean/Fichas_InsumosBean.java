package bean;

import com.mysql.jdbc.Driver;
import dao.Fichas_insumosDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import modelo.Fichas_Insumos;
import modelo.Insumos;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@ViewScoped

public class Fichas_InsumosBean {

    private Fichas_Insumos ficha_insumo = new Fichas_Insumos();
    private List<Fichas_Insumos> lstFicha_Insumo;
    private String accion;
    private Insumos insumo = new Insumos();
    private int cantidad;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.ficha_insumo.setIdFicha_produccion(0);
        this.ficha_insumo.setIdInsumo(0);
        this.ficha_insumo.setCantidad(0);
        this.ficha_insumo.setValorInsumos(0);
    }

    public Fichas_Insumos getFicha_insumo() {
        return ficha_insumo;
    }

    public void setFicha_insumo(Fichas_Insumos ficha_insumo) {
        this.ficha_insumo = ficha_insumo;
    }

    public List<Fichas_Insumos> getLstFicha_Insumo() {
        return lstFicha_Insumo;
    }

    public void setLstFicha_Insumo(List<Fichas_Insumos> lstFicha_Insumo) {
        this.lstFicha_Insumo = lstFicha_Insumo;
    }

    public Insumos getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumos insumo) {
        this.insumo = insumo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
        Fichas_insumosDAO pdao;
        try {
            pdao = new Fichas_insumosDAO();
            pdao.registrar(ficha_insumo);
            this.listar(true);
            
        } catch (SQLException e) {
            throw e;
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        Fichas_insumosDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new Fichas_insumosDAO();
                    lstFicha_Insumo = pdao.listar();
                }
            } else {
                pdao = new Fichas_insumosDAO();
                lstFicha_Insumo = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void leerID(Fichas_Insumos per) throws Exception {
        Fichas_insumosDAO pdao;
        Fichas_Insumos temp;
        try {
            pdao = new Fichas_insumosDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.ficha_insumo = temp;
                this.accion = "Modificar Registro";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        Fichas_insumosDAO proddao;
        try {
            proddao = new Fichas_insumosDAO();
            proddao.actualizar(ficha_insumo);
            this.listar(true);
            
        } catch (Exception e) {
            throw e;
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void eliminar() throws Exception {
        Fichas_insumosDAO proddao;
        try {
            proddao = new Fichas_insumosDAO();
            proddao.eliminar(ficha_insumo);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación de detalles de la ficha producción exitoso", "Eliminación de detalles de la ficha producción exitoso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación de detalles de la ficha de producción", "Error en la eliminación de detalles de la ficha de producción"));
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
                String query = "INSERT INTO fichas_insumos VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idFicha_produccion - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //idInsumo - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 3: //cantidad - int
                            query += ", '" + celda.getNumericCellValue()+ "'";
                            break;
                        case 4: //valorInsumos - double
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
