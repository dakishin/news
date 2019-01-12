package ru.soft.news.dagger;

public class SimpleProvider<T> implements Provider<T> {

  private T instance;

  public SimpleProvider(T instance) {
    this.instance = instance;
  }

  @Override public T get() {
    return instance;
  }
}
