package com.bancomalvader.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseConnection {
  private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

  private static final String URL = "jdbc:mysql://localhost:3306/banco_malvader";
  private static final String USER = "root";
  private static final String PASSWORD = "";

  /**
   * Obtém uma conexão com o banco de dados.
   *
   * @return Objeto Connection para realizar operações no banco de dados
   */
  public static Connection getConnection() {
    try {
      Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
      logger.info("Conexão com o banco de dados estabelecida com sucesso.");
      return connection;
    } catch (SQLException e) {

      throw new RuntimeException("Erro ao conectar com o banco de dados" + e.getMessage(), e);
    }
  }

  /**
   * Fecha a conexão com o banco de dados.
   *
   * @param connection A conexão a ser fechada
   */
  public static void closeConnection(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
        logger.info("Conexão com o banco de dados fechada com sucesso.");
      } catch (SQLException e) {

        throw new RuntimeException(
            "Erro ao fechar conexão com o banco de dados" + e.getMessage(), e);
      }
    }
  }
}
