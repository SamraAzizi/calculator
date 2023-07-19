import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame implements ActionListener {
    private JTextField textField;

    public CalculatorGUI() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Create the text field
        textField = new JTextField(10);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        Font font = new Font("Arial", Font.PLAIN, 20);
        textField.setFont(font);

        // Create the number buttons
        JButton[] numberButtons = new JButton[10];
        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(font);
            numberButtons[i].addActionListener(this);
        }

        // Create the operator buttons
        JButton addButton = createButton("+", font);
        JButton subtractButton = createButton("-", font);
        JButton multiplyButton = createButton("*", font);
        JButton divideButton = createButton("/", font);
        JButton equalsButton = createButton("=", font);

        // Create the clear button
        JButton clearButton = createButton("C", font);
        clearButton.setForeground(Color.RED);

        // Create the panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add buttons to the panel
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(addButton);
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subtractButton);
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(clearButton);
        buttonPanel.add(equalsButton);
        buttonPanel.add(divideButton);

        // Create the main panel and add components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(textField, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add main panel to the frame
        add(mainPanel);

        // Set the size of the frame
        pack();
    }

    private JButton createButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String currentText = textField.getText();

        if (command.equals("C")) {
            // Clear the text field
            textField.setText("");
        } else if (command.equals("=")) {
            // Evaluate the expression and display the result
            try {
                double result = evaluateExpression(currentText);
                textField.setText(Double.toString(result));
            } catch (ArithmeticException ex) {
                textField.setText("Error");
            }
        } else {
            // Append the clicked button's text to the text field
            textField.setText(currentText + command);
        }
    }

    private double evaluateExpression(String expression) {
        try {
            return evaluate(expression);
        } catch (Exception e) {
            throw new ArithmeticException("Invalid expression");
        }
    }

    private double evaluate(String expression) {
        char[] arr = expression.toCharArray();
        double operand1 = 0, operand2 = 0;
        char operator = ' ';
        boolean operatorFound = false;

        for (char c : arr) {
            if (Character.isDigit(c)) {
                if (!operatorFound) {
                    operand1 = operand1 * 10 + Character.getNumericValue(c);
                } else {
                    operand2 = operand2 * 10 + Character.getNumericValue(c);
                }
            } else {
                operator = c;
                operatorFound = true;
            }
        }

        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    throw new ArithmeticException("Division by zero is not allowed!");
                }
            default:
                throw new ArithmeticException("Invalid operator!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CalculatorGUI().setVisible(true);
            }
        });
    }
}
