package com.bazafirm.scrappers;

import com.bazafirm.model.SiteStructure;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

public class ScrapperTest extends ScrapperSettings {

    Scrapper scrapper = new Scrapper();
    private final String LINK = "http://bazafirm.e-gratka.info/branza/bankowo%C5%9B%C4%87+i+finanse/1";

    private Elements setUp() throws IOException {
        Document document = connectWith(LINK);
        Elements elements = document.select("div#srodek> div.lista");
        return elements;
    }

    @Test
    public void showStructureSiteSet() throws IOException {
        Set<SiteStructure> set = scrapper.createStructureSiteSet();
        for (SiteStructure siteStructure : set
        ) {
            System.out.println(siteStructure.getName());
            System.out.println(siteStructure.getNumber());
            System.out.println(siteStructure.getLink());
            System.out.println(siteStructure.getLastPaginationNumber());
            System.out.println(siteStructure.getLinkList());
        }
    }

    @Test
    public void shouldSayThatNameIsNotNull() throws IOException {
        Elements elements = setUp();
        int counter = 1;
        for (Element element : elements
        ) {
            Assert.assertNotNull(scrapper.searchForName(element));
            System.out.println(counter++ + ": " +scrapper.searchForName(element));
        }
    }

    @Test
    public void shouldSayThatLinkIsNotNull() throws IOException {
        Elements elements = setUp();
        int counter = 1;
        for (Element element : elements
        ) {
            Assert.assertNotNull(scrapper.searchForLink(element));
            System.out.println(counter++ + ": " +scrapper.searchForLink(element));
        }
    }

    @Test
    public void shouldSayThatStreetIsNotNull() throws IOException {
        Elements elements = setUp();
        int counter = 1;
        for (Element element : elements
        ) {
            Assert.assertNotNull(scrapper.searchForStreet(element));
            System.out.println(counter++ + ": " +scrapper.searchForStreet(element));
        }
    }

    @Test
    public void shouldSayThatPostalCodeIsNotNull() throws IOException {
        Elements elements = setUp();
        int counter = 1;
        for (Element element : elements
        ) {
            Assert.assertNotNull(scrapper.searchForPostalCode(element));
            System.out.println(counter++ + ": " +scrapper.searchForPostalCode(element));
        }
    }

    @Test
    public void shouldSayThatEmailIsNotNull() throws IOException {
        Elements elements = setUp();
        int counter = 1;
        for (Element element : elements
        ) {
            Assert.assertNotNull(scrapper.searchForEmail(element));
            System.out.println(counter++ + ": " +scrapper.searchForEmail(element));
        }
    }

    @Test
    public void shouldSayThatPhoneIsNotNull() throws IOException {
        Elements elements = setUp();
        int counter = 1;
        for (Element element : elements
        ) {
            Assert.assertNotNull(scrapper.searchForPhone(element));
            System.out.println(counter++ + ": " +scrapper.searchForPhone(element));
        }
    }

    @Test
    public void shouldSayThatDatePublishedIsNotNull() throws IOException {
        Elements elements = setUp();
        int counter = 1;
        for (Element element : elements
        ) {
            Assert.assertNotNull(scrapper.searchForPublishedDate(element));
            System.out.println(counter++ + ": " +scrapper.searchForPublishedDate(element));
        }
    }

    @Test
    public void shouldSayThatRegionIsNotNull() throws IOException {
        Elements elements = setUp();
        int counter = 1;
        for (Element element : elements
        ) {
            Assert.assertNotNull(scrapper.searchForRegion(element));
            System.out.println(counter++ + ": " +scrapper.searchForRegion(element));
        }
    }

    @Test
    public void shouldSayThatBranchIsNotNull() throws IOException {
        Elements elements = setUp();
        int counter = 1;
        for (Element element : elements
        ) {
            Assert.assertNotNull(scrapper.searchForBranch(element));
            System.out.println(counter++ + ": " +scrapper.searchForBranch(element));
        }
    }

}