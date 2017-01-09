package cn.sczhckj.kitchen.animation;

import android.content.Context;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import cn.sczhckj.kitchen.until.AppSystemUntil;

/**
 * @ describe:动画实现
 * translationY:沿Y轴平移
 * translationX:沿X平移
 * rotationX:沿X轴旋转
 * rotationY:沿Y轴旋转
 * rotation:沿中心旋转
 * scaleX:沿X轴缩放
 * scaleY:沿Y轴缩放
 * alpha:透明变换
 * @ author: Like on 2017-01-09.
 * @ email: 572919350@qq.com
 */

public class AnimationImpl {

    private Context mContext;

    private final long DURATION = 300;

    public AnimationImpl(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 上边退出
     * @param view
     */
    public void upOut(Object view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view,"translationY", 0,-AppSystemUntil.height(mContext)),
                ObjectAnimator.ofFloat(view,"alpha", 1f,0f)
        );
        animatorSet.setDuration(DURATION).start();
    }

    /**
     * 上边进入
     * @param view
     */
    public void upIn(Object view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view,"translationY", -AppSystemUntil.height(mContext),0),
                ObjectAnimator.ofFloat(view,"alpha", 0f,1f)
        );
        animatorSet.setDuration(DURATION).start();
    }

    /**
     * 下边进入
     * @param view
     */
    public void downIn(Object view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view,"translationY", AppSystemUntil.height(mContext),0),
                ObjectAnimator.ofFloat(view,"alpha", 0f,1f)
        );
        animatorSet.setDuration(DURATION).start();
    }

    /**
     * 下边退出
     * @param view
     */
    public void downOut(Object view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view,"translationY", 0,AppSystemUntil.height(mContext)),
                ObjectAnimator.ofFloat(view,"alpha", 1f,0f)
        );
        animatorSet.setDuration(DURATION).start();
    }

}
