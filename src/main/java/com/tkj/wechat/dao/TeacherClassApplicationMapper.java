package com.tkj.wechat.dao;

import com.tkj.wechat.domain.TeacherClassApplication;
import com.tkj.wechat.domain.TeacherClassApplicationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeacherClassApplicationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    long countByExample(TeacherClassApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    int deleteByExample(TeacherClassApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    int insert(TeacherClassApplication record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    int insertSelective(TeacherClassApplication record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    TeacherClassApplication selectOneByExample(TeacherClassApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    TeacherClassApplication selectOneByExampleSelective(@Param("example") TeacherClassApplicationExample example, @Param("selective") TeacherClassApplication.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    TeacherClassApplication selectOneByExampleWithBLOBs(TeacherClassApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    List<TeacherClassApplication> selectByExampleSelective(@Param("example") TeacherClassApplicationExample example, @Param("selective") TeacherClassApplication.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    List<TeacherClassApplication> selectByExampleWithBLOBs(TeacherClassApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    List<TeacherClassApplication> selectByExample(TeacherClassApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    TeacherClassApplication selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") TeacherClassApplication.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    TeacherClassApplication selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TeacherClassApplication record, @Param("example") TeacherClassApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") TeacherClassApplication record, @Param("example") TeacherClassApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TeacherClassApplication record, @Param("example") TeacherClassApplicationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TeacherClassApplication record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(TeacherClassApplication record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_class_application
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TeacherClassApplication record);
}