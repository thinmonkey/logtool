package zhanghao.com.logtool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhanghao.log.LogBean;

import java.util.List;

/**
 * Created by zhanghao.
 * 2017/11/1-10:38
 */

public class LogAdapter extends BaseAdapter {

    private List<LogBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public LogAdapter(Context context, List<LogBean> logBeanList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_log, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tv_log);
            holder.llLogDetail = (LinearLayout) convertView.findViewById(R.id.ll_log_detail);
            holder.tvHead = (TextView) convertView.findViewById(R.id.tv_head);
            holder.tvParam = (TextView) convertView.findViewById(R.id.tv_param);
            holder.tvResponse = (TextView) convertView.findViewById(R.id.tv_response);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mDatas.get(position).getTag());
        holder.tvHead.setText("\t   heads :Content-Type: application/json; charset=UTF-8\n" +
                "    Content-Length: 314\n" +
                "    Host: tc-com.g-netlink.net\n" +
                "    Connection: Keep-Alive\n" +
                "    Accept-Encoding: gzip\n" +
                "    User-Agent: okhttp/3.8.0\n" +
                "    Content-Type: application/json; charset=utf-8\n" +
                "    Accept: application/json\n" +
                "    language: zh_CN");
        holder.tvParam.setText("{\"language\":\"zh_CN\"," +
                "\"password\":\"QRB0axphZQF809ac0jH0Eb1D6FBfhgNQfaE83s2Wm7nfsAIj43zjCt" +
                "+B1mIia20KMlXfnzvE1MIaczU4w+xSDmlSRngYYugMgCBmwzSZ/7xlodrwnpE53HaeN3/ChCjPaB" +
                "+6V80+T/PRGwYSmpSCJ4SXyJd4Y8VtcWAxDFsLvoA=\",\"platform\":\"NON-CMA\"," +
                "\"registrationId\":\"190e35f7e07cae8b02b\",\"systemType\":\"Android\"," +
                "\"username\":\"13591667927\"}");
        holder.tvResponse.setText("{\"userId\":null,\"accessToken\":null,\"refreshToken\":null," +
                "\"expiresIn\":null,\"idToken\":null,\"tcToken\":null,\"alias\":null," +
                "\"platform\":null,\"resultCode\":\"30011\",\"resultMessage\":\"User account is " +
                "locked for user\"}");
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.llLogDetail.setVisibility(holder.llLogDetail.getVisibility() == View
                        .VISIBLE ? View.GONE : View.VISIBLE);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
        LinearLayout llLogDetail;
        TextView tvHead;
        TextView tvParam;
        TextView tvResponse;
    }
}
