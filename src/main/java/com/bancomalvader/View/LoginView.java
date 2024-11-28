/**
 * Classe responsável pela interface gráfica de login do sistema Banco Malvader.
 * <p>
 * Permite a autenticação dos usuários, solicitando credenciais como nome de usuário e senha,
 * e direciona para o menu principal após validação bem-sucedida.
 * </p>
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.View;

import com.bancomalvader.DAO.ClienteDAO;
import com.bancomalvader.DAO.FuncionarioDAO;
import com.bancomalvader.Model.Funcionario;
import com.bancomalvader.Model.Usuario;
import com.bancomalvader.Util.AdminValidation;
import com.bancomalvader.Util.RoundedButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public final class LoginView extends JFrame {

  private CustomTextField usernameField;
  private CustomPasswordField passwordField;
  private final String ADMIN_PASSWORD = "admin";

  public LoginView(String userType) {
    // Configuração da janela principal
    setTitle("Login de " + userType);
    setSize(800, 481);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Centraliza a janela
    setLayout(new BorderLayout());
    getContentPane().setBackground(new Color(30, 30, 30));

    // Cabeçalho
    JPanel headerPanel = new JPanel();
    headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
    headerPanel.setBackground(Color.BLACK);

    JLabel headerLabel = new JLabel("MVDR", SwingConstants.CENTER);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 40));
    headerLabel.setForeground(new Color(255, 69, 0));
    headerPanel.setLayout(new BorderLayout());
    headerPanel.add(headerLabel, BorderLayout.CENTER);

    add(headerPanel, BorderLayout.NORTH);

    // Painel central
    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(new Color(30, 30, 30));

    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(new Color(45, 45, 45));
    formPanel.setPreferredSize(new Dimension(670, 386));
    formPanel.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));
    formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    StringBuilder pontos = new StringBuilder();
    for (int i = 0; i < 100; i++) {
      pontos.append(".");
    }

    JLabel spaceLabel = new JLabel(pontos.toString(), SwingConstants.RIGHT);
    spaceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    spaceLabel.setForeground(new Color(45, 45, 45));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    formPanel.add(spaceLabel, gbc);

    // Título
    JLabel titleLabel = new JLabel("Login de " + userType, SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    titleLabel.setForeground(Color.WHITE);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    formPanel.add(titleLabel, gbc);

    // Campo de usuário
    JLabel usernameLabel = new JLabel("Usuário:");
    usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
    usernameLabel.setForeground(new Color(224, 224, 224));
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.WEST;
    formPanel.add(usernameLabel, gbc);

    usernameField = new CustomTextField();
    usernameField.setPreferredSize(new Dimension(542, 36));
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    formPanel.add(usernameField, gbc);

    // Campo de senha
    JLabel passwordLabel = new JLabel("Senha:");
    passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
    passwordLabel.setForeground(new Color(224, 224, 224));
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.WEST;
    formPanel.add(passwordLabel, gbc);

    passwordField = new CustomPasswordField();
    passwordField.setPreferredSize(new Dimension(542, 36));
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    formPanel.add(passwordField, gbc);

    // Adiciona o botão "Cadastre-se" apenas para Funcionário
    if (userType.equalsIgnoreCase("Funcionário")) {
      JLabel registerLabel = new JLabel("<html><u>Cadastre-se</u></html>", SwingConstants.LEFT);
      registerLabel.setFont(new Font("Arial", Font.BOLD, 14));
      registerLabel.setForeground(new Color(240, 66, 11));
      registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      registerLabel.addMouseListener(
          new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              if (AdminValidation.validarSenhaAdministrador()) {
                new RegisterView().setVisible(true);
                dispose();
              }
            }
          });

      gbc.gridx = 0;
      gbc.gridy = 5;
      gbc.gridwidth = 2;
      formPanel.add(registerLabel, gbc);
    }

    centerPanel.add(formPanel);
    add(centerPanel, BorderLayout.CENTER);

    // Painel de botões
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setBackground(new Color(30, 30, 30));

    JButton loginButton = new RoundedButton("Entrar", new Color(255, 69, 0), Color.WHITE);
    JButton cancelButton =
        new RoundedButton("Voltar", new Color(109, 6, 6, 25), new Color(161, 61, 61));

    loginButton.setPreferredSize(new Dimension(113, 45));
    loginButton.setForeground(Color.WHITE);
    cancelButton.setPreferredSize(new Dimension(96, 45));
    cancelButton.setForeground(new Color(161, 61, 61));

    // Validação de Login
    loginButton.addActionListener(
        e -> {
          String username = usernameField.getText().trim();
          String password = new String(passwordField.getPassword());

          if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
          }

          try {
            if (userType.equalsIgnoreCase("Funcionário")) {
              FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
              Funcionario funcionario = funcionarioDAO.validarLogin(username, password);
              if (funcionario != null) {
                // Redireciona para a tela principal de funcionários
                MenuFuncionarioView menuFuncionarioView = new MenuFuncionarioView(funcionario);
                menuFuncionarioView.setVisible(true);
                dispose(); // Fecha a tela de login
              } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Usuário ou senha inválidos para Funcionário.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
              }
            } else if (userType.equalsIgnoreCase("Cliente")) {
              ClienteDAO clienteDAO = new ClienteDAO();
              Usuario usuario =
                  clienteDAO.validarLogin(username, password); // Agora retorna um Usuario
              if (usuario != null) {
                // Redireciona para a tela principal de clientes
                MenuClienteView menuClienteView = new MenuClienteView(usuario); // Passa o Usuario
                menuClienteView.setVisible(true);
                dispose(); // Fecha a tela de login
              } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Usuário ou senha inválidos para Cliente.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
              }
            }
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Erro ao validar login: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
          }
        });

    cancelButton.addActionListener(
        e -> {
          MainMenuView mainView = new MainMenuView();
          mainView.setVisible(true);
          dispose(); // Fecha a LoginView atual
        });

    buttonPanel.add(loginButton);
    buttonPanel.add(cancelButton);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  // CustomTextField
  class CustomTextField extends JTextField {
    public CustomTextField() {
      setBackground(new Color(60, 60, 60));
      setForeground(Color.WHITE);
      setCaretColor(Color.WHITE);
      setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
  }

  // CustomPasswordField
  class CustomPasswordField extends JPasswordField {
    public CustomPasswordField() {
      setBackground(new Color(60, 60, 60));
      setForeground(Color.WHITE);
      setCaretColor(Color.WHITE);
      setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
  }
}
