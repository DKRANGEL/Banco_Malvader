/**
 * Classe de modelo que representa uma conta bancária no sistema Banco Malvader.
 *
 * <p>Fornece atributos e métodos básicos para manipular informações como número da conta, saldo,
 * titular, e operações financeiras. Serve como base para contas específicas como corrente e
 * poupança.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.Model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conta {
  private int id;
  private String numeroConta;
  private String agencia;
  private double saldo;
  private String tipoConta;
  private int idCliente;

  private String nomeCliente;
  private String cpfCliente;

  private Double limite;
  private String dataVencimento;

  public Conta(
      int id, String numeroConta, String agencia, double saldo, String tipoConta, int idCliente) {
    this.id = id;
    this.numeroConta = numeroConta;
    this.agencia = agencia;
    this.saldo = saldo;
    this.tipoConta = tipoConta;
    this.idCliente = idCliente;
  }

  // Classe Conta.java
  public void setLimite(BigDecimal limite) {}

  public void setDataVencimento(String dataVencimento) {
    this.dataVencimento = dataVencimento;
  }
}
