package com.levelup.library.enums;

public enum WithdrawStatus {
    PENDENT("pendent"),
    RETURNED("returned");

    public final String label;
    private WithdrawStatus(String label){
        this.label = label;
    }
}
