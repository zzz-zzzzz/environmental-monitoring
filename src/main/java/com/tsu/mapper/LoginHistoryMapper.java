package com.tsu.mapper;

import com.tsu.entity.LoginHistory;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author ZZZ
 * @create 2020/9/28/19:33
 */
@Mapper
public interface LoginHistoryMapper {

    @Insert("insert into t_login_history(login_name,login_ip,login_result,login_date) values(#{loginName},#{loginIp},#{loginResult},#{loginDate})")
    void save(LoginHistory loginHistory);

    @Results(id = "loginHistoryResultMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "login_name", property = "loginName"),
            @Result(column = "login_ip", property = "loginIp"),
            @Result(column = "login_result", property = "loginResult"),
            @Result(column = "login_date", property = "loginDate"),
    })
    @Select("select * from t_login_history where login_date between #{beginTime} and #{endTime}")
    List<LoginHistory> findByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);


}
