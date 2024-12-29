package com.example.labmanagement.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.labmanagement.exception.Code;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {
    private int code;
    private String message;
    private Object data;

    private static final ResultVO EMPTY = ResultVO.builder()
            .code(200)
            .build();

    public static ResultVO success(Object data) {
        return ResultVO.builder()
                .code(200)
                .data(data)
                .build();
    }
    public static ResultVO ok() {
        return EMPTY;
    }
    public static ResultVO postLabSuccess() {
        return ResultVO.builder()
                .code(200)
                .message("添加实验室成功!!!")
                .build();
    }
    public static ResultVO removeLabSuccess() {
        return ResultVO.builder()
                .code(200)
                .message("删除实验室成功!!!")
                .build();
    }
    public static ResultVO patchPasswordSuccess() {
        return ResultVO.builder()
                .code(200)
                .message("更改密码成功！！！")
                .build();
    }
    public static ResultVO patchUserNameSuccess() {
        return ResultVO.builder()
                .code(200)
                .message("更改用户名成功！！！")
                .build();
    }
    public static ResultVO reservedSuccess() {
        return ResultVO.builder()
                .code(200)
                .message("预约成功!!!")
                .build();
    }
    public static ResultVO reservedFailed() {
        return ResultVO.builder()
                .code(400)
                .message("预约失败，实验室已被占用!!!")
                .build();
    }
    public static ResultVO deleteSuccess() {
        return ResultVO.builder()
                .code(200)
                .message("取消预约成功！！！")
                .build();
    }
    public static ResultVO error(Code code) {
        return ResultVO.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }
    public static ResultVO error(int code, String message) {
        return ResultVO.builder()
                .code(code)
                .message(message)
                .build();
    }
}
