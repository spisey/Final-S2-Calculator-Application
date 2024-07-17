//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorApp {
    private JFrame frame;
    private JTextField textField;
    private String currentOperator = "";
    private double firstNumber = 0;
    private boolean isNewInput = true;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculatorApp::new);
    }

    public CalculatorApp() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Calculator");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textField = createTextField();
        frame.add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField("0");
        textField.setFont(new Font("Arial", Font.BOLD, 24));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        return textField;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        return panel;
    }

    private void processNumberInput(String text) {
        if (isNewInput) {
            textField.setText("");
            isNewInput = false;
        }
        textField.setText(textField.getText() + text);
    }

    private void performCalculation(String operator) {
        if (!currentOperator.isEmpty()) {
            calculate();
        }
        firstNumber = Double.parseDouble(textField.getText());
        currentOperator = operator;
        isNewInput = true;
    }

    private void calculate() {
        double secondNumber = Double.parseDouble(textField.getText());
        double result = 0;

        switch (currentOperator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    textField.setText("Error");
                    return;
                }
                break;
        }

        textField.setText(String.valueOf(result));
        currentOperator = "";
        isNewInput = true;
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
                processNumberInput(command);
            } else if (command.equals("=")) {
                calculate();
            } else {
                performCalculation(command);
            }
        }
    }
}