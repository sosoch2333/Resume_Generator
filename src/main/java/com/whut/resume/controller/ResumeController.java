package com.whut.resume.controller;

import com.whut.resume.pojo.BasicInfo;
import com.whut.resume.pojo.FamilyInfo;
import com.whut.resume.pojo.studyInfo;
import com.whut.resume.pojo.workInfo;
import com.whut.resume.service.ResumeService;
import com.whut.resume.util.domUtil;
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

    /***
     * 显示主页
     * @return
     */
    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }

    /***
     * 显示成功界面
     * @return
     */
    @RequestMapping("/success")
    public String showSuccess(){
        return "initSuccess";
    }

    /***
     * 显示添加界面
     * @return
     */
    @RequestMapping("/showAddInfo")
    public String showAddInfo(){
        return "addBasicInfo";
    }

    /***
     * 添加基本信息
     * @param basicInfo
     * @param image
     * @return
     */
    @RequestMapping("/addInfo")
    public String addInfo(BasicInfo basicInfo,String image){
        if(basicInfo.getSex().equals("0")) {
            basicInfo.setSex("男");
        } else {
            basicInfo.setSex("女");
        }
        this.basicInfo=basicInfo;
        this.image=image;
        return "addFamilyInfo";
    }

    /***
     * 添加家庭成员信息
     * @param familyInfo
     * @param model
     * @return
     */
    @RequestMapping("/addFamilyInfo")
    public String addFamilyInfo(FamilyInfo familyInfo, Model model){
        familyInfoList.add(familyInfo);
        String message=familyInfo.getMemberName()+"的信息已添加成功，请继续添加，如添加完毕请点下一步";
        model.addAttribute("message",message);
        return "addFamilyInfo";
    }

    /***
     * 显示添加学业信息的界面
     * @return
     */
    @RequestMapping("/showAddStudyExperience")
    public String showAddStudyExperience(){
        return "addStudyInfo";
    }

    /***
     * 添加学业信息
     * @param studyInfo
     * @return
     */
    @RequestMapping("/addStudyExperience")
    public String addStudyExperience(studyInfo studyInfo){
        this.studyInfo=studyInfo;
        return "addWorkInfo";
    }

    /***
     * 添加工作信息
     * @param workInfo
     * @return
     */
    @RequestMapping("/addWorkInfo")
    public String addWorkInfo(workInfo workInfo){
        this.workInfo=workInfo;
        return "addHobby";
    }

    /***
     * 添加爱好
     * @param hobbies
     * @param model
     * @return
     */
    @RequestMapping("/addHobby")
    public String addHobby(String hobbies,Model model){
        this.hobbies=hobbies;
        model.addAttribute("name",basicInfo.getName());
        resumeService.generateXML(basicInfo,familyInfoList,studyInfo,workInfo,this.hobbies,image);
        return "initSuccess";
    }

    /***
     * 更新基本信息前的数据准备
     * @param model
     * @return
     */
    @RequestMapping("/beforeUpdateBasicInfo")
    public String beforeUpdateBasicInfo(Model model){
        try {
            //获取旧resume中的信息，用于前端显示
            domUtil.loadXML("src/main/resources/static/resume.xml");
            List<Object> values=domUtil.loadObject("basic_information");
            BasicInfo oldBasicInfo=new BasicInfo();
            oldBasicInfo.setName(values.get(0).toString());
            oldBasicInfo.setSex(values.get(1).toString());
            oldBasicInfo.setBirth(values.get(2).toString());
            oldBasicInfo.setNativeAdd(values.get(3).toString());
            oldBasicInfo.setAddress(values.get(4).toString());
            oldBasicInfo.setTel(values.get(5).toString());
            oldBasicInfo.setRelativeTel(values.get(6).toString());
            model.addAttribute("info", oldBasicInfo);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "updateBasicInfo";
    }

    /***
     * 更新基本信息
     * @param newBasicInfo
     * @return
     */
    @RequestMapping("/updateBasicInfo")
    public String updateBasicInfo(BasicInfo newBasicInfo){
        resumeService.updateBasicInformation(newBasicInfo);
        return "initSuccess";
    }

    /***
     * 显示添加学业信息界面
     * @return
     */
    @RequestMapping("/showAddStudyExperienceSingle")
    public String showAddStudyExperienceSingle(){
        return "addStudyInfoSingle";
    }

    /***
     * 添加学业信息
     * @param studyInfo
     * @return
     */
    @RequestMapping("/addStudyExperienceSingle")
    public String addStudyExperienceSingle(studyInfo studyInfo){
        resumeService.addInformation(studyInfo,"study_information");
        return "initSuccess";
    }

    /***
     * 显示删除爱好的界面
     * @return
     */
    @RequestMapping("/showDeleteHobby")
    public String showDeleteHobby(){
        return "deleteHobby";
    }

    /***
     * 删除爱好
     * @param tobeDeleted
     * @return
     */
    @RequestMapping("/deleteHobby")
    public String deleteHobby(String tobeDeleted){
        resumeService.deleteInformation(tobeDeleted,"hobby");
        return "initSuccess";
    }

    /***
     * 显示挑选内容项输出的页面
     * @return
     */
    @RequestMapping("/showSelectContent")
    public String showSelectContent(){
        return "selectContent";
    }

    /***
     * 挑选内容项输出页面
     * @param contentValue
     * @return
     */
    @RequestMapping("/outputSelectedContent")
    public String outputSelectedContent(String[] contentValue){
        try {
            domUtil.outputSelected(contentValue);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "initSuccess";
    }
}
