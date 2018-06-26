package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Emprestimo;
import util.ConnectionFactory;
import util.exception.ErroSistema;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */
//isbn, id, cpf, data,
public class EmprestimoDAO {

    public void salvar(Emprestimo e) throws ErroSistema {
        String sql = "insert into emprestimo(ISBN,CPF,data_emprestimo,data_prevista) values(?,?,curdate(),date_add(curdate(),interval 14 day))";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, e.getISBN());
            ps.setString(2, e.getCPF());
            ps.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao registrar emprestimo", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao registrar emprestimo", ex);
        }
    }

    public void deletar(Emprestimo e) throws ErroSistema {
        String sql = "delete from emprestimo where id_emprestimo = ?";
        try {

            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, e.getId());
            ps.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao deletar emprestimo", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar emprestimo", ex);
        }
    }

    public List<Emprestimo> buscar(String txtBusca, String opBusca) throws ErroSistema {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql;
        if (txtBusca.isEmpty()) {
            sql = "select * from emprestimo_nao_devolvido";
        } else {
            sql = "select * from emprestimo_nao_devolvido where " + opBusca + " like \'%" + txtBusca + "%\'";
        }
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Emprestimo em = new Emprestimo();
                em.setId(rs.getInt("id"));
                em.setNome(rs.getString("nome"));
                em.setCPF(rs.getString("CPF"));
                em.setTitulo(rs.getString("titulo"));
                em.setISBN(rs.getString("isbn"));
                em.setData_emprestimo(rs.getDate("data_emprestimo"));
                em.setData_prevista(rs.getDate("data_prevista"));
                em.setData_devolucao(rs.getDate("data_devolucao"));
                em.setMulta(rs.getDouble("valor_multa"));
                emprestimos.add(em);
            }
            ConnectionFactory.fechaConexao();
            return emprestimos;
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao listar emprestimos!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar emprestimos!", ex);
        }
    }

    public Integer devolver(Emprestimo e) throws ErroSistema {
        String sql = "select devolver(?)";
        Integer resultado;
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, e.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultado = rs.getInt(1);
                ConnectionFactory.fechaConexao();
                return resultado;
            }
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao devolver emprestimos!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao devolver emprestimos!", ex);
        }
        return -2;
    }

    public Integer renovar(Emprestimo e) throws ErroSistema {
        String sql = "select renovar(?)";
        Integer resultado;
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, e.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultado = rs.getInt(1);
                ConnectionFactory.fechaConexao();
                return resultado;
            }
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao renovar emprestimos!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao renovar emprestimos!", ex);
        }
        return -2;
    }

    public List<Emprestimo> multas(String cpf) throws ErroSistema {
        String sql
                = "select ISBN, titulo, id_emprestimo, data_emprestimo, data_prevista, data_devolucao,valor_multa from multas where CPF = ?";
        List<Emprestimo> emprestimos = new ArrayList<>();

        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Emprestimo em = new Emprestimo();
                em.setId(rs.getInt("id_emprestimo"));
                em.setTitulo(rs.getString("titulo"));
                em.setISBN(rs.getString("isbn"));
                em.setData_emprestimo(rs.getDate("data_emprestimo"));
                em.setData_prevista(rs.getDate("data_prevista"));
                em.setData_devolucao(rs.getDate("data_devolucao"));
                em.setMulta(rs.getDouble("valor_multa"));
                emprestimos.add(em);
            }
            ConnectionFactory.fechaConexao();
            return emprestimos;
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao listar multas!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar multas!", ex);
        }
    }

    public void pagarMulta(Emprestimo e) throws ErroSistema {
        String sql = "update emprestimo set valor_multa = 0.0 where id_emprestimo = ?";

        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, e.getId());
            ps.execute();
            ConnectionFactory.fechaConexao();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao pagar multa!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pagar multa!", ex);
        }
    }
    
    public Boolean clienteBloqueado(String cpf) throws ErroSistema {
        String sql = "select cpf from cliente where status = 1 and CPF = ?";

        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ConnectionFactory.fechaConexao();
                return true;
            } else {
                ConnectionFactory.fechaConexao();
                return false;
            }
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao verificar status dos clientes!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao verificar status dos clientes!", ex);
        }
    }
}
