package bean;

import dao.graficoProductosDAO;
import java.util.List;
import javax.faces.bean.ManagedBean;
import modelo.graficoProductos;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean

public class graficoProductosBean {
    
    private PieChartModel pieModel;

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }
    
    public void graficar(){
        graficoProductosDAO pdao = new graficoProductosDAO();
        List<graficoProductos> lista = pdao.listarGrafica();
        
        pieModel = new PieChartModel();
        
        for(graficoProductos pro: lista){
            pieModel.set(pro.getCategoriaProducto(), pro.getCantidadProducto());
        }
        
        pieModel.setTitle("Productos agrupados por categoria");
        pieModel.setLegendPosition("n");
        pieModel.setFill(true);
        pieModel.setLegendRows(1);
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(200);
        pieModel.setShadow(true);
    } 
}
