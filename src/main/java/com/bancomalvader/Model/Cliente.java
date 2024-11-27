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

  public Cliente(int idCliente, int id, Usuario usuario, Endereco endereco) {}
}
