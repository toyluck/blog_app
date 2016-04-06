package com.wugang.src.blog.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳州富科技有限公司
 * Created by lwg on 2016/4/6.
 */
public class DataModel {
    public int pageSize;//总页数
    public int totalCount;//数据条数
    public List<ItemDataModel> itemDataModels;

    public DataModel() {
        itemDataModels = new ArrayList<>();
    }

    public static class ItemDataModel {
        //标题
        public String title;
        //描述
        public String desc;
        // 详情地址
        public String detailUrl;
        // 评论和点赞
        public String comment;
    }
}
