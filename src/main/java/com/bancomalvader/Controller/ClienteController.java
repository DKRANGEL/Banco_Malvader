package com.bancomalvader.Controller;

import com.bancomalvader.DAO.ClienteDAO;
import com.bancomalvader.Model.Cliente;

public class ClienteController {

  private final ClienteDAO clienteDAO;

  public ClienteController() {
    this.clienteDAO = new ClienteDAO();
  }

  public Cliente buscarClientePorCPF(String cpf) {
    try {
      return clienteDAO.buscarClientePorCPF(cpf);
    } catch (Exception e) {
      throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage(), e);
    }
  }
}
