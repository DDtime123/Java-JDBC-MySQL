package com.jdbc.repository;

import com.jdbc.models.StuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

@Repository
public class StuRepository {
    // 1. JdbcTemplate 对象的自动装配注入
    @Autowired
    private NamedParameterJdbcTemplate sqlDao;

    private final String addStu_sql = "INSERT INTO stuinfo(id,name,email,createDate,updateDate)"
            + "VALUES(:id,:name,:email,:createDate,:updateDate)";

    private final String getStus_sql = "SELECT id,"
            + "name,"
            + "email,"
            + "createDate,"
            + "updateDate "
            + "FROM stuinfo WHERE id = :id";

    @Transactional
    public void addStu(StuModel stuToAdd)
    {
        if(stuToAdd != null)
        {
            Map<String, Object> parameters = new HashMap<String, Object>();

            Date dateNow = new Date();

            parameters.put("id", stuToAdd.getId());
            parameters.put("name",stuToAdd.getName());
            parameters.put("email", stuToAdd.getEmail());
            parameters.put("createDate", dateNow);
            parameters.put("updateDate", dateNow);

            int retVal = sqlDao.update(addStu_sql,parameters);
            System.out.println("Rows updated: "+retVal);
        }
        else
        {
            System.out.println("Car to add is invalid. Null Object.");
        }
    }

    @Transactional
    public StuModel findStu(String id){
        StuModel foundObjs = sqlDao.query(getStus_sql,
                Collections.singletonMap("id",id),
                //new HashMap<String,String>(1),
                //new MapSqlParameterSource("id",id),
                (rs) -> {
            StuModel retVal = new StuModel();
            if(rs != null)
            {
                while(rs.next())
                {
                        StuModel sm = new StuModel();
                        sm.setId(String.valueOf(rs.getInt("id")));
                        sm.setName(rs.getString("name"));
                        sm.setEmail(rs.getString("email"));
                        sm.setCreateDate(rs.getDate("createDate"));
                        sm.setUpdateDate(rs.getDate("updateDate"));
                        retVal = sm;
                }
            }

            return retVal;
                });

        return foundObjs;
    }
}
