package beans;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.LoginModel;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */
@ManagedBean
@SessionScoped
public class LoginController implements Serializable{
    private LoginModel login;

    public LoginController() {
        login = new LoginModel();
    }
    
    public String logarNoSistema(){
        if(login.getLogin().equals("admin") && login.getSenha().equals("admin")){
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("usuario", login);
            return "/app/index?faces-redirect=true";
        }else{
            adicionarMensagem("Falha no login!", "Verifique se as informações estão corretas", FacesMessage.SEVERITY_WARN);
            //return "/security/login?faces-redirect=true";
            return "";
        }
    }
    
    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext contex = FacesContext.getCurrentInstance();
        FacesMessage mensage = new FacesMessage(tipoErro, sumario, detalhe);
        contex.addMessage(null, mensage);

    }
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/security/login?faces-redirect=true";
    }

    public LoginModel getLogin() {
        return login;
    }

    public void setLogin(LoginModel login) {
        this.login = login;
    }
    
    
}
