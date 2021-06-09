package com.tsu.entity;

/**
 * @author ZZZ
 * @create 2020/10/9/19:30
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 监控数据历史实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MonitorHistory {
    private Integer id;
    private String deviceName;
    private String deviceKey;
    private Double tem;
    private Double hum;

    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private Date recordTime;
}
