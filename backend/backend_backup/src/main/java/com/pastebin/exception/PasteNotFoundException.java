package com.pastebin.exception;

public class PasteNotFoundException extends RuntimeException {

    public PasteNotFoundException(String id) {
        super("Paste not found with id: " + id);
    }
}
