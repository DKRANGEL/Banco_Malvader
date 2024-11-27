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
