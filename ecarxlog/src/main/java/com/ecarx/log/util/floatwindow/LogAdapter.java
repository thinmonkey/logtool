package com.ecarx.log.util.floatwindow;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecarx.log.R;

import java.util.List;

/**
 * Created by zhanghao.
 * 2017/11/1-10:38
 */

public class LogAdapter extends BaseAdapter {

    private List<LogShowModel> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public LogAdapter(Context context, List<LogShowModel> logBeanList) {
        this.mContext = context;
        this.mDatas = logBeanList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.float_item_log, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tv_log_show);
            holder.llLogDetail = (LinearLayout) convertView.findViewById(R.id.ll_log_detail);
            holder.tvHead = (TextView) convertView.findViewById(R.id.tv_head);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(position + "、" + mDatas.get(position).getTitle());
        holder.tvHead.setText(mDatas.get(position).getDetail());
        holder.llLogDetail.setVisibility(mDatas.get(position).isShowDetail() ? View.VISIBLE : View
                .GONE);
        holder.textView.setBackgroundColor(mDatas.get(position).isAlreadyRead() ? Color
                .parseColor("#c4c4c4") : Color.WHITE);
        holder.textView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatas.get(position).setAlreadyRead(true);
                        mDatas.get(position).setShowDetail(!mDatas.get(position).isShowDetail());
                        holder.llLogDetail.setVisibility(mDatas.get(position).isShowDetail() ?
                                View.VISIBLE : View.GONE);
//                        notifyDataSetChanged();
                    }
                });
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
        LinearLayout llLogDetail;
        TextView tvHead;
    }
}
