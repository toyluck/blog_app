package com.wugang.src.blog.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wugang.src.blog.R;


public class CustomEmptyView extends LinearLayout
{

    private TextView mBarTv;

    private ProgressBar mBar;

    private ImageView mImageView;

    public boolean isLoadFails;

    public CustomEmptyView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        ViewInit(context, attrs);
    }

    public CustomEmptyView(Context context)
    {
        super(context);
        ViewInit(context, null);
    }

    private void ViewInit(Context context, AttributeSet attrs)
    {
        View view;
        view = LayoutInflater.from(context).inflate(
                R.layout.layout_custom_emptyview, this);
        mBarTv = (TextView) view.findViewById(R.id.empty_text);
        mBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mImageView = (ImageView) view.findViewById(R.id.empty_img);
    }

    public void setLoadFailure()
    {
        isLoadFails = true;
        setBarGone();
        setClickable(true);
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setImageResource(R.mipmap.load_fail_nol);
    }

    public void setLoadDataNull()
    {
        isLoadFails = false;
        setBarGone();
        setClickable(false);
        //        mImageView.setVisibility(View.VISIBLE);
        //        mImageView.se mBarTv.setVisibility(View.GONE);tImageResource(R.mipmap.load_null);
        mBarTv.setVisibility(View.VISIBLE);
        mBarTv.setText("暂无数据");
    }

    public void setLoadDataProgress()
    {
        isLoadFails = false;
        //        mImageView.setVisibility(View.VISIBLE);
        //        mImageView.se mBarTv.setVisibility(View.GONE);tImageResource(R.mipmap.load_null);
        mBar.setVisibility(View.VISIBLE);
        mBarTv.setVisibility(View.VISIBLE);
        mBarTv.setText("正在加载。。。");
    }

    public void setBarGone()
    {
        mBarTv.setVisibility(View.GONE);
        mBar.setVisibility(View.GONE);
    }

    public void setGone()
    {
        mImageView.setVisibility(View.GONE);
        mBarTv.setVisibility(View.GONE);
        mBar.setVisibility(View.GONE);
    }

    public void setBarVisibility()
    {
        mBarTv.setText("正在加载。。。");
        mImageView.setVisibility(View.GONE);
        mBarTv.setVisibility(View.VISIBLE);
        mBar.setVisibility(View.VISIBLE);
    }
}
