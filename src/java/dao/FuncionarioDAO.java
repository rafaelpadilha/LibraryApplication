package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Funcionario;
import util.ConnectionFactory;
import util.exception.ErroSistema;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */
public class FuncionarioDAO {

    public void salvar(Funcionario f) throws ErroSistema {
        String sql = "insert into pessoas(nome,cpf,endereco,telefone,data_nascimento,status) values(?,?,?,?,?,?)";
        Integer id;
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCPF());
            ps.setString(3, f.getEndereco());
            ps.setString(4, f.getTelefone());
            ps.setDate(5, new Date(f.getData_nascimento().getTime()));
            ps.setInt(6, f.getStatus());
            ps.execute();
            ps = conexao.prepareStatement("SELECT LAST_INSERT_ID()");
            ResultSet rs = ps.executeQuery();
            rs.next();
            id = rs.getInt(1);
            ps = conexao.prepareStatement("insert into login values(?,?,?)");
            ps.setInt(1, id);
            ps.setString(2, f.getLogin());
            ps.setString(3, f.getSenha());
            ps.execute();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao cadastrar funcionário!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar funcionário!", ex);
        }
    }

    //sehna
    public void alterar(Funcionario f) throws ErroSistema{
        String sql = "update funcionarios set nome=?, cpf=?, endereco=?, telefone=?, data_nascimento=?, status=? where id=?";
        String sql2;
        if (f.getSenha().isEmpty()) {
            sql2 = "update login set login=? where id_func=?";
        } else {
            sql2 = "update login set login=?, sehna=" + f.getSenha() + " where id_func = ?";
        }

        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1,f.getNome());
            ps.setString(2,f.getCPF());
            ps.setString(3,f.getEndereco());
            ps.setString(4,f.getTelefone());
            ps.setDate(5, new Date(f.getData_nascimento().getTime()));
            ps.setInt(6, f.getStatus());
            ps.setInt(7, f.getId());
            ps.execute();
            System.out.println("SQL:" + f.getStatus());
            ps = conexao.prepareStatement(sql2);
            ps.setString(1, f.getLogin());
            ps.setInt(2, f.getId());
            ps.execute();
            ConnectionFactory.fechaConexao();
        }catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao cadastrar funcionário!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar funcionário!", ex);
        }
    }

    public List<Funcionario> buscar(String txt, String op) throws ErroSistema {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql;
        if (txt.isEmpty()) {
            sql = "select * from funcionarios";
        } else {
            sql = "select * from funcionarios where " + op + " like \'%" + txt + "%\'";
        }
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setCPF(rs.getString("cpf"));
                f.setEndereco(rs.getString("endereco"));
                f.setTelefone(rs.getString("telefone"));
                f.setData_nascimento(rs.getDate("data_nascimento"));
                f.setStatus(rs.getInt("status"));
                f.setLogin(rs.getString("login"));
                funcionarios.add(f);
            }
            ConnectionFactory.fechaConexao();
            return funcionarios;
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao listar funcionarios!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar funcionarios!(SQLE)", ex);
        }
    }

    public void deletar() {

    }
}
