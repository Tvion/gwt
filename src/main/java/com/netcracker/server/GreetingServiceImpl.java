package com.netcracker.server;

import com.netcracker.shared.Book;


import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


@Path("book")
public class GreetingServiceImpl{

  @Context
  private ServletContext context;
//
//  @POST
//  @Produces(MediaType.APPLICATION_JSON)
//  @Consumes(MediaType.APPLICATION_JSON)
//  public Book greetServer(Book input) throws IllegalArgumentException {
//    input.setKolStr(input.getKolStr()+100);
//
//    return input;
//  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Book greetServerDelete(Book input) throws IllegalArgumentException, IOException {
    String path=context.getRealPath("/WEB-INF/");
    BookService bs=new BookService(path);
    bs.removeBooks(input);
    return input;
  }


  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Book greetServerAdd(Book input) throws IllegalArgumentException, IOException {

    input.setDate(System.currentTimeMillis());
    String path=context.getRealPath("/WEB-INF/");
    BookService bs=new BookService(path);
    bs.addBook(input);
    return input;
  }
}
