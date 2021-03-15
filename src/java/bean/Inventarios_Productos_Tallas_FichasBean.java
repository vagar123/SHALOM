package bean;

import com.mysql.jdbc.Driver;
import dao.Inventarios_Productos_Tallas_FichasDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import modelo.Inventarios_Productos_Tallas_Fichas;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;

@ManagedBean
@ViewScoped

public class Inventarios_Productos_Tallas_FichasBean {

    private Inventarios_Productos_Tallas_Fichas inven = new Inventarios_Productos_Tallas_Fichas();
    private List<Inventarios_Productos_Tallas_Fichas> lstinven;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.inven.setIdProducto(0);
        this.inven.setIdInventario(0);
        this.inven.setIdTalla("");
        this.inven.setCantidadProducto(0);
        this.inven.setIdFicha_produccio(0);
    }

    public Inventarios_Productos_Tallas_Fichas getInven() {
        return inven;
    }

    public void setInven(Inventarios_Productos_Tallas_Fichas inven) {
        this.inven = inven;
    }

    public List<Inventarios_Productos_Tallas_Fichas> getLstinven() {
        return lstinven;
    }

    public void setLstinven(List<Inventarios_Productos_Tallas_Fichas> lstinven) {
        this.lstinven = lstinven;
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
        Inventarios_Productos_Tallas_FichasDAO pdao;
        try {
            pdao = new Inventarios_Productos_Tallas_FichasDAO();
            pdao.registrar(inven);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de producto al inventario exitoso", "Registro de producto al inventario exitoso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en registro del producto al inventario", "Error en registro del producto al inventario"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        Inventarios_Productos_Tallas_FichasDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new Inventarios_Productos_Tallas_FichasDAO();
                    lstinven = pdao.listar();
                }
            } else {
                pdao = new Inventarios_Productos_Tallas_FichasDAO();
                lstinven = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void leerID(Inventarios_Productos_Tallas_Fichas per) throws Exception {
        Inventarios_Productos_Tallas_FichasDAO pdao;
        Inventarios_Productos_Tallas_Fichas temp;
        try {
            pdao = new Inventarios_Productos_Tallas_FichasDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.inven = temp;
                this.accion = "Modificar Registro";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        Inventarios_Productos_Tallas_FichasDAO proddao;
        try {
            proddao = new Inventarios_Productos_Tallas_FichasDAO();
            proddao.actualizar(inven);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación de la cantidad/inventario exitosa", "Modificación de la cantidad/inventario exitosa"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación de la cantidad/inventario", "Error en la modificación de la cantidad/inventario"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void eliminar() throws Exception {
        Inventarios_Productos_Tallas_FichasDAO proddao;
        try {
            proddao = new Inventarios_Productos_Tallas_FichasDAO();
            proddao.eliminar(inven);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación del producto en inventario exitosa", "Eliminación del producto en inventario exitosa"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación del producto en inventario", "Error en la eliminación del producto en inventario"));
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
                String query = "INSERT INTO inventarios_productos_tallas_fichas VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idProducto - int
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //idInventario - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 3: //idTalla - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 4: //cantidadProducto - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 5: //idFicha_produccio - int
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
        }
    }
}
