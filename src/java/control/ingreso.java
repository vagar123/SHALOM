package control;

import dao.UsuariosDAO;
import modelo.Usuarios;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class ingreso {

    private String usu;
    private int contra;
    private String tipo_usu;
    private int usuario;
    private Usuarios usuar = new Usuarios();

    public String getUsu() {
        return usu;
    }

    public void setUsu(String usu) {
        this.usu = usu;
    }

    public int getContra() {
        return contra;
    }

    public void setContra(int contra) {
        this.contra = contra;
    }

    public String getTipo_usu() {
        return tipo_usu;
    }

    public void setTipo_usu(String tipo_usu) {
        this.tipo_usu = tipo_usu;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public Usuarios getUsuar() {
        return usuar;
    }

    public void setUsuar(Usuarios usuar) {
        this.usuar = usuar;
    }

    public String autenticar() {
        String resp = "";
        
        try {
            Driver drv = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(drv);
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shalom?user=root&password=&useSSL=false");
            PreparedStatement ps = conn.prepareStatement("select * from usu_contra inner join usuarios on usu_contra.usuario = usuarios.idUsuario where usu = ? and contra = ?");
            ps.setString(1, this.usu);
            ps.setInt(2, this.contra);
            ResultSet rs = ps.executeQuery(); 
            
            if (rs.next()) {
                this.usuar.setIdUsuario(rs.getInt("idUsuario"));
                this.usuar.setNomUsuario(rs.getString("nomUsuario"));
                this.usuar.setApellUsuario(rs.getString("apellUsuario"));
                this.usuar.setCorreoUsuario(rs.getString("correoUsuario"));
                this.usuar.setTeleUsuario(rs.getInt("teleUsuario"));
                this.usuar.setCargoUsuario(rs.getString("cargoUsuario"));
                this.usuar.setIdLocal(rs.getInt("idLocal"));
                
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sesion", usuar);
                
                if (rs.getString("tipo_usu").equals("Ad")) { 
                    resp = "admin?faces-redirect=true";
                }
                else
                    resp = "empleado?faces-redirect=true";
            } else {
                resp = "error?faces-redirect=true";
            }
        } catch (SQLException e) {
            System.out.println("Error en Conexión de Base de Datos");
        }
        return resp;
    }
    
    public void usuariosDatos(boolean list){       
        try {
            if(!list){
                if(!esPostBack()){
                    this.usuar = (Usuarios)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sesion");
                }
            }else{
                this.usuar = (Usuarios)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sesion");
            }
        } catch (Exception e) {
        }
    }
    
    private boolean esPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }
    
    public void verificarAdmin(){
        try {
            Usuarios us = (Usuarios)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sesion");
            if(us == null || !(us.getCargoUsuario().equals("Administrador"))){
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }
        } catch (Exception e) {
        }
    }
    
    public void verificarEmpleado(){
        try {
            Usuarios us = (Usuarios)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sesion");
            if(us == null || !(us.getCargoUsuario().equals("Empleado"))){
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }
        } catch (Exception e) {
        }
    }
    
    public void cerrarSesion(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("sesion");
        } catch (Exception e) {
        }
    }
}
