/**
 * Controlador responsável por gerenciar as operações relacionadas às contas no sistema Banco
 * Malvader.
 *
 * <p>Este controlador fornece métodos para criar, atualizar, consultar e excluir contas bancárias,
 * além de realizar operações como depósitos, saques e transferências.
 *
 * @author [Seu Nome]
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.Controller;

import com.bancomalvader.DAO.*;
import com.bancomalvader.Model.Cliente;
import com.bancomalvader.Model.Conta;
import java.math.BigDecimal;
import java.sql.Date;

public class ContaController {

  public boolean cadastrarConta(
      int idUsuario,
      String numeroConta,
      String agencia,
      String tipoConta,
      String limite,
      Date dataVencimento) {
    try {
      ClienteDAO clienteDAO = new ClienteDAO();
      ContaDAO contaDAO = new ContaDAO();
      ContaCorrenteDAO contaCorrenteDAO = new ContaCorrenteDAO();
      ContaPoupancaDAO contaPoupancaDAO = new ContaPoupancaDAO();

      Integer idCliente = clienteDAO.buscarIdClientePorUsuario(idUsuario);
      if (idCliente == null) {
        throw new IllegalArgumentException("Cliente não encontrado.");
      }

      Conta conta = contaDAO.inserirConta(numeroConta, agencia, tipoConta, idCliente);
      if (conta == null || idCliente <= 0) {
        System.err.println("Erro: ID da conta não foi gerado ou é inválido.");
        throw new IllegalArgumentException("Erro ao cadastrar conta: ID não gerado.");
      } else {
        System.out.println("Conta cadastrada com sucesso. ID: " + idCliente);
      }

      if (tipoConta.equalsIgnoreCase("CORRENTE")) {
        if (limite == null || limite.isEmpty() || dataVencimento == null) {
          throw new IllegalArgumentException("Limite e data de vencimento são obrigatórios.");
        }
        contaCorrenteDAO.inserirContaCorrente(new BigDecimal(limite), dataVencimento, idCliente);
      } else if (tipoConta.equalsIgnoreCase("POUPANCA")) {
        contaPoupancaDAO.inserirContaPoupanca(idCliente);
      } else {
        throw new IllegalArgumentException("Tipo de conta inválido: " + tipoConta);
      }

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public Conta buscarContaPorNumero(String numeroConta) {
    try {
      ContaDAO contaDAO = new ContaDAO();
      return contaDAO.buscarContaPorNumero(numeroConta);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Erro ao buscar conta: " + e.getMessage(), e);
    }
  }

  public boolean encerrarConta(String nomeCliente, String numeroConta) {
    try {
      // DAOs necessários
      UsuarioDAO usuarioDAO = new UsuarioDAO();

      // Busca a conta pelo nome do cliente e número da conta para localizar o usuário
      ContaDAO contaDAO = new ContaDAO();
      Conta conta = contaDAO.buscarContaPorNomeENumero(nomeCliente, numeroConta);
      if (conta == null) {
        throw new IllegalArgumentException("Conta não encontrada.");
      }

      // Busca o cliente associado à conta
      ClienteDAO clienteDAO = new ClienteDAO();
      Cliente cliente = clienteDAO.buscarClientePorId(conta.getIdCliente());
      if (cliente == null) {
        throw new IllegalArgumentException("Cliente não encontrado para a conta especificada.");
      }

      // Exclui o usuário principal, o restante será tratado pelo ON DELETE CASCADE
      usuarioDAO.excluirUsuario(cliente.getIdUsuario());

      return true; // Exclusão bem-sucedida
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Exclusão falhou
    }
  }
}
