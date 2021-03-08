import parser.CategoryParser;
import parser.HtmlParser;
import pojo.Item;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {
    public static void main(String[] args)  {

        HtmlParser compCategory = new CategoryParser("https://allegro.pl/kategoria/komputery?bmatch=cl-e2101-d3720-c3682-ele-1-4-0304");
        HtmlParser cosmeticCategory = new CategoryParser("https://allegro.pl/kategoria/higiena-jamy-ustnej-257760?bmatch=e2101-d3720-c3682-hea-1-4-0304");
        HtmlParser coffeeCategory = new CategoryParser("https://allegro.pl/kategoria/produkty-spozywcze-kawa-74030?order=qd&bmatch=e2101-d3720-c3682-sup-1-4-0304");

        ExecutorService executor = Executors.newFixedThreadPool(5);
        Future<List<Item>> itemCompFutuer = executor.submit(compCategory);
        Future<List<Item>> itemCosmeticFutuer = executor.submit(cosmeticCategory);
        Future<List<Item>> itemCoffeeFutuer = executor.submit(coffeeCategory);

        executor.shutdown();

        String home = System.getProperty("user.home");
        Path path = Paths.get(home, "Desktop", "DanylkoHtmlParser.csv");
        File cvsFile = new File(path.toString());
        try {
            PrintWriter writer = new PrintWriter(cvsFile);
            StringBuilder sb = new StringBuilder();
            List<Item> itemsComp = itemCompFutuer.get();
            List<Item> itemsCosmetic = itemCosmeticFutuer.get();
            List<Item> itemsCoffee = itemCoffeeFutuer.get();

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

            writer.close();
        } catch (FileNotFoundException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }
}
