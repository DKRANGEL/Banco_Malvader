/**
 * Classe de modelo que representa um endereço no sistema Banco Malvader.
 *
 * <p>Contém informações como rua, número, bairro, cidade, estado e CEP, que são usadas para
 * associar clientes e funcionários a locais específicos.
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
public class Endereco {
  private int idEndereco; // ID do endereço
  private String cep; // CEP do endereço
  private String local; // Nome da rua ou local
  private int numeroCasa; // Número da casa
  private String bairro; // Bairro
  private String cidade; // Cidade
  private String estado; // Estado

  // Construtor sem ID, para casos de inserção
  public Endereco(
      String cep, int local, String numeroCasa, String bairro, String cidade, String estado) {
    this.cep = cep;
    this.local = String.valueOf(local);
    this.numeroCasa = Integer.parseInt(numeroCasa);
    this.bairro = bairro;
    this.cidade = cidade;
    this.estado = estado;
  }

  public Endereco(
      String cep, String local, int numeroCasa, String bairro, String cidade, String estado) {
    this.cep = cep;
    this.local = local;
    this.numeroCasa = numeroCasa;
    this.bairro = bairro;
    this.cidade = cidade;
    this.estado = estado;
  }
}
