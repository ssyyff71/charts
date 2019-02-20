package mytest.demo.KAD.util;


import mytest.demo.KAD.KBucketImprovement.Contact;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author sophie
 * @create 2019-01-23 上午10:09
 **/
public class WebSpider {
    /**
     *      * 下载HTML页面源码
     *   
     *     
     */

    public static String getHtml(String url, String encoding) {
        StringBuffer sb = new StringBuffer();
        BufferedReader bf = null;
        InputStreamReader isr = null;
        try {
            URL url1 = new URL(url);
            URLConnection uc = url1.openConnection();
            uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            isr = new InputStreamReader(uc.getInputStream(), encoding);
            bf = new BufferedReader(isr);

            String temp = null;
            while ((temp = bf.readLine()) != null) {
                sb.append(temp + "\n");
            }

        } catch (MalformedURLException e) {
            System.out.println("网页打开失败，请重新输入网址。");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("网页打开失败,请检查网络。");
            e.printStackTrace();
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (isr != null) {
                    try {
                        isr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sb.toString();

    }

    //
//    /**
//     * 读取profilelist信息
//     * @param url
//     * @param encoding
//     * @param fileName
//     * @return
//     */
//    public static String getGoalDate(String url, String encoding, String fileName) {
//        String html = getHtml(url, encoding);
//        Document document = Jsoup.parse(html);
//        Elements elements = document.getElementById("profilelist").select("tbody");
//        List<String>  maps = new ArrayList<>();
//        for (int i =0;i<elements.size();i++) {
//            Element el=elements.get(i);
//            Elements children = el.children();
//            for(int j=1;j<children.size();j++){
//                Element element = children.get(j);
//                String hashPre = element.child(0).text();
//                String speed=element.child(4).text();
//                String capacity=element.child(5).text();
//                String integration=element.child(6).text();
//            }
//        }
//        return maps.toString();
//    }
    public static void getSCValue(String url, String encoding, Contact contact) {
        String html = getHtml(url, encoding);
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementById("viewprofile").select("pre");
        String profile = elements.get(0).text();
        String[] split = profile.split("\n");
        if(split.length>2){
            String speed = split[3].split(":")[1];
            String capacity = split[4].split(":")[1];
            String integration = split[5].split(":")[1];
            float speedValue = Float.parseFloat(speed);
            float capacityValue = Float.parseFloat(capacity);
            float integrationValue = Float.parseFloat(integration);
            contact.setSpeedValue(speedValue);
            contact.setCapacityValue(capacityValue);
        }
    }

    public static void getContactSC(Contact contact, String hash) {
        String newUrl = "http://127.0.0.1:7657/viewprofile?peer=" + hash;
        getSCValue(newUrl, "utf-8", contact);
    }


}