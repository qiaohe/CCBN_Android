package com.exhibition.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ImageURLUtil {

	private static AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

	// 引入线程池，并引入内存缓存功能,并对外部调用封装了接口，简化调用过程
	public static void loadImage(String url, final ImageView view) {
		// 如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Drawable cacheImage = asyncImageLoader.loadDrawable(url,
				new AsyncImageLoader.ImageCallback() {
					// 请参见实现：如果第一次加载url时下面方法会执行
					public void imageLoaded(Drawable imageDrawable) {
						view.setImageDrawable(imageDrawable);
					}
				});
		if (cacheImage != null) {
			view.setImageDrawable(cacheImage);
		}
	}
}
