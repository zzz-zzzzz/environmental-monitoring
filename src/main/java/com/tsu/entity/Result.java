package com.tsu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ZZZ
 * @create 2020/9/19/15:41
 */

//返回数据实体类
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Result {
    private Integer code;
    private String message;
    private Object data;
}
