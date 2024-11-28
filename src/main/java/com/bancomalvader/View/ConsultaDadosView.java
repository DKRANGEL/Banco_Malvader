/**
 * Classe responsável pela interface gráfica para consulta de dados gerais no sistema Banco
 * Malvader.
 *
 * <p>Permite aos usuários visualizarem informações detalhadas sobre clientes, contas ou outras
 * entidades, de acordo com as permissões disponíveis.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.View;

import com.bancomalvader.Model.Funcionario;
import com.bancomalvader.Util.RoundedButton;
import java.awt.*;
import javax.swing.*;

public final class ConsultaDadosView extends JFrame {

  public ConsultaDadosView(Funcionario funcionario) {
    // Configuração da janela principal
    setTitle("Home");
    setSize(800, 500); // Tamanho exato conforme especificado
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Centraliza a janela na tela
    setUndecorated(false); // Remove borda da janela, se necessário
    setLayout(new BorderLayout());
    getContentPane().setBackground(new Color(30, 30, 30)); // Fundo cinza escuro da janela

    // Cabeçalho com fundo preto e título "MVDR"
    JPanel headerPanel = new JPanel();
    headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
    headerPanel.setBackground(Color.BLACK);

    JLabel headerLabel = new JLabel("MVDR", SwingConstants.CENTER);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 40));
    headerLabel.setForeground(new Color(255, 69, 0)); // Vermelho para "MVDR"
    headerPanel.setLayout(new BorderLayout());
    headerPanel.add(headerLabel, BorderLayout.CENTER);

    add(headerPanel, BorderLayout.NORTH);

    // Painel central com título e botões
    JPanel centerPanel = new JPanel();
    centerPanel.setBackground(new Color(30, 30, 30)); // Mesmo fundo da janela
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

    // Fundo cinza claro para o título e os botões
    JPanel containerPanel = new JPanel();
    containerPanel.setBackground(new Color(45, 45, 45)); // Fundo cinza do container
    containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
    containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem interna

    JLabel loginLabel = new JLabel("Consultar Dados", SwingConstants.CENTER);
    loginLabel.setFont(new Font("Arial", Font.BOLD, 18));
    loginLabel.setForeground(Color.WHITE);
    loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    loginLabel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    containerPanel.add(loginLabel);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaço entre título e botões

    // Configurar tamanho dos botões
    Dimension buttonSize = new Dimension(286, 45);
    Dimension exitButtonSize = new Dimension(94, 45);

    // Botões de login
    JButton consultaContaButton =
        new RoundedButton("Consultar Conta", new Color(240, 66, 11, 70), new Color(255, 69, 0));
    consultaContaButton.setMaximumSize(buttonSize);
    consultaContaButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton consultaFuncionarioButton =
        new RoundedButton(
            "Consultar Funcionário", new Color(240, 66, 11, 70), new Color(255, 69, 0));
    consultaFuncionarioButton.setMaximumSize(buttonSize);
    consultaFuncionarioButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton consultaClienteButton =
        new RoundedButton("Consultar Cliente", new Color(240, 66, 11, 70), new Color(255, 69, 0));
    consultaClienteButton.setMaximumSize(buttonSize);
    consultaClienteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Adicionar ações aos botões
    consultaContaButton.addActionListener(
        e -> {
          String numeroConta =
              JOptionPane.showInputDialog(
                  this,
                  "Digite o número da conta:",
                  "Consultar Conta",
                  JOptionPane.QUESTION_MESSAGE);

          if (numeroConta != null && !numeroConta.trim().isEmpty()) {
            new ConsultaContaView(funcionario, numeroConta).setVisible(true);
            dispose();
          } else {
            JOptionPane.showMessageDialog(
                this, "Número da conta não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
          }
        });

    consultaFuncionarioButton.addActionListener(
        e -> {
          String codigoFuncionario =
              JOptionPane.showInputDialog(
                  this,
                  "Digite o código do funcionário:",
                  "Consultar Funcionário",
                  JOptionPane.QUESTION_MESSAGE);

          if (codigoFuncionario != null && !codigoFuncionario.trim().isEmpty()) {
            new ConsultaFuncionarioView(funcionario, codigoFuncionario).setVisible(true);
            dispose();
          } else {
            JOptionPane.showMessageDialog(
                this,
                "Código do funcionário não pode estar vazio.",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
          }
        });

    consultaClienteButton.addActionListener(
        e -> {
          String cpfCliente =
              JOptionPane.showInputDialog(
                  this,
                  "Digite o CPF do cliente:",
                  "Consultar Cliente",
                  JOptionPane.QUESTION_MESSAGE);

          if (cpfCliente != null && !cpfCliente.trim().isEmpty()) {
            new ConsultaClienteView(cpfCliente).setVisible(true);
          } else {
            JOptionPane.showMessageDialog(
                this, "CPF não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
          }
        });

    containerPanel.add(consultaContaButton);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espaço entre os botões
    containerPanel.add(consultaFuncionarioButton);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
    containerPanel.add(consultaClienteButton);

    centerPanel.add(containerPanel);
    add(centerPanel, BorderLayout.CENTER);

    // Botão "Sair" fora do containerPanel
    JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
    footerPanel.setBackground(new Color(30, 30, 30));

    // Adicionar botão "Sair" logo abaixo do containerPanel
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

    centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    centerPanel.add(exitButton);

    add(centerPanel, BorderLayout.CENTER);
  }
}
