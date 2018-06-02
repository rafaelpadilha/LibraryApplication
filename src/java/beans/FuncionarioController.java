package beans;

import dao.FuncionarioDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Funcionario;
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
    private String opBusca;
    private Funcionario funcionarioSelecionado;

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
            if(f.getSenha() == null)
                f.setSenha("");
            fdao.alterar(f);
            adicionarMensagem("Concluido,", "Funcionário alterado com sucesso!", FacesMessage.SEVERITY_INFO);
            
        }  catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void cancelar(Funcionario f) {

    }

    public void listar() {
        try {
            setFuncionarios(fdao.buscar(this.textoBusca, this.opBusca));
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
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
