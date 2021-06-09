package com.tsu.mapper;

import com.tsu.entity.SetRelaysHistory;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author ZZZ
 * @create 2020/9/29/14:43
 */

@Mapper
public interface SetRelaysHistoryMapper {

    @Insert("insert into t_set_relay_history(device_name,relay_id,set_date,relay_status,device_key) " +
            "values(#{deviceName},#{relayId},#{setDate},#{relayStatus},#{deviceKey})")
    void save(SetRelaysHistory setRelaysHistory);


    @Results(id = "setRelaysHistoryResultMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "device_name", property = "deviceName"),
            @Result(column = "relay_id", property = "relayId"),
            @Result(column = "set_date", property = "setDate"),
            @Result(column = "relay_status", property = "relayStatus"),
            @Result(column = "device_key", property = "deviceKey"),
    })
    @Select("select * from t_set_relay_history where device_key=#{deviceKey} and set_date between #{beginTime} and #{endTime}")
    List<SetRelaysHistory> findByDateAndDeviceKey(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("deviceKey") String deviceKey);
}
