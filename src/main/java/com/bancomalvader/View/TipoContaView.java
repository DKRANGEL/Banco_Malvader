/**
 * Classe responsável pela interface gráfica para seleção do tipo de conta no sistema Banco
 * Malvader.
 *
 * <p>Permite aos usuários escolher entre diferentes tipos de contas, como corrente ou poupança,
 * durante o processo de abertura de conta.
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

public final class TipoContaView extends JFrame {

  public TipoContaView(Funcionario funcionario) {
    // Configuração da janela principal
    setTitle("Home");
    setSize(800, 481); // Tamanho exato conforme especificado
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

    JLabel loginLabel = new JLabel("Selecione o Tipo de Conta", SwingConstants.CENTER);
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
    JButton contaCorrenteButton =
        new RoundedButton("CC - Conta Corrente", new Color(240, 66, 11, 70), new Color(255, 69, 0));
    contaCorrenteButton.setMaximumSize(buttonSize);
    contaCorrenteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton contaPoupancaButton =
        new RoundedButton("CP - Conta Poupança", new Color(240, 66, 11, 70), new Color(255, 69, 0));
    contaPoupancaButton.setMaximumSize(buttonSize);
    contaPoupancaButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Adicionar ações aos botões
    contaCorrenteButton.addActionListener(
        e -> {
          AberturaContaView aberturaContaView = new AberturaContaView(funcionario, "Corrente");
          aberturaContaView.setVisible(true);
          dispose();
        });

    contaPoupancaButton.addActionListener(
        e -> {
          AberturaContaView aberturaContaView = new AberturaContaView(funcionario, "Poupanca");
          aberturaContaView.setVisible(true);
          dispose();
        });

    containerPanel.add(contaCorrenteButton);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espaço entre os botões
    containerPanel.add(contaPoupancaButton);

    centerPanel.add(containerPanel);
    add(centerPanel, BorderLayout.CENTER);

    // Botão "Sair" fora do containerPanel
    JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
    footerPanel.setBackground(new Color(30, 30, 30));

    // Adicionar botão "Sair" logo abaixo do containerPanel
    RoundedButton exitButton =
        new RoundedButton("Sair", new Color(109, 6, 6, 25), new Color(161, 61, 61));
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
