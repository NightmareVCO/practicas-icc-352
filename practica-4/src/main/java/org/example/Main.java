package org.example;

import io.javalin.Javalin;

public class Main {
  public static void main(String[] args) {
    var app = Javalin.create(/*config*/)
      .get("/", ctx -> ctx.result("App #2 running successfully!"))
      .start(7070);
  }
}