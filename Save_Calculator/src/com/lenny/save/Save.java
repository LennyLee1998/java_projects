package com.lenny.save;

import javax.swing.*;
import java.awt.*;

public class Save {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new IncomeCalculatorGUI());
  }
}

class IncomeCalculatorGUI {
  private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 16);
  private static final int WINDOW_WIDTH = 500;
  private static final int WINDOW_HEIGHT = 300;
  private static final int MARGIN = 20;
  private static final int LABEL_WIDTH = 80;
  private static final int LABEL_HEIGHT = 30;
  private static final int FIELD_WIDTH = 300;

  private final JFrame frame;
  private final JTextField yearSalaryField;
  private final JTextField yearCostField;
  private final JTextField yearField;
  private final JLabel resultLabel;
  private final IncomeCalculatorService calculatorService;

  public IncomeCalculatorGUI() {
    calculatorService = new IncomeCalculatorService();
    frame = new JFrame("收入计算器");
    frame.setLayout(null);
    frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // 初始化组件
    yearSalaryField = createTextField(MARGIN + LABEL_WIDTH + MARGIN, MARGIN);
    yearCostField = createTextField(MARGIN + LABEL_WIDTH + MARGIN, MARGIN + LABEL_HEIGHT + MARGIN);
    yearField = createTextField(MARGIN + LABEL_WIDTH + MARGIN, MARGIN + 2 * (LABEL_HEIGHT + MARGIN));

    addLabel("年收入", MARGIN, MARGIN);
    addLabel("年消费", MARGIN, MARGIN + LABEL_HEIGHT + MARGIN);
    addLabel("第几年", MARGIN, MARGIN + 2 * (LABEL_HEIGHT + MARGIN));

    resultLabel = createResultLabel();
    JButton calculateButton = createCalculateButton();

    frame.setVisible(true);
  }

  private JTextField createTextField(int x, int y) {
    JTextField field = new JTextField();
    field.setBounds(x, y, FIELD_WIDTH, LABEL_HEIGHT);
    field.setFont(DEFAULT_FONT);
    frame.add(field);
    return field;
  }

  private void addLabel(String text, int x, int y) {
    JLabel label = new JLabel(text);
    label.setBounds(x, y, LABEL_WIDTH, LABEL_HEIGHT);
    label.setFont(DEFAULT_FONT);
    frame.add(label);
  }

  private JLabel createResultLabel() {
    JLabel label = new JLabel("计算结果将显示在这里");
    label.setBounds(MARGIN, MARGIN + 3 * (LABEL_HEIGHT + MARGIN), WINDOW_WIDTH - 2 * MARGIN, LABEL_HEIGHT);
    label.setFont(DEFAULT_FONT);
    frame.add(label);
    return label;
  }

  private JButton createCalculateButton() {
    JButton button = new JButton("计算收入");
    button.setBounds(WINDOW_WIDTH / 2 - 60, WINDOW_HEIGHT - 80, 120, 40);
    button.setFocusable(false);
    button.addActionListener(e -> calculateIncome());
    frame.add(button);
    return button;
  }

  private void calculateIncome() {
    try {
      double yearSalary = Double.parseDouble(yearSalaryField.getText());
      double yearCost = Double.parseDouble(yearCostField.getText());
      int years = Integer.parseInt(yearField.getText());

      if (yearSalary < 0 || yearCost < 0 || years <= 0) {
        showError("请输入有效的正数值");
        return;
      }

      double income = calculatorService.calculateIncome(yearSalary, yearCost, years);
      resultLabel.setText(String.format("您的年收入是: %.2f", income));
    } catch (NumberFormatException ex) {
      showError("请输入有效的数字");
    }
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(frame, message, "输入错误", JOptionPane.ERROR_MESSAGE);
  }
}

class IncomeCalculatorService {
  private static final double INTEREST_RATE = 0.05;
  private static final double SALARY_INCREASE_RATE = 0.05;
  private static final double COST_INCREASE_RATE = 0.03;

  public double calculateIncome(double salary, double cost, int years) {
    double income = 0;
    for (int i = 0; i < years; i++) {
      double save = salary - cost;
      income = income * (1 + INTEREST_RATE) + save;
      salary *= (1 + SALARY_INCREASE_RATE);
      cost *= (1 + COST_INCREASE_RATE);
    }

    return income;
  }
}