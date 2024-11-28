/**
 * Classe de modelo que representa um cliente no sistema Banco Malvader.
 *
 * <p>Contém informações pessoais como nome, CPF, endereço e outros dados relevantes para o
 * gerenciamento de clientes no sistema.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
  private int id;
  private int idUsuario;
  private Usuario usuario; // Composição com a classe Usuario
  private Endereco endereco; // Composição com a classe Endereco

  // Construtor específico para uso geral
  public Cliente(int idCliente, int idUsuario) {
    this.id = idCliente;
    this.idUsuario = idUsuario;
  }

  // Construtor específico para a View
  public Cliente(int idCliente, Usuario usuario, Endereco endereco) {
    this.id = idCliente;
    this.usuario = usuario;
    this.endereco = endereco;
  }
}
