package com.bazafirm.scrappers;

import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class ScrapperSettings {

    private static final Logger log = Logger.getLogger(ScrapperSettings.class);

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/45.0.2454.101 Safari/537.36";
    private static final String REFERRER = "http://www.google.com";
    private static final String PROXY_HOST = "webproxy.stat.intra";
    private static final int PROXY_PORT = 8080;
    private static final int TIMEOUT = 10 * 1000;


    public Document connectWith(String link) throws IOException {
        if (link == null) {
            log.error("Empty url.");
            throw new IllegalArgumentException("Url should not be empty!");
        }
        Document document = null;
        try {
            document = Jsoup.connect(link)
                    .proxy(PROXY_HOST, PROXY_PORT)
                    .userAgent(USER_AGENT)
                    .referrer(REFERRER)
                    .timeout(TIMEOUT)
                    .ignoreHttpErrors(true)
                    .followRedirects(true)
                    .get();
        } catch (IOException e) {
            log.error("Jsoup - error while getting webpage[" + link + "],error: [" + e.getMessage() + "]");
        }
        return document;
    }
}
