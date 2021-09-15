package com.tkj.wechat.dao;

import com.tkj.wechat.domain.Authentication;
import com.tkj.wechat.domain.AuthenticationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthenticationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    long countByExample(AuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    int deleteByExample(AuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    int insert(Authentication record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    int insertSelective(Authentication record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    Authentication selectOneByExample(AuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    Authentication selectOneByExampleSelective(@Param("example") AuthenticationExample example, @Param("selective") Authentication.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    Authentication selectOneByExampleWithBLOBs(AuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    List<Authentication> selectByExampleSelective(@Param("example") AuthenticationExample example, @Param("selective") Authentication.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    List<Authentication> selectByExampleWithBLOBs(AuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    List<Authentication> selectByExample(AuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    Authentication selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") Authentication.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    Authentication selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Authentication record, @Param("example") AuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") Authentication record, @Param("example") AuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Authentication record, @Param("example") AuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Authentication record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(Authentication record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authentication
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Authentication record);
}