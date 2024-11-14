package com.bancomalvader.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginView extends JFrame {
  private JTextField userField;
  private JPasswordField passwordField;
  private JButton loginButton;
  private JButton exitButton;

  public LoginView() {
    // Configurações da janela principal
    setTitle("Banco Malvader - Login");
    setSize(400, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Configuração do layout
    setLayout(new BorderLayout());

    // Painel superior para as instruções
    JPanel topPanel = new JPanel();
    topPanel.add(new JLabel("Selecione o tipo de acesso e insira sua senha"));

    // Painel central para os campos de entrada
    JPanel centerPanel = new JPanel(new GridLayout(3, 2));
    centerPanel.add(new JLabel("Usuário:"));
    userField = new JTextField();
    centerPanel.add(userField);

    centerPanel.add(new JLabel("Senha:"));
    passwordField = new JPasswordField();
    centerPanel.add(passwordField);

    // Painel inferior para os botões
    JPanel bottomPanel = new JPanel();
    loginButton = new JButton("Login");
    exitButton = new JButton("Sair");

    bottomPanel.add(loginButton);
    bottomPanel.add(exitButton);

    // Adiciona os painéis à janela
    add(topPanel, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);

    // Ações dos botões
    loginButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String user = userField.getText();
            String password = new String(passwordField.getPassword());

            // Aqui é onde você implementa a lógica de autenticação
            if (autenticar(user, password)) {
              JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
              // Abrir o próximo menu de acordo com o tipo de usuário
              abrirMenuPrincipal();
            } else {
              JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos.");
            }
          }
        });

    exitButton.addActionListener(e -> System.exit(0));
  }

  // Método de autenticação (exemplo)
  private boolean autenticar(String user, String password) {
    // Implemente a lógica de autenticação aqui
    return true; // Exemplo simples, você deve substituir por lógica real
  }

  // Método para abrir o menu principal após login
  private void abrirMenuPrincipal() {
    // Implemente a navegação para o menu principal aqui
    dispose(); // Fecha a tela de login
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        () -> {
          LoginView login = new LoginView();
          login.setVisible(true);
        });
  }
}
