package beans;

import dao.LivroDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Livro;
import util.exception.ErroSistema;

@ManagedBean
@SessionScoped
public class LivroController {
    private Livro livro = new Livro();
    private List<Livro> livros = new ArrayList<>();
    private LivroDAO ldao = new LivroDAO();
    private String textoBusca = "";
    private String opBusca;
    private Livro livroSelecionado;
    
    public void cadastrar(){
        try {
            ldao.salvar(livro);
            livro = new Livro();
            adicionarMensagem("Concluido!", "Livro cadastrado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void listar(){
        try {
            setLivros(ldao.buscar(this.getTextoBusca(), this.getOpBusca()));
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void deletar(Livro l){
        try{
            ldao.deletar(l);
        }catch(ErroSistema ex){
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void editar(Livro l){
        try {
            ldao.atualizar(this.getLivroSelecionado());
            adicionarMensagem("Concluido!", "Edição feita com sucesso.", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
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

    public Livro getLivroSelecionado() {
        return livroSelecionado;
    }

    public void setLivroSelecionado(Livro livroSelecionado) {
        this.livroSelecionado = livroSelecionado;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public LivroDAO getLdao() {
        return ldao;
    }

    public void setLdao(LivroDAO ldao) {
        this.ldao = ldao;
    }
    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext contex = FacesContext.getCurrentInstance();
        FacesMessage mensage = new FacesMessage(tipoErro, sumario, detalhe);
        contex.addMessage(null, mensage);

    }
}
