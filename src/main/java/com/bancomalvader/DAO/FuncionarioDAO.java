/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência relacionadas aos
 * funcionários no sistema Banco Malvader.
 *
 * <p>Fornece métodos para inserir, atualizar, excluir e consultar informações de funcionários no
 * banco de dados.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.DAO;

import com.bancomalvader.DatabaseConnection.DatabaseConnection;
import com.bancomalvader.Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioDAO extends UsuarioDAO {

  /**
   * Insere os dados do funcionário na tabela `funcionario`.
   *
   * @param funcionario Objeto contendo os dados do funcionário.
   * @param idUsuario ID do usuário previamente inserido na tabela `usuario`.
   */
  public void inserirFuncionario(Funcionario funcionario, int idUsuario) {
    String query =
        "INSERT INTO funcionario (codigo_funcionario, cargo, id_usuario) VALUES (?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, funcionario.getCodigoFuncionario());
      ps.setString(2, funcionario.getCargo());
      ps.setInt(3, idUsuario);

      ps.executeUpdate();

      System.out.println(
          "Funcionário inserido com sucesso. Código: " + funcionario.getCodigoFuncionario());
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao inserir funcionário: " + e.getMessage(), e);
    }
  }

  public Funcionario validarLogin(String username, String password) {
    String sql =
        "SELECT * FROM funcionario f INNER JOIN usuario u ON f.id_usuario = u.id_usuario WHERE u.nome = ? AND u.senha = ?";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, username);
      stmt.setString(2, password);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return new Funcionario(
            rs.getInt("id_funcionario"),
            rs.getString("codigo_funcionario"),
            rs.getString("cargo"),
            rs.getInt("id_usuario"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Erro ao validar login: " + e.getMessage(), e);
    }
    return null; // Retorna null se não encontrar o funcionário
  }

  public Funcionario buscarFuncionarioPorCodigo(String codigoFuncionario) {
    String query =
        """
            SELECT f.codigo_funcionario, f.cargo,
                   u.nome AS nome_funcionario, u.cpf, u.data_nascimento, u.telefone,
                   e.local, e.numero_casa, e.cep, e.bairro, e.cidade, e.estado
            FROM funcionario f
            INNER JOIN usuario u ON f.id_usuario = u.id_usuario
            INNER JOIN endereco e ON u.id_usuario = e.id_usuario
            WHERE f.codigo_funcionario = ?
        """;

    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setString(1, codigoFuncionario);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        // Construa o objeto Endereco
        Endereco endereco =
            new Endereco(
                rs.getString("cep"),
                rs.getString("local"),
                rs.getInt("numero_casa"),
                rs.getString("bairro"),
                rs.getString("cidade"),
                rs.getString("estado"));

        // Construa o objeto Funcionario
        Funcionario funcionario =
            new Funcionario(
                rs.getString("codigo_funcionario"),
                rs.getString("cargo"),
                rs.getString("nome_funcionario"),
                rs.getString("cpf"),
                rs.getDate("data_nascimento"),
                rs.getString("telefone"),
                endereco);

        System.out.println("Objeto Funcionario criado: " + funcionario);
        return funcionario;
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar funcionário por código: " + e.getMessage(), e);
    }

    return null; // Retorna null se nenhum funcionário for encontrado
  }
}
