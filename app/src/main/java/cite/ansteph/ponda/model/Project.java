package cite.ansteph.ponda.model;

import java.io.Serializable;

/**
 * Created by loicstephan on 2018/02/12.
 */

public class Project implements Serializable {

    int id,clientId;

    String name, startDate, endDate, projManName;

    public Project(){

    }


    //delete after use
    public Project(int id, int clientId, String name, String projManName) {
        this.id = id;
        this.clientId = clientId;
        this.name = name;
        this.projManName = projManName;
    }

    public Project(int clientId, String name, String startDate, String endDate, String projManName) {
        this.name = name;
        this.clientId = clientId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projManName = projManName;
    }


    public Project(int id, String name, int clientId, String startDate, String endDate, String projManName) {
        this.id = id;
        this.name = name;
        this.clientId = clientId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projManName = projManName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return id;
    }

    public void setClientId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProjManName() {
        return projManName;
    }

    public void setProjManName(String projManName) {
        this.projManName = projManName;
    }


}
