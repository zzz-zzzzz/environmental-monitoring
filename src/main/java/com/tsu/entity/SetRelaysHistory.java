package com.tsu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author ZZZ
 * @create 2020/9/29/14:36
 */

/**
 * 操作继电器实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SetRelaysHistory {
    private Integer id;
    private String deviceKey;
    private String deviceName;
    private Integer relayId;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private Date setDate;
    private Integer relayStatus;
}
