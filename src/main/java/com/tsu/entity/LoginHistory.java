package com.tsu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author ZZZ
 * @create 2020/9/28/19:33
 */

/**
 * 登陆历史的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LoginHistory {
    private Integer id;
    private String loginName;
    private String loginIp;
    private String loginResult;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private Date loginDate;
}
