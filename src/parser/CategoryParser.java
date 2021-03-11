package parser;

import org.jsoup.HttpStatusException;
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

public class CategoryParser implements HtmlParser {

    private static final String MAIN_DIV = ".mx7m_1.mnyp_co.mlkp_ag";
    private static final String LINK_SELECTOR = "._w7z6o._uj8z7.meqh_en.mpof_z0.mqu1_16._9c44d_2vTdY";
    private static final String PRICE_SELECTOR = ".msa3_z4._9c44d_2K6FN";
    private static final String DISCOUNT_SELECTOR = ".mp0t_ji.mpof_vs._9c44d_1VS-Y._9c44d_3_DDQ.mpof_vs._9c44d_2MDwk";
    private static final String DISCOUNT_PERCENT_SELECTOR = "._9c44d_1uHr2";
    private static final String FULL_PRICE_SELECTOR = ".mpof_uk.mqu1_ae._9c44d_18kEF.m9qz_yp._9c44d_2BSa0._9c44d_KrRuv";
    private static final String PROP_SELECTOR = ".mpof_uk.mgmw_ag.mp4t_0.m3h2_0.mryx_0.munh_0.mg9e_0.mvrt_0.mj7a_0.mh36_0._9c44d_3hPFO";
    private static final String PROP_VALUE_SELECTOR = ".mpof_uk.mp4t_0.m3h2_0.mryx_0.munh_0.mgmw_ia.mg9e_0.mj7a_0.mh36_0._9c44d_3n9Wf";
    private String url;
    private List<Item> items;

    public CategoryParser(String url) {
        this.url = url;
        items = new ArrayList<>();
    }

    public List<Item> getItemsFromHtml() {
        while (items.size() < 100) {
            try {
                Document doc = Jsoup.connect(url).userAgent(userAgent).timeout(timeout).get();
                Elements elements = doc.select(MAIN_DIV);
                for (Element element : elements) {
                    Item item = new Item();
                    Elements discountElement = element.select(DISCOUNT_SELECTOR);
                    if (!discountElement.isEmpty()) {
                        item.setName(element.select(LINK_SELECTOR).text());
                        item.setLink(element.select(LINK_SELECTOR).attr("href"));
                        item.setFullPrice(element.select(FULL_PRICE_SELECTOR).text());
                        item.setDiscount(element.select(DISCOUNT_PERCENT_SELECTOR).text());
                        item.setDiscountPrice(element.select(PRICE_SELECTOR).text());
                        Elements prop = element.select(PROP_SELECTOR);
                        Elements propValue = element.select(PROP_VALUE_SELECTOR);
                        Map<String, String> productProperties = new HashMap<>();
                        for (int i = 0; i < prop.size(); i++) {
                            productProperties.put(prop.get(i).text(), propValue.get(i).text());
                        }
                        item.setCharacteristics(productProperties);
                        items.add(item);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Extract from url: " + url);

            url = HtmlParser.super.nextPage(url);
        }
        return items;
    }
}
