package com.wugang.src.blog.htmlparser;

/**
 * 深圳州富科技有限公司
 * Created by lwg on 2016/4/6.
 */
public interface IParser<DataType> {

    /**
     * 解析数据
     * @return
     */
    DataType parse(int page);

    /**
     * 解析详情数据
     * @return
     */
    String parseDetail(String url);
}
