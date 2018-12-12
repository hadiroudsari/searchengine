/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hadi Vahabpour Roudsari
 */
public class SearchDto extends GenericUrlDto {

    private List<Integer> keyList = new ArrayList<>();
    private List<Integer> valueList = new ArrayList<>();
    private Integer[] keys;
    private Integer[] values;

    public List<Integer> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<Integer> keyList) {
        this.keyList = keyList;
    }

    public List<Integer> getValueList() {
        return valueList;
    }

    public void setValueList(List<Integer> valueList) {
        this.valueList = valueList;
    }

    public Integer[] getKeys() {
        return keys;
    }

    public void setKeys(Integer[] keys) {
        this.keys = keys;
    }

    public Integer[] getValues() {
        return values;
    }

    public void setValues(Integer[] values) {
        this.values = values;
    }

   


}
