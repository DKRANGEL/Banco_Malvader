/**
 * Controlador responsável por gerenciar as operações relacionadas aos funcionários no sistema Banco
 * Malvader.
 *
 * <p>Este controlador fornece métodos para adicionar, atualizar, consultar e remover informações de
 * funcionários, além de gerenciar permissões e funções atribuídas a eles.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.Controller;

import com.bancomalvader.DAO.FuncionarioDAO;
import com.bancomalvader.Model.Funcionario;

public class FuncionarioController {

  public Funcionario buscarFuncionarioPorCodigo(String codigoFuncionario) {
    try {
      FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
      return funcionarioDAO.buscarFuncionarioPorCodigo(codigoFuncionario);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Erro ao buscar funcionário por código: " + e.getMessage(), e);
    }
  }
}
