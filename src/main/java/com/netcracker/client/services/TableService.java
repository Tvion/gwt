package com.netcracker.client.services;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.TextColumn;
import com.netcracker.client.gwt;
import com.netcracker.shared.Book;

import java.util.Date;

public class TableService {
    private static Integer clickedHeader = 1;

    public static void addIdColumn(CellTable<Book> cellTable) {
        TextColumn<Book> idColumn = new TextColumn<Book>() {

            @Override
            public String getValue(Book object) {
                return String.valueOf(object.getId());
            }

        };
        Header<String> hb = new Header<String>(new ClickableTextCell()) {

            @Override
            public String getValue() {
                return "Id";
            }
        };
        hb.setUpdater(new ValueUpdater<String>() {
            @Override
            public void update(String s) {
                sortTable(cellTable, 1);
            }
        });


        cellTable.addColumn(idColumn, hb);
    }

    public static void addNameColumn(CellTable<Book> cellTable) {
        TextColumn<Book> nameColumn = new TextColumn<Book>() {

            @Override
            public String getValue(Book object) {
                return object.getName();
            }

        };
        Header<String> hb = new Header<String>(new ClickableTextCell()) {

            @Override
            public String getValue() {
                return "Name";
            }
        };
        hb.setUpdater(new ValueUpdater<String>() {
            @Override
            public void update(String s) {
                sortTable(cellTable, 2);
            }
        });


        cellTable.addColumn(nameColumn, hb);
    }

    public static void addAuthorColumn(CellTable<Book> cellTable) {
        TextColumn<Book> authorsColumn = new TextColumn<Book>() {

            @Override
            public String getValue(Book object) {
                return String.valueOf(object.getAuthor());
            }

        };

        Header<String> hb = new Header<String>(new ClickableTextCell()) {

            @Override
            public String getValue() {
                return "Author";
            }
        };
        hb.setUpdater(new ValueUpdater<String>() {
            @Override
            public void update(String s) {
                sortTable(cellTable, 3);
            }
        });

        cellTable.addColumn(authorsColumn, hb);

    }

    public static void addYearColumn(CellTable<Book> cellTable) {
        TextColumn<Book> locationColumn = new TextColumn<Book>() {

            @Override
            public String getValue(Book object) {
                return String.valueOf(object.getYear());
            }

        };

        Header<String> hb = new Header<String>(new ClickableTextCell()) {

            @Override
            public String getValue() {
                return "Year";
            }
        };
        hb.setUpdater(new ValueUpdater<String>() {
            @Override
            public void update(String s) {
                sortTable(cellTable, 4);
            }
        });


        cellTable.addColumn(locationColumn, hb);
    }

    public static void addKolstrColumn(CellTable<Book> cellTable) {
        TextColumn<Book> locationColumn = new TextColumn<Book>() {

            @Override
            public String getValue(Book object) {
                return String.valueOf(object.getKolStr());
            }

        };

        Header<String> hb = new Header<String>(new ClickableTextCell()) {

            @Override
            public String getValue() {
                return "Amount of lists";
            }
        };
        hb.setUpdater(new ValueUpdater<String>() {
            @Override
            public void update(String s) {
                sortTable(cellTable, 5);
            }
        });
        cellTable.addColumn(locationColumn, hb);
    }

    public static void addDateColumn(CellTable<Book> cellTable) {
        TextColumn<Book> locationColumn = new TextColumn<Book>() {

            @Override
            public String getValue(Book object) {
                String date = DateTimeFormat.getFormat("dd.MM.yyyy").format(new Date(object.getDate()));
                return date;
            }

        };

        Header<String> hb = new Header<String>(new ClickableTextCell()) {

            @Override
            public String getValue() {
                return "Date";
            }
        };
        hb.setUpdater(new ValueUpdater<String>() {
            @Override
            public void update(String s) {
                sortTable(cellTable, 6);
            }
        });
        cellTable.addColumn(locationColumn, hb);
    }


    private static void sortTable(CellTable<Book> cellTable, int i) {
        if (clickedHeader == i) {
            gwt.sortTable(cellTable, i * 10);
            clickedHeader = 0;
        } else {
            clickedHeader = i;
            gwt.sortTable(cellTable, i);
        }
    }

}
