/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import constant.Status;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hadi Vahabpour Roudsari
 */
public class GenericUrlDto {

    public GenericUrlDto() {
        txnId= Long.toString(System.currentTimeMillis());
        
    }

    
    private String txnId;
    private String urlFullName;

    // private List<String> urlList = new ArrayList<>();
    private String bodyPage;

    private long hashText;

    private Status status;

    private short[]letters;
    
    private short[]numbers;
    
    private short[]binaryOperand;
    
    
    public String getUrlFullName() {
        return urlFullName;
    }

    private boolean searchNeed;//status int can be enum

    public void setUrlFullName(String urlFullName) {
        this.urlFullName = urlFullName;
    }

    public String getBodyPage() {
        return bodyPage;
    }

    public void setBodyPage(String bodyPage) {
        this.bodyPage = bodyPage;
    }

    public long getHashText() {
        return hashText;
    }

    public void setHashText(long hashText) {
        this.hashText = hashText;
    }

    public boolean isSearchNeed() {
        return searchNeed;
    }

    public void setSearchNeed(boolean searchNeed) {
        this.searchNeed = searchNeed;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public short[] getLetters() {
        return letters;
    }

    public void setLetters(short[] letters) {
        this.letters = letters;
    }

    public short[] getNumbers() {
        return numbers;
    }

    public void setNumbers(short[] numbers) {
        this.numbers = numbers;
    }

    public short[] getBinaryOperand() {
        return binaryOperand;
    }

    public void setBinaryOperand(short[] binaryOperand) {
        this.binaryOperand = binaryOperand;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }
    
    

}
