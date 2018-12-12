/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author Hadi Vahavpour Roudsari
 */
public class SwingView extends JFrame {

    private final Controller controller = new Controller();
    private final JLabel jLabel = new JLabel("                   Enter the URL :");
    private final JTextField jTextField = new JTextField(50);
    private final JButton jButton = new JButton("ADD");
    private final JTextArea jTextArea = new JTextArea();
    private JPanel mainPanel;
    private JPanel insertPanel;
    private JPanel resultPanel;

    public static void main(String[] args) {

        new SwingView().init();

    }

    public void init() {
        controller.startConsumer();
        insertPanel = new JPanel();
        insertPanel.setLayout(new GridLayout(1, 3));
        insertPanel.add(jLabel);
        insertPanel.add(jTextField);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.produce(jTextField.getText());
                jTextArea.setText(jTextField.getText() +"entered ");
            }
        });
        insertPanel.add(jButton);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(insertPanel, BorderLayout.NORTH);
        resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout());
        resultPanel.add(jTextArea);
        mainPanel.add(resultPanel, BorderLayout.CENTER);
        getContentPane().add(mainPanel);
        setTitle("Search Engine");
        setSize(700, 300);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

}
