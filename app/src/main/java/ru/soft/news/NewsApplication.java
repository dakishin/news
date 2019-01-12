package ru.soft.news;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;
import ru.soft.news.dagger.AppComponent;
import ru.soft.news.dagger.AppComponentImpl;
import ru.soft.news.dagger.AppModule;

public class NewsApplication extends Application {
  private static AppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();

    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return;
    }
    LeakCanary.install(this);


    appComponent = new AppComponentImpl()
        .builder()
        .withAppModule(new AppModule(getApplicationContext()))
        .build();
  }

  public static AppComponent appComponent() {
    return appComponent;
  }
}
