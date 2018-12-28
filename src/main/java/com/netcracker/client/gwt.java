package com.netcracker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.netcracker.client.services.BookService;
import com.netcracker.client.services.TableService;
import com.netcracker.shared.Book;
import com.netcracker.shared.FieldVerifier;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;


public class gwt implements EntryPoint {


    private final Messages messages = GWT.create(Messages.class);

    private Book selectedBookGlob;

    public void onModuleLoad() {


        String root = Defaults.getServiceRoot();
        root = root.replace("gwt/", "");
        Defaults.setServiceRoot(root);
        final Button deleteButton = new Button(messages.deleteButton());
        final Button addButton = new Button(messages.addButton());


        CellTable<Book> cellTable = new CellTable<>();
        cellTable.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TableService.addIdColumn(cellTable);
        TableService.addNameColumn(cellTable);
        TableService.addAuthorColumn(cellTable);
        TableService.addYearColumn(cellTable);
        TableService.addKolstrColumn(cellTable);
        TableService.addDateColumn(cellTable);

        final SingleSelectionModel<Book> singleSelectionModel = new SingleSelectionModel<Book>();
        cellTable.setSelectionModel(singleSelectionModel);
        singleSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                deleteButton.setEnabled(true);
                Book selectedBook = singleSelectionModel.getSelectedObject();
                if (selectedBook != null) {
                    selectedBookGlob = selectedBook;
                }
            }
        });

//        deleteButton.setStyleName("deleteButton");
//        addButton.setStyleName("addBookButton");
        deleteButton.setEnabled(false);
        RootPanel.get("deleteContainer").add(deleteButton);
        RootPanel.get("addContainer").add(addButton);

        DialogBox addDialog = dialogBox(messages.addButton(), cellTable, addButton);

        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                addDialog.center();
                addButton.setEnabled(false);
            }
        });
        deleteButton.addClickHandler(new ClickHandler() {
            BookService service = GWT.create(BookService.class);

            @Override
            public void onClick(ClickEvent clickEvent) {
                Book book = selectedBookGlob;
                service.callDelete(book, new MethodCallback<Book>() {
                    @Override
                    public void onFailure(Method method, Throwable throwable) {
                        Window.alert("Can't Delete Book");
                    }

                    @Override
                    public void onSuccess(Method method, Book book) {
                        deleteButton.setEnabled(false);
                        setTable(cellTable);
                    }
                });
            }
        });

        setTable(cellTable);
    }


    public static void setTable(CellTable<Book> cellTable) {
        BookService serv = GWT.create(BookService.class);
        serv.getTable(new MethodCallback<List<Book>>() {

            @Override
            public void onSuccess(Method method, List<Book> books) {
                RootPanel.get("tableContainer").remove(cellTable);
                cellTable.setRowCount(books.size(), true);
                cellTable.setRowData(0, books);
                RootPanel.get("tableContainer").add(cellTable);
            }

            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Table download error");
            }
        });
    }


    public static void sortTable(CellTable<Book> cellTable, Integer colNum) {
        BookService serv = GWT.create(BookService.class);
        serv.sortTable(colNum, new MethodCallback<List<Book>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Can't sort the table");
            }

            @Override
            public void onSuccess(Method method, List<Book> books) {
                RootPanel.get("tableContainer").remove(cellTable);
                cellTable.setRowCount(books.size(), true);
                cellTable.setRowData(0, books);
                RootPanel.get("tableContainer").add(cellTable);
            }
        });

    }

    private static DialogBox dialogBox(String message, CellTable<Book> cellTable, Button addButton) {
        final DialogBox addDialog = new DialogBox();
        addDialog.setText("Add new Book");
        addDialog.setAnimationEnabled(true);
        final Button closeAddDialogButton = new Button("Close");
        final Button addDialogButton = new Button(message);
        closeAddDialogButton.getElement().setId("closeButton");
        addDialogButton.getElement().setId("addButton");
        final Label NameBookL = new Label("Book's name");
        final Label AuthorBookL = new Label("Author's Name");
        final Label KolStrBookL = new Label("Amount of list");
        final Label YearBookL = new Label("Year");
        final Label ErrorBook = new Label();
        ErrorBook.setStyleName("validationError");
        final TextBox NameBookT = new TextBox();
        final TextBox AuthorBookT = new TextBox();
        final TextBox KolStrBookT = new TextBox();
        final TextBox YearBookT = new TextBox();
        VerticalPanel dialogPanel = new VerticalPanel();
        dialogPanel.addStyleName("dialogVPanel");

        dialogPanel.add(NameBookL);
        dialogPanel.add(NameBookT);
        dialogPanel.add(AuthorBookL);
        dialogPanel.add(AuthorBookT);
        dialogPanel.add(KolStrBookL);
        dialogPanel.add(KolStrBookT);
        dialogPanel.add(YearBookL);
        dialogPanel.add(YearBookT);
        dialogPanel.add(ErrorBook);

        dialogPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

        HorizontalPanel hp = new HorizontalPanel();
        hp.add(closeAddDialogButton);
        hp.add(addDialogButton);
        hp.setVerticalAlignment(HorizontalPanel.ALIGN_TOP);

        dialogPanel.add(hp);
        addDialog.setWidget(dialogPanel);

        closeAddDialogButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                NameBookT.setText("");
                AuthorBookT.setText("");
                KolStrBookT.setText("");
                YearBookT.setText("");
                addDialog.hide();
                addButton.setEnabled(true);
                addButton.setFocus(true);
            }
        });

        addDialogButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (!FieldVerifier.isValidName(NameBookT.getText())) {
                    ErrorBook.setText("Invalid Name");
                    return;
                }
                if (!FieldVerifier.isValidName(AuthorBookT.getText())) {
                    ErrorBook.setText("Invalid Author's Name");
                    return;
                }
                if (!FieldVerifier.isValidNumber(KolStrBookT.getText())) {
                    ErrorBook.setText("Invalid Amount of lists");
                    return;
                }
                if (!FieldVerifier.isValidNumber(YearBookT.getText())) {
                    ErrorBook.setText("Invalid Year");
                    return;
                }
                BookService service = GWT.create(BookService.class);
                service.callAdd(new Book(cellTable.getRowCount(), NameBookT.getText(), AuthorBookT.getText(), Integer.parseInt(KolStrBookT.getText()), Integer.parseInt(YearBookT.getText()), System.currentTimeMillis()), new MethodCallback<Book>() {
                    @Override
                    public void onFailure(Method method, Throwable throwable) {
                        Window.alert("Can't add book");
                    }

                    @Override
                    public void onSuccess(Method method, Book book) {
                        setTable(cellTable);
                        addButton.setEnabled(true);
                        NameBookT.setText("");
                        AuthorBookT.setText("");
                        KolStrBookT.setText("");
                        YearBookT.setText("");
                        addDialog.hide();
                    }
                });
            }
        });
        return addDialog;
    }

}
