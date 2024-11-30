package com.lenny.calc;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator implements ActionListener {
  //  JFrame
  JFrame frame;
  JTextField textField;
  JPanel panel;

  Font font = new Font("Ink Free", Font.BOLD, 25);

  JButton[] funcBtns = new JButton[8];
  JButton[] numBtns = new JButton[10];
  JButton addBtn, subBtn, mulBtn, diviBtn, decBtn, equalBtn, deleteBtn, clearBtn;
  double num1 = 0, num2 = 0, result;
  char operator;

  Calculator() {
//    frame
    frame = new JFrame("caculator1");
    frame.setSize(500, 600);
//    属性是静态属性,JFrame 类是 Window 的子类, WindowConstants 是一个定义了一些常量的接口 JFrame implement WindowConstants
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//    使用 null 布局后，您可以使用 setBounds(int x, int y, int width, int height) 方法在窗口中定位组件。
    frame.setLayout(null);

    
//    textfield
    textField = new JTextField();
    textField.setFont(font);
    textField.setBounds(45, 30, 400, 60);
    textField.setEditable(false);

//  panel
    panel = new JPanel();
    panel.setBounds(45, 120, 400, 340);
    panel.setLayout(new GridLayout(4, 4, 10, 10));

//    初始化funcbutton
    addBtn = new JButton("+");
    subBtn = new JButton("-");
    mulBtn = new JButton("*");
    diviBtn = new JButton("/");
    decBtn = new JButton(".");
    equalBtn = new JButton("=");
    deleteBtn = new JButton("Delete");
    clearBtn = new JButton("Clear");
//    加入数组
    funcBtns[0] = addBtn;
    funcBtns[1] = subBtn;
    funcBtns[2] = mulBtn;
    funcBtns[3] = diviBtn;
    funcBtns[4] = decBtn;
    funcBtns[5] = equalBtn;
    funcBtns[6] = deleteBtn;
    funcBtns[7] = clearBtn;

    for (JButton funcBtn : funcBtns) {
      funcBtn.addActionListener(this);
      funcBtn.setFont(font);
      funcBtn.setFocusable(false);
//      funcBtn.setRadi;
    }

//初始化numBtn => 统一设置
    for (int i = 0; i < numBtns.length; i++) {
      numBtns[i] = new JButton(i + "");
      numBtns[i].addActionListener(this);
      numBtns[i].setFont(font);
      numBtns[i].setFocusable(false);
    }

    panel.add(numBtns[1]);
    panel.add(numBtns[2]);
    panel.add(numBtns[3]);
    panel.add(addBtn);
    panel.add(numBtns[4]);
    panel.add(numBtns[5]);
    panel.add(numBtns[6]);
    panel.add(subBtn);
    panel.add(numBtns[7]);
    panel.add(numBtns[8]);
    panel.add(numBtns[9]);
    panel.add(mulBtn);
    panel.add(decBtn);
    panel.add(numBtns[0]);
    panel.add(equalBtn);
    panel.add(diviBtn);

    deleteBtn.setBounds(45, 470, 195, 80);
    clearBtn.setBounds(250, 470, 195, 80);

    frame.add(textField);
    frame.add(panel);
    frame.add(deleteBtn);
    frame.add(clearBtn);
//    要把compoennt在可见之前加入frame中
    frame.setVisible(true);

  }

  public static void main(String[] args) {
    new Calculator();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == deleteBtn) {
      String curText = textField.getText();
      textField.setText(curText.substring(0, curText.length() - 1));
    }
    if (e.getSource() == clearBtn) {
      textField.setText("");
      num1 = 0;
      num2 = 0;
      result = 0;
    }
    if (e.getSource() == addBtn) {
      operator = '+';
      num1 = Double.parseDouble(textField.getText());
      textField.setText("");
    }
    if (e.getSource() == subBtn) {
      operator = '-';
      num1 = Double.parseDouble(textField.getText());
      textField.setText("");

    }
    if (e.getSource() == mulBtn) {
      operator = '*';
      num1 = Double.parseDouble(textField.getText());
      textField.setText("");

    }
    if (e.getSource() == diviBtn) {
      operator = '/';
      num1 = Double.parseDouble(textField.getText());
      textField.setText("");

    }
    if (e.getSource() == equalBtn) {
      num2 = Double.parseDouble(textField.getText());
      switch (operator) {
        case '+':
          result = num1 + num2;
          break;
        case '-':
          result = num1 - num2;
          break;
        case '*':
          result = num1 * num2;
          break;
        case '/':
          result = num1 / num2;
          break;
      }
      textField.setText(result + "");
    }
//      小数点
    if (e.getSource() == decBtn) {
      textField.setText(textField.getText() + ".");
    }

    for (JButton numBtn : numBtns) {
      if (e.getSource() == numBtn) {
        textField.setText(textField.getText() + numBtn.getText());
      }
    }


  }
}
