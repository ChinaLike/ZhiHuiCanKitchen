package cn.sczhckj.kitchen.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sczhckj.kitchen.R;
import cn.sczhckj.kitchen.data.bean.kitchen.TodoBean;
import cn.sczhckj.kitchen.data.event.SendEvent;

/**
 * @ describe:  代加工菜品列表
 * @ author: Like on 2017/1/6.
 * @ email: 572919350@qq.com
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private Context mContext;

    private List<TodoBean> mList;

    public TodoAdapter(Context mContext, List<TodoBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TodoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        final TodoBean bean = mList.get(position);
        holder.foodIndex.setText("" + disposeIndex(position + 2));
        if (position == 0) {
            holder.foodIndexParent.setBackgroundResource(R.drawable.shape_dark_green);
        } else if (position == 1) {
            holder.foodIndexParent.setBackgroundResource(R.drawable.shape_light_green);
        } else {
            holder.foodIndexParent.setBackgroundResource(R.drawable.shape_gray);
        }
        /**设置选中的背景*/
        if (bean.isSelect()){
            /**被选中*/
            holder.parent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.breviary_bg));
            holder.foodName.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.foodCount.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.foodUtil.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }else {
            /**位置选中*/
            holder.parent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.foodName.setTextColor(ContextCompat.getColor(mContext, R.color.print_item_text));
            holder.foodCount.setTextColor(ContextCompat.getColor(mContext, R.color.print_item_text));
            holder.foodUtil.setTextColor(ContextCompat.getColor(mContext, R.color.print_item_text));
        }

        holder.foodName.setText(bean.getName());
        holder.foodCount.setText(bean.getCount() + "");
        holder.foodUtil.setText(bean.getUnit());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**手动点击菜品进行出菜*/
                EventBus.getDefault().post(new SendEvent(SendEvent.NON_AUTO_FOOD_FINISH, bean, false));
            }
        });

    }

    public void notifyDataSetChanged(List<TodoBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 处理下标
     *
     * @param postion
     * @return
     */
    private String disposeIndex(int postion) {
        if (postion < 10) {
            return "0" + postion;
        } else {
            return "" + postion;
        }
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.food_index)
        TextView foodIndex;
        @Bind(R.id.food_index_parent)
        FrameLayout foodIndexParent;
        @Bind(R.id.item_parent)
        RelativeLayout parent;
        @Bind(R.id.food_name)
        TextView foodName;
        @Bind(R.id.food_count)
        TextView foodCount;
        @Bind(R.id.food_util)
        TextView foodUtil;

        public TodoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
