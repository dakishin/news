package ru.soft.news.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ImageLoader implements RequestManager {
  private final ThreadPoolExecutor threadPoolExecutor;
  private FileLoader fileLoader;
  private static final String TAG = "ImageLoader";
  private static ImageLoader IMAGE_LOADER = new ImageLoader();

  private ImageLoader() {
    String fileDirectory = Environment
        .getExternalStorageDirectory().toString();
    fileLoader = new FileLoader(fileDirectory);
    threadPoolExecutor = new ThreadPoolExecutor(5, 10,
        1, TimeUnit.SECONDS, new SynchronousQueue<>());
  }

  public void load(LoadRequest request) {
    threadPoolExecutor.execute(() -> loadFile(request));
  }

  private void loadFile(LoadRequest request) {
    try {
      String filePath = fileLoader.load(request.getUrl());
      Bitmap bitmap = BitmapFactory.decodeFile(filePath);
      Drawable drawable = new BitmapDrawable(request.getResources(), bitmap);
      if (request.getOnLoadSuccess() != null) {
        request.getTarget().post(() -> request.getOnLoadSuccess().onSuccess(drawable));
      }
    } catch (IOException e) {
      if (request.getOnLoadFail() != null) {
        request.getTarget().post(() -> request.getOnLoadFail().onFail());
      }
      Log.e(TAG, e.getMessage(), e);
    }
  }

  public static LoadRequest imageLoader(ImageView imageView) {
    return new LoadRequest(IMAGE_LOADER, imageView);
  }
}
