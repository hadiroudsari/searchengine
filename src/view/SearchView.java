/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import core.CrawlerManager;
import dto.TableDto;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author STS
 */
public class SearchView extends JFrame {

    JLabel jLabel = new JLabel("Write what you want to search ");
    Set<JLabel> jLabels;
    JTextField jTextField = new JTextField(50);
    JButton jButton = new JButton("Search");
    JTable jTable;
    JPanel mainPanle;
    JPanel searchPanel;
    JPanel tablePanel;
    JPanel tagCloudPannel;
    List<TableDto> tableList;
    TableModel tableModel;
    private Logger myLog = Logger.getLogger(SearchView.class.getName());
    public static void main(String[] args) {
        new SearchView().intit();
    }

    public void intit() {
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableList = new SearchController().search(jTextField.getText());
//                System.out.println("table list size :" + tableList.size());

                initTable();
            }
        });
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(1, 3));
        searchPanel.add(jLabel);
        searchPanel.add(jTextField);
        searchPanel.add(jButton);
        mainPanle = new JPanel();
        mainPanle.setLayout(new BorderLayout());
        mainPanle.add(searchPanel, BorderLayout.NORTH);

        getContentPane().add(mainPanle);
        setTitle("Search Frame");
        //   setSize(800, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        // setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void initTable() {
        if(tableList.size()>=20){
        if (jTable == null) {
            System.out.println(jTable);
            //    System.out.println("____" + tableList.size());
            tableModel = new AbstractTableModel() {

                private String[] colNames = new String[]{"Uri", "Rank", "TagCloud"};

                @Override
                public String getColumnName(int column) {
                    return colNames[column];
                }

                @Override
                public int getRowCount() {
                    return 20;
                }

                @Override
                public int getColumnCount() {
                    return 3;
                }

                @Override
                public Object getValueAt(int rowIndex, int columnIndex) {
                    Object value = null;
                    TableDto tableDto = tableList.get(rowIndex);
                    switch (columnIndex) {
                        case 0:
                            value = tableDto.getUrl().getText();
                            break;
                        case 1:
                            value = tableDto.getProbableRank();
                            break;
                        case 2:
                            value = new JTextField("Tag Cloud").getText();
                    }
                    return value;
                }
            };
            jTable = new JTable(tableModel);

            jTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //      System.out.println(e.hashCode());
                    //      System.out.println("jTable" + jTable.hashCode() + "   " + jTable);
                    int row = jTable.getSelectedRow();
                    int col = jTable.getSelectedColumn();
                    //       System.out.println(row);
                    //        System.out.println(col);
                    //  System.out.println(jTable.getValueAt(row, col));
                    if (col == 0) {
                        openInBrowser(jTable.getValueAt(row, col).toString());
                    }
                    if (col == 2) {
                    //    System.out.println("start tag cloud with " + jTable.getValueAt(row, 0).toString());
                        jLabels = new TagCloud().processTheText(tableList.get(row).getWholeText());
                        tagCloudPannel = new JPanel();
                        tagCloudPannel.setLayout(new FlowLayout());
                        for (JLabel jLabel : jLabels) {
//                            System.out.println("--" + jLabel.getText());
                            tagCloudPannel.add(jLabel);
                            mainPanle.add(tagCloudPannel, BorderLayout.PAGE_END);
                        }
                        revalidate();
                    }
                }

            });
            tablePanel = new JPanel();
            tablePanel.setLayout(new GridLayout());

            tablePanel.add(new JScrollPane(jTable));
            mainPanle.add(tablePanel, BorderLayout.CENTER);
            revalidate();
        } else {
            jTable.repaint();
        }
        
    }else{
            myLog.log(Level.INFO,"there is less than minimum sorry");
        }
    }

    public void openInBrowser(String str) {
        try {
            Desktop.getDesktop().browse(new URI(str));

        } catch (URISyntaxException ex) {
            Logger.getLogger(SearchView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SearchView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
