/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import core.CrawlerManager;
import dto.GenericUrlDto;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author STS
 */
public class Controller {
  private  CrawlerManager manager = CrawlerManager.getManager();
    
    
    public void startConsumer(){
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    manager.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
    }
    
    public void produce(String str){
        
         GenericUrlDto urlDto = new GenericUrlDto();
         urlDto.setUrlFullName(str);
        try {
            manager.produce(urlDto);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
