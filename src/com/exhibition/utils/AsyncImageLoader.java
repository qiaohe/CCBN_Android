package com.exhibition.utils;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;

public class AsyncImageLoader {
	public Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();

	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private final Handler handler = new Handler();

	public Drawable loadDrawable(final String imageUrl,
			final ImageCallback callback) {
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				return softReference.get();
			}
		}
		executorService.submit(new Runnable() {
			public void run() {
				try {
					final Drawable drawable = loadImageFromUrl(imageUrl);
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
					handler.post(new Runnable() {
						public void run() {
							callback.imageLoaded(drawable);
						}
					});
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		return null;
	}

	protected Drawable loadImageFromUrl(String imageUrl) {
		try {
			URL url = new URL(imageUrl);
			InputStream is = url.openStream();
			System.out.println("" + is.toString().length());
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			options.inSampleSize = 1;
			Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
			return new BitmapDrawable(bitmap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	} 

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable);
	}
}
