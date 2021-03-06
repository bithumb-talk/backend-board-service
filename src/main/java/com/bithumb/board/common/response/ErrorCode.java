    package com.bithumb.board.common.response;

public enum ErrorCode {
    //GET
    //POST
    //PUT
    //DELETE
    BOARD_NOT_EXIST("BOARD NOT EXIST"),
    COMMENT_NOT_EXIST("COMMENT NOT EXIST"),
    REPLY_NOT_EXIST("REPLY NOT EXIST"),
    ID_NOT_EXIST("ID NOT EXIST");


    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
