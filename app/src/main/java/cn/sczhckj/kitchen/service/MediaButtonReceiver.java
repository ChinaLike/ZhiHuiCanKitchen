package cn.sczhckj.kitchen.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

import cn.sczhckj.kitchen.until.show.L;

/**
 * @ describe:  广播接收器
 * @ author: Like on 2017-01-09.
 * @ email: 572919350@qq.com
 */

public class MediaButtonReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        L.d("测试001");
        /**获取Action*/
        String intentAction = intent.getAction();
        /**获取KeyEvent对象*/
        KeyEvent keyEvent = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
        if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)){
            int keyCode = keyEvent.getKeyCode();
            if (KeyEvent.KEYCODE_MEDIA_NEXT == keyCode){
                L.d("测试1");
            }
            if (KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE == keyCode) {
                L.d("测试2");
            }
            if (KeyEvent.KEYCODE_HEADSETHOOK == keyCode) {
                L.d("测试3");
            }
            if (KeyEvent.KEYCODE_MEDIA_PREVIOUS == keyCode) {
                L.d("测试4");
            }
            if (KeyEvent.KEYCODE_MEDIA_STOP == keyCode) {
                L.d("测试5");
            }
        }
    }
}
