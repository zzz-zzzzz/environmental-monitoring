package com.tsu.mapper;

import com.tsu.entity.MonitorHistory;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author ZZZ
 * @create 2020/10/9/19:55
 */

@Mapper
public interface MonitorHistoryMapper {

    @Insert("insert into t_monitor_history(device_name,device_key,tem,hum,record_time) values(#{deviceName},#{deviceKey},#{tem},#{hum},#{recordTime})")
    void save(MonitorHistory monitorHistory);


    @Results(id = "monitorHistoryResult",
            value = {
                    @Result(id = true, column = "id", property = "id"),
                    @Result(column = "device_name", property = "deviceName"),
                    @Result(column = "device_key", property = "deviceKey"),
                    @Result(column = "tem", property = "tem"),
                    @Result(column = "hum", property = "hum"),
                    @Result(column = "record_time", property = "recordTime"),
            })
    @Select("select * from t_monitor_history where device_key = #{deviceKey} and record_time between #{beginTime} and #{endTime}")
    List<MonitorHistory> findByRecordTimeAndDeviceKey(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("deviceKey") String deviceKey);

    @ResultMap("monitorHistoryResult")
    @Select("select * from t_monitor_history where device_key = #{deviceKey} and record_time between #{beginTime} and #{endTime} order by record_time limit 0,1")
    MonitorHistory queryByTime(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("deviceKey") String deviceKey);
}
