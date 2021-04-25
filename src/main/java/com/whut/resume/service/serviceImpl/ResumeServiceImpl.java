package com.whut.resume.service.serviceImpl;

import com.sun.media.jfxmedia.events.NewFrameEvent;
import com.sun.org.apache.bcel.internal.util.ClassPath;
import com.whut.resume.pojo.BasicInfo;
import com.whut.resume.pojo.FamilyInfo;
import com.whut.resume.pojo.studyInfo;
import com.whut.resume.pojo.workInfo;
import com.whut.resume.service.ResumeService;
import com.whut.resume.util.domUtil;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.io.File;
import java.util.List;

/**
 * @author AC <chuhan@133.cn>
 * @describe
 * @date 2021/4/25 12:08
 */
@Service
public class ResumeServiceImpl implements ResumeService {

    @Override
    public void generateXML(BasicInfo basicInfo, List<FamilyInfo> familyInfo, studyInfo studyInfo, workInfo workInfo, String hobby){
        try {
            Element root=domUtil.DOCUMENT.createElement("resume");
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
                element.setTextContent(h);
                root.appendChild(element);
            }
            domUtil.DOCUMENT.appendChild(root);

            //生成文件
            File target= new File("resume.xml");
            domUtil.generateXML(target);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
