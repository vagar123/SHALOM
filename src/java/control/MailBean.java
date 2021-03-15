package control;

import dao.CorreoDAO;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import modelo.Clientes;

@ManagedBean
@ViewScoped

public class MailBean implements Serializable{
    
    private List<Clientes> lstClientes;
    private CorreoDAO cdao;
    SenderData sender = new SenderData();

    private String asunto;
    private String textmsj;
    private List<String> dest;

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getTextmsj() {
        return textmsj;
    }

    public void setTextmsj(String textmsj) {
        this.textmsj = textmsj;
    }

    public List<String> getDest() {
        return dest;
    }

    public void setDest(List<String> dest) {
        this.dest = dest;
    }

    
    public List<Clientes> getLstClientes() {
        return lstClientes;
    }

    public void setLstClientes(List<Clientes> lstClientes) {
        this.lstClientes = lstClientes;
    }

    public void listar() {
        try {
            cdao = new CorreoDAO();

            lstClientes = cdao.listar();
        } catch (Exception e) {
        }
    }
    
    public void listarSel(){
        dest.forEach(cad -> {
            System.out.println(cad);
        });
    }
    
    public void enviarMail() throws MessagingException {
        System.out.println("Preparando Mensaje para enviar");
        //MailPrep.enviarMail("rogers.ar@gmail.com", asunto, textmsj);

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session sesion = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender.user, sender.pass);
            }
        });
        
        try {
            Message mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(sender.user));

            InternetAddress[] destinos = new InternetAddress[dest.size()];
            int i = 0;
            
            Iterator itr = dest.iterator();
            
            while(itr.hasNext()){
                InternetAddress ndir = new InternetAddress(itr.next().toString());
                destinos[i] = ndir;                
                i++;
            }
                                 
            mensaje.setRecipients(Message.RecipientType.TO, destinos);
            mensaje.setSubject(asunto);
            mensaje.setText(textmsj);
                                
            Transport.send(mensaje);
            
             System.out.println("Mensaje enviado exitosamente");
        } catch (MessagingException e) {
        }
    }
}
