/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência relacionadas aos
 * usuários no sistema Banco Malvader.
 *
 * <p>Fornece métodos para inserir, atualizar, excluir e consultar informações de usuários no banco
 * de dados, incluindo autenticação e gerenciamento de perfis.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.DAO;

import com.bancomalvader.DatabaseConnection.DatabaseConnection;
import com.bancomalvader.Model.Usuario;
import java.sql.*;

public class UsuarioDAO {
  public void excluirUsuario(int idUsuario) {
    String query = "DELETE FROM usuario WHERE id_usuario = ?";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setInt(1, idUsuario);
      ps.executeUpdate();
      System.out.println("Usuário excluído com sucesso. ID do usuário: " + idUsuario);

    } catch (SQLException e) {
      throw new RuntimeException("Erro ao excluir usuário: " + e.getMessage(), e);
    }
  }

  public int inserirUsuario(
      String nome,
      String cpf,
      Date dataNascimento,
      String telefone,
      String tipoUsuario,
      String senha) {
    try (Connection conn = DatabaseConnection.getConnection()) {
      String sql =
          "INSERT INTO usuario (nome, cpf, data_nascimento, telefone, tipo_usuario, senha) "
              + "VALUES (?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      stmt.setString(1, nome);
      stmt.setString(2, cpf);
      stmt.setDate(3, dataNascimento);
      stmt.setString(4, telefone);
      stmt.setString(5, tipoUsuario);
      stmt.setString(6, senha);

      stmt.executeUpdate();
      ResultSet rs = stmt.getGeneratedKeys();

      if (rs.next()) {
        return rs.getInt(1); // Retorna o ID gerado
      } else {
        throw new RuntimeException("Falha ao inserir usuário, ID não gerado.");
      }
    } catch (Exception e) {
      throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage(), e);
    }
  }

  public int obterUltimoIdUsuario() {
    String query = "SELECT MAX(id_usuario) AS id_usuario FROM usuario";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery()) {
      if (rs.next()) {
        return rs.getInt("id_usuario");
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao obter o último ID de usuário: " + e.getMessage(), e);
    }
    return -1; // Retorna -1 se não encontrar nenhum ID
  }

  public Usuario buscarUsuarioPorId(int idUsuario) {
    String query = "SELECT * FROM usuario WHERE id_usuario = ?";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setInt(1, idUsuario);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return new Usuario(
            rs.getInt("id_usuario"),
            rs.getString("nome"),
            rs.getString("cpf"),
            rs.getDate("data_nascimento"),
            rs.getString("telefone"),
            rs.getString("tipo_usuario"),
            rs.getString("senha"));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage(), e);
    }
    return null;
  }

  public static boolean verificarCpfExiste(String cpf) {
    String query = "SELECT COUNT(*) AS total FROM usuario WHERE cpf = ?";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, cpf);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getInt("total") > 0;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao verificar duplicidade de CPF: " + e.getMessage(), e);
    }
    return false;
  }
}
