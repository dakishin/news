package ru.soft.news.glide;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import ru.soft.news.Utils;

public class FileLoader {

  private String fileDirectory;

  public FileLoader(String fileDirectory) {
    this.fileDirectory = fileDirectory;
  }

  public String load(String urlStr) throws IOException {
    int count;
    InputStream input = null;
    OutputStream output = null;
    String filePath = fileDirectory + "/image_file";
    try {
      URL url = new URL(urlStr);
      URLConnection connection = url.openConnection();
      connection.connect();

      // this will be useful so that you can show a tipical 0-100%
      // progress bar
      int lenghtOfFile = connection.getContentLength();

      // download the file
      input = new BufferedInputStream(url.openStream(), 8192);

      // Output stream
      output = new FileOutputStream(filePath);

      byte data[] = new byte[1024];

      long total = 0;

      while ((count = input.read(data)) != -1) {
        total += count;
        // publishing the progress....
        // After this onProgressUpdate will be called
        //publishProgress("" + (int) ((total * 100) / lenghtOfFile));

        // writing data to file
        output.write(data, 0, count);
      }

      // flushing output
      output.flush();

      // closing streams
      output.close();
      input.close();
    } finally {
      Utils.closeQuietly(input);
      Utils.closeQuietly(output);
    }

    return filePath;
  }
}


