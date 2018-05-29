package dao;

import java.sql.Connection;
import model.Livro;
import util.ConnectionFactory;
import util.exception.ErroSistema;

public class LivroDAO {
    public void salvar(Livro livro) throws ErroSistema{
        String sql = 
                "insert into livros values(?,?,?,?,?)";
        Connection conexao = ConnectionFactory.getConexao();
    }
    public void buscar(){
        
    }
    public void atualizar(){
        
    }
    public void deletar(){
        
    }
}
