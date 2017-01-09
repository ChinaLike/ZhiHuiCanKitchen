package cn.sczhckj.kitchen.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sczhckj.kitchen.R;
import cn.sczhckj.kitchen.data.bean.kitchen.DoneBean;
import cn.sczhckj.kitchen.listenner.OnItemClickListenner;

/**
 * @ describe:
 * @ author: Like on 2017/1/6.
 * @ email: 572919350@qq.com
 */

public class PrintAdapter extends RecyclerView.Adapter<PrintAdapter.PrintViewHolder> {

    private Context mContext;

    private List<DoneBean> mList;

    private OnItemClickListenner onItemClickListenner;

    public PrintAdapter(Context mContext, List<DoneBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public PrintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PrintViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_print, parent, false));
    }

    @Override
    public void onBindViewHolder(final PrintViewHolder holder, final int position) {
        final DoneBean bean = mList.get(position);
        holder.foodName.setText(bean.getName());
        holder.tableName.setText(bean.getTableName());
        if (bean.getPrintCount() > 0) {
            holder.remark.setVisibility(View.VISIBLE);
            holder.remark.setText("已补打" + bean.getPrintCount() + "次  " + bean.getLastPrint());
        } else {
            holder.remark.setVisibility(View.GONE);
        }
        if (bean.isSelect()) {
            holder.printParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.breviary_bg));
            holder.view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.breviary_bg));
            holder.foodName.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.tableName.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.printParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.line));
            holder.foodName.setTextColor(ContextCompat.getColor(mContext, R.color.print_item_text));
            holder.tableName.setTextColor(ContextCompat.getColor(mContext, R.color.print_item_text));
        }
        holder.printParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (DoneBean item : mList) {
                    item.setSelect(false);
                }
                mList.get(position).setSelect(true);
                notifyDataSetChanged();
                onItemClickListenner.onClick(view, holder.getLayoutPosition(), bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void notifyDataSetChanged(List<DoneBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListenner(OnItemClickListenner onItemClickListenner) {
        this.onItemClickListenner = onItemClickListenner;
    }

    static class PrintViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.food_name)
        TextView foodName;
        @Bind(R.id.table_name)
        TextView tableName;
        @Bind(R.id.remark)
        TextView remark;
        @Bind(R.id.print_parent)
        RelativeLayout printParent;
        @Bind(R.id.print_view)
        View view;

        public PrintViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
