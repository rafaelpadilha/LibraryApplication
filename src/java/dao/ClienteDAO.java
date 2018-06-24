package dao;

import java.sql.CallableStatement;
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
        //(in CPF_ varchar(11), in nome_ varchar(100), in email_ varchar(64) , in endereco_ varchar(200),
        //in telefone_cel_ varchar(20), in telefone_res_ varchar(20), in data_nascimento_ date, in status_ int(2))
        String sql = "{call inserir_cliente(?,?,?,?,?,?,?,2)}";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            CallableStatement cs = conexao.prepareCall(sql);
            cs.setString(1, c.getCPF());
            cs.setString(2, c.getNome());
            cs.setString(3, c.getEmail());
            cs.setString(4, c.getEndereco());
            cs.setString(5, c.getTelefoneCel());
            cs.setString(6, c.getTelefoneRes());
            cs.setDate(7, new Date(c.getData_nascimento().getTime()));
            cs.execute();
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
            sql = "select * from cliente_ativo";
        } else {
            sql = "select * from cliente_ativo where " + op + " like \'%" + texto + "%\'";
        }
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            System.out.println(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setNome(rs.getString("nome"));
                c.setCPF(rs.getString("CPF"));
                c.setEndereco(rs.getString("endereco"));
                c.setEmail(rs.getString("email"));
                c.setTelefoneCel(rs.getString("telefone_cel"));
                c.setTelefoneRes(rs.getString("telefone_res"));
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
        String sql = "update cliente set nome = ?,endereco = ?, email = ?, telefone_cel = ?, telefone_res = ?, data_nascimento=? where CPF = ?";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getEndereco());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getTelefoneCel());
            ps.setString(5, c.getTelefoneRes());
            ps.setDate(6, new Date(c.getData_nascimento().getTime()));
            ps.setString(7, c.getCPF());
            ps.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao atualizar cliente!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao atualizar cliente!(SQLE)", ex);
        }
    }

    public void cancelar(Cliente c) throws ErroSistema {
        String sql = "update cliente set status = 0 where CPF=?";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, c.getCPF());
            ps.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao cancelar cliente!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cancelar cliente!(SQLE)", ex);
        }
    }
}
