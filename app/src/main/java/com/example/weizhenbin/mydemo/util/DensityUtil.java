package com.example.weizhenbin.mydemo.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: CommonBar
 * @ProjectName Show
 * @Package com.weizhenbin.show.widget
 * @date 2016/2/29 14:02
 */
public class DensityUtil {
	// private static final String TAG = DensityUtil.class.getSimpleName();

	private float dmDensityDpi = 0.0f;
	private DisplayMetrics dm;
	private float scale = 0.0f;
	float fontScale;

	public DensityUtil(Context context) {
		dm = new DisplayMetrics();
		dm = context.getApplicationContext().getResources().getDisplayMetrics();
		setDmDensityDpi(dm.densityDpi);
		scale = getDmDensityDpi() / 160;
		fontScale = context.getResources().getDisplayMetrics().scaledDensity;
	}

	public float getDmDensityDpi() {
		return dmDensityDpi;
	}

	public void setDmDensityDpi(float dmDensityDpi) {
		this.dmDensityDpi = dmDensityDpi;
	}

	public int dip2px(float dipValue) {

		return (int) (dipValue * scale + 0.5f);

	}

	public int px2dip(float pxValue) {
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public int px2sp(float pxValue) {
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public int sp2px(float spValue) {
		return (int) (spValue * fontScale + 0.5f);
	}
}
