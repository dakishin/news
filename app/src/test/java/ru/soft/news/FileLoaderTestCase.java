package ru.soft.news;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.soft.news.glide.FileLoader;

public class FileLoaderTestCase {

  FileLoader fileLoader;

  @Before
  public void setUp() throws Exception {
    String dir = System.getProperty("java.io.tmpdir");
    fileLoader = new FileLoader(dir);
  }

  @Test
  public void testLoadFile() throws IOException {
    String url = "https://picsum.photos/400/400/?random";
    String filePath = fileLoader.load(url);
    Assert.assertTrue(new File(filePath).exists());
  }
}
