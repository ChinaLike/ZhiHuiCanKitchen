package cn.sczhckj.kitchen.until;

import android.graphics.Color;

/**
 * @describe: 颜色转换器
 * @author: Like on 2016/12/9.
 * @Email: 572919350@qq.com
 */

public class ColorUntils {


    /**
     * #FFFFFF转化成long型
     * @param color
     * @return
     */
    public static int stringToHex(String color) throws NumberFormatException {
        try {
            if (color!=null&&!"".equals(color)){
                return Color.parseColor(color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Color.parseColor("#FF0000");
    }

}
