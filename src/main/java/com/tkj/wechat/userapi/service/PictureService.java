package com.tkj.wechat.userapi.service;

import com.tkj.wechat.domain.Picture;
import com.tkj.wechat.domain.ViewClassTypePoster;
import com.tkj.wechat.domain.ViewUserPoster;

import java.util.List;

public interface PictureService {

    List<Picture> getCourselList(String type);

    List<ViewUserPoster> getAllTeacherPoster();

    List<ViewClassTypePoster> getAllClassPoster();

}
