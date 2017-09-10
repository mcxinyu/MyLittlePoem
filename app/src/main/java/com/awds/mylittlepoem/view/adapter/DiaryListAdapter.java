package com.awds.mylittlepoem.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.model.Diary;
import com.awds.mylittlepoem.view.base.BaseActivity;
import com.awds.mylittlepoem.view.widget.VerticalTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huangyuefeng on 2016/12/14.
 */

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.DiaryListViewHolder> {
    private BaseActivity mContent;
    private final List<Diary> mDiaryList;
    private RecyclerViewClickListener mListener;

    public void setRecyclerViewClickListener(RecyclerViewClickListener listener) {
        mListener = listener;
    }

    public DiaryListAdapter(BaseActivity content, List<Diary> diaryList) {
        mContent = content;
        mDiaryList = diaryList;
    }

    @Override
    public DiaryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiaryListViewHolder(LayoutInflater.from(mContent)
                .inflate(R.layout.item_diary_list, parent, false));
    }

    @Override
    public void onBindViewHolder(DiaryListViewHolder holder, int position) {
        final Diary diary = mDiaryList.get(position);
        if (position == 0) {
            //如果是第一项，那么就显示年标签
            holder.mItemDiaryYear.setVisibility(View.VISIBLE);
        } else {
            if (mDiaryList.get(position).getYearMonthChinese().equals(mDiaryList.get(position - 1).getYearMonthChinese())) {
                //其他项不使用年标签
                holder.mItemDiaryYear.setVisibility(View.GONE);
            } else {
                //除非遇到新的一年
                holder.mItemDiaryYear.setVisibility(View.VISIBLE);
            }
        }
        holder.mItemDiaryYear.setText(diary.getYearMonthChinese());
        holder.mItemDiaryTitle.setText(diary.getCatalogueTitle());
        holder.mItemDiaryTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClickListener(diary);
                }
            }
        });
        holder.mItemDiaryTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mListener != null) {
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

    static class DiaryListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_diary_title)
        VerticalTextView mItemDiaryTitle;
        @BindView(R.id.item_diary_year)
        VerticalTextView mItemDiaryYear;

        public DiaryListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface RecyclerViewClickListener {
        void onItemClickListener(Diary diary);

        void onItemLongClickListener(Diary diary);
    }
}
