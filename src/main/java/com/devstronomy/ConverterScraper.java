package com.devstronomy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

/**
 * Reads data from planets https://nssdc.gsfc.nasa.gov/planetary/factsheet/  and insert them into .csv file.
*/

@Component
@Order(1)
final class ConverterScraper implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(ConverterScraper.class);

    private static final String PLANETS_FROM_SCRAPER_CSV_PATH = "./data/raw/";
    private static final String PLANETS_FROM_SCRAPER_CSV_NAME = "planetsFromScraper.csv";
    @Override
    public void run(String... args) {
        clean();
        scrape();
    }

    private void clean() {
        try {
            File dir = new File(".");
            String loc = dir.getCanonicalPath() + File.separator + PLANETS_FROM_SCRAPER_CSV_PATH + PLANETS_FROM_SCRAPER_CSV_NAME;
            new FileWriter(loc, false).close();
        } catch (
                FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scrape() {
        try {
            Document doc = Jsoup.connect("https://nssdc.gsfc.nasa.gov/planetary/factsheet/").get();
            System.out.printf("\nWebsite Title: %s\n\n", doc.getElementsByTag("h1"));
            Elements rows = doc.getElementsByTag("tr");
            int i = 0;
            for (Element row : rows) {
                if (i < rows.size() - 1) {
                    if (i != 0) {
                        addNewLine();
                    }
                    List<Element> childs = row.children();
                    if (childs.size() > 0) {
                        if (i == 0) {
                            processFirstRow(childs);
                        } else {
                            processOtherRows(childs);
                        }
                    }
                }
                i++;
            }
        } catch (
                FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void processMathematicalExpression(List<Node> listElementu) throws IOException {
        for (Node element : listElementu) {
            if ((element.childNodes().size() > 0)) {
                processMathematicalExpression(element.childNodes());
            } else {
                if (!element.toString().equals("&nbsp;")) {
                    System.out.println("Basic element === " + element.toString());
                    addComposedStringToCsv(element.toString());
                } else {
                    System.out.println("Non-trivial element: Scrape again!" + element.toString());
                }
            }
        }
    }

    public static void processFirstRow(List<Element> listElementu) throws IOException {
        for (Element element : listElementu) {
            if ((element.getElementsByTag("sup")).size() > 0) {
                processMathematicalExpression(element.childNodes());
            }
            if ((element.children().size() > 0) && (((Element) element).getElementsByTag("sup")).size() == 0) {
                processFirstRow(element.children());
            } else {
                if (!element.childNodes().get(0).toString().equals("&nbsp;") &&
                        (((Element) element).getElementsByTag("sup")).size() == 0) {
                    System.out.println("Basic element === " + element.toString());
                    addFirstSeparatorAndStringToCsv(element.childNodes().get(0).toString());
                } else {
                    System.out.println("Non-trivial element: Scrape again!" + element.toString());
                }
            }
        }
    }

    public static void processOtherRows(List<Element> listElementu) throws IOException {
        int j = 0;
        for (Element element : listElementu) {
            if ((element.getElementsByTag("sup")).size() > 0) {
                processMathematicalExpression(element.childNodes());
            }
            if ((element.children().size() > 0) && (((Element) element).getElementsByTag("sup")).size() == 0) {
                processOtherRows(element.children());
            } else {
                if (!element.childNodes().get(0).toString().equals("&nbsp;") &&
                        (((Element) element).getElementsByTag("sup")).size() == 0) {
                    System.out.println("Basic element === " + element.toString());
                    if (j == 0) {
                        addNoSeparatorAndStringToCsv(element.childNodes().get(0).toString());
                    } else {
                        addFirstSeparatorAndStringToCsv(element.childNodes().get(0).toString());
                    }
                } else {
                    System.out.println("Non-trivial element: Scrape again!" + element.toString());
                }
            }
            j++;
        }
    }

    public static void addNoSeparatorAndStringToCsv(String string) throws IOException {
        File dir = new File(".");
        String loc = dir.getCanonicalPath() + File.separator + PLANETS_FROM_SCRAPER_CSV_PATH + PLANETS_FROM_SCRAPER_CSV_NAME;
        FileWriter fstream = new FileWriter(loc, true);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(string);
        out.close();
    }

    public static void addNewLine() throws IOException {
        File dir = new File(".");
        String loc = dir.getCanonicalPath() + File.separator + PLANETS_FROM_SCRAPER_CSV_PATH + PLANETS_FROM_SCRAPER_CSV_NAME;
        FileWriter fstream = new FileWriter(loc, true);
        BufferedWriter out = new BufferedWriter(fstream);
        out.newLine();
        out.close();
    }

    public static void addFirstSeparatorAndStringToCsv(String string) throws IOException {
        File dir = new File(".");
        String loc = dir.getCanonicalPath() + File.separator + PLANETS_FROM_SCRAPER_CSV_PATH + PLANETS_FROM_SCRAPER_CSV_NAME;
        FileWriter fstream = new FileWriter(loc, true);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(";" + string);
        out.close();
    }

    public static void addComposedStringToCsv(String string) throws IOException {
        File dir = new File(".");
        String loc = dir.getCanonicalPath() + File.separator + PLANETS_FROM_SCRAPER_CSV_PATH + PLANETS_FROM_SCRAPER_CSV_NAME;
        FileWriter fstream = new FileWriter(loc, true);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(" " + string);
        out.close();
    }
}
