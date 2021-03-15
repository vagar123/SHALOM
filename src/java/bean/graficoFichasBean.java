package bean;

import dao.graficoFichasDAO;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.ChartSeries;
import javax.faces.bean.RequestScoped;

@ManagedBean
@ViewScoped
@RequestScoped

public class graficoFichasBean {
    
    private BarChartModel barModel;

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    
    public void graficar() throws SQLException{
        
        graficoFichasDAO pdao = new graficoFichasDAO();        
        barModel = new BarChartModel();
       
        for(int i=0; i<pdao.listarGrafica().size(); i++){
            
            ChartSeries serie = new BarChartSeries();
            
            serie.setLabel(pdao.listarGrafica().get(i).getProductFicha_producc());
            serie.set(pdao.listarGrafica().get(i).getProductFicha_producc(), pdao.listarGrafica().get(i).getValorTotalFicha_producc());
            barModel.addSeries(serie);
        }
        
        barModel.setTitle("Valor Total de las fichas de producción agrupado por el Producto");
        barModel.setLegendPosition("no");
        barModel.setAnimate(true);
        
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Productos");
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Valor total");
        yAxis.setMin(20000);
        yAxis.setMax(400000);
    }    
}
