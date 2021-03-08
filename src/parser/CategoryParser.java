package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pojo.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class CategoryParser implements HtmlParser {

    private static final String mainDiv = ".mx7m_1.mnyp_co.mlkp_ag";
    private static final String linkSelector = "._w7z6o._uj8z7.meqh_en.mpof_z0.mqu1_16._9c44d_2vTdY";
    private static final String priceSelector = ".msa3_z4._9c44d_2K6FN";
    private static final String discountSelector = ".mp0t_ji.mpof_vs._9c44d_1VS-Y._9c44d_3_DDQ.mpof_vs._9c44d_2MDwk";
    private static final String discountPerSelector = "._9c44d_1uHr2";
    private static final String fullPriceSelector = ".mpof_uk.mqu1_ae._9c44d_18kEF.m9qz_yp._9c44d_2BSa0._9c44d_KrRuv";
    private static final String characSelector = ".mpof_uk.mgmw_ag.mp4t_0.m3h2_0.mryx_0.munh_0.mg9e_0.mvrt_0.mj7a_0.mh36_0._9c44d_3hPFO";
    private static final String characValueSelector = ".mpof_uk.mp4t_0.m3h2_0.mryx_0.munh_0.mgmw_ia.mg9e_0.mj7a_0.mh36_0._9c44d_3n9Wf";
    private String url;
    private List<Item> items;

    public CategoryParser(String url) {
        this.url = url;
        items = new ArrayList<>();
    }

    public List<Item> call() {
        int n = 1;
        while (items.size() < 100) {
            try {
                Document doc = Jsoup.connect(url).userAgent(userAgent).timeout(timeout).get();
                Elements elements = doc.select(mainDiv);
                for (Element element : elements) {
                    Item item = new Item();
                    Elements discountElement = element.select(discountSelector);
                    if (!discountElement.isEmpty()) {
                        item.setName(element.select(linkSelector).text());
                        item.setLink(element.select(linkSelector).attr("href"));
                        item.setFullPrice(element.select(fullPriceSelector).text());
                        item.setDiscount(element.select(discountPerSelector).text());
                        item.setDiscountPrice(element.select(priceSelector).text());
                        Elements character = element.select(characSelector);
                        Elements characterValue = element.select(characValueSelector);
                        Map<String, String> characteristic = new HashMap<>();
                        for (int i = 0; i < character.size(); i++) {
                            characteristic.put(character.get(i).text(), characterValue.get(i).text());
                        }
                        item.setCharacteristics(characteristic);
                        items.add(item);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            url = HtmlParser.super.nextPage(url);
            System.out.println(n++);
        }
        return items;
    }
}
