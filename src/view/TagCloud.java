/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JLabel;

/**
 *
 * @author STS
 */
public class TagCloud {

    Set<JLabel> jLabels = new HashSet<>();

    public void addToList(StringRank stringSize) {
        JLabel label = new JLabel(stringSize.wordName);
        label.setFont(new Font("Serif", Font.PLAIN, stringSize.size));
        jLabels.add(label);
    }

    public Set<JLabel> getjLabels() {
        return jLabels;
    }

    public Set<JLabel> processTheText(String text) {
        
        Map<String, Integer> wordMap = new HashMap<String, Integer>();
        List<StringRank> wordList = new ArrayList<>();
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
        }
        for (String word : words) {
            wordMap.put(word, (wordMap.containsKey(word) ? wordMap.get(word) + 1 : 1));
        }
        Iterator<Map.Entry<String, Integer>> entries = wordMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Integer> entry = entries.next();
            //    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            wordList.add(new StringRank(entry.getKey(), entry.getValue()));
        }
        Collections.sort(wordList, new Comparator<StringRank>() {
            @Override
            public int compare(StringRank o1, StringRank o2) {
                return o2.size - o1.size;
            }
        });
        float maxFrequent = 1;
        if(wordList.size()>20){
        for (int i = 0; i < 20; i++) {
            //  System.out.println("  " + wordList.get(i).size);
            if (i == 0) {
                maxFrequent = wordList.get(i).size;
                System.out.println(maxFrequent);
            }
            System.out.println("==="+wordList.get(i).size);
            wordList.get(i).size = (int) ((wordList.get(i).size / maxFrequent) * 110);
            System.out.println("==="+wordList.get(i).size);
            addToList(wordList.get(i));
        }
    }
        return jLabels;
    }

    public class StringRank {

        private String wordName;
        private int size;

        public StringRank(String wordName, int size) {
            this.wordName = wordName;
            this.size = size;
        }

//        public String getWordName() {
//            return wordName;
//        }
//
//        public int getSize() {
//            return size;
//        }
    }

}
