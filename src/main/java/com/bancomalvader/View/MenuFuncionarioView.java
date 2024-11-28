/**
 * Classe responsável pela interface gráfica do menu de operações para funcionários no sistema Banco
 * Malvader.
 *
 * <p>Permite acesso a funcionalidades administrativas e de gerenciamento, como cadastro de
 * clientes, manipulação de contas e consulta de dados de usuários.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.View;

import com.bancomalvader.Controller.ContaController;
import com.bancomalvader.Model.Funcionario;
import com.bancomalvader.Util.AdminValidation;
import com.bancomalvader.Util.RoundedButton;
import java.awt.*;
import javax.swing.*;

public class MenuFuncionarioView extends JFrame {

  public MenuFuncionarioView(Funcionario funcionario) {
    setTitle("Bem-vindo, " + funcionario.getNome());
    setSize(800, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    getContentPane().setBackground(new Color(30, 30, 30));
    setLayout(new BorderLayout());

    // Cabeçalho
    JPanel headerPanel = new JPanel();
    headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
    headerPanel.setBackground(Color.BLACK);

    JLabel headerLabel = new JLabel("MVDR", SwingConstants.CENTER);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 40));
    headerLabel.setForeground(new Color(255, 69, 0));
    headerPanel.add(headerLabel);

    add(headerPanel, BorderLayout.NORTH);

    // Painel central
    JPanel centerPanel = new JPanel();
    centerPanel.setBackground(new Color(30, 30, 30));
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Ajuste para centralizar

    // Painel cinza claro atrás dos botões
    JPanel containerPanel = new JPanel();
    containerPanel.setBackground(new Color(45, 45, 45));
    containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
    containerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Margens internas
    containerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel titleLabel = new JLabel("Escolha uma função", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 22)); // Tamanho maior para o título
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    containerPanel.add(titleLabel);
    containerPanel.add(
        Box.createRigidArea(new Dimension(0, 20))); // Espaço entre o título e os botões

    // Ajustando os tamanhos dos botões
    Dimension buttonSize = new Dimension(300, 50); // Botões maiores
    String[] actions = {
      "Abertura de conta",
      "Encerramento de conta",
      "Consultar dados",
      "Alterar dados",
      "Cadastrar funcionário",
      "Gerar relatório"
    };

    for (String action : actions) {
      RoundedButton button =
          new RoundedButton(action, new Color(240, 66, 11, 70), new Color(240, 66, 0));
      button.setPreferredSize(buttonSize);
      button.setMaximumSize(buttonSize); // Garante que o botão respeite o tamanho
      button.setAlignmentX(Component.CENTER_ALIGNMENT);

      // Adiciona a ação ao botão
      button.addActionListener(e -> handleButtonClick(action, funcionario));
      containerPanel.add(button);
      containerPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espaço entre os botões
    }

    centerPanel.add(containerPanel);
    add(centerPanel, BorderLayout.CENTER);

    // Botão de Sair (fora do painel cinza claro)
    JPanel footerPanel = new JPanel();
    footerPanel.setBackground(new Color(30, 30, 30));
    footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15)); // Centraliza o botão

    RoundedButton exitButton =
        new RoundedButton("Sair", new Color(109, 6, 6, 25), new Color(161, 61, 61));
    exitButton.setForeground(new Color(161, 61, 61));
    exitButton.setPreferredSize(new Dimension(94, 45));
    exitButton.addActionListener(
        e -> {
          dispose();
          new LoginView("Funcionário").setVisible(true);
        });

    footerPanel.add(exitButton);
    add(footerPanel, BorderLayout.SOUTH);
  }

  private void handleButtonClick(String action, Funcionario funcionario) {
    switch (action) {
      case "Abertura de conta":
        TipoContaView tipoContaView = new TipoContaView(funcionario);
        tipoContaView.setVisible(true);
        dispose(); // Fecha a tela atual
        break;
      case "Encerramento de conta":
        if (AdminValidation.validarSenhaAdministrador()) {
          // Captura nome do cliente e número da conta
          JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
          inputPanel.add(new JLabel("Nome do Cliente:"));
          JTextField nomeClienteField = new JTextField();
          inputPanel.add(nomeClienteField);
          inputPanel.add(new JLabel("Número da Conta:"));
          JTextField numeroContaField = new JTextField();
          inputPanel.add(numeroContaField);

          int result =
              JOptionPane.showConfirmDialog(
                  this,
                  inputPanel,
                  "Encerrar Conta",
                  JOptionPane.OK_CANCEL_OPTION,
                  JOptionPane.PLAIN_MESSAGE);

          if (result == JOptionPane.OK_OPTION) {
            String nomeCliente = nomeClienteField.getText().trim();
            String numeroConta = numeroContaField.getText().trim();

            if (nomeCliente.isEmpty() || numeroConta.isEmpty()) {
              JOptionPane.showMessageDialog(
                  this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
              return;
            }

            // Exclui conta, cliente e usuário associados
            try {
              ContaController contaController = new ContaController();
              boolean sucesso = contaController.encerrarConta(nomeCliente, numeroConta);

              if (sucesso) {
                JOptionPane.showMessageDialog(
                    this,
                    "Conta encerrada com sucesso.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
              } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Erro ao encerrar conta. Verifique os dados e tente novamente.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
              }
            } catch (Exception e) {
              JOptionPane.showMessageDialog(
                  this,
                  "Erro durante o encerramento: " + e.getMessage(),
                  "Erro",
                  JOptionPane.ERROR_MESSAGE);
              e.printStackTrace();
            }
          }
        }
        break;

      case "Consultar dados":
        ConsultaDadosView consultaDadosView = new ConsultaDadosView(funcionario);
        consultaDadosView.setVisible(true);
        dispose();
        break;
      case "Alterar dados":
        JOptionPane.showMessageDialog(this, "Alteração de dados em desenvolvimento.");
        break;
      case "Cadastrar funcionário":
        JOptionPane.showMessageDialog(this, "Cadastro de funcionário em desenvolvimento.");
        break;
      case "Gerar relatório":
        JOptionPane.showMessageDialog(this, "Geração de relatório em desenvolvimento.");
        break;
      default:
        JOptionPane.showMessageDialog(this, "Ação desconhecida: " + action);
    }
  }
}
