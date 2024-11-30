package com.lenny.refcalc;

import javax.swing.*;
import java.awt.*;

public class Test {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(TestGUI::new);
  }
}

class TestGUI extends JFrame {
  private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 30);
  private static final int WINDOW_WIDTH = 500;
  private static final int WINDOW_HEIGHT = 600;
  private static final int MARGIN = 10;
  private static final int PADDING = 40;
  private static final int BTN_WIDTH = (WINDOW_WIDTH - PADDING * 2 - MARGIN * 3) / 4;
  private static final int BTN_HEIGHT = 45;
  private static final int FIELD_WIDTH = WINDOW_WIDTH - (PADDING * 2);
  private static final int FIELD_HEIGHT = 55;

  private static final char[] BTN_ARR = {'1', '2', '3', '+', '4', '5', '6', '-', '7', '8', '9', '*', '.', '0', '=', '/'};

  private JTextField field;
  private double currentValue = 0;
  private char currentOperator = '\0';

  public TestGUI() {
    setTitle("简易计算器");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setLayout(new BorderLayout(PADDING, PADDING));

    field = new JTextField();
    field.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
    field.setFont(DEFAULT_FONT);
    field.setHorizontalAlignment(JTextField.RIGHT);
    field.setEditable(false);
    field.setFocusable(false);
    add(field, BorderLayout.NORTH);

    JPanel panel = new JPanel(new GridLayout(5, 4, MARGIN, MARGIN));
    panel.setPreferredSize(new Dimension(FIELD_WIDTH, WINDOW_HEIGHT - FIELD_HEIGHT - PADDING * 2));
    add(panel, BorderLayout.CENTER);

    for (char btn : BTN_ARR) {
      addButton(panel, String.valueOf(btn));
    }

    addButton(this, "Clear");
    addButton(this, "Delete");

    setVisible(true);
  }

  private void addButton(Container container, String name) {
    JButton button = new JButton(name);
    button.setFont(DEFAULT_FONT);
    button.setFocusable(false);
    button.addActionListener(e -> buttonClick(name));
    container.add(button);
  }

  private void buttonClick(String name) {
    switch (name) {
      case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> appendToDisplay(name);
      case "." -> addDecimalPoint();
      case "+" -> performOperation(name);
      case "-" -> performOperation(name);
      case "*" -> performOperation(name);
      case "/" -> performOperation(name);
      case "=" -> calculateResult();
      case "Clear" -> clearDisplay();
      case "Delete" -> deleteLastDigit();
    }
  }

  private void appendToDisplay(String digit) {
    field.setText(field.getText() + digit);
  }

  private void addDecimalPoint() {
    if (!field.getText().contains(".")) {
      appendToDisplay(".");
    }
  }

  private void performOperation(String name) {
    if (!field.getText().isEmpty()) {
      calculateResult();
    }
    currentOperator = name.charAt(0);
    field.setText("");
  }

  private void calculateResult() {
    double newValue = Double.parseDouble(field.getText());
    switch (currentOperator) {
      case '+' -> currentValue += newValue;
      case '-' -> currentValue -= newValue;
      case '*' -> currentValue *= newValue;
      case '/' -> {
        if (newValue != 0) {
          currentValue /= newValue;
        } else {
          JOptionPane.showMessageDialog(this, "错误：除以零");
          clearDisplay();
          return;
        }
      }
      default -> currentValue = newValue;
    }
    field.setText(String.format("%.2f", currentValue));
    currentOperator = '\0';
  }

  private void clearDisplay() {
    field.setText("");
    currentValue = 0;
    currentOperator = '\0';
  }

  private void deleteLastDigit() {
    String text = field.getText();
    if (!text.isEmpty()) {
      field.setText(text.substring(0, text.length() - 1));
    }
  }
}