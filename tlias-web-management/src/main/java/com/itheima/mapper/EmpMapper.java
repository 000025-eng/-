package com.itheima.mapper;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


//操作员工基本信息
@Mapper
public interface EmpMapper {
//方法一：
    //查询总记录数
//    @Select("select count(*) from emp e left  join dept d on e.dept_id =d.id")
//    public long count();

//   分页查询的方法
//    @Select("select e.*,d.name daptName from emp e left  join dept d on e.dept_id =d.id" +
//            " order by e.update_time desc limit #{start},#{pageSize}")
//    public List<Emp> list(Integer start,Integer pageSize);

    //方式二//   分页查询的方法
    // @Select("select e.*,d.name daptName from emp e left  join dept d on e.dept_id =d.id\n" +
      //       "                               order by e.update_time desc")

   // public List<Emp> list(String name, Integer gender,LocalDate begin,LocalDate end );
//条件查询的员工方法
    List<Emp> list(EmpQueryParam empQueryParam);
//新增加员工的基本信息
    @Options(useGeneratedKeys = true,keyProperty = "id")//获取到生成的主键--主键返回
    @Insert(
            "insert  into emp(username,  name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) values" +
            "    (#{username}, #{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})" )
    void insert(Emp emp);

    //根据ID批量删除员工的基本信息
    void deleteByIds(List<Integer> ids);

    //根据ID查询员工的信息
    Emp getById(Integer id);
//根据ID来更新员工的信息
    void updateBtId(Emp emp);
//统计员工职位人数
    @MapKey("pos")
    List<Map<String, Object>> countEmpJobDate();
//统计员工性别人数
    @MapKey("name")
    List<Map<String, Object>> countEmpGenderData();

    //根据用户名和密码查询员工信息（登录用）
    @Select("select * from emp where username = #{username} and password = #{password}")
    Emp selectByUsernamePassword(@Param("username") String username, @Param("password") String password);
}
