package ru.soft.news;

import java.io.Closeable;

public class Utils {

  public static void closeQuietly(Closeable closeable) {
    try {
      closeable.close();
    } catch (Throwable e) {

    }
  }
}
