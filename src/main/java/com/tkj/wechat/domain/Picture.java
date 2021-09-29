package com.tkj.wechat.domain;

import java.util.Objects;

/**
 * @ClassName Picture
 * @Description TODO
 * @Author 幸运的大树
 * @Date 2021/9/23 11:15
 * @Version 1.0
 **/

public class Picture {
    private Integer id;
    private String fileName;
    private String type;
    private Integer ownerId;
    private Integer sequence;
    private Integer isDelete;

    public Picture(Integer id, String fileName, String type, Integer ownerId, Integer sequence, Integer isDelete) {
        this.id = id;
        this.fileName = fileName;
        this.type = type;
        this.ownerId = ownerId;
        this.sequence = sequence;
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", type='" + type + '\'' +
                ", ownerId=" + ownerId +
                ", sequence=" + sequence +
                ", isDelete=" + isDelete +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Picture picture = (Picture) o;
        return Objects.equals(id, picture.id) &&
                Objects.equals(fileName, picture.fileName) &&
                Objects.equals(type, picture.type) &&
                Objects.equals(ownerId, picture.ownerId) &&
                Objects.equals(sequence, picture.sequence) &&
                Objects.equals(isDelete, picture.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, type, ownerId, sequence, isDelete);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
