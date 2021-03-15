package control;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class filtroUrl implements PhaseListener{

    @Override
    public void afterPhase(PhaseEvent event) {        
        FacesContext facesContext = event.getFacesContext();       
        String currentPage = facesContext.getViewRoot().getViewId();       
        boolean isPageLogin = currentPage.lastIndexOf("index.xhtml") > -1 ? true : false;       
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);       
        Object usuario = session.getAttribute("usuario");       
        if(!isPageLogin && usuario==null){
            NavigationHandler nHandler = facesContext.getApplication().getNavigationHandler();
            nHandler.handleNavigation(facesContext, null, "faces/index.xhtml");
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {       
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
