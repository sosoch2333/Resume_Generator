package com.whut.resume.pojo;

/**工作信息
 * @author AC
 * @describe
 * @date 2021/4/25 14:33
 */
public class workInfo {
    private String workPeriod;
    private String company;
    private String desc;

    public workInfo(String period, String company, String desc) {
        this.workPeriod = period;
        this.company = company;
        this.desc = desc;
    }

    public String getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(String workPeriod) {
        this.workPeriod = workPeriod;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
