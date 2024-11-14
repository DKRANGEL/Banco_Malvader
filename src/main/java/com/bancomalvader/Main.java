package com.bancomalvader;

import com.bancomalvader.View.LoginView;
import javax.swing.SwingUtilities;

public class Main {
  public static void main(String[] args) {
    // Inicializa o Swing na thread correta
    SwingUtilities.invokeLater(
        () -> {
          // Cria uma instância da tela de login
          LoginView loginView = new LoginView();
          // Torna a tela de login visível
          loginView.setVisible(true);
        });
  }
}
