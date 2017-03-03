package com.wangxw.zhihudaily.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wangxw.zhihudaily.bean.Comment;

import java.util.List;

/**
 * Created by wangxw on 2017/3/3 0003 13:46.
 * E-mail:wangxw725@163.com
 * function:
 */
public class CommentsAdapter extends RecyclerView.Adapter {

    private static final int TITLE = 1;
    private static final int LONG_CONTENT = 2;
    private static final int SHORT_CONTENT = 3;

    private List<Comment> longCommentList;
    private List<Comment> shorCommentList;

    public void setLongCommentList(List<Comment> commentList) {
        longCommentList = commentList;
        notifyDataSetChanged();
    }

    public void setShortCommentList(List<Comment> commentList) {
        shorCommentList = commentList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TITLE;
        }
        return super.getItemViewType(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return longCommentList ==null ? 3 : (longCommentList.size()+2);
    }
}
