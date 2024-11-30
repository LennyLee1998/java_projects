package com.lenny.refcalc;

import javax.swing.*;
import java.awt.*;

public class RefactorCalc {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(RefactorCalcGUI::new);
  }
}

class RefactorCalcGUI {
  private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 30);
  private static final int WINDOW_WIDTH = 500;
  private static final int WINDOW_HEIGHT = 600;
  private static final int MARGIN = 10;
  private static final int PADDING = 40;
  private static final int BTN_WIDTH = (WINDOW_WIDTH - PADDING * 2 - MARGIN * 3) / 4;
  private static final int BTN_HEIGHT = 45;
  private static final int FIELD_WIDTH = WINDOW_WIDTH - (PADDING * 2);
  private static final int FIELD_HEIGHT = 55;

  private static final char[] BTN_ARR = {'1', '2', '3', '+', '4', '5', '6', '-',
      '7', '8', '9', '*', '.', '0', '=', '/',};

  private final JFrame frame;
  private final JTextField field;
  private final JPanel panel;

  private final StringBuilder currentInput;
  private double result, number;
  private char operator;

  public RefactorCalcGUI() {
    frame = new JFrame("简易计算器");
    frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new FlowLayout(FlowLayout.CENTER, PADDING, PADDING)); // 使用流布局

    field = new JTextField();
    field.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
    field.setFont(DEFAULT_FONT);
    field.setEditable(false);
    field.setFocusable(false);

    panel = new JPanel(new GridLayout(4, 4, MARGIN, MARGIN));
    panel.setPreferredSize(new Dimension(FIELD_WIDTH, WINDOW_HEIGHT - FIELD_HEIGHT - PADDING - 200));

    currentInput = new StringBuilder();

    for (char btn : BTN_ARR) {
      addButton(String.valueOf(btn));
    }
    frame.add(field);
    frame.add(panel);
    addButton("Delete");
    addButton("Clear");
    frame.setVisible(true);
  }

  public void addButton(String name) {
    JButton button = new JButton(name);
    button.setFont(DEFAULT_FONT);
    button.setFocusable(false);
    button.addActionListener(e -> buttonClick(name));
    if ("Delete".equals(name) || "Clear".equals(name)) {
      frame.add(button);
    } else {
      panel.add(button);
    }
  }

  // 点击事件
  private void buttonClick(String name) {
    switch (name) {
      case "0", "1", "2", "3", "4", "6", "7", "8", "9" -> appendToDisplay(name);
      case "+", "-", "*", "/" -> performOperation(name);
      case "." -> addDecimalPoint();
      case "=" -> calculate();
      case "Clear" -> clearDiplay();
      case "Delete" -> deleteLastDigit();
    }
  }

  private void deleteLastDigit() {
    if (!currentInput.isEmpty()) {
      currentInput.setLength(currentInput.length() - 1);
      field.setText(currentInput.toString());
    }
  }

  private void clearDiplay() {
    field.setText("");
    currentInput.setLength(0);
    number = 0;
    result = 0;
  }

  private void addDecimalPoint() {
    if (!currentInput.toString().contains(".")) {
      currentInput.append(".");
      field.setText(currentInput.toString());
    }
  }

  private void performOperation(String name) {
    if (result == 0) {
      result = Double.parseDouble(currentInput.toString());
    }
    operator = name.charAt(0);
    currentInput.setLength(0);
  }

  private void appendToDisplay(String name) {
    currentInput.append(name);
    field.setText(currentInput.toString());
  }

  private void calculate() {
    number = Double.parseDouble(currentInput.toString());
    switch (operator) {
      case '+' -> result += number;
      case '-' -> result -= number;
      case '*' -> result *= number;
      case '/' -> result /= number;
      default -> System.out.println("错误的操作符");
    }
    field.setText(result + "");
  }


}