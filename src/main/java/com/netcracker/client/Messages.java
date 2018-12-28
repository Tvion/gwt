package com.netcracker.client;

import com.google.gwt.i18n.client.LocalizableResource.Generate;

@Generate(format = "com.google.gwt.i18n.server.PropertyCatalogFactory")
public interface Messages extends com.google.gwt.i18n.client.Messages {

    @DefaultMessage("Enter your name")
    String nameField();

    @DefaultMessage("Send")
    String sendButton();

    @DefaultMessage("Delete book")
    String deleteButton();

    @DefaultMessage("Add book")
    String addButton();
}
