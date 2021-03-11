import parser.CategoryParser;
import parser.HtmlParser;
import pojo.Item;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args)  {

        HtmlParser compCategory = new CategoryParser("https://allegro.pl/kategoria/komputery?bmatch=cl-e2101-d3720-c3682-ele-1-4-0304");
        HtmlParser cosmeticCategory = new CategoryParser("https://allegro.pl/kategoria/higiena-jamy-ustnej-257760?bmatch=e2101-d3720-c3682-hea-1-4-0304");
        HtmlParser coffeeCategory = new CategoryParser("https://allegro.pl/kategoria/produkty-spozywcze-kawa-74030?order=qd&bmatch=e2101-d3720-c3682-sup-1-4-0304");

        List<Item> itemsComp = compCategory.getItemsFromHtml();
        List<Item> itemsCosmetic = cosmeticCategory.getItemsFromHtml();
        List<Item> itemsCoffee = coffeeCategory.getItemsFromHtml();

        try {
            String home = System.getProperty("user.home");
            Path path = Paths.get(home, "Desktop", "AlegroHtmlParserDanylko.csv");
            File cvsFile = path.toFile();

            PrintWriter writer = new PrintWriter(cvsFile);
            StringBuilder sb = new StringBuilder();

            sb.append("Name");
            sb.append(";");
            sb.append("Link");
            sb.append(";");
            sb.append("Full price");
            sb.append(';');
            sb.append("Discount");
            sb.append(';');
            sb.append("Discount Price");
            sb.append(';');
            sb.append("Characteristic");

            sb.append("\n\nKomputery");
            writer.println(sb.toString());
            itemsComp.stream().limit(100).forEach(writer::println);
            writer.println("\nHigiena jamy ustnej");
            itemsCosmetic.stream().limit(100).forEach(writer::println);
            writer.println("\nProdukty spozywcze kawa");
            itemsCoffee.stream().limit(100).forEach(writer::println);

            System.out.println("Done! The result saved to file: " + path.toString());

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
