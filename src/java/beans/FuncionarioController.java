package beans;

import dao.FuncionarioDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Funcionario;
import model.LoginModel;
import util.exception.ErroSistema;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */
@ManagedBean
@SessionScoped
public class FuncionarioController {

    private Funcionario funcionario = new Funcionario();
    private List<Funcionario> funcionarios = new ArrayList<>();
    private FuncionarioDAO fdao = new FuncionarioDAO();
    private String textoBusca = "";
    private String opBusca = "nome";
    private Funcionario funcionarioSelecionado;
    private Boolean gerente = false;
    private String cpf = "";
    private String senha_a;
    private String senha_n;

    public void buscarCPF(String login, String senha) {
        if (cpf.equals("")) {
            try {
                this.setCpf(fdao.buscarCPF(login, senha));
            } catch (ErroSistema ex) {
                adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
            }
        }
    }

    public void cargo() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }

        try {
            Integer res = fdao.buscarCargo(this.getCpf());
            if (res == 4) {
                this.setGerente(true);
            } else if (res == 3) {
                this.setGerente(false);
            }
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void cadastrar() {
        try {
            fdao.salvar(this.funcionario);
            funcionario = new Funcionario();
            adicionarMensagem("Concluido,", "Funcionário cadastrado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Funcionario f) {
        try {
            if (f.getSenha() == null) {
                f.setSenha("");
            }
            fdao.alterar(f);
            adicionarMensagem("Concluido,", "Funcionário alterado com sucesso!", FacesMessage.SEVERITY_INFO);

        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void cancelar(Funcionario f) {
        try {
            fdao.cancelar(f);
            adicionarMensagem("Concluido,", "Funcionário cancelado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void listar() {
        try {
            setFuncionarios(fdao.buscar(this.getTextoBusca(), this.getOpBusca()));
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void trocarSenha() {
        try {
            if (fdao.alterarSenha(this.getSenha_a(), this.getSenha_n(), this.getCpf()) == 1) {
                adicionarMensagem("Concluido,", "Senha alterada com sucesso!", FacesMessage.SEVERITY_INFO);
            }else{
                adicionarMensagem("Aviso,", "Não foi possível alterar a senha!", FacesMessage.SEVERITY_WARN);
                
            }
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public String getSenha_a() {
        return senha_a;
    }

    public void setSenha_a(String senha_a) {
        this.senha_a = senha_a;
    }

    public String getSenha_n() {
        return senha_n;
    }

    public void setSenha_n(String senha_n) {
        this.senha_n = senha_n;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Boolean getGerente() {
        return gerente;
    }

    public void setGerente(Boolean gerente) {
        this.gerente = gerente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public FuncionarioDAO getFdao() {
        return fdao;
    }

    public void setFdao(FuncionarioDAO fdao) {
        this.fdao = fdao;
    }

    public String getTextoBusca() {
        return textoBusca;
    }

    public void setTextoBusca(String textoBusca) {
        this.textoBusca = textoBusca;
    }

    public String getOpBusca() {
        return opBusca;
    }

    public void setOpBusca(String opBusca) {
        this.opBusca = opBusca;
    }

    public Funcionario getFuncionarioSelecionado() {
        return funcionarioSelecionado;
    }

    public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
        this.funcionarioSelecionado = funcionarioSelecionado;
    }

    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext contex = FacesContext.getCurrentInstance();
        FacesMessage mensage = new FacesMessage(tipoErro, sumario, detalhe);
        contex.addMessage(null, mensage);

    }

}
