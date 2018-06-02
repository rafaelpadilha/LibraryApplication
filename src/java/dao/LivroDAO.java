package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Livro;
import util.ConnectionFactory;
import util.exception.ErroSistema;

public class LivroDAO {

    public void salvar(Livro livro) throws ErroSistema {
        String sql = "insert into livros values(?,?,?,?,?)";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, livro.getIsbn());
            ps.setString(2, livro.getTitulo());
            ps.setString(3, livro.getAutor());
            ps.setInt(4, livro.getQuantidade());
            ps.setString(5, livro.getDescricao());
            ps.execute();
            ConnectionFactory.fechaConexao();
        }catch(ErroSistema ex){
            throw new ErroSistema("Erro ao cadastrar livro:", ex);
        }catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar livro:", ex);
        }
    }

    public List<Livro> buscar(String txt,String op) throws ErroSistema {
        List<Livro> livros = new ArrayList<>();
        String sql;
        if(txt.isEmpty()){
            sql = "select * from livros";
        }else{
            sql = "select * from livros where " + op + " like \'%" + txt + "%\'";
        }
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Livro livro = new Livro();
                livro.setIsbn(rs.getString("isbn"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setQuantidade(rs.getInt("quantidade"));
                livro.setDescricao(rs.getString("descricao"));
                livros.add(livro);
            }
            ConnectionFactory.fechaConexao();
            return livros;
        } catch(ErroSistema ex){
            throw new ErroSistema("Erro ao cadastrar livro:", ex);
        }catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar livro:", ex);
        }
    }

    public void atualizar(Livro l) throws ErroSistema{
        String sql = "update livros set titulo=?, autor=?, quantidade=?, descricao=? where isbn=?";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, l.getTitulo());
            ps.setString(2, l.getAutor());
            ps.setInt(3, l.getQuantidade());
            ps.setString(4, l.getDescricao());
            ps.setString(5, l.getIsbn());
            ps.execute();
            ConnectionFactory.fechaConexao();
        } catch(ErroSistema ex){
            throw new ErroSistema("Erro ao editar livro:", ex);
        }catch (SQLException ex) {
            throw new ErroSistema("Erro ao editar livro:", ex);
        }
    }

    public void deletar(Livro l) throws ErroSistema{
        String sql = "delete from livros where isbn=?";
        try{
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, l.getIsbn());
            ps.execute();
            ConnectionFactory.fechaConexao();
        }catch(ErroSistema ex){
            throw new ErroSistema("Erro ao editar livro:", ex);
        }catch (SQLException ex) {
            throw new ErroSistema("Erro ao editar livro:", ex);
        }
    }
}
