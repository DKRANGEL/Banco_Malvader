/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência relacionadas às contas
 * correntes no sistema Banco Malvader.
 *
 * <p>Fornece métodos para inserir, atualizar, excluir e consultar informações de contas correntes
 * no banco de dados.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.DAO;

import com.bancomalvader.DatabaseConnection.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContaCorrenteDAO {

  public void inserirContaCorrente(BigDecimal limite, Date dataVencimento, int idConta) {
    String query =
        "INSERT INTO conta_corrente (limite, data_vencimento, id_conta) VALUES (?, ?, ?)";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setBigDecimal(1, limite);
      ps.setDate(2, dataVencimento);
      ps.setInt(3, idConta);

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected == 0) {
        throw new SQLException("Erro ao inserir conta corrente: nenhuma linha afetada.");
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao inserir conta corrente: " + e.getMessage(), e);
    }
  }

  public boolean excluirContaCorrente(int idConta) {
    String query = "DELETE FROM conta_corrente WHERE id_conta = ?";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {

      ps.setInt(1, idConta);
      int rowsAffected = ps.executeUpdate();

      return rowsAffected > 0; // Retorna true se excluiu ao menos um registro
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao excluir conta corrente: " + e.getMessage(), e);
    }
  }
}
