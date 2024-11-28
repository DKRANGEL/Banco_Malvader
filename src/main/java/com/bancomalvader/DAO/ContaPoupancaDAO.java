/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência relacionadas às contas
 * poupança no sistema Banco Malvader.
 *
 * <p>Fornece métodos para inserir, atualizar, excluir e consultar informações de contas poupança no
 * banco de dados.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.DAO;

import com.bancomalvader.DatabaseConnection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContaPoupancaDAO {

  public void inserirContaPoupanca(int idConta) {
    String query = "INSERT INTO conta_poupanca (taxa_rendimento, id_conta) VALUES (?, ?)";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setBigDecimal(1, new java.math.BigDecimal("0.02")); // Taxa de rendimento padrão (2%)
      ps.setInt(2, idConta);

      ps.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Erro ao inserir conta poupança: " + e.getMessage(), e);
    }
  }

  public boolean excluirContaPoupanca(int idConta) {
    String query = "DELETE FROM conta_poupanca WHERE id_conta = ?";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setInt(1, idConta);
      int rowsAffected = ps.executeUpdate();

      return rowsAffected > 0; // Retorna true se excluiu ao menos um registro
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao excluir conta poupança: " + e.getMessage(), e);
    }
  }
}
