package com.tsu.mapper;

import com.tsu.entity.AlarmInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author ZZZ
 * @create 2020/10/16/8:49
 */

@Mapper
public interface AlarmInfoMapper {


    @Results(id = "alarmInfoResultMap",
            value = {
                    @Result(id = true, column = "id", property = "id"),
                    @Result(column = "device_key", property = "deviceKey"),
                    @Result(column = "flag", property = "flag"),
                    @Result(column = "tem_max", property = "temMax"),
                    @Result(column = "tem_min", property = "temMin"),
                    @Result(column = "hum_max", property = "humMax"),
                    @Result(column = "hum_min", property = "humMin"),
                    @Result(column = "associated_phone", property = "associatedPhone"),
                    @Result(column = "alarm_type", property = "alarmType"),
                    @Result(column = "start_time", property = "startTime"),
            }
    )
    @Select("select * from t_alarm_info where device_key = #{deviceKey}")
    AlarmInfo findByDeviceKey(String deviceKey);


    void update(AlarmInfo alarmInfo);


    @Update("update t_alarm_info set start_time=#{startTime} where device_key=#{deviceKey}")
    void updateStartTime(AlarmInfo alarmInfo);
}
