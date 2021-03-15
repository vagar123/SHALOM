package bean;

import dao.graficoOrdenPagoDAO;
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

public class graficoOrdenPagoBean {
    
    private BarChartModel barModel;

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }
    
    public void graficar() throws SQLException{
        
        graficoOrdenPagoDAO pdao = new graficoOrdenPagoDAO();        
        barModel = new BarChartModel();
       
        for(int i=0; i<pdao.listarGrafica().size(); i++){
            
            ChartSeries serie = new BarChartSeries();
            
            serie.setLabel(pdao.listarGrafica().get(i).getNomProducto());
            serie.set(pdao.listarGrafica().get(i).getNomProducto(), pdao.listarGrafica().get(i).getCantidad());
            barModel.addSeries(serie);
        }
        
        barModel.setTitle("Productos vendidos");
        barModel.setLegendPosition("no");
        barModel.setAnimate(true);
        
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Productos");
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad");
        yAxis.setMin(0);
        yAxis.setMax(8);
    }
}
