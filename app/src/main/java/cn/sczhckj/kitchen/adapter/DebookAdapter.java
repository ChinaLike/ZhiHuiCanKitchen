package cn.sczhckj.kitchen.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sczhckj.kitchen.R;
import cn.sczhckj.kitchen.data.bean.kitchen.DebookBean;

/**
 * @ describe: 退菜界面列表
 * @ author: Like on 2017-05-02.
 * @ email: 572919350@qq.com
 */

public class DebookAdapter extends RecyclerView.Adapter<DebookAdapter.DebookViewHolder> {

    private Context mContext;

    private List<DebookBean> mList;

    public DebookAdapter(Context mContext, List<DebookBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public DebookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DebookViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_debook, parent, false));
    }

    @Override
    public void onBindViewHolder(DebookViewHolder holder, int position) {
        DebookBean bean = mList.get(position);
        if (bean.isSelect()){
            holder.parent.setBackgroundColor(ContextCompat.getColor(mContext,R.color.breviary_bg));
            holder.debookFoodName.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            holder.debookTableName.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            holder.debookNumber.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            holder.debookTime.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            holder.currentTime.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        }else {
            holder.parent.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
            holder.debookFoodName.setTextColor(ContextCompat.getColor(mContext,R.color.black));
            holder.debookTableName.setTextColor(ContextCompat.getColor(mContext,R.color.black));
            holder.debookNumber.setTextColor(ContextCompat.getColor(mContext,R.color.black));
            holder.debookTime.setTextColor(ContextCompat.getColor(mContext,R.color.debook_bg));
            holder.currentTime.setTextColor(ContextCompat.getColor(mContext,R.color.debook_bg));
        }
        holder.debookFoodName.setText(bean.getName());
        holder.debookTableName.setText(bean.getTableName());
        holder.debookNumber.setText(bean.getDebookCount() + "" + bean.getDebookUnit());
        holder.debookTime.setText("退菜时间：" + bean.getDebookTime());
        holder.currentTime.setText("当前时间："+currentTime());
    }

    public void notifyDataSetChanged(List<DebookBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    /**
     * 获取当前时间
     * @return
     */
    private String currentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    static class DebookViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.debook_food_name)
        TextView debookFoodName;
        @Bind(R.id.debook_table_name)
        TextView debookTableName;
        @Bind(R.id.debook_number)
        TextView debookNumber;
        @Bind(R.id.debook_time)
        TextView debookTime;
        @Bind(R.id.current_time)
        TextView currentTime;
        @Bind(R.id.debook_parent)
        LinearLayout parent;

        public DebookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
