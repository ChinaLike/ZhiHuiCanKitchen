package cn.sczhckj.kitchen.mode;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

import cn.sczhckj.kitchen.R;
import cn.sczhckj.kitchen.data.bean.kitchen.TodoBean;

/**
 * @ describe: 新菜提醒
 * @ author: Like on 2017-04-18.
 * @ email: 572919350@qq.com
 */

public class MusicImpl {

    private MediaPlayer mMediaPlayer;
    /**
     * 刷新前菜品集合
     */
    private List<TodoBean> mList;
    /**
     * 刷新后集合
     */
    private List<TodoBean> list;

    public MusicImpl(Context mContext) {
        mList = new ArrayList<>();
        list = new ArrayList<>();
        mMediaPlayer = MediaPlayer.create(mContext, R.raw.dd);
    }

    /**
     * 播放
     */
    public void play() {
        boolean isHaveNewFood = isHaveNewFood();
        if (isHaveNewFood) {
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
            }
        }
    }

    /**
     * 是否有新菜品
     *
     * @return
     */
    private boolean isHaveNewFood() {
        if ((mList == null || mList.size() == 0) && list.size() == 0) {
            return false;
        } else {
            int num = mList.size();//刷新前大小
            int currNum = list.size();//刷新后大小
            if (currNum > num) {
                return true;
            } else if (currNum == num) {
                if (mList.get(num - 1).getCount() == list.get(currNum - 1).getCount()) {
                    return false;
                }
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 菜品刷新前需要保存提交前菜品数量
     *
     * @param bean
     * @param list
     */
    public void setNum(TodoBean bean, List<TodoBean> list) {
        mList.clear();
        if (bean.getDetails() == null || bean.getDetails().size() == 0) {
            return;
        }
        mList.add(bean);
        for (TodoBean tdBean : list) {
            mList.add(tdBean);
        }
    }

    /**
     * 刷新后菜品集合
     *
     * @param list
     */
    public void setList(List<TodoBean> list) {
        this.list.clear();
        for (TodoBean bean : list) {
            this.list.add(bean);
        }
    }

}
