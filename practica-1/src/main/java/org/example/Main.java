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

    //2.
    Elements paragraphs = document.select("p");
    int totalParagraphs = paragraphs.size();
    System.out.println("Paragraphs: " + totalParagraphs);

    //3.
    for(Element p : paragraphs) totalImages += paragraphs.select("img").size();
    System.out.println("Images: " + totalImages);

    //4.
    Elements forms = document.select("form");
    System.out.println("Forms: " + forms.size());

    for(Element f : forms){
      String method = f.attr("method");
      if(method.equalsIgnoreCase("POST")) post++;
      if(method.equalsIgnoreCase("GET")) get++;
    }
    System.out.println("POST: " + post);
    System.out.println("GET: " + get);

    //5.
    for(Element f : forms){
      Elements input = f.select("input");
      for(Element i : input) System.out.println("Type: " + i.attr("type"));
    }

    //6.
    for(Element f : forms){
      String method = forms.attr("method");
      if(!method.equalsIgnoreCase("POST")) continue;

      Document respServer = Jsoup.connect(String.valueOf(url))
        .data("asignatura","practica1")
        .header("matricula-id","10141415")
        .post();
        System.out.println("POST response: " + respServer.body().text());
    }
  }
}