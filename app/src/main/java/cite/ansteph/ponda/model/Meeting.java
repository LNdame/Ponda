package cite.ansteph.ponda.model;

/**
 * Created by loicstephan on 2018/02/12.
 */

public class Meeting {

    int id,clientId,projectId ;

    String projectManagersRef,site, startDate,endDate;



    public Meeting() {
    }

    public Meeting(int id, int clientId, int projectId, String projectManagersRef, String site, String startDate, String endDate) {
        this.id = id;
        this.clientId = clientId;
        this.projectId = projectId;
        this.projectManagersRef = projectManagersRef;
        this.site = site;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public Meeting(int clientId, int projectId, String projectManagersRef, String site, String startDate, String endDate) {
        this.clientId = clientId;
        this.projectId = projectId;
        this.projectManagersRef = projectManagersRef;
        this.site = site;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectManagersRef() {
        return projectManagersRef;
    }

    public void setProjectManagersRef(String projectManagersRef) {
        this.projectManagersRef = projectManagersRef;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
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
}
