/**
 * Controlador responsável por gerenciar as operações relacionadas aos usuários do sistema Banco
 * Malvader.
 *
 * <p>Este controlador fornece métodos para criar, atualizar, autenticar e excluir usuários, além de
 * gerenciar perfis e tipos de usuários no sistema.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.Controller;

import com.bancomalvader.DAO.ClienteDAO;
import com.bancomalvader.DAO.EnderecoDAO;
import com.bancomalvader.DAO.FuncionarioDAO;
import com.bancomalvader.DAO.UsuarioDAO;
import com.bancomalvader.Model.Funcionario;
import com.bancomalvader.Model.TipoUsuario;
import java.util.Date;

public class UsuarioController {
  private ClienteDAO clienteDAO = new ClienteDAO();
  private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

  /**
   * Cadastra um cliente ou funcionário baseado no tipo de usuário.
   *
   * @param nome Nome do usuário.
   * @param cpf CPF do usuário.
   * @param dataNascimento Data de nascimento.
   * @param telefone Telefone do usuário.
   * @param tipoUsuario Tipo do usuário (CLIENTE ou FUNCIONARIO).
   * @param senha Senha do usuário.
   * @param codigoFuncionario Código do funcionário (somente para FUNCIONARIO).
   * @param cargo Cargo do funcionário (somente para FUNCIONARIO).
   * @return true se o cadastro foi bem-sucedido, false caso contrário.
   */
  public boolean cadastrarUsuario(
      String nome,
      String cpf,
      Date dataNascimento,
      String telefone,
      String tipoUsuario,
      String senha,
      String codigoFuncionario, // Específico para funcionário
      String cargo, // Específico para funcionário
      String cep,
      String local,
      int numeroCasa,
      String bairro,
      String cidade,
      String estado) {
    try {
      // Converte o tipo de usuário para enum
      TipoUsuario tipo = TipoUsuario.valueOf(tipoUsuario.toUpperCase());
      UsuarioDAO usuarioDAO = new UsuarioDAO();
      EnderecoDAO enderecoDAO = new EnderecoDAO();

      // Verifica se o CPF já existe
      if (usuarioDAO.verificarCpfExiste(cpf)) {
        throw new IllegalArgumentException("O CPF já está cadastrado no sistema.");
      }

      // Insere o usuário e obtém o ID gerado
      int idUsuario =
          usuarioDAO.inserirUsuario(
              nome, cpf, new java.sql.Date(dataNascimento.getTime()), telefone, tipoUsuario, senha);

      // Cadastrar o endereço associado ao usuário
      enderecoDAO.inserirEndereco(cep, local, numeroCasa, bairro, cidade, estado, idUsuario);

      if (tipo == TipoUsuario.CLIENTE) {
        // Cadastra o cliente associado ao usuário
        clienteDAO.inserirCliente(idUsuario);

      } else if (tipo == TipoUsuario.FUNCIONARIO) {
        // Valida se os campos de funcionário foram preenchidos
        if (codigoFuncionario == null || cargo == null) {
          throw new IllegalArgumentException("Código e cargo são obrigatórios para Funcionários.");
        }

        // Cria e cadastra o funcionário
        Funcionario funcionario =
            new Funcionario(
                idUsuario,
                nome,
                cpf,
                new java.sql.Date(dataNascimento.getTime()),
                telefone,
                tipo,
                senha,
                codigoFuncionario,
                cargo);

        funcionarioDAO.inserirFuncionario(funcionario, idUsuario);
      }

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean excluirCliente(int idUsuario) {
    try {
      // Primeiro, exclui o cliente na tabela cliente
      ClienteDAO clienteDAO = new ClienteDAO();
      clienteDAO.excluirCliente(idUsuario);

      // Depois, exclui o usuário associado na tabela usuario
      UsuarioDAO usuarioDAO = new UsuarioDAO();
      usuarioDAO.excluirUsuario(idUsuario);

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Retorna false caso haja algum erro na exclusão
    }
  }
}
