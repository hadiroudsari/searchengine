/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import biz.CheckNewUrl;
import biz.UrlFacade;
import constant.Status;
import dto.GenericUrlDto;
import java.util.logging.Level;
import java.util.logging.Logger;
import textprocessing.FullTextProcessing;
import textprocessing.TextUtility;

/**
 *
 * @author STS
 */
public class Crawler implements Runnable {

    private GenericUrlDto dto ;
    private final TextUtility textUtility = new TextUtility();
    private final CheckNewUrl checkNewUrl = new CheckNewUrl();
    private final FullTextProcessing fullTextProcessing = new FullTextProcessing();
    private final UrlFacade facade=new UrlFacade();
    private final static Logger MYLOG = Logger.getLogger(Crawler.class.getName());
    public Crawler(GenericUrlDto dto) {
        this.dto = dto;
    }

    public void prepareHashAndText() {   //status 0 to 2
        textUtility.prepareUrlWholeBody(this.dto);
    }

    public void checkHashAndUrl() {      //status 2 to4
        checkNewUrl.findUrlIfExists(this.dto);
    }

    public void lexicalAnalyze() {      //status 4to 6
        fullTextProcessing.FullAnalyse(this.dto);
    }

    public void insertNewUrl() {        //status 6to 8
      facade.insertUrlAndBlooms(this.dto);
    }

    public final void crawling() {
        prepareHashAndText();
        checkHashAndUrl();
        if (this.dto.getStatus().equals(Status.NEED)) {
            lexicalAnalyze();
        }
        if(dto.getStatus().equals(Status.LEXICALANALYZE))
        insertNewUrl();

    }

    @Override
    public void run() {
           MYLOG.log(Level.INFO, "start tnxId :"+ this.dto.getTxnId() +" crawling state   0 ...");
        crawling();
          
    }

}
