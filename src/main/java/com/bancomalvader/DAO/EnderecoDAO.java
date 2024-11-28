/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência relacionadas aos
 * endereços no sistema Banco Malvader.
 *
 * <p>Fornece métodos para inserir, atualizar, excluir e consultar informações de endereços no banco
 * de dados.
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

public class EnderecoDAO {
  public void inserirEndereco(
      String cep,
      String local,
      int numeroCasa,
      String bairro,
      String cidade,
      String estado,
      int idUsuario) {
    String query =
        "INSERT INTO endereco (cep, local, numero_casa, bairro, cidade, estado, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, cep);
      ps.setString(2, local);
      ps.setInt(3, numeroCasa);
      ps.setString(4, bairro);
      ps.setString(5, cidade);
      ps.setString(6, estado);
      ps.setInt(7, idUsuario);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao inserir endereço: " + e.getMessage(), e);
    }
  }
}
