package com.wugang.src.blog.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wugang.src.blog.R;
import com.wugang.src.blog.WebViewDetailActivtiy;
import com.wugang.src.blog.htmlparser.impl.CSDNParser;
import com.wugang.src.blog.model.DataModel;
import com.wugang.src.blog.utils.ParserUtils;
import com.wugang.src.blog.view.PullLoadMoreRecyclerView;

import org.byteam.superadapter.recycler.BaseViewHolder;
import org.byteam.superadapter.recycler.OnItemClickListener;
import org.byteam.superadapter.recycler.SuperAdapter;

/**
 * 深圳州富科技有限公司
 * Created by lwg on 2016/4/6.
 */
public class ContentFragment extends Fragment implements ParserUtils.OnParserCompletedListener<DataModel>, PullLoadMoreRecyclerView.PullLoadMoreListener, OnItemClickListener {
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private SuperAdapter adapter;
    private DataModel dataModel;
    private int currentPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new PullLoadMoreRecyclerView(inflater.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) view;
        pullLoadMoreRecyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        loadData(currentPage);
    }

    private void loadData(int page) {
        ParserUtils.parse(new CSDNParser(), page, this);
    }

    @Override
    public void onCompleted(final DataModel dataModel) {
        this.dataModel = dataModel;
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setEmptyStatu(PullLoadMoreRecyclerView.STATU_SUCCESS);
        if(adapter==null)
            setDataAdapter(dataModel);
        else {
            adapter.addAll(dataModel.itemDataModels);
        }
    }

    private void setDataAdapter(final DataModel dataModel) {
        pullLoadMoreRecyclerView.setAdapter(adapter = new SuperAdapter<DataModel.ItemDataModel>(getContext(), dataModel.itemDataModels, R.layout.item) {
            @Override
            public void onBind(int viewType, BaseViewHolder holder, int position, DataModel.ItemDataModel item) {
                holder.setText(R.id.title, item.title);
                holder.setText(R.id.desc, item.desc);
                holder.setText(R.id.tv_comment, Html.fromHtml(item.comment));
            }
        });
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onError(Throwable t) {
        Snackbar.make(getView(), t.getMessage(), 0).show();
    }

    @Override
    public void onLoadMore() {
        if (dataModel.pageSize > currentPage) {
            currentPage++;
            loadData(currentPage);
        }else
            pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        DataModel.ItemDataModel dataModel = (DataModel.ItemDataModel) adapter.getList().get(position);
        Intent intent = new Intent(getActivity(),WebViewDetailActivtiy.class);
        intent.putExtra(WebViewDetailActivtiy.EXTRA_DATA,dataModel.detailUrl);
        startActivity(intent);
    }
}
