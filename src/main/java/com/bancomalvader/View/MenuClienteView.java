/**
 * Classe responsável pela interface gráfica do menu de operações para clientes no sistema Banco
 * Malvader.
 *
 * <p>Permite acesso a funcionalidades específicas voltadas para clientes, como consulta de contas,
 * realização de operações bancárias e atualização de dados pessoais.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.View;

import com.bancomalvader.Model.Usuario;
import com.bancomalvader.Util.RoundedButton;
import java.awt.*;
import javax.swing.*;

public class MenuClienteView extends JFrame {

  public MenuClienteView(Usuario usuario) {
    setTitle("Bem-vindo, " + usuario.getNome());
    setSize(800, 927);
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
    centerPanel.setBackground(new Color(45, 45, 45));
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

    JLabel welcomeLabel = new JLabel("Bem-vindo, " + usuario.getNome() + "!");
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
    welcomeLabel.setForeground(Color.WHITE);
    welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    centerPanel.add(welcomeLabel);

    centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    // Botões para funcionalidades de cliente
    String[] actions = {"Saldo", "Depósito", "Saque", "Extrato", "Consultar Limite", "Sair"};

    for (String action : actions) {
      boolean isExitButton = action.equalsIgnoreCase("Sair");
      RoundedButton button =
          new RoundedButton(
              action, isExitButton ? new Color(109, 6, 6) : new Color(240, 66, 11), Color.WHITE);
      button.setPreferredSize(isExitButton ? new Dimension(94, 45) : new Dimension(262, 45));
      button.setAlignmentX(Component.CENTER_ALIGNMENT);
      button.setPreferredSize(new Dimension(170, 45));

      // Adicione ação ao botão
      button.addActionListener(e -> handleButtonClick(action, usuario));
      centerPanel.add(button);
      centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    add(centerPanel, BorderLayout.CENTER);
  }

  private void handleButtonClick(String action, Usuario usuario) {
    switch (action) {
      case "Saldo":
        JOptionPane.showMessageDialog(this, "Saldo atual: ...");
        break;
      case "Depósito":
        JOptionPane.showMessageDialog(this, "Tela de depósito.");
        break;
      case "Saque":
        JOptionPane.showMessageDialog(this, "Tela de saque.");
        break;
      case "Extrato":
        JOptionPane.showMessageDialog(this, "Exibindo extrato...");
        break;
      case "Consultar Limite":
        JOptionPane.showMessageDialog(this, "Limite disponível: ...");
        break;
      case "Sair":
        JOptionPane.showMessageDialog(this, "Saindo...");
        dispose();
        new LoginView("Cliente").setVisible(true);
        break;
      default:
        JOptionPane.showMessageDialog(this, "Ação desconhecida: " + action);
    }
  }
}
