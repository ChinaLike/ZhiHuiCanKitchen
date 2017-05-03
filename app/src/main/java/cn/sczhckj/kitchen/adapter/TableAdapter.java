package cn.sczhckj.kitchen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sczhckj.kitchen.R;
import cn.sczhckj.kitchen.data.bean.kitchen.DetailBean;
import cn.sczhckj.kitchen.data.constant.TypeCode;
import cn.sczhckj.kitchen.data.event.SendEvent;

/**
 * @ describe: 台桌列表
 * @ author: Like on 2017/1/6.
 * @ email: 572919350@qq.com
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {


    private Context mContext;

    private List<DetailBean> mList;

    public TableAdapter(Context mContext, List<DetailBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_table, parent, false));
    }

    @Override
    public void onBindViewHolder(TableViewHolder holder, final int position) {
        DetailBean bean = mList.get(position);
        holder.tableName.setText(bean.getTableName());
        if (bean.isSelect()) {
            holder.tableName.setTextColor(0xFF3C3C3C);
        } else {
            holder.tableName.setTextColor(0xFFFFFFFF);
        }
        //设置催单
        if (bean.getAttribute() == TypeCode.COMMON){
            holder.tableFlagParent.setVisibility(View.GONE);
        }else {
            holder.tableFlagParent.setVisibility(View.VISIBLE);
            if (bean.getAttribute() == TypeCode.REMINDER){
                holder.tableFlag.setText("催");
            }
        }
        holder.tableName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**手动点击菜品进行出菜,对于同一个菜品无需对指定那一桌出菜？*/
                EventBus.getDefault().post(new SendEvent(SendEvent.NON_AUTO_FOOD_FINISH, position, true));
            }
        });
    }

    public void notifyDataSetChanged(List<DetailBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    /**
     * 选中台桌
     *
     * @param isShow 是否选中
     */
    public void handleShow(boolean isShow) {
        if (mList != null) {
            if (isShow) {
                for (int i = 0; i < mList.size(); i++) {
                    if (i == 0) {
                        mList.get(i).setSelect(true);
                    } else {
                        mList.get(i).setSelect(false);
                    }
                }
            } else {
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).setSelect(false);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class TableViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.table_name)
        TextView tableName;
        @Bind(R.id.table_flag)
        TextView tableFlag;
        @Bind(R.id.table_flag_parent)
        FrameLayout tableFlagParent;

        public TableViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
