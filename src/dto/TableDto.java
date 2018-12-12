/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.net.URI;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 *
 * @author STS
 */
public class TableDto {

    JTextField url;
//    Link url;
    int probableRank;
    
    String wholeText;
    public JTextField getUrl() {
        return url;
    }

    public void setUrl(JTextField url) {
        this.url = url;
    }

    public int getProbableRank() {
        return probableRank;
    }

    public void setProbableRank(int probableRank) {
        this.probableRank = probableRank;
    }

    public String getWholeText() {
        return wholeText;
    }

    public void setWholeText(String wholeText) {
        this.wholeText = wholeText;
    }
    

}
