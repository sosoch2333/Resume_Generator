package com.whut.resume.pojo;

/**家庭信息
 * @author AC
 * @describe
 * @date 2021/4/25 13:53
 */
public class FamilyInfo {
    private String memberName;
    private String relation;

    public FamilyInfo(String name, String relation) {
        this.memberName = name;
        this.relation = relation;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
