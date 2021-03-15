package control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class emailValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Pattern patern = Pattern.compile("\\w+@\\w+\\.\\w+");
        Matcher matcher = patern.matcher((CharSequence) value);
        HtmlInputText htmlinputtext = (HtmlInputText)component;
        String label;
        if(htmlinputtext.getLabel()==null || htmlinputtext.getLabel().trim().equals("")){
            label = htmlinputtext.getId();
        } else{
            label = htmlinputtext.getLabel();
        } if(!matcher.matches()){
            FacesMessage facesmensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, label + ": El correo es inválido, recuerde el formato example@gmail.com", "El correo es inválido, recuerde el formato example@gmail.com");
            throw new ValidatorException(facesmensaje); 
        } 
    }
}
