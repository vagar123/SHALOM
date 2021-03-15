package bean;

import dao.graficoUsuariosDAO;
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

public class graficoUsuariosBean {
    
    //Reporte Gráfico Barra
    private BarChartModel barModel;

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public void graficar() throws SQLException{
        
        graficoUsuariosDAO pdao = new graficoUsuariosDAO();        
        barModel = new BarChartModel();
       
        for(int i=0; i<pdao.listarGrafica().size(); i++){
            
            ChartSeries serie = new BarChartSeries();
            
            serie.setLabel(pdao.listarGrafica().get(i).getNomUsuario());
            serie.set(pdao.listarGrafica().get(i).getValorTotalOrdenpago(), pdao.listarGrafica().get(i).getValorTotalOrdenpago());
            barModel.addSeries(serie);
        }
        
        barModel.setTitle("Ventas realizadas por cada empleado");
        barModel.setLegendPosition("n");
        barModel.setAnimate(true);
        
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Empleados");
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Ventas");
        yAxis.setMin(300000);
        yAxis.setMax(2000000);
    }
}
