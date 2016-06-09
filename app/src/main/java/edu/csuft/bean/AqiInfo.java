package edu.csuft.bean;

import java.io.Serializable;

public class AqiInfo implements Serializable {

    /** aqi等级 */
    private String level;
    /** aqi等级符合颜色 */
    private String color;
    /** 影响 */
    private String affect;
    /** 采取措施 */
    private String measure;

    public AqiInfo(String level, String color, String affect, String measure) {
        this.level = level;
        this.color = color;
        this.affect = affect;
        this.measure = measure;
    }

    public AqiInfo(){}

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAffect() {
        return affect;
    }

    public void setAffect(String affect) {
        this.affect = affect;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
