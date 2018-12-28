package com.netcracker.server;

import com.netcracker.shared.Book;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Path("gettable")
public class TableServiceImpl {

    @Context
    private ServletContext context;

    @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public List<Book> greetServer() throws IllegalArgumentException, IOException {
        String path=context.getRealPath("/WEB-INF/");
        BookService bs=new BookService(path);
        return bs.getList();
  }


    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Book> sortTable(Integer numCol) throws IllegalArgumentException, IOException {
        String path=context.getRealPath("/WEB-INF/");
        BookService bs=new BookService(path);
        return bs.getSortedList(numCol);
    }
}
