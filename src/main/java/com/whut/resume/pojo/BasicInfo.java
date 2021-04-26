package com.whut.resume.pojo;

/**基本信息
 * @author AC
 * @describe
 * @date 2021/4/25 11:52
 */
public class BasicInfo {
    private String name;
    private String sex;
    private String birth;
    private String nativeAdd;
    private String address;
    private String tel;
    private String relativeTel;

    public BasicInfo(String name, String sex, String birth, String nativeAdd, String address, String tel, String relativeTel) {
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.nativeAdd = nativeAdd;
        this.address = address;
        this.tel = tel;
        this.relativeTel = relativeTel;
    }

    public BasicInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getNativeAdd() {
        return nativeAdd;
    }

    public void setNativeAdd(String nativeAdd) {
        this.nativeAdd = nativeAdd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRelativeTel() {
        return relativeTel;
    }

    public void setRelativeTel(String relativeTel) {
        this.relativeTel = relativeTel;
    }
}
