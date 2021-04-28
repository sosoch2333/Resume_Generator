package com.whut.resume.service.serviceImpl;
import com.whut.resume.pojo.BasicInfo;
import com.whut.resume.pojo.FamilyInfo;
import com.whut.resume.pojo.studyInfo;
import com.whut.resume.pojo.workInfo;
import com.whut.resume.service.ResumeService;
import com.whut.resume.util.domUtil;
import com.whut.resume.util.sqlUtil;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import java.io.File;
import java.util.List;

/**
 * @author AC <chuhan@133.cn>
 * @describe
 * @date 2021/4/25 12:08
 */
@Service
public class ResumeServiceImpl implements ResumeService {

    /***
     * 在输入了基本信息、家庭、学业、工作、爱好信息后，形成DOM树
     * @param basicInfo
     * @param familyInfo
     * @param studyInfo
     * @param workInfo
     * @param hobby
     * @param image
     */
    @Override
    public void generateXML(BasicInfo basicInfo, List<FamilyInfo> familyInfo, studyInfo studyInfo, workInfo workInfo, String hobby,String image){
        try {
            Element root=domUtil.DOCUMENT.createElement("resume");
            //图片
            Element imageElem= domUtil.DOCUMENT.createElement("image");
            imageElem.setTextContent(image);
            root.appendChild(imageElem);
            //基本信息
            root.appendChild(domUtil.parseInfo(basicInfo,"basic_information"));
            //家庭信息
            Element familyInfomation=domUtil.DOCUMENT.createElement("family_infomation");
            Integer i=0;
            for(FamilyInfo member:familyInfo) {
                Element element=domUtil.parseInfo(member,"member");
                element.setAttribute("id",i.toString());
                familyInfomation.appendChild(element);
                i++;
            }
            root.appendChild(familyInfomation);
            //学业信息
            root.appendChild(domUtil.parseInfo(studyInfo,"study_information"));
            //工作信息
            root.appendChild(domUtil.parseInfo(workInfo,"work_information"));
            //爱好
            String[] hobbies=hobby.split("\\s+");
            for(String h:hobbies){
                Element element=domUtil.DOCUMENT.createElement("hobby");
                element.setTextContent("hobby:"+h);
                root.appendChild(element);
            }
            domUtil.DOCUMENT.appendChild(root);

            //生成文件
            File target= new File("src/main/resources/static/resume.xml");
            domUtil.generateXML(target);
            //如果生成文件成功，将用户信息写入数据库
            sqlUtil.open();
            sqlUtil.addInfo(basicInfo,"basic_info",null);
            Integer currentID=sqlUtil.getID();//获取刚刚插入的用户id
            for(FamilyInfo info:familyInfo){
                sqlUtil.addInfo(info,"family_information",currentID);
            }
            sqlUtil.addInfo(studyInfo,"study_information",currentID);
            sqlUtil.addInfo(workInfo,"work_information",currentID);
            sqlUtil.addInfo(hobby,"hobby",currentID);
            sqlUtil.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /***
     * 更新基本信息
     * @param newBasicInfo
     */
    @Override
    public void updateBasicInformation(BasicInfo newBasicInfo) {
        try {
            if(newBasicInfo.getSex().equals("0")) {
                newBasicInfo.setSex("男");
            } else {
                newBasicInfo.setSex("女");
            }
            domUtil.update(newBasicInfo, "basic_information");
            File target = new File("src/main/resources/static/resume.xml");
            domUtil.generateXML(target);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * 在tagName下新增子元素，子元素信息来自object
     * @param object
     * @param tagName
     */
    @Override
    public void addInformation(Object object, String tagName) {
        try {
            domUtil.add(object, tagName);
            File target = new File("src/main/resources/static/resume.xml");
            domUtil.generateXML(target);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /***
     * 删除tagName下内容为tobeDeleted的元素
     * @param tobeDeleted
     * @param tagName
     */
    @Override
    public void deleteInformation(String tobeDeleted, String tagName){
        try{
            domUtil.delete(tobeDeleted,tagName);
            File target = new File("src/main/resources/static/resume.xml");
            domUtil.generateXML(target);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
