package parser;

import pojo.Item;

import java.util.List;

public interface HtmlParser {
     String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.72 Safari/537.36";
     int timeout = 10 * 1000;

     List<Item> getItemsFromHtml();

     default String nextPage(String url) {
          String result;
          int index = url.indexOf("&p=");
          if (index != -1) {
               int page = Integer.parseInt(url.substring(index+3));
               if (page != 100) {
                    result = url.replace("&p="+ page, "&p=" + (page +1));
               } else
                    throw new RuntimeException("You have reached the 100th page");
          }
          else {
               result = url + "&p=1";
          }
          return result;
     }
}
