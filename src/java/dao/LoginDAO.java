package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.LoginModel;
import util.ConnectionFactory;
import util.exception.ErroSistema;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */
public class LoginDAO{

    public int autenticar(LoginModel login) throws ErroSistema {
        String sql = "select autentica(?,?)";
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, login.getLogin());
            ps.setString(2, login.getSenha());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    ConnectionFactory.fechaConexao();
                    return 1;
                } else {
                    ConnectionFactory.fechaConexao();
                    return -1;
                }
            }
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao tentar se logar!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar se logar!", ex);
        }

        ConnectionFactory.fechaConexao();
        return -2;
    }
}
