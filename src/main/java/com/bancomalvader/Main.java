package com.bancomalvader;

import com.bancomalvader.View.MainMenuView;
import javax.swing.SwingUtilities;

public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        () -> {
          MainMenuView mainMenuView = new MainMenuView();
          mainMenuView.setVisible(true);
        });
  }
}
