package com.whut.resume.pojo;

/**学业信息
 * @author AC
 * @describe
 * @date 2021/4/25 14:23
 */
public class studyInfo {
    private String period;
    private String school;
    private String degree;

    public studyInfo(String period, String school, String degree) {
        this.period = period;
        this.school = school;
        this.degree = degree;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
