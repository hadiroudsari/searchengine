/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author STS
 */
public class StringRank {
      private String wordName;
    private int size;

    public StringRank(String wordName, int size) {
        this.wordName = wordName;
        this.size = size;
    }

    public String getWordName() {
        return wordName;
    }

    public int getSize() {
        return size;
    }
}
