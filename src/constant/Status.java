/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constant;

/**
 *
 * @author STS
 */
public enum Status {
    
   PREPARE(2),PREPAREERROR(1),NEED(4),CHECKNEEDERROR(3),CHECKNOTNEED(9),
   LEXICALANALYZE(6),LEXICALANALYZEERROR(5),INSERT(8),INSERTERROR(7);
    
   private int StatusCode;

    private Status(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }
   
    
   
}
