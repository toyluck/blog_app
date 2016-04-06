package com.wugang.src.blog.htmlparser.impl;

import com.wugang.src.blog.htmlparser.IParser;
import com.wugang.src.blog.model.DataModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 深圳州富科技有限公司
 * Created by lwg on 2016/4/6.
 * csdn 解析器
 */
public class CSDNParser implements IParser<DataModel> {
    public static final String BASE_URL = "http://blog.csdn.net";

    @Override
    public DataModel parse(int page){
        DataModel dataModel = new DataModel();
        try {
            Document document = Jsoup.connect(BASE_URL + String.format("/lmj623565791/article/list/%d", page))
                    .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").get();
            Elements listItem = document.getElementsByClass("list_item");
            for (int i = 0; i < listItem.size(); i++) {
                Node node = listItem.get(i);
                parseData(node, dataModel);
            }
            String papelist = document.getElementById("papelist").child(0).text();
            Pattern p=Pattern.compile("(\\d+)");
            Matcher m=p.matcher(papelist);
            if (m.find()){
                dataModel.totalCount = Integer.parseInt(m.group(1));
            }
            if(m.find()){
                dataModel.pageSize = Integer.parseInt(m.group(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dataModel;
    }

    @Override
    public String parseDetail(String url) {
        try {
            Document document = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").get();
            Element article_details = document.getElementById("article_details");
            return article_details.html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取标题
     *
     * @param node
     * @param dataModel
     * @return
     */
    void parseData(Node node, DataModel dataModel) {
        DataModel.ItemDataModel itemDataModel = new DataModel.ItemDataModel();
        Element link_title = ((Element) node).getElementsByClass("link_title").get(0);
        itemDataModel.detailUrl = BASE_URL + link_title.child(0).attr("href");
        itemDataModel.title = link_title.child(0).text();
        itemDataModel.desc = ((Element) node).getElementsByClass("article_description").get(0).text();
        itemDataModel.comment = ((Element) node).getElementsByClass("article_manage").get(0).html();
        dataModel.itemDataModels.add(itemDataModel);
    }
}
