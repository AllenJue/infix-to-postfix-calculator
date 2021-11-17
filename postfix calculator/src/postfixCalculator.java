
/**
 * Author: Allen Jue
 * Date: November 17, 2021
 * Collaborators: None
 * 
 * This postfix calculator utilizes Java Swing and an infix to postfix algorithm 
 * to process user-inputed mathematical expressions to calculate a numberic value
 */
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class postfixCalculator {

    private JFrame frame;
    private Stack<Character> operators;
    private Stack<Double> values;
    private StringBuilder screenLetters;
    private Panel screen;
    private JTextField textField;

    private static final String OPERATOR_CHARS = "-+x/^";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    postfixCalculator window = new postfixCalculator();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public postfixCalculator() {
        initialize();
    }

    /**
     * Calculate the value of an expression inputed by the user. <br>
     * pre: none
     * 
     * @return result of inputed expression
     */
    private double calculate() {
        // trim white space and split postFix expression by white space
        String[] postFix = infixToPostfix().trim().split("\\s+");
        for (String s : postFix) {
            // if there is an operation and two numbers, push the value of the numbers post
            // operation onto values
            if (isOperation(s) != -1) {
                if (values.size() < 2) {
                    clearText();
                    throw new IllegalStateException("Invalid expression");
                }
                double secondVar = values.pop();
                double firstVar = values.pop();
                // cases for operations
                if (s.equals("^")) {
                    values.push(Math.pow(firstVar, secondVar));
                } else if (s.equals("/")) {
                    values.push(firstVar / secondVar);
                } else if (s.equals("x")) {
                    values.push(firstVar * secondVar);
                } else if (s.equals("-")) {
                    values.push(firstVar - secondVar);
                } else {
                    values.push(firstVar + secondVar);
                }
            } else {
                // if not an operation, just push the number on
                values.push(Double.parseDouble(s));
            }
        }
        return values.pop();
    }

    /**
     * Given an infix expression, convert it into a postfix expression
     * 
     * @return a postfix expression equivalent to the inputed infix expression
     */
    private String infixToPostfix() {
        // remove spaces before and after the infix operation and split by white space
        String[] expression = screenLetters.toString().trim().split("\\s+");
        StringBuilder postFix = new StringBuilder();
        for (String s : expression) {
            int precedence = isOperation(s);
            if (precedence == -1 && !s.equals("(") && !s.equals(")")) {
                // number, add to postFix
                postFix.append(" " + Double.parseDouble(s) + " ");
            } else if (s.equals("(")) {
                // open parenthesis, put it in operation stack
                operators.push('(');
            } else if (s.equals("^")) {
                // exponent, put it in operation stack
                operators.push('^');
            } else if (s.equals(")")) {
                // closed parenthesis, everything within must be highest precedence, push to postFix
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postFix.append(" " + operators.pop() + " ");
                }
                // not valid parenthesis, no closed parenthesis
                if (operators.isEmpty()) {
                    clearText();
                    textField.setText("Invalid Expression");
                    throw new IllegalStateException("Expresion has invalid parenthesis");
                }
                // remove ending start parenthesis
                operators.pop();
            } else {
                // add all operators with higher or equal precedence to the postFix expression
                while (!operators.isEmpty()
                        && precedence <= (OPERATOR_CHARS.indexOf(operators.peek() + ""))) {
                    postFix.append(" " + operators.pop() + " ");
                }
                operators.push(OPERATOR_CHARS.charAt(precedence));
            }
        }
        // add all remaining operators to the postFix expression
        while (!operators.isEmpty()) {
            postFix.append(" " + operators.pop() + " ");
        }
        return postFix.toString();
    }

    /**
     * Gets the precedence of a String
     * 
     * @param s the String being considered
     * @return -1 if s is a number, or precedence of the operation
     */
    private int isOperation(String s) {
        int index = 0;
        for (char c : OPERATOR_CHARS.toCharArray()) {
            if (c == s.charAt(0)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Clears the textField, values, and operators
     */
    private void clearText() {
        operators.clear();
        values.clear();
        screenLetters.setLength(0);
        textField.setText(screenLetters.toString());
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        screenLetters = new StringBuilder();
        // for keeping track of operators and values after operations
        operators = new Stack<Character>();
        values = new Stack<Double>();

        // Create Buttons 0 - 9
        JButton btnNewButton_0 = new JButton("0");
        btnNewButton_0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append('0');
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_0.setBounds(130, 191, 71, 29);
        frame.getContentPane().add(btnNewButton_0);

        JButton btnNewButton_1 = new JButton("1");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append('1');
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_1.setBounds(51, 150, 71, 29);
        frame.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("2");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append('2');
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_2.setBounds(130, 150, 71, 29);
        frame.getContentPane().add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("3");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append('3');
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_3.setBounds(207, 150, 71, 29);
        frame.getContentPane().add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("4");
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append('4');
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_4.setBounds(51, 112, 71, 29);
        frame.getContentPane().add(btnNewButton_4);

        JButton btnNewButton_5 = new JButton("5");
        btnNewButton_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append('5');
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_5.setBounds(130, 112, 71, 29);
        frame.getContentPane().add(btnNewButton_5);

        JButton btnNewButton_6 = new JButton("6");
        btnNewButton_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append('6');
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_6.setBounds(207, 112, 71, 29);
        frame.getContentPane().add(btnNewButton_6);

        JButton btnNewButton_7 = new JButton("7");
        btnNewButton_7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append('7');
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_7.setBounds(51, 71, 71, 29);
        frame.getContentPane().add(btnNewButton_7);

        JButton btnNewButton_8 = new JButton("8");
        btnNewButton_8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append('8');
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_8.setBounds(130, 71, 71, 29);
        frame.getContentPane().add(btnNewButton_8);

        JButton btnNewButton_9 = new JButton("9");
        btnNewButton_9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append('9');
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_9.setBounds(207, 71, 71, 29);
        frame.getContentPane().add(btnNewButton_9);

        // Establish operator Buttons: Clear, +, -, x, /, ^, (, )
        JButton btnNewButton_10 = new JButton("Clear");
        btnNewButton_10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearText();
            }
        });
        btnNewButton_10.setBounds(295, 71, 117, 29);
        frame.getContentPane().add(btnNewButton_10);

        JButton btnNewButton_11 = new JButton("+");
        btnNewButton_11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append(" + ");
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_11.setBounds(295, 112, 117, 29);
        frame.getContentPane().add(btnNewButton_11);

        JButton btnNewButton_12 = new JButton("-");
        btnNewButton_12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append(" - ");
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_12.setBounds(295, 150, 117, 29);
        frame.getContentPane().add(btnNewButton_12);

        JButton btnNewButton_13 = new JButton("x");
        btnNewButton_13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append(" x ");
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_13.setBounds(295, 191, 117, 29);
        frame.getContentPane().add(btnNewButton_13);

        JButton btnNewButton_14 = new JButton("/");
        btnNewButton_14.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append(" / ");
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_14.setBounds(295, 225, 117, 29);
        frame.getContentPane().add(btnNewButton_14);

        JButton btnNewButton_15 = new JButton("^");
        btnNewButton_15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append(" ^ ");
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_15.setBounds(51, 225, 117, 29);
        frame.getContentPane().add(btnNewButton_15);

        JButton btnNewButton_16 = new JButton("=");
        btnNewButton_16.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Double ans = calculate();
                textField.setText(ans + "");
                screenLetters.setLength(0);
            }
        });
        btnNewButton_16.setBounds(166, 225, 117, 29);
        frame.getContentPane().add(btnNewButton_16);

        JButton btnNewButton_17 = new JButton("(");
        btnNewButton_17.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append(" ( ");
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_17.setBounds(51, 191, 73, 29);
        frame.getContentPane().add(btnNewButton_17);

        JButton btnNewButton_18 = new JButton(")");
        btnNewButton_18.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenLetters.append(" ) ");
                textField.setText(screenLetters.toString());
            }
        });
        btnNewButton_18.setBounds(207, 191, 71, 29);
        frame.getContentPane().add(btnNewButton_18);

        // Create frame and text field
        screen = new Panel();
        screen.setBackground(Color.WHITE);
        screen.setBounds(26, 10, 386, 43);
        frame.getContentPane().add(screen);

        textField = new JTextField();
        screen.add(textField);
        textField.setColumns(20);
    }
}
