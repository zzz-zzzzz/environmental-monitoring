package com.tsu.entity;

/**
 * @author ZZZ
 * @create 2020/10/16/8:36
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 报警信息实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AlarmInfo {
    private Integer id;
    private String deviceKey; //设备号
    private Integer flag; //是否开启
    private Double temMax;
    private Double temMin;
    private Double humMax;
    private Double humMin;
    private String associatedPhone;
    private Integer alarmType;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime; //报警的开始时间
}
