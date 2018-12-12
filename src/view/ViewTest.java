package view;

import core.Crawler;
import core.CrawlerManager;
import dto.GenericUrlDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hadi Vahabpour Roudsari
 */
public class ViewTest {
    
    
    

    public static void main(String[] args) throws InterruptedException {

        CrawlerManager manager = CrawlerManager.getManager();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    manager.consume();
                } catch (InterruptedException e) {
                    System.out.println("error in thread consumer ");
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println("error in thread consumer ");
                    e.printStackTrace();
                }
            }
        });
        t2.start();

        GenericUrlDto urlDto = new GenericUrlDto();

        urlDto.setUrlFullName("http://localhost:8080/David_Niven.html");
        manager.produce(urlDto);


        t2.join();
        System.out.println("t2 join");

    }

}
