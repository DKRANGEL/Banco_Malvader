/**
 * Classe utilitária responsável pela validação de permissões administrativas no sistema Banco
 * Malvader.
 *
 * <p>Fornece métodos para verificar se um usuário tem privilégios administrativos e garantir a
 * segurança de operações restritas.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.Util;

import javax.swing.*;

public class AdminValidation {

  private static final String ADMIN_PASSWORD = "admin";

  /**
   * Exibe um modal solicitando a senha de administrador.
   *
   * @return true se a senha for válida, false caso contrário.
   */
  public static boolean validarSenhaAdministrador() {
    JPasswordField passwordField = new JPasswordField();
    Object[] message = {"Senha de administrador:", passwordField};

    int option =
        JOptionPane.showConfirmDialog(
            null,
            message,
            "Acesso Restrito",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);

    if (option == JOptionPane.OK_OPTION) {
      String adminPassword = new String(passwordField.getPassword());
      if (ADMIN_PASSWORD.equals(adminPassword)) {
        return true; // Senha válida
      } else {
        JOptionPane.showMessageDialog(
            null, "Senha incorreta! Acesso negado.", "Erro", JOptionPane.ERROR_MESSAGE);
      }
    }
    return false; // Senha inválida ou operação cancelada
  }
}
