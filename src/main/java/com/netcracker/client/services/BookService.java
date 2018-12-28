package com.netcracker.client.services;

import com.netcracker.shared.Book;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface BookService extends RestService {

    @POST
    @Path("api/book")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void call(Book request,
              MethodCallback<Book> callback);

    @DELETE
    @Path("api/book")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void callDelete(Book request,
                    MethodCallback<Book> callback);

    @PUT
    @Path("api/book")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void callAdd(Book request,
                 MethodCallback<Book> callback);

    @POST
    @Path("api/gettable")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void getTable(
            MethodCallback<List<Book>> callback);

    @PUT
    @Path("api/gettable")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void sortTable(Integer request,
                   MethodCallback<List<Book>> callback);
}
