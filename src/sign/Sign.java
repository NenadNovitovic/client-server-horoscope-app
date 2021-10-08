package sign;

import java.io.Serializable;

public class Sign implements Serializable {

    int id; //verovatno ne treba
    String name;
    String basicDescription;
    String famousPeopleInSameSign;
    String dailyHoroscope;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBasicDescription() {
        return basicDescription;
    }

    public void setBasicDescription(String basicDescription) {
        this.basicDescription = basicDescription;
    }

    public String getFamousPeopleInSameSign() {
        return famousPeopleInSameSign;
    }

    public void setFamousPeopleInSameSign(String famousPeopleInSameSign) {
        this.famousPeopleInSameSign = famousPeopleInSameSign;
    }

    public String getDailyHoroscope() {
        return dailyHoroscope;
    }

    public void setDailyHoroscope(String dailyHoroscope) {
        this.dailyHoroscope = dailyHoroscope;
    }


    public Sign(){
        id=0;
        name="";
        basicDescription="";
        famousPeopleInSameSign="";
        dailyHoroscope="";
    }

    public Sign(int id,String name,String basicDesc,String famousPeople,String dailyHoroscope){
        this.id=id;
        this.name=name;
        this.basicDescription=basicDesc;
        this.famousPeopleInSameSign=famousPeople;
        this.dailyHoroscope=dailyHoroscope;
    }

    @Override
    public String toString() {
        return "Sign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", basicDescription='" + basicDescription + '\'' +
                ", famousPeopleInSameSign='" + famousPeopleInSameSign + '\'' +
                ", birthStone='" + dailyHoroscope + '\'' +
                '}';
    }
}
