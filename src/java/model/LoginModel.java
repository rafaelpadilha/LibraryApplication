package model;

import java.io.Serializable;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */
public class LoginModel implements Serializable{
    public static final long serialVersionUID = 1L;
    
    private String login;
    private String senha;

    public LoginModel() {
    }

    public LoginModel(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
}
