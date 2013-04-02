package com.exhibition.utils;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

public class TextViewLinkUtil {

	/**
	 * 设置字体(default,default-bold,monospace,serif,sans-serif)
	 * 
	 * @param spannableString
	 * @param typeface
	 * @param starIndex
	 * @param endIndex
	 */
	public void getTypefaceSpan(SpannableString spannableString,
			String typeface, int starIndex, int endIndex) {
		spannableString.setSpan(new TypefaceSpan(typeface), starIndex,
				endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 设置绝对字体大小（单位：像素）
	 * 
	 * @param spannableString
	 * @param pixel
	 * @param starIndex
	 * @param endIndex
	 */
	public void getAbsoluteSizeSpan(SpannableString spannableString, int pixel,
			int starIndex, int endIndex) {
		spannableString.setSpan(new AbsoluteSizeSpan(pixel), starIndex,
				endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 设置绝对字体大小（单位：像素）， flag为ture,表示前面的字体大小单位为dip,flase字体大小单位为像素
	 * 
	 * @param spannableString
	 * @param pixel
	 * @param flag
	 * @param starIndex
	 * @param endIndex
	 */
	public void getAbsoluteSizeSpan(SpannableString spannableString, int pixel,
			boolean flag, int starIndex, int endIndex) {
		spannableString.setSpan(new AbsoluteSizeSpan(pixel, flag), starIndex,
				endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 设置相对字体大小（单位：像素） 参数表示为默认字体大小的多少倍 0.5f表示默认字体大小的一半,2.0f表示默认字体大小的两倍
	 * 
	 * @param spannableString
	 * @param pixel
	 * @param starIndex
	 * @param endIndex
	 */
	public void getRelativeSizeSpan(SpannableString spannableString,
			float pixel, int starIndex, int endIndex) {
		spannableString.setSpan(new RelativeSizeSpan(pixel), starIndex,
				endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 设置字体颜色
	 * 
	 * @param spannableString
	 * @param color
	 * @param starIndex
	 * @param endIndex
	 */
	public void getForegroundColorSpan(SpannableString spannableString,
			int color, int starIndex, int endIndex) {
		spannableString.setSpan(new ForegroundColorSpan(color), starIndex,
				endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 设置字体背景色
	 * 
	 * @param spannableString
	 * @param color
	 * @param starIndex
	 * @param endIndex
	 */
	public void getBackgroundColorSpan(SpannableString spannableString,
			int color, int starIndex, int endIndex) {
		spannableString.setSpan(new BackgroundColorSpan(color), starIndex,
				endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 设置字体样式正常(NORMAL)，粗体(BOLD)，斜体(ITALIC)，粗斜体(BOLD_ITALIC)
	 * eg:android.graphics.Typeface.NORMAL
	 * 
	 * @param spannableString
	 * @param style
	 * @param starIndex
	 * @param endIndex
	 */
	public void getStyleSpan(SpannableString spannableString, int style,
			int starIndex, int endIndex) {
		spannableString.setSpan(new StyleSpan(style), starIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 设置下划线
	 * 
	 * @param spannableString
	 * @param starIndex
	 * @param endIndex
	 */
	public void getUnderlineSpan(SpannableString spannableString,
			int starIndex, int endIndex) {
		spannableString.setSpan(new UnderlineSpan(), starIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 设置删除线
	 * 
	 * @param spannableString
	 * @param starIndex
	 * @param endIndex
	 */
	public void getStrikethroughSpan(SpannableString spannableString,
			int starIndex, int endIndex) {
		spannableString.setSpan(new StrikethroughSpan(), starIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 设置下标
	 * 
	 * @param spannableString
	 * @param starIndex
	 * @param endIndex
	 */
	public void getSubscriptSpan(SpannableString spannableString,
			int starIndex, int endIndex) {
		spannableString.setSpan(new SubscriptSpan(), starIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 设置上标
	 * 
	 * @param spannableString
	 * @param starIndex
	 * @param endIndex
	 */
	public void getSuperscriptSpan(SpannableString spannableString,
			int starIndex, int endIndex) {
		spannableString.setSpan(new SuperscriptSpan(), starIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 超级链接（需要添加setMovementMethod方法附加响应） eg:电话,linkType="tel:4155551212"
	 * 邮件,linkType="mailto:webmaster@google.com"
	 * 网络,linkType="http://www.baidu.com"
	 * 短信,使用"sms:"或者"smsto:",linkType="sms:4155551212"
	 * 彩信,使用"mms:"或者"mmsto:",linkType="mms:4155551212"
	 * 地图,linkType="geo:38.899533,-77.036476"
	 * 
	 * @param spannableString
	 * @param linkType
	 * @param starIndex
	 * @param endIndex
	 */
	public void getURLSpan(SpannableString spannableString, String linkType,
			int starIndex, int endIndex) {
		spannableString.setSpan(new URLSpan(linkType), starIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 设置相对字体大小（单位：像素） 参数表示为默认字体宽度的多少倍 eg:2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变
	 * 
	 * @param spannableString
	 * @param pixel
	 * @param starIndex
	 * @param endIndex
	 */
	public void getScaleXSpan(SpannableString spannableString, float pixel,
			int starIndex, int endIndex) {
		spannableString.setSpan(new ScaleXSpan(pixel), starIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 设置项目符号 BulletSpan(width,color)第一个参数表示项目符号占用的宽度，第二个参数为项目符号的颜色
	 * eg:BulletSpan
	 * (android.text.style.BulletSpan.STANDARD_GAP_WIDTH,Color.GREEN)
	 * 
	 * @param spannableString
	 * @param width
	 * @param color
	 * @param starIndex
	 * @param endIndex
	 */
	public void getBulletSpan(SpannableString spannableString, int width,
			int color, int starIndex, int endIndex) {
		spannableString.setSpan(new BulletSpan(width, color), starIndex,
				endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	/**
	 * 设置图片 eg: Drawable drawable =
	 * getResources().getDrawable(R.drawable.ic_action_search);
	 * drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
	 * drawable.getIntrinsicHeight());
	 * 
	 * @param spannableString
	 * @param drawable
	 * @param starIndex
	 * @param endIndex
	 */
	public void getImageSpan(SpannableString spannableString,
			Drawable drawable, int starIndex, int endIndex) {
		spannableString.setSpan(new ImageSpan(drawable), starIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
}
