package com.study.base.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: luoshangcai
 * @Date 2020-08-15 14:48
 **/
public class ValidateResult {
    /**
     * 是否有错误
     */
    private boolean hasErrors;

    /**
     * 错误信息
     */
    private List<ErrorMessage> errors;

    public ValidateResult() {
        this.errors = new ArrayList<>();
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    /**
     * 获取所有验证信息
     *
     * @return 集合形式
     */
    public List<ErrorMessage> getAllErrors() {
        return errors;
    }

    /**
     * 获取所有验证信息
     *
     * @return 字符串形式
     */
    public String getErrors() {
        StringBuilder sb = new StringBuilder();
        for (ErrorMessage error : errors) {
            sb.append(error.getMessage()).append(" ");
        }
        return sb.toString();
    }

    public void addError(String message) {
        this.errors.add(new ErrorMessage(message));
    }

    @Data
    public class ErrorMessage {

        private String message;

        public ErrorMessage() {
        }

        public ErrorMessage(String message) {
            this.message = message;
        }
    }
}
