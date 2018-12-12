/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cofiguration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author STS
 */
@XmlRootElement(name = "config")
public class Config {

    private String url;
    private String user;
    private String password;
    private int threadNumbers;
    private boolean eager;
    public String getUrl() {
        return url;
    }

    @XmlElement
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    @XmlElement
    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public int getThreadNumbers() {
        return threadNumbers;
    }

    @XmlElement
    public void setThreadNumbers(int threadNumbers) {
        this.threadNumbers = threadNumbers;
    }

    public boolean isEager() {
        return eager;
    }
    
    @XmlElement
    public void setEager(boolean eager) {
        this.eager = eager;
    }
    
    

}
