package io.khw.common.response;import lombok.Getter;@Getterpublic class ErrCommonResponse{    private String errorMessage;    private String errorCode;    public ErrCommonResponse(String errorMessage, String errorCode) {        this.errorCode = errorMessage;        this.errorMessage = errorCode;    }}