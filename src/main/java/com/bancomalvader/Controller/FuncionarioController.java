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
