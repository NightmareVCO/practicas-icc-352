package org.example;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    HttpResponse httpResponse = null;
    HttpRequest httpRequest;
    HttpClient httpClient;
    URL url = null;
    int totalImages = 0, post = 0, get  = 0;

    System.out.print("url: ");
    String newUrl = scanner.nextLine();

    try {
      url = new URL(newUrl);
      httpClient = HttpClient.newHttpClient();
      httpRequest = HttpRequest.newBuilder().uri(URI.create(String.valueOf(url))).build();
      httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.discarding());
    } catch (MalformedURLException e) {
      System.out.println("Invalid URL");
    } catch (Exception e) {
      System.out.println("Something went wrong: " + e.getMessage());
    }
    if (httpResponse == null) throw new RuntimeException("Response is null");

    String contentType = String.valueOf(httpResponse.headers().firstValue("Content-Type"));
    System.out.println("Content-Type: " + contentType);
    if (!contentType.contains("text/html")) throw new RuntimeException("Content-Type is not text/html");

    Document document = Jsoup.connect(String.valueOf(url)).get();

    //1.
    String html = document.html();
    int length = html.split("\n").length;
    System.out.println("Length: " + length);
  }
}