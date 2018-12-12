/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cofiguration;

import constant.Constant;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author STS
 */
public class Configuration {

    public static Config getConfig() {
        File file = new File(Constant.congigFile);
    //  System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+file.isFile());
    
        Config conf = null;
        try {
          //  System.out.println(file.exists());
            JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            conf = (Config) jaxbUnmarshaller.unmarshal(file);
         
         //   System.out.println("eager : "+conf.isEager());

        } catch (JAXBException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conf;

    }
 }
