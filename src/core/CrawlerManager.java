/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import cofiguration.Configuration;
import dto.GenericUrlDto;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hadi Vahabpour Roudsari
 */
public final class CrawlerManager {

    private final LinkedList<GenericUrlDto> list = new LinkedList<>();
    private final int capacity = 100;
    private ExecutorService executor = Executors.newFixedThreadPool(Configuration.getConfig().getThreadNumbers());
    private final static Logger MYLOG = Logger.getLogger(CrawlerManager.class.getName());

    private CrawlerManager() {
    }
    private final static CrawlerManager MANAGER = new CrawlerManager();

    public static CrawlerManager getManager() {
        return MANAGER;
    }

    public /*synchronized */ void produce(GenericUrlDto dto) throws InterruptedException {
        synchronized (this) {
            while (list.size() == capacity) {
                wait();
            }
            MYLOG.log(Level.INFO, "Producer produced  ************:" + dto.getUrlFullName() + "************");
            list.add(dto);
            notify();

        }

    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (this) {

                while (list.isEmpty()) {
                    wait();
                }
                GenericUrlDto dto = list.removeFirst();
                MYLOG.log(Level.INFO, "new url is add to be crawled with txnId :" + dto.getTxnId() + " url Name :" + dto.getUrlFullName() + "************");
                Runnable r = new Crawler(dto);
                executor.submit(r);
//                Thread t = new Thread(new Crawler(dto));
//                t.start();
               notify();

            }
        }
    }
}
