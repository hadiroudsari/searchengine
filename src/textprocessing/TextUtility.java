/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textprocessing;

import cofiguration.Configuration;
import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import constant.Constant;
import constant.Status;
import core.CrawlerManager;
import dto.GenericUrlDto;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Hadi Vahabpour Roudsari
 */
public class TextUtility {

    private final static Logger MYLOG = Logger.getLogger(TextUtility.class.getName());
    

    public void prepareUrlWholeBody(GenericUrlDto dto) {
      
        try {
            MYLOG.log(Level.INFO, "Downloading  " + dto.getUrlFullName() + "  page... ");
            Document doc = Jsoup.connect(dto.getUrlFullName()).get();
            String text = doc.body().text();
            dto.setBodyPage(text);

            HashFunction hf = Hashing.murmur3_128();
            HashCode hc = hf.newHasher()
                    .putString(text, Charsets.UTF_8)
                    .hash();
            dto.setHashText(hc.asLong() & 1000000000L);
            dto.setStatus(Status.PREPARE);
            //
            if(Configuration.getConfig().isEager()){
            CrawlerManager manager = CrawlerManager.getManager();
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                if (!link.attr("abs:href").equals("") && link.attr("abs:href") != null) {
                    GenericUrlDto tempDto = new GenericUrlDto();
                    tempDto.setUrlFullName(link.attr("abs:href"));
                    manager.produce(tempDto);
                    tempDto = null;
                }

            }
            }
            //
        } catch (IOException ex) {
            Logger.getLogger(TextUtility.class.getName()).log(Level.SEVERE, null, ex);
            MYLOG.log(Level.SEVERE, ex.toString());
            dto.setStatus(Status.PREPAREERROR);
        } catch (Exception ex) {
            MYLOG.log(Level.SEVERE, ex.toString());
            dto.setStatus(Status.PREPAREERROR);
        } finally {
            MYLOG.log(Level.INFO, " txnId : " + dto.getTxnId() + "    status code : " + dto.getStatus().getStatusCode());
        }

    }

//    public static void main(String[] args) {
//        GenericUrlDto dto = new GenericUrlDto();
//        dto.setUrlFullName("https://en.wikipedia.org/wiki/Batman ");
//        TextUtility tu = new TextUtility();
//        tu.prepareUrlWholeBody(dto);
//
//    }

}
