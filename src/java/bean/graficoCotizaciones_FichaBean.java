package bean;

import dao.graficoCotizaciones_FichaDAO;
import java.sql.SQLException;
import modelo.graficoCotizaciones_Ficha;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean
@ViewScoped
@RequestScoped

public class graficoCotizaciones_FichaBean {
    
    private PieChartModel pieModel;

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }
    
    public void graficar() throws SQLException{
        
        graficoCotizaciones_FichaDAO pdao = new graficoCotizaciones_FichaDAO(); 
        List<graficoCotizaciones_Ficha> lista = pdao.listarGrafica();
        
        pieModel = new PieChartModel();
       
        for(graficoCotizaciones_Ficha pro: lista){          
            pieModel.set(pro.getProductoCotizacion_ficha(), pro.getCantidadCotizacion_ficha());
        }
 
        pieModel.setTitle("Entregas");
        pieModel.setLegendPosition("s");
        pieModel.setFill(true);
        pieModel.setLegendRows(1);
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(300);
        pieModel.setShadow(true);
        
    }
}