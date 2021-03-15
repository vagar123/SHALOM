package bean;

import dao.Fichas_produccionDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Fichas_produccion;
import modelo.Fichas_Insumos;
import modelo.Insumos;
import modelo.NuevaFicha;

//Librerias para la migración
import com.mysql.jdbc.Driver;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
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
public class Fichas_produccionBean {

    private Fichas_produccion fichaprodu = new Fichas_produccion();
    private Fichas_Insumos detalleFicha = new Fichas_Insumos();
    private List<Fichas_produccion> lstFichas_produccion;
    private List<Fichas_Insumos> lstFichas_Insumos = new ArrayList();
    private List<NuevaFicha> lstNueva_Ficha  = new ArrayList();
    private Insumos insumo = new Insumos();
    private NuevaFicha nuevaFicha = new NuevaFicha();
    private int cantidad;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiar();
        this.accion = accion;
    }

    private void limpiar() {
        this.fichaprodu.setIdFicha_produccion(0);
        this.fichaprodu.setProductFicha_producc("");
        this.fichaprodu.setFechaIFicha_producc(new java.sql.Date(0));
        this.fichaprodu.setFechaSFicha_producc(new java.sql.Date(0));
        this.fichaprodu.setCantidadFicha_producc(0);
        this.fichaprodu.setTiempoFicha_producc("");
        this.fichaprodu.setTallaFicha_producc("");
        this.fichaprodu.setValorTotalFicha_producc(0);
        this.fichaprodu.setCategoriaFicha_producc("");
        this.fichaprodu.setGeneroFicha_producc("");
        this.fichaprodu.setColorFicha_producc("");
        this.fichaprodu.setDescripcionFicha_producc("");
        this.fichaprodu.setIdOrden_produccion(0);
        this.fichaprodu.setIdSatelite(0);
        this.fichaprodu.setEstadoFicha_producc("");

    }

    public List<Fichas_produccion> getLstFichas_produccion() {
        return lstFichas_produccion;
    }

    public void setLstFichas_produccion(List<Fichas_produccion> lstFichas_produccion) {
        this.lstFichas_produccion = lstFichas_produccion;
    }

    public Fichas_produccion getFichas_produccion() {
        return fichaprodu;
    }

    public void setFichas_produccion(Fichas_produccion fichas_produccion) {
        this.fichaprodu = fichas_produccion;
    }

    public Fichas_produccion getFichaprodu() {
        return fichaprodu;
    }

    public void setFichaprodu(Fichas_produccion fichaprodu) {
        this.fichaprodu = fichaprodu;
    }

    public Fichas_Insumos getDetalleFicha() {
        return detalleFicha;
    }

    public void setDetalleFicha(Fichas_Insumos detalleFicha) {
        this.detalleFicha = detalleFicha;
    }

    public List<Fichas_Insumos> getLstFichas_Insumos() {
        return lstFichas_Insumos;
    }

    public void setLstFichas_Insumos(List<Fichas_Insumos> lstFichas_Insumos) {
        this.lstFichas_Insumos = lstFichas_Insumos;
    }

    public List<NuevaFicha> getLstNueva_Ficha() {
        return lstNueva_Ficha;
    }

    public void setLstNueva_Ficha(List<NuevaFicha> lstNueva_Ficha) {
        this.lstNueva_Ficha = lstNueva_Ficha;
    }

    public Insumos getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumos insumo) {
        this.insumo = insumo;
    }

    public NuevaFicha getNuevaFicha() {
        return nuevaFicha;
    }

    public void setNuevaFicha(NuevaFicha nuevaFicha) {
        this.nuevaFicha = nuevaFicha;
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
        }
    }

    public void agregar() {
        Fichas_Insumos detv = new Fichas_Insumos();
        detv.setInsumo(insumo);
        detv.setCantidad(cantidad);
        detv.setValorInsumos(detv.getCantidad() * detv.getInsumo().getPrecioUniInsumo());
        this.lstFichas_Insumos.add(detv);
        this.totalFicha();
    }
    
    public void totalFicha() {
        int valorTotalFicha_producc = 0;
        try {
            for (Fichas_Insumos det : lstFichas_Insumos) {
                valorTotalFicha_producc += det.getInsumo().getPrecioUniInsumo() * det.getCantidad();
            }
            this.fichaprodu.setValorTotalFicha_producc(valorTotalFicha_producc);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void totalInsumo() {
        double valorInsumos = 0;
        try {
            for (Fichas_Insumos det : lstFichas_Insumos) {
                valorInsumos = det.getInsumo().getPrecioUniInsumo() * det.getCantidad();
            }
            this.detalleFicha.setValorInsumos(valorInsumos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void quitarInsumo(int idInsumo) {
        try {
            int i = 0;
            for (Fichas_Insumos item : this.lstFichas_Insumos) {
                if (item.getInsumo().equals(idInsumo)) {
                } else {
                    this.lstFichas_Insumos.remove(i);
                    break;
                }
                i++;
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Se retiró correctamente el insumo de la ficha de producción"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", e.getMessage()));
        }
    }
    
    public void registrar() {
        Fichas_produccionDAO dao;
        int  valorInsumos = 0;
        for (Fichas_Insumos det : lstFichas_Insumos) {
            valorInsumos += det.getInsumo().getPrecioUniInsumo() * det.getCantidad();
        }
        try {
            dao = new Fichas_produccionDAO();
            fichaprodu.setValorTotalFicha_producc(valorInsumos);
            dao.registrar(fichaprodu, lstFichas_Insumos);
            
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en Registro de la Ficha de producció", "Error en Registro de la Ficha de producción"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void listar(boolean lis) throws SQLException {
        Fichas_produccionDAO pdao;
        try {
            if (!lis) {
                if (!esPostBack()) {
                    pdao = new Fichas_produccionDAO();
                    lstFichas_produccion = pdao.listar();
                }
            } else {
                pdao = new Fichas_produccionDAO();
                lstFichas_produccion = pdao.listar();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void leerID(Fichas_produccion per) throws Exception {
        Fichas_produccionDAO pdao;
        Fichas_produccion temp;

        try {
            pdao = new Fichas_produccionDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.fichaprodu = temp;
                this.accion = "Modificar Ficha de producción";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void ver(Fichas_produccion per) throws Exception {
        Fichas_produccionDAO pdao;
        Fichas_produccion temp;

        try {
            pdao = new Fichas_produccionDAO();
            temp = pdao.leedId(per);
            if (temp != null) {
                this.fichaprodu = temp;
                this.accion = "Ver Ficha de producción";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void modificar() throws Exception {
        Fichas_produccionDAO proddao;
        try {
            proddao = new Fichas_produccionDAO();
            proddao.actualizar(fichaprodu);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modificó correctamente la ficha de producción", "Se modificó correctamente la ficha de producción"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la modificación de la ficha de producción", "Error en la modificación de la ficha de producción"));
        }
    }

    public void eliminar() throws Exception {
        Fichas_produccionDAO pdao;
        try {
            pdao = new Fichas_produccionDAO();
            pdao.eliminar(fichaprodu);
            this.listar(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se eliminó correctamente la ficha de producción", "Se eliminó correctamente la ficha de producción"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la eliminación de la ficha de producción", "Error en la eliminación de la ficha de producción"));
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
                String query = "INSERT INTO fichas_produccion VALUES(";
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (ncamp) {
                        case 1: //idFicha_produccion
                            query += celda.getNumericCellValue();
                            if (celda.getNumericCellValue() == 0) {
                                guardar = false;
                            }
                            break;
                        case 2: //productoFicha_produccion - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 3: //fechaIFicha_producc - date
                            Date d = celda.getDateCellValue();
                            java.sql.Date fechain = new java.sql.Date(d.getTime());
                            query += ", '" + fechain + "'";
                            break;
                        case 4: //fechaSFicha_producc  - date
                            Date de = celda.getDateCellValue();
                            java.sql.Date fechafin = new java.sql.Date(de.getTime());
                            query += ", '" + fechafin + "'";
                            break;
                        case 5: //cantidad - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 6: //tiempo - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 7: //talla - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 8: //valorTotal - Decimal
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 9: //categoria - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 10: //genero - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 11: //color - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 12: //descripcion - String
                            query += ", '" + celda.getRichStringCellValue() + "'";
                            break;
                        case 13: //idOrden - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 14: //idSatelite - int
                            query += ", '" + celda.getNumericCellValue() + "'";
                            break;
                        case 15: //estado - String
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en los datos suministrados", "Error en los datos suministrados"));
        } catch (InvalidFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en Formato de Datos", "Error en Formato de Datos"));
        } catch (IllegalStateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en los datos, algunos valores no se encuentran en su formato (númerico / alfabetico)", "Error en los datos, algunos valores no se encuentran en su formato (númerico / alfabetico)"));
        }
    }
}
