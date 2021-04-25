package com.whut.resume.controller;

import com.whut.resume.pojo.BasicInfo;
import com.whut.resume.pojo.FamilyInfo;
import com.whut.resume.pojo.studyInfo;
import com.whut.resume.pojo.workInfo;
import com.whut.resume.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AC <chuhan@133.cn>
 * @describe
 * @date 2021/4/25 9:36
 */
@Controller
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    private BasicInfo basicInfo;
    private List<FamilyInfo> familyInfoList=new ArrayList<>();
    private studyInfo studyInfo;
    private workInfo workInfo;
    private String hobbies;

    private String image;


    @RequestMapping("/showAddInfo")
    public String showAddInfo(){
        return "addBasicInfo";
    }

    @RequestMapping("/addInfo")
    public String addInfo(BasicInfo basicInfo,String image){
        this.basicInfo=basicInfo;
        this.image=image;
        return "addFamilyInfo";
    }

    @RequestMapping("/addFamilyInfo")
    public String addFamilyInfo(FamilyInfo familyInfo, Model model){
        familyInfoList.add(familyInfo);
        String message=familyInfo.getMemberName()+"的信息已添加成功，请继续添加，如添加完毕请点下一步";
        model.addAttribute("message",message);
        return "addFamilyInfo";
    }

    @RequestMapping("/showAddStudyExperience")
    public String showAddStudyExperience(){
        return "addStudyInfo";
    }

    @RequestMapping("/addStudyExperience")
    public String addStudyExperience(studyInfo studyInfo){
        this.studyInfo=studyInfo;
        return "addWorkInfo";
    }

    @RequestMapping("/addWorkInfo")
    public String addWorkInfo(workInfo workInfo){
        this.workInfo=workInfo;
        return "addHobby";
    }

    @RequestMapping("/addHobby")
    public String addHobby(String hobbies,Model model){
        this.hobbies=hobbies;
        model.addAttribute("name",basicInfo.getName());
        resumeService.generateXML(basicInfo,familyInfoList,studyInfo,workInfo,this.hobbies);
        return "initSuccess";
    }
}
