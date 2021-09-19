package com.bithumb.board.response;

public enum SuccessCode {
    BOARD_DELETE_SUCCESS("BOARD DELETE SUCCESS");

    private final String message;

    SuccessCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
