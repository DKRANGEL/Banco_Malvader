/**
 * Classe utilitária que define um botão com bordas arredondadas para a interface gráfica do sistema
 * Banco Malvader.
 *
 * <p>Este componente personalizado melhora a estética e a usabilidade do sistema, permitindo
 * estilização adicional e comportamento consistente.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.Util;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class RoundedButton extends JButton {

  private Color borderColor;

  public RoundedButton(String text, Color backgroundColor, Color borderColor) {
    super(text);
    this.borderColor = borderColor; // Cor da borda
    setFont(new Font("Arial", Font.BOLD, 14));
    setForeground(new Color(240, 66, 0));
    setBackground(backgroundColor); // Cor de fundo
    setFocusPainted(false);
    setContentAreaFilled(false);
    setOpaque(false);
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Desenha o fundo arredondado
    g2.setColor(getBackground());
    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));

    super.paintComponent(g);
    g2.dispose();
  }

  @Override
  protected void paintBorder(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Desenha a borda arredondada
    g2.setColor(borderColor);
    g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));

    g2.dispose();
  }
}
