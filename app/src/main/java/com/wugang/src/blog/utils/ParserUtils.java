package com.wugang.src.blog.utils;

import com.wugang.src.blog.htmlparser.IParser;
import com.wugang.src.blog.model.DataModel;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 深圳州富科技有限公司
 * Created by lwg on 2016/4/6.
 */
public class ParserUtils {
    /**
     * 解析xml工具类
     *
     * @param parser
     */
    public static void parse(final IParser<DataModel> parser, final int page, final OnParserCompletedListener<DataModel> onParserCompletedListener) {
        Observable.just("").subscribeOn(Schedulers.io()).map(new Func1<Object, DataModel>() {
            @Override
            public DataModel call(Object dataModel) {
                return parser.parse(page);
            }
        }) .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DataModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onParserCompletedListener.onError(e);
            }

            @Override
            public void onNext(DataModel dataModel) {
                onParserCompletedListener.onCompleted(dataModel);
            }
        });
    }


    /**
     * 解析xml工具类
     *
     * @param parser
     */
    public static void parseDetail(final IParser<DataModel> parser, final String url,
                                   final OnParserCompletedListener<String> onParserCompletedListener) {
        Observable.just("").subscribeOn(Schedulers.io()).map(new Func1<Object, String>() {
            @Override
            public String call(Object dataModel) {
                return parser.parseDetail(url);
            }
        }) .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onParserCompletedListener.onError(e);
            }

            @Override
            public void onNext(String dataModel) {
                onParserCompletedListener.onCompleted(dataModel);
            }
        });
    }

    public interface OnParserCompletedListener<T> {
        void onCompleted(T t);

        void onError(Throwable t);
    }
}
