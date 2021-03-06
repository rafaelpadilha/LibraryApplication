package beans;

import dao.EmprestimoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Emprestimo;
import util.exception.ErroSistema;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */
@SessionScoped
@ManagedBean
public class EmprestimoController {

    private Emprestimo emprestimo = new Emprestimo();
    private List<Emprestimo> emprestimos = new ArrayList<>();
    private List<Emprestimo> multas = new ArrayList<>();
    private Emprestimo emprestimoSelecionado = new Emprestimo();
    private EmprestimoDAO edao = new EmprestimoDAO();
    private String opBusca = "";
    private String txtBusca = "";

    public void emprestar() {
        try {
            if (edao.clienteBloqueado(this.emprestimo.getCPF())) {
                adicionarMensagem("Cliente Bloqueado!", "Novo empréstimo não será possível", FacesMessage.SEVERITY_WARN);

            } else {

                edao.salvar(emprestimo);
                emprestimo = new Emprestimo();
                adicionarMensagem("Emprestimo registrado com sucessso!", "", FacesMessage.SEVERITY_INFO);
            }
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void listar() {
        try {
            setEmprestimos(edao.buscar(this.getTxtBusca(), this.getOpBusca()));
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar() {
        try {
            this.edao.deletar(this.emprestimoSelecionado);
            adicionarMensagem("Emprestimo deletado com sucessso!", "", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void renovar() {
        try {
            Integer result = this.edao.renovar(this.emprestimoSelecionado);
            if (result < 0) {
                adicionarMensagem("Não foi possível renovar!", "Empréstimo Atrasado", FacesMessage.SEVERITY_WARN);
            } else {
                adicionarMensagem("Empréstimo renovado!", "", FacesMessage.SEVERITY_INFO);
            }
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void devover() {
        try {
            Integer result = this.edao.devolver(this.emprestimoSelecionado);
            if (result < 0) {
                adicionarMensagem("Devolução atrasada registrada!", "Verificar Multas", FacesMessage.SEVERITY_WARN);
            } else {
                adicionarMensagem("Devolução registrada!", "Nenhuma pendência!", FacesMessage.SEVERITY_INFO);
            }
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void listarMultas(String cpf) {
        try {
            this.setMultas(this.edao.multas(cpf));
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void pagarMulta(Emprestimo emp) {
        try {
            edao.pagarMulta(emp);
            adicionarMensagem("Multa Paga!", "", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
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

    public List<Emprestimo> getMultas() {
        return multas;
    }

    public void setMultas(List<Emprestimo> multas) {
        this.multas = multas;
    }

    public EmprestimoDAO getEdao() {
        return edao;
    }

    public void setEdao(EmprestimoDAO edao) {
        this.edao = edao;
    }

    public EmprestimoController() {
    }

    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext contex = FacesContext.getCurrentInstance();
        FacesMessage mensage = new FacesMessage(tipoErro, sumario, detalhe);
        contex.addMessage(null, mensage);

    }
}
