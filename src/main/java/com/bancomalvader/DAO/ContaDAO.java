/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência relacionadas às contas
 * bancárias no sistema Banco Malvader.
 *
 * <p>Fornece métodos genéricos para manipular dados de contas, como inserção, atualização, exclusão
 * e consultas no banco de dados. Esta classe serve como base para tipos específicos de contas, como
 * correntes e poupanças.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.DAO;

import com.bancomalvader.DatabaseConnection.DatabaseConnection;
import com.bancomalvader.Model.Conta;
import java.sql.*;

public class ContaDAO {

  // Método para inserir uma nova conta
  public Conta inserirConta(String numeroConta, String agencia, String tipoConta, int idCliente) {
    String sql =
        "INSERT INTO conta (numero_conta, agencia, tipo_conta, id_cliente) VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      // Configura os parâmetros
      stmt.setString(1, numeroConta);
      stmt.setString(2, agencia);
      stmt.setString(3, tipoConta);
      stmt.setInt(4, idCliente);

      // Executa a inserção
      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected == 0) {
        throw new SQLException("Falha ao inserir conta: nenhuma linha foi afetada.");
      }

      // Tenta obter o ID gerado
      try (ResultSet rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
          int idConta = rs.getInt(1);
          System.out.println("Conta criada com ID: " + idConta); // Log para debug
          return new Conta(idConta, numeroConta, agencia, 0.00, tipoConta, idCliente);
        } else {
          throw new SQLException("Falha ao obter o ID da conta inserida.");
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao inserir conta: " + e.getMessage(), e);
    }
  }

  // Método para verificar se um cliente possui outras contas
  public boolean verificarOutrasContas(int idCliente) {
    String query = "SELECT COUNT(*) AS total FROM conta WHERE id_cliente = ?";

    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setInt(1, idCliente);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getInt("total") > 0; // Retorna true se o cliente tiver outras contas
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao verificar outras contas: " + e.getMessage(), e);
    }

    return false; // Retorna false se não encontrar outras contas
  }

  // Método para buscar uma conta pelo nome do cliente e número da conta
  public Conta buscarContaPorNomeENumero(String nomeCliente, String numeroConta) {
    String query =
        """
                SELECT c.id_conta, c.numero_conta, c.agencia, c.saldo, c.tipo_conta, c.id_cliente,
                       u.nome AS nome_cliente, u.cpf AS cpf_cliente
                FROM conta c
                INNER JOIN cliente cl ON c.id_cliente = cl.id_cliente
                INNER JOIN usuario u ON cl.id_usuario = u.id_usuario
                WHERE u.nome = ? AND c.numero_conta = ?
            """;

    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setString(1, nomeCliente);
      ps.setString(2, numeroConta);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          Conta conta =
              new Conta(
                  rs.getInt("id_conta"),
                  rs.getString("numero_conta"),
                  rs.getString("agencia"),
                  rs.getDouble("saldo"),
                  rs.getString("tipo_conta"),
                  rs.getInt("id_cliente"));

          // Adiciona informações do cliente
          conta.setNomeCliente(rs.getString("nome_cliente"));
          conta.setCpfCliente(rs.getString("cpf_cliente"));

          return conta;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar conta por nome e número: " + e.getMessage(), e);
    }

    return null; // Retorna null se a conta não for encontrada
  }

  // Método para buscar uma conta pelo número
  public Conta buscarContaPorNumero(String numeroConta) {
    String query =
        """
        SELECT c.id_conta, c.numero_conta, c.agencia, c.saldo, c.tipo_conta, c.id_cliente,
               u.nome AS nome_cliente, u.cpf AS cpf_cliente,
               cc.limite, cc.data_vencimento
        FROM conta c
        INNER JOIN cliente cl ON c.id_cliente = cl.id_cliente
        INNER JOIN usuario u ON cl.id_usuario = u.id_usuario
        LEFT JOIN conta_corrente cc ON c.id_conta = cc.id_conta
        WHERE c.numero_conta = ?
    """;

    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setString(1, numeroConta);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        Conta conta =
            new Conta(
                rs.getInt("id_conta"),
                rs.getString("numero_conta"),
                rs.getString("agencia"),
                rs.getDouble("saldo"),
                rs.getString("tipo_conta"),
                rs.getInt("id_cliente"));

        // Dados adicionais
        conta.setNomeCliente(rs.getString("nome_cliente"));
        conta.setCpfCliente(rs.getString("cpf_cliente"));

        // Dados da conta corrente
        if ("CORRENTE".equalsIgnoreCase(rs.getString("tipo_conta"))) {
          conta.setLimite(rs.getBigDecimal("limite"));
          conta.setDataVencimento(String.valueOf(rs.getDate("data_vencimento")));
        }

        return conta;
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar conta por número: " + e.getMessage(), e);
    }

    return null;
  }

  // Método para excluir uma conta
  public void excluirConta(int idConta) {
    String query = "DELETE FROM conta WHERE id_conta = ?";

    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setInt(1, idConta);
      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException("Erro ao excluir conta: " + e.getMessage(), e);
    }
  }
}
