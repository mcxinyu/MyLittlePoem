package com.awds.mylittlepoem.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.model.Diary;
import com.awds.mylittlepoem.view.base.BaseActivity;

import java.util.List;

/**
 * Created by huangyuefeng on 2016/12/14.
 */

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.DiaryListViewHolder> {
    private BaseActivity mContent;
    private final List<Diary> mDiaryList;
    private RecyclerViewClickListener mListener;

    public void setRecyclerViewClickListener(RecyclerViewClickListener listener){
        mListener = listener;
    }

    public DiaryListAdapter(BaseActivity content, List<Diary> diaryList) {
        mContent = content;
        mDiaryList = diaryList;
    }

    @Override
    public DiaryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContent).inflate(R.layout.item_diary_list, parent, false);
        return new DiaryListViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(DiaryListViewHolder holder, int position) {
        final Diary diary = mDiaryList.get(position);
        if (position == 0){
            //如果是第一项，那么就显示年标签
            holder.mYearTextView.setVisibility(View.VISIBLE);
        } else {
            if (mDiaryList.get(position).getYearMonthChinese().equals(mDiaryList.get(position - 1).getYearMonthChinese())){
                //其他项不使用年标签
                holder.mYearTextView.setVisibility(View.GONE);
            } else {
                //除非遇到新的一年
                holder.mYearTextView.setVisibility(View.VISIBLE);
            }
        }
        holder.mYearTextView.setText(diary.getYearMonthChinese());
        holder.mTitleTextView.setText(diary.getCatalogueTitle());
        holder.mTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.onItemClickListener(diary);
                }
            }
        });
        holder.mTitleTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mListener != null){
                    mListener.onItemLongClickListener(diary);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiaryList.size();
    }

    class DiaryListViewHolder extends RecyclerView.ViewHolder{
        TextView mTitleTextView, mYearTextView;
        View contentView;

        public DiaryListViewHolder(View itemView) {
            super(itemView);
            contentView = itemView;
            mTitleTextView = (TextView) itemView.findViewById(R.id.item_diary_title);
            mYearTextView = (TextView) itemView.findViewById(R.id.item_diary_year);
        }
    }

    public interface RecyclerViewClickListener {
        void onItemClickListener(Diary diary);
        void onItemLongClickListener(Diary diary);
    }
}
