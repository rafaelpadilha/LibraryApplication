package beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Emprestimo;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */
@SessionScoped
@ManagedBean
public class EmprestimoController {
    private Emprestimo emprestimo = new Emprestimo();
    private List<Emprestimo> emprestimos = new ArrayList<>();
    private Emprestimo emprestimoSelecionado = new Emprestimo();
    private String opBusca = "";
    private String txtBusca;
    
    public void emprestar(){
        //TODO
    }
    
    public void cancelar(){
        //TODO
    }
    
    public void renovar(){
        //TODO
    }
    
    public void devover(){
        //TODO
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    public Emprestimo getEmprestimoSelecionado() {
        return emprestimoSelecionado;
    }

    public void setEmprestimoSelecionado(Emprestimo emprestimoSelecionado) {
        this.emprestimoSelecionado = emprestimoSelecionado;
    }

    public String getOpBusca() {
        return opBusca;
    }

    public void setOpBusca(String opBusca) {
        this.opBusca = opBusca;
    }

    public String getTxtBusca() {
        return txtBusca;
    }

    public void setTxtBusca(String txtBusca) {
        this.txtBusca = txtBusca;
    }

    public EmprestimoController() {
    }
    
    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext contex = FacesContext.getCurrentInstance();
        FacesMessage mensage = new FacesMessage(tipoErro, sumario, detalhe);
        contex.addMessage(null, mensage);

    }
}
