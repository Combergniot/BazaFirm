package com.bazafirm.scrappers;

import com.bazafirm.model.Company;
import com.bazafirm.model.SiteStructure;
import com.bazafirm.services.CompanyService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class Scrapper extends ScrapperSettings {

    @Autowired
    CompanyService companyService;

    private static final Logger log = LoggerFactory.getLogger(Scrapper.class);

    private final String URL = "http://bazafirm.e-gratka.info/";
    private Set<SiteStructure> siteStructuresByCategory = new HashSet<>();
    private Set<SiteStructure> siteStructuresByRegion = new HashSet<>();
    private List<String> categoryNames = new ArrayList<>();
    private List<String> regionNames = new ArrayList<>();
    private final static String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    private SiteStructure findByName(Collection<SiteStructure> siteStructures, String name) {
        return siteStructures.stream()
                .filter(siteStructure -> name.equals(siteStructure.getName()))
                .findFirst()
                .orElse(null);
    }

    public void downloadDataFromALlBranches() throws IOException {
//        createStructureSiteSet();
        for (int i = 0; i < categoryNames.size(); i++) {
            System.out.println("Begin downloading companies from category: " + categoryNames.get(i));
            collectCompaniesFromBranch(categoryNames.get(i));
        }
    }

    public void downloadDataFromALlRegions() throws IOException {
//        createStructureSiteSet();
        for (int i = 0; i < regionNames.size(); i++) {
            collectCompaniesFromRegion(regionNames.get(i));
        }
    }

    public void collectCompaniesFromBranch(String branch) throws IOException {
//        createStructureSiteSet();
        SiteStructure siteStructure = findByName(siteStructuresByCategory, branch);
        for (int i = 0; i < siteStructure.getLinkList().size(); i++) {
            Document document = connectWith(siteStructure.getLinkList().get(i));
            Elements elements = document.select("div#srodek> div.lista");
            for (Element element : elements
            ) {
                Company company = downloadCompanyElement(element);
                company.setBranch(siteStructure.getName());
                company.setRegion(searchForRegion(element));
                companyService.save(company);
            }
        }
        log.info("All data collected");
    }

    public void collectCompaniesFromRegion(String region) throws IOException {
//        createStructureSiteSet();
        SiteStructure siteStructure = findByName(siteStructuresByRegion, region);
        for (int i = 0; i < siteStructure.getLinkList().size(); i++) {
            Document document = connectWith(siteStructure.getLinkList().get(i));
            Elements elements = document.select("div#srodek> div.lista");
            for (Element element : elements
            ) {
                Company company = downloadCompanyElement(element);
                company.setRegion(siteStructure.getName());
                company.setBranch(searchForBranch(element));
                companyService.save(company);
            }
        }
        log.info("All data collected");
    }

    public void collectCompaniesFromLink(String link) throws IOException {
        Document document = connectWith(link);
        Elements elements = document.select("div#srodek> div.lista");
        for (Element element : elements
        ) {
            Company company = downloadCompanyElement(element);
            companyService.save(company);
        }
    }

    private Company downloadCompanyElement(Element element) {
        Company company = new Company();
        company.setLink(searchForLink(element));
        company.setName(searchForName(element));
        company.setStreet(searchForStreet(element));
        company.setPostalCode(searchForPostalCode(element));
        company.setEmail(searchForEmail(element));
        company.setPhone(searchForPhone(element));
        company.setPublishedDate(searchForPublishedDate(element));
        return company;
    }


    protected String searchForBranch(Element element) {
        String description = element.select("div.szary-bordo").select("p").first().text();
        String branch = description
                .substring(description.indexOf("::") + 3, description.lastIndexOf("::"));

        return branch;
    }

    protected String searchForRegion(Element element) {
        String description = element.select("div.szary-bordo").select("p").first().text();
        String region = description.substring(description.lastIndexOf("::") + 3);
        return region;
    }

    protected Date searchForPublishedDate(Element element) {
        String dateText = element.select("div.szary-bordo").select("p").first().text();
        String onlyDate = dateText.substring(0, 10);
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat(DATE_FORMAT_PATTERN);
            date = simpleDateFormat.parse(onlyDate);
        } catch (ParseException e) {
            log.error("Error while parsing text " + onlyDate + "to date");
        }
        return date;
    }

    protected String searchForPhone(Element element) {
        String phone = element.select("div.kontener-270").last().select("p").first().text();
        return phone.replaceAll("tel.: ", "");
    }


    protected String searchForEmail(Element element) {
        String email = element.select("div.kontener-270").last().select("p").last().text();
        return email.replaceAll("e-mail: ", "");
    }

    protected String searchForPostalCode(Element element) {
        return element.select("div.kontener-270").first().select("p").last().text();
    }

    protected String searchForStreet(Element element) {
        return element.select("div.kontener-270").first().select("p").first().text();
    }

    protected String searchForLink(Element element) {
        return element.select("a").attr("abs:href");
    }

    protected String searchForName(Element element) {
        return element.select("div.szary-bordo a").text();
    }


    public Set<SiteStructure> createStructureSiteSet() throws IOException {
        Set<SiteStructure> siteStructureList = new HashSet<>();
        Document document = connectWith(URL);
        Elements elements = document.select("div#lewy > p");
        for (Element element : elements
        ) {
            SiteStructure siteStructure = downloadSiteStructure(element);
            if (siteStructureList.size() < 19) {
                siteStructureList.add(siteStructure);
                siteStructuresByCategory.add(siteStructure);
                categoryNames.add(siteStructure.getName());
            } else {
                siteStructureList.add(siteStructure);
                siteStructuresByRegion.add(siteStructure);
                regionNames.add(siteStructure.getName());
            }
        }
        log.info("Site structure collected!");
        return siteStructureList;
    }

    private SiteStructure downloadSiteStructure(Element element) {
        String link = element.select("a").attr("abs:href");
        String key = element.select("a").text();
        String name = key.replaceAll("[(0-9)]", "").trim();
        String stringNumber = String.valueOf(key.subSequence(key.indexOf("(") + 1, key.indexOf(")")));
        int number = Integer.valueOf(stringNumber);
        int lastPaginationNumber = (int) Math.ceil(number / 15.0);

        SiteStructure siteStructure = new SiteStructure();
        siteStructure.setName(name);
        siteStructure.setLink(link);
        siteStructure.setNumber(number);
        siteStructure.setLastPaginationNumber(lastPaginationNumber);
        siteStructure.setLinkList(createLinkList(link, lastPaginationNumber));
        return siteStructure;
    }

    private List<String> createLinkList(String link, int lastPaginationNumber) {
        List<String> list = new ArrayList<>();
        String correctLink = link.substring(0, link.length() - 1);
        for (int i = 1; i <= lastPaginationNumber; i++) {
            list.add(correctLink + i);
        }
        return list;
    }


}
