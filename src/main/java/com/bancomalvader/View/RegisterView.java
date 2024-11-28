/**
 * Classe responsável pela interface gráfica para registro de novos usuários no sistema Banco
 * Malvader.
 *
 * <p>Permite o cadastro de clientes e funcionários, solicitando informações como nome, CPF, senha e
 * outros dados relevantes. Inclui validações para garantir a consistência dos dados inseridos.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.View;

import com.bancomalvader.Controller.UsuarioController;
import com.bancomalvader.Util.RoundedButton;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public final class RegisterView extends JFrame {

  private CustomTextField codeField, positionField, nameField, localField, numberField;
  private CustomTextField neighborhoodField, cityField;
  private StateTextField stateField;
  private CustomPasswordField passwordField;
  private CustomFormattedField cpfField, phoneField, cepField;
  private CustomDatePicker datePicker;

  public RegisterView() {
    // Configuração da janela principal
    setTitle("Cadastro");
    setSize(900, 800); // Tamanho ajustado
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Centraliza a janela
    getContentPane().setBackground(new Color(30, 30, 30));
    setLayout(new BorderLayout());

    // Cabeçalho
    JPanel headerPanel = new JPanel();
    headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
    headerPanel.setBackground(Color.BLACK);

    JLabel headerLabel = new JLabel("MVDR - Cadastro de Funcionário", SwingConstants.CENTER);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerLabel.setForeground(new Color(255, 69, 0));
    headerPanel.add(headerLabel);

    add(headerPanel, BorderLayout.NORTH);

    // Painel central com GridBagLayout
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(new Color(45, 45, 45));
    formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Criação dos campos
    codeField = new CustomTextField();
    positionField = new CustomTextField();
    nameField = new CustomTextField();
    cpfField = createFormattedField("###.###.###-##");
    phoneField = createFormattedField("(##) #####-####");
    cepField = createFormattedField("#####-###");
    localField = new CustomTextField();
    numberField = new CustomTextField();
    neighborhoodField = new CustomTextField();
    cityField = new CustomTextField();
    stateField = new StateTextField();
    passwordField = new CustomPasswordField();
    datePicker = createCustomDatePicker();

    // Adiciona os campos com labels
    int row = 0;

    // Primeira coluna
    addLabeledField("Código:", codeField, formPanel, gbc, row++, 0);
    addLabeledField("Cargo:", positionField, formPanel, gbc, row++, 0);
    addLabeledField("Nome:", nameField, formPanel, gbc, row++, 0);
    addLabeledField("CPF:", cpfField, formPanel, gbc, row++, 0);
    addLabeledField("Telefone:", phoneField, formPanel, gbc, row++, 0);

    // Segunda coluna
    row = 0;
    addLabeledField("CEP:", cepField, formPanel, gbc, row++, 1);
    addLabeledField("Local:", localField, formPanel, gbc, row++, 1);
    addLabeledField("Número:", numberField, formPanel, gbc, row++, 1);
    addLabeledField("Bairro:", neighborhoodField, formPanel, gbc, row++, 1);
    addLabeledField("Cidade:", cityField, formPanel, gbc, row++, 1);

    // Terceira coluna
    row = 0;
    addLabeledField("Estado:", stateField, formPanel, gbc, row++, 2);
    addLabeledField("Data de Nascimento:", datePicker, formPanel, gbc, row++, 2);
    addLabeledField("Senha:", passwordField, formPanel, gbc, row++, 2);

    // Adiciona o painel central ao layout
    JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
    centerPanel.setBackground(new Color(30, 30, 30));
    centerPanel.add(formPanel);
    add(centerPanel, BorderLayout.CENTER);

    // Botões de ação
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(30, 30, 30));
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

    RoundedButton registerButton =
        new RoundedButton("Cadastrar-se", new Color(240, 66, 11), Color.WHITE);
    registerButton.setPreferredSize(new Dimension(170, 45));
    registerButton.setForeground(Color.WHITE);

    RoundedButton backButton =
        new RoundedButton("Voltar", new Color(109, 6, 6, 25), new Color(161, 61, 61));
    backButton.setPreferredSize(new Dimension(120, 45));
    backButton.setForeground(new Color(161, 61, 61));

    registerButton.addActionListener(
        e -> {
          if (cadastrarFuncionario()) {
            JOptionPane.showMessageDialog(
                this,
                "Cadastro realizado com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);

            // Reabre a tela de login do funcionário
            new LoginView("Funcionário").setVisible(true);
            dispose(); // Fecha a tela de cadastro
          }
        });

    backButton.addActionListener(e -> dispose());

    buttonPanel.add(registerButton);
    buttonPanel.add(backButton);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  private boolean cadastrarFuncionario() {
    try {
      // Validação dos campos obrigatórios
      if (codeField.getText().isEmpty()
          || positionField.getText().isEmpty()
          || nameField.getText().isEmpty()
          || cpfField.getText().isEmpty()
          || phoneField.getText().isEmpty()
          || passwordField.getPassword().length == 0
          || cepField.getText().isEmpty()
          || localField.getText().isEmpty()
          || numberField.getText().isEmpty()
          || neighborhoodField.getText().isEmpty()
          || cityField.getText().isEmpty()
          || stateField.getText().isEmpty()) {
        JOptionPane.showMessageDialog(
            this,
            "Por favor, preencha todos os campos obrigatórios.",
            "Erro",
            JOptionPane.ERROR_MESSAGE);
        return false;
      }

      // Convertendo data de nascimento
      java.sql.Date dateOfBirth =
          new java.sql.Date(((java.util.Date) datePicker.getModel().getValue()).getTime());

      UsuarioController usuarioController = new UsuarioController();

      boolean success =
          usuarioController.cadastrarUsuario(
              nameField.getText(),
              cpfField.getText(),
              dateOfBirth,
              phoneField.getText(),
              "FUNCIONARIO",
              new String(passwordField.getPassword()),
              codeField.getText(),
              positionField.getText(),
              cepField.getText(),
              localField.getText(),
              Integer.parseInt(numberField.getText()),
              neighborhoodField.getText(),
              cityField.getText(),
              stateField.getText());

      if (success) {
        JOptionPane.showMessageDialog(
            this,
            "Funcionário cadastrado com sucesso!",
            "Sucesso",
            JOptionPane.INFORMATION_MESSAGE);
        new LoginView("Funcionário").setVisible(true); // Voltar para login de funcionário
        dispose();
      } else {
        JOptionPane.showMessageDialog(
            this, "Erro ao cadastrar funcionário.", "Erro", JOptionPane.ERROR_MESSAGE);
      }

    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(
          this,
          "Erro ao cadastrar funcionário: " + e.getMessage(),
          "Erro",
          JOptionPane.ERROR_MESSAGE);
    }
    return false;
  }

  private void addLabeledField(
      String label,
      JComponent inputField,
      JPanel formPanel,
      GridBagConstraints gbc,
      int row,
      int col) {
    gbc.gridx = col;
    gbc.gridy = row * 2;
    gbc.gridwidth = 1;

    JLabel fieldLabel = new JLabel(label);
    fieldLabel.setForeground(Color.WHITE);
    fieldLabel.setFont(new Font("Arial", Font.BOLD, 14));
    formPanel.add(fieldLabel, gbc);

    gbc.gridy++;
    inputField.setMaximumSize(new Dimension(250, 30));
    formPanel.add(inputField, gbc);
  }

  private CustomDatePicker createCustomDatePicker() {
    UtilDateModel dateModel = new UtilDateModel();
    Properties dateProperties = new Properties();
    dateProperties.put("text.today", "Hoje");
    dateProperties.put("text.month", "Mês");
    dateProperties.put("text.year", "Ano");
    JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, dateProperties);
    return new CustomDatePicker(datePanel, new DateLabelFormatter());
  }

  private CustomFormattedField createFormattedField(String mask) {
    try {
      MaskFormatter formatter = new MaskFormatter(mask);
      formatter.setPlaceholderCharacter('_');
      return new CustomFormattedField(formatter);
    } catch (ParseException e) {
      throw new RuntimeException("Erro ao criar campo formatado", e);
    }
  }

  private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private final String datePattern = "yyyy-MM-dd";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
      if (text == null || text.trim().isEmpty()) {
        return null; // Retorna null se o texto estiver vazio ou nulo
      }
      return dateFormatter.parse(text); // Converte o texto em um objeto java.util.Date
    }

    @Override
    public String valueToString(Object value) {
      if (value == null) {
        return ""; // Retorna uma string vazia se o valor for null
      }

      if (value instanceof java.util.Date) {
        return dateFormatter.format((java.util.Date) value); // Formata java.util.Date
      }

      if (value instanceof java.util.Calendar) {
        java.util.Calendar calendar = (java.util.Calendar) value;
        return dateFormatter.format(calendar.getTime()); // Formata o tempo do Calendar
      }

      throw new IllegalArgumentException("Valor inesperado: " + value.getClass().getName());
    }
  }

  class CustomTextField extends JTextField {
    public CustomTextField() {
      setBackground(new Color(60, 60, 60));
      setForeground(Color.WHITE);
      setCaretColor(Color.WHITE);
      setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      setPreferredSize(new Dimension(250, 30));
    }
  }

  class CustomPasswordField extends JPasswordField {
    public CustomPasswordField() {
      setBackground(new Color(60, 60, 60));
      setForeground(Color.WHITE);
      setCaretColor(Color.WHITE);
      setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      setPreferredSize(new Dimension(250, 30));
    }
  }

  class CustomDatePicker extends JDatePickerImpl {
    public CustomDatePicker(
        JDatePanelImpl datePanel, JFormattedTextField.AbstractFormatter formatter) {
      super(datePanel, formatter);
      JFormattedTextField textField = this.getJFormattedTextField();
      textField.setBackground(new Color(60, 60, 60));
      textField.setForeground(Color.WHITE);
      textField.setCaretColor(Color.WHITE);
      textField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
      textField.setHorizontalAlignment(JTextField.CENTER);
      textField.setFont(new Font("Arial", Font.PLAIN, 14));
    }
  }

  class CustomFormattedField extends JFormattedTextField {
    public CustomFormattedField(MaskFormatter formatter) {
      super(formatter);
      setBackground(new Color(60, 60, 60));
      setForeground(Color.WHITE);
      setCaretColor(Color.WHITE);
      setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
  }

  class StateTextField extends JTextField {
    public StateTextField() {
      setBackground(new Color(60, 60, 60));
      setForeground(Color.WHITE);
      setCaretColor(Color.WHITE);
      setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      // Limitar a entrada a 2 letras maiúsculas
      setDocument(
          new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a)
                throws javax.swing.text.BadLocationException {
              if (str == null || getLength() + str.length() > 2) {
                return; // Limita a entrada a 2 caracteres
              }
              if (str.matches("[a-zA-Z]+")) {
                super.insertString(offs, str.toUpperCase(), a); // Apenas letras maiúsculas
              }
            }
          });
    }
  }
}
