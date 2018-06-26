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
import model.Funcionario;
import model.LoginModel;
import util.ConnectionFactory;
import util.exception.ErroSistema;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */

/*

create procedure inserir_funcionario(in CPF_ varchar(11), in nome_ varchar(100), in email_ varchar(64) , in endereco_ varchar(200),
 in telefone_cel_ varchar(20), in telefone_res_ varchar(20), in data_nascimento_ date, in status_ int(2), in senha_ varchar(10))

CPF     Nome    Email   Endereco    Telefone_cel    Telefone_res    Data_nascimento     Status      Senha
1       2       3       4           5               6               7                   8           9
 */
public class FuncionarioDAO {

    public void salvar(Funcionario f) throws ErroSistema {
        String sql = "call inserir_funcionario(?,?,?,?,?,?,?,?,?)";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            CallableStatement cs = conexao.prepareCall(sql);
            cs.setString(1, f.getCPF());
            cs.setString(2, f.getNome());
            cs.setString(3, f.getEmail());
            cs.setString(4, f.getEndereco());
            cs.setString(5, f.getTelefoneCel());
            cs.setString(6, f.getTelefoneRes());
            cs.setDate(7, new Date(f.getData_nascimento().getTime()));
            cs.setInt(8, f.getStatus());
            cs.setString(9, f.getSenha());
            cs.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao cadastrar funcionário!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar funcionário!", ex);
        }
    }

    //sehna
    public void alterar(Funcionario f) throws ErroSistema {
        String sql = "update funcionario set nome=?, email=?, endereco=?, telefone_cel=?, telefone_res=?, data_nascimento=?, status=? where cpf=?";

        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, f.getNome());
            ps.setString(2, f.getEmail());
            ps.setString(3, f.getEndereco());
            ps.setString(4, f.getTelefoneCel());
            ps.setString(5, f.getTelefoneRes());
            ps.setDate(6, new Date(f.getData_nascimento().getTime()));
            ps.setInt(7, f.getStatus());
            ps.setString(8, f.getCPF());
            ps.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao editar funcionário!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao editar funcionário!", ex);
        }
    }

    public List<Funcionario> buscar(String txt, String op) throws ErroSistema {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql;
        if (txt.isEmpty()) {
            sql = "select * from funcionarios_ativos";
        } else {
            sql = "select * from funcionarios_ativos where " + op + " like \'%" + txt + "%\'";
        }
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setCPF(rs.getString("CPF"));
                f.setNome(rs.getString("nome"));
                f.setEmail(rs.getString("email"));
                f.setEndereco(rs.getString("endereco"));
                f.setTelefoneCel(rs.getString("telefone_cel"));
                f.setTelefoneRes(rs.getString("telefone_res"));
                f.setData_nascimento(rs.getDate("data_nascimento"));
                f.setStatus(rs.getInt("status"));
                f.setSenha(rs.getString("senha"));
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

    public void cancelar(Funcionario f) throws ErroSistema {
        String sql = "update funcionario set status = 0 where CPF = ?";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, f.getCPF());
            ps.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao cancelar funcionarios!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cancelar funcionarios!", ex);
        }
    }

    public String buscarCPF(String login,String senha) throws ErroSistema {
        String sql = "select CPF from funcionario where email = ? and senha = ?";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String resultado = rs.getString("CPF");
                ConnectionFactory.fechaConexao();
                return resultado;
            }
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao cancelar funcionarios!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cancelar funcionarios!", ex);
        }
        return "";
    }

    public Integer buscarCargo(String cpf) throws ErroSistema {
        String sql = "select status from funcionario where CPF=?";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Integer resultado = rs.getInt("status");
                ConnectionFactory.fechaConexao();
                return resultado;
            }
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao cancelar funcionarios!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cancelar funcionarios!", ex);
        }
        return null;
    }
    
    public Integer alterarSenha(String senha_ant,String senha_nova,String cpf) throws ErroSistema{
        String sql = "select alterar_senha(?,?,?)";
        Integer re;
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, senha_ant);
            ps.setString(2, senha_nova);
            ps.setString(3, cpf);
            ResultSet rs = ps.executeQuery();
            System.out.println(ps.toString());
            while (rs.next()) {                
                re = rs.getInt(1);
                System.out.println(re.toString());
                ConnectionFactory.fechaConexao();
                return re;
            }
            
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao tentar alterar senha!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar alterar senha!", ex);
        }
        return -2;
    }
}
