package beans;

import dao.LivroDAO;
import java.util.ArrayList;
import java.util.List;
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
    
    public void cadastrar(){
        try {
            ldao.salvar(livro);
            livro = new Livro();
            
            adicionarMensagem("Concluido!", "Cliente cadastrado com sucesso", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
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
