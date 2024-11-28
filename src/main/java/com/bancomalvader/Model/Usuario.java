/**
 * Classe de modelo que representa um usuário no sistema Banco Malvader.
 *
 * <p>Contém informações como nome, CPF, senha, tipo de usuário e outros dados relevantes para a
 * autenticação e identificação no sistema.
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
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
  private int id; // Certifique-se de que o atributo está definido
  private String nome;
  private String cpf;
  private java.sql.Date dataNascimento;
  private String telefone;
  private String tipoUsuario;
  private String senha;
}
