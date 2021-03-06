package com.whut.resume.service;

import com.whut.resume.pojo.BasicInfo;
import com.whut.resume.pojo.FamilyInfo;
import com.whut.resume.pojo.studyInfo;
import com.whut.resume.pojo.workInfo;

import java.util.List;

/**
 * @author AC
 * @describe
 * @date 2021/4/25 12:08
 */
public interface ResumeService {
    void generateXML(BasicInfo basicInfo, List<FamilyInfo> familyInfo, studyInfo studyInfo, workInfo workInfo, String hobby,String image);

    void updateBasicInformation(BasicInfo newBasicInfo);

    void addInformation(Object object,String tagName);

    void deleteInformation(String tobeDeleted,String tagName);
}
