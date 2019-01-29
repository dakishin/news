package ru.soft.news.glide;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

public class LoadRequest {
  private View target;
  private OnLoadFail onLoadFail;
  private OnLoadSuccess onLoadSuccess;
  private String url;

  private RequestManager requestManager;
  private Resources resources;

  LoadRequest(RequestManager requestManager, View target) {
    this.requestManager = requestManager;
    this.target = target;
    this.resources = target.getResources();
  }

  public interface OnLoadSuccess {
    void onSuccess(Drawable drawable);
  }

  public interface OnLoadFail {
    void onFail();
  }

  public LoadRequest withOnLoadFail(OnLoadFail onLoadFail) {
    this.onLoadFail = onLoadFail;
    return this;
  }

  public LoadRequest withOnLoadSuccess(OnLoadSuccess onLoadSuccess) {
    this.onLoadSuccess = onLoadSuccess;
    return this;
  }

  View getTarget() {
    return target;
  }

  OnLoadFail getOnLoadFail() {
    return onLoadFail;
  }

  OnLoadSuccess getOnLoadSuccess() {
    return onLoadSuccess;
  }

  String getUrl() {
    return url;
  }

  public LoadRequest withUrl(String url) {
    this.url = url;
    return this;
  }

  public void load() {
    requestManager.load(this);
  }

  Resources getResources() {
    return resources;
  }
}
