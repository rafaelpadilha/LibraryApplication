package dao;

import java.sql.CallableStatement;
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

    //create procedure inserir_livro(in ISBN_ varchar(20), in titulo_ varchar(100), in autor_ varchar(100),
    //in descricao_ varchar(500), in quantidade_exemplares_ numeric(3), in quantidade_disponivel_ numeric(3))
    public void salvar(Livro livro) throws ErroSistema {
        String sql = "{call inserir_livro(?,?,?,?,?,?)}";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            CallableStatement cs = conexao.prepareCall(sql);
            cs.setString(1, livro.getIsbn());
            cs.setString(2, livro.getTitulo());
            cs.setString(3, livro.getAutor());
            cs.setString(4, livro.getDescricao());
            cs.setInt(5, livro.getQuantidade_exemplares());
            cs.setInt(6, livro.getQuantidade_exemplares());
            cs.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao cadastrar livro:", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar livro:", ex);
        }
    }

    public List<Livro> buscar(String txt, String op) throws ErroSistema {
        List<Livro> livros = new ArrayList<>();
        String sql;
        if (txt.isEmpty()) {
            sql = "select * from livros_disponiveis";
        } else {
            sql = "select * from livros_disponiveis where " + op + " like \'%" + txt + "%\'";
        }
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setIsbn(rs.getString("isbn"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setQuantidade_exemplares(rs.getInt("quantidade_exemplares"));
                livro.setQuantidade_disponivel(rs.getInt("quantidade_disponivel"));
                livro.setDescricao(rs.getString("descricao"));
                livros.add(livro);
            }
            ConnectionFactory.fechaConexao();
            return livros;
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao cadastrar livro:", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar livro:", ex);
        }
    }

    public void atualizar(Livro l) throws ErroSistema {
        String sql = "update livro set titulo=?, autor=?, quantidade_exemplares=?, quantidade_disponivel=?, descricao=? where isbn=?";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, l.getTitulo());
            ps.setString(2, l.getAutor());
            ps.setInt(3, l.getQuantidade_exemplares());
            ps.setInt(4, l.getQuantidade_disponivel());
            ps.setString(5, l.getDescricao());
            ps.setString(6, l.getIsbn());
            ps.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao editar livro:", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao editar livro:", ex);
        }
    }

    public void deletar(Livro l) throws ErroSistema {
        String sql = "update livro set quantidade_exemplares = 0, quantidade_disponivel = 0 where isbn=?";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, l.getIsbn());
            ps.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao editar livro:", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao editar livro:", ex);
        }
    }
}
