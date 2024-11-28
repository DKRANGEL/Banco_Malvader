/**
 * Classe responsável pela interface gráfica para consulta de informações de funcionários no sistema
 * Banco Malvader.
 *
 * <p>Permite a busca e visualização de dados dos funcionários, como nome, cargo e permissões
 * atribuídas.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.View;

import com.bancomalvader.Controller.FuncionarioController;
import com.bancomalvader.Model.Funcionario;
import com.bancomalvader.Util.RoundedButton;
import java.awt.*;
import javax.swing.*;

public class ConsultaFuncionarioView extends JFrame {

  public ConsultaFuncionarioView(Funcionario funcionario, String codigoFuncionario) {
    // Configurações da janela
    setTitle("Consulta de Funcionário");
    setSize(600, 650);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    getContentPane().setBackground(new Color(30, 30, 30));
    setLayout(new BorderLayout());

    // Cabeçalho
    JPanel headerPanel = new JPanel();
    headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
    headerPanel.setBackground(Color.BLACK);

    JLabel headerLabel = new JLabel("Detalhes do Funcionário", SwingConstants.CENTER);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerLabel.setForeground(new Color(255, 69, 0));
    headerPanel.add(headerLabel);

    add(headerPanel, BorderLayout.NORTH);

    // Painel central
    JPanel detailsPanel = new JPanel();
    detailsPanel.setBackground(new Color(45, 45, 45));
    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
    detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Busca os dados do funcionário
    FuncionarioController funcionarioController = new FuncionarioController();
    Funcionario funcionarioDetalhes =
        funcionarioController.buscarFuncionarioPorCodigo(codigoFuncionario);

    if (funcionarioDetalhes != null) {
      addDetail(detailsPanel, "Código do Funcionário:", funcionarioDetalhes.getCodigoFuncionario());
      addDetail(detailsPanel, "Cargo:", funcionarioDetalhes.getCargo());
      addDetail(detailsPanel, "Nome do Funcionário:", funcionarioDetalhes.getNome());
      addDetail(detailsPanel, "CPF do Funcionário:", funcionarioDetalhes.getCpf());
      addDetail(
          detailsPanel, "Data de Nascimento:", funcionarioDetalhes.getDataNascimento().toString());
      addDetail(detailsPanel, "Telefone:", funcionarioDetalhes.getTelefone());
      addDetail(detailsPanel, "Local (Endereço):", funcionarioDetalhes.getEndereco().getLocal());
      addDetail(
          detailsPanel,
          "Número da Casa:",
          String.valueOf(funcionarioDetalhes.getEndereco().getNumeroCasa()));
      addDetail(detailsPanel, "CEP:", funcionarioDetalhes.getEndereco().getCep());
      addDetail(detailsPanel, "Bairro:", funcionarioDetalhes.getEndereco().getBairro());
      addDetail(detailsPanel, "Cidade:", funcionarioDetalhes.getEndereco().getCidade());
      addDetail(detailsPanel, "Estado:", funcionarioDetalhes.getEndereco().getEstado());
    } else {
      JLabel errorLabel = new JLabel("Funcionário não encontrado.", SwingConstants.CENTER);
      errorLabel.setForeground(Color.RED);
      errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
      detailsPanel.add(errorLabel);
    }

    add(detailsPanel, BorderLayout.CENTER);

    Dimension exitButtonSize = new Dimension(94, 45);

    // Botão "Voltar"
    JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
    footerPanel.setBackground(new Color(30, 30, 30));

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

    footerPanel.add(exitButton);
    add(footerPanel, BorderLayout.SOUTH);
  }

  private void addDetail(JPanel panel, String label, String value) {
    JLabel detailLabel = new JLabel(label + " " + value);
    detailLabel.setForeground(Color.WHITE);
    detailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    detailLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    panel.add(detailLabel);
  }
}
