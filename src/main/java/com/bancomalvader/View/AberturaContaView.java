/**
 * Classe responsável pela interface gráfica para abertura de contas no sistema Banco Malvader.
 *
 * <p>Permite que os usuários insiram as informações necessárias para criar uma nova conta, com
 * validações e feedback visual.
 *
 * @author Dérick Rangel
 * @version 1.0
 * @since 2024-11-27
 */
package com.bancomalvader.View;

import com.bancomalvader.Controller.ContaController;
import com.bancomalvader.Controller.UsuarioController;
import com.bancomalvader.DAO.UsuarioDAO;
import com.bancomalvader.Model.Funcionario;
import com.bancomalvader.Util.RoundedButton;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class AberturaContaView extends JFrame {

  private final Funcionario funcionario;
  private final String tipoConta;

  private CustomTextField agenciaField,
      numeroContaField,
      limiteField,
      localField,
      numeroCasaField,
      bairroField,
      cidadeField;
  private StateTextField estadoField;
  private CustomPasswordField senhaField;
  private CustomTextField nomeField;
  private CustomFormattedField cpfField;
  private CustomFormattedField telefoneField;
  private CustomFormattedField cepField;
  private CustomDatePicker dataNascimentoPicker, dataVencimentoPicker;

  public AberturaContaView(Funcionario funcionario, String tipoConta) {
    this.funcionario = funcionario;
    this.tipoConta = tipoConta;

    setTitle("Abertura de Conta - " + tipoConta);
    setSize(900, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    getContentPane().setBackground(new Color(30, 30, 30));
    setLayout(new BorderLayout());

    // Cabeçalho
    JPanel headerPanel = new JPanel();
    headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
    headerPanel.setBackground(Color.BLACK);

    JLabel headerLabel = new JLabel("MVDR - Cadastro de Conta " + tipoConta, SwingConstants.CENTER);
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
    nomeField = new CustomTextField();
    cpfField = createFormattedField("###.###.###-##");
    telefoneField = createFormattedField("(##) #####-####");
    cepField = createFormattedField("#####-###");
    agenciaField = new CustomTextField();
    numeroContaField = new CustomTextField();
    limiteField = new CustomTextField();
    localField = new CustomTextField();
    numeroCasaField = new CustomTextField();
    bairroField = new CustomTextField();
    cidadeField = new CustomTextField();
    estadoField = new StateTextField();
    senhaField = new CustomPasswordField();

    // DatePicker customizado
    dataNascimentoPicker = createCustomDatePicker();
    dataVencimentoPicker = createCustomDatePicker();

    // Adiciona os campos com labels
    int row = 0;

    // Primeira coluna
    addLabeledField("Nome do Cliente:", nomeField, formPanel, gbc, row++, 0);
    addLabeledField("CPF do Cliente:", cpfField, formPanel, gbc, row++, 0);
    addLabeledField("Telefone de Contato:", telefoneField, formPanel, gbc, row++, 0);
    addLabeledField("Agência:", agenciaField, formPanel, gbc, row++, 0);
    addLabeledField("Número da Conta:", numeroContaField, formPanel, gbc, row++, 0);

    // Segunda coluna
    row = 0;
    addLabeledField("CEP:", cepField, formPanel, gbc, row++, 1);
    addLabeledField("Local:", localField, formPanel, gbc, row++, 1);
    addLabeledField("Número da Casa:", numeroCasaField, formPanel, gbc, row++, 1);
    addLabeledField("Bairro:", bairroField, formPanel, gbc, row++, 1);
    addLabeledField("Cidade:", cidadeField, formPanel, gbc, row++, 1);

    // Terceira coluna
    row = 0;
    addLabeledField("Estado:", estadoField, formPanel, gbc, row++, 2);
    addLabeledField("Data de Nascimento:", dataNascimentoPicker, formPanel, gbc, row++, 2);
    if (tipoConta.equalsIgnoreCase("Corrente")) {
      addLabeledField("Data de Vencimento:", dataVencimentoPicker, formPanel, gbc, row++, 2);
      addLabeledField("Limite da Conta:", limiteField, formPanel, gbc, row++, 2);
    }
    addLabeledField("Senha do Cliente:", senhaField, formPanel, gbc, row++, 2);

    // Adiciona o painel central ao layout
    JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
    centerPanel.setBackground(new Color(30, 30, 30));
    centerPanel.add(formPanel);
    add(centerPanel, BorderLayout.CENTER);

    // Botões de ação
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(30, 30, 30));
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

    RoundedButton createButton =
        new RoundedButton("Criar Conta", new Color(240, 66, 11), Color.WHITE);
    createButton.setPreferredSize(new Dimension(170, 45));
    createButton.setForeground(Color.WHITE);

    RoundedButton backButton =
        new RoundedButton("Voltar", new Color(109, 6, 6, 25), new Color(161, 61, 61));
    backButton.setPreferredSize(new Dimension(120, 45));
    backButton.setForeground(new Color(161, 61, 61));

    createButton.addActionListener(e -> cadastrarUsuarioEConta());

    backButton.addActionListener(
        e -> {
          new MenuFuncionarioView(funcionario).setVisible(true);
          dispose();
        });

    buttonPanel.add(createButton);
    buttonPanel.add(backButton);

    add(buttonPanel, BorderLayout.SOUTH);
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

  private void cadastrarUsuarioEConta() {
    try {
      // Validação dos campos obrigatórios
      if (nomeField.getText().isEmpty()
          || cpfField.getText().isEmpty()
          || telefoneField.getText().isEmpty()
          || senhaField.getPassword().length == 0
          || agenciaField.getText().isEmpty()
          || numeroContaField.getText().isEmpty()
          || cepField.getText().isEmpty()
          || localField.getText().isEmpty()
          || numeroCasaField.getText().isEmpty()
          || bairroField.getText().isEmpty()
          || cidadeField.getText().isEmpty()
          || estadoField.getText().isEmpty()) {
        JOptionPane.showMessageDialog(
            this,
            "Por favor, preencha todos os campos obrigatórios.",
            "Erro",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      UsuarioController usuarioController = new UsuarioController();
      boolean success =
          usuarioController.cadastrarUsuario(
              nomeField.getText(),
              cpfField.getText(),
              new Date(((java.util.Date) dataNascimentoPicker.getModel().getValue()).getTime()),
              telefoneField.getText(),
              "CLIENTE",
              new String(senhaField.getPassword()),
              null, // código do funcionário (não necessário para cliente)
              null, // cargo (não necessário para cliente)
              cepField.getText(),
              localField.getText(),
              Integer.parseInt(numeroCasaField.getText()),
              bairroField.getText(),
              cidadeField.getText(),
              estadoField.getText());

      if (!success) {
        JOptionPane.showMessageDialog(
            this, "Erro ao cadastrar usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
        return; // Retorna para impedir o restante do processo
      }

      // Se o cadastro foi bem-sucedido, obtenha o ID do usuário do DAO diretamente
      UsuarioDAO usuarioDAO = new UsuarioDAO();
      int idUsuario = usuarioDAO.obterUltimoIdUsuario(); // Implementação necessária no DAO

      ContaController contaController = new ContaController();
      boolean sucesso =
          contaController.cadastrarConta(
              idUsuario,
              numeroContaField.getText(),
              agenciaField.getText(),
              tipoConta.toUpperCase(),
              limiteField.getText(),
              (dataVencimentoPicker.getModel().getValue() != null)
                  ? new Date(
                      ((java.util.Date) dataVencimentoPicker.getModel().getValue()).getTime())
                  : null);

      if (sucesso) {
        JOptionPane.showMessageDialog(
            this, "Conta criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        new LoginView("Cliente").setVisible(true);
        dispose();
      } else {
        JOptionPane.showMessageDialog(
            this, "Erro ao criar conta. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
      }

    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(
          this, "Erro ao criar conta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
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

      setDocument(
          new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a)
                throws javax.swing.text.BadLocationException {
              if (str == null || getLength() + str.length() > 2) {
                return;
              }
              if (str.matches("[a-zA-Z]+")) {
                super.insertString(offs, str.toUpperCase(), a);
              }
            }
          });
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
}
