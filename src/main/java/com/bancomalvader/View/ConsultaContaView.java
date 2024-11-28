/**
 * Classe responsável pela interface gráfica para consulta de informações de clientes no sistema
 * Banco Malvader.
 *
 * <p>Permite a visualização e busca de dados dos clientes, exibindo detalhes relevantes de forma
 * organizada.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.View;

import com.bancomalvader.Controller.ContaController;
import com.bancomalvader.Model.Conta;
import com.bancomalvader.Model.Funcionario;
import com.bancomalvader.Util.RoundedButton;
import java.awt.*;
import javax.swing.*;

public class ConsultaContaView extends JFrame {

  public ConsultaContaView(Funcionario funcionario, String numeroConta) {
    // Configurações da janela
    setTitle("Consulta de Conta");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    getContentPane().setBackground(new Color(30, 30, 30));
    setLayout(new BorderLayout());

    // Cabeçalho
    JPanel headerPanel = new JPanel();
    headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
    headerPanel.setBackground(Color.BLACK);

    JLabel headerLabel = new JLabel("Detalhes da Conta", SwingConstants.CENTER);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerLabel.setForeground(new Color(255, 69, 0));
    headerPanel.add(headerLabel);

    add(headerPanel, BorderLayout.NORTH);

    // Painel central
    JPanel detailsPanel = new JPanel();
    detailsPanel.setBackground(new Color(45, 45, 45));
    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
    detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Busca os dados da conta
    ContaController contaController = new ContaController();
    Conta conta = contaController.buscarContaPorNumero(numeroConta);

    if (conta != null) {
      addDetail(detailsPanel, "Tipo de Conta:", conta.getTipoConta());
      addDetail(detailsPanel, "Nome do Cliente:", conta.getNomeCliente());
      addDetail(detailsPanel, "CPF do Cliente:", conta.getCpfCliente());
      addDetail(detailsPanel, "Saldo da Conta:", String.format("R$ %.2f", conta.getSaldo()));

      if ("CORRENTE".equalsIgnoreCase(conta.getTipoConta())) {
        addDetail(detailsPanel, "Limite Disponível:", String.format("R$ %.2f", conta.getLimite()));
        addDetail(detailsPanel, "Data de Vencimento:", conta.getDataVencimento().toString());
      }
    } else {
      JLabel errorLabel = new JLabel("Conta não encontrada.", SwingConstants.CENTER);
      errorLabel.setForeground(Color.RED);
      errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
      detailsPanel.add(errorLabel);
    }

    add(detailsPanel, BorderLayout.CENTER);

    Dimension exitButtonSize = new Dimension(94, 45);

    // Botão "Sair" fora do containerPanel
    JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
    footerPanel.setBackground(new Color(30, 30, 30));

    RoundedButton exitButton =
        new RoundedButton("Voltar", new Color(109, 6, 6, 25), new Color(161, 61, 61));
    exitButton.setForeground(new Color(161, 61, 61));
    exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    exitButton.setMaximumSize(exitButtonSize);

    exitButton.addActionListener(
        e -> {
          new MenuFuncionarioView(funcionario).setVisible(true);
          dispose();
        });

    footerPanel.add(exitButton);
    add(footerPanel, BorderLayout.SOUTH);
  }

  private void addDetail(JPanel panel, String label, String value) {
    JLabel detailLabel = new JLabel(label + " " + value);
    detailLabel.setForeground(Color.WHITE);
    detailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    detailLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    panel.add(detailLabel);
  }
}
