package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;
import util.ConnectionFactory;
import util.exception.ErroSistema;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */
public class ClienteDAO {

    public void salvar(Cliente c) throws ErroSistema {
        String sql
                = "insert into pessoas(nome,cpf,endereco,telefone,data_nascimento,status) values(?,?,?,?,?,2)";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCPF());
            ps.setString(3, c.getEndereco());
            ps.setString(4, c.getTelefone());
            ps.setDate(5, new Date(c.getData_nascimento().getTime()));
            ps.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao cadastrar cliente!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar cliente!", ex);
        }
    }

    public List<Cliente> buscar(String texto, String op) throws ErroSistema {
        List<Cliente> clientes = new ArrayList<>();
        String sql;
        if (texto.isEmpty()) {
            sql = "select * from pessoas where status=2";
        } else {
            sql = "select * from pessoas where status=2 and " + op + " like \'%" + texto + "%\'";
        }
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCPF(rs.getString("cpf"));
                c.setEndereco(rs.getString("endereco"));
                c.setTelefone(rs.getString("telefone"));
                c.setData_nascimento(rs.getDate("data_nascimento"));
                c.setStatus(rs.getInt("status"));
                clientes.add(c);
            }
            ConnectionFactory.fechaConexao();
            return clientes;
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao listar cliente!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar cliente!(SQLE)", ex);
        }
    }

    public void atualizar(Cliente c) throws ErroSistema {
        String sql = "update pessoas set nome=?,cpf=?,endereco=?,telefone=?,data_nascimento=? where id=?";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCPF());
            ps.setString(3, c.getEndereco());
            ps.setString(4, c.getTelefone());
            ps.setDate(5, new Date(c.getData_nascimento().getTime()));
            ps.setInt(6, c.getId());
            ps.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao listar cliente!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar cliente!(SQLE)", ex);
        }
    }

    public void deletar(Cliente c) throws ErroSistema {
        String sql = "delete from pessoas where id=?";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, c.getId());
            ps.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao listar cliente!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar cliente!(SQLE)", ex);
        }
    }
}
