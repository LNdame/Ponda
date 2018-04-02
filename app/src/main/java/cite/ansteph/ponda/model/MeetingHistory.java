package cite.ansteph.ponda.model;

import java.io.Serializable;

/**
 * Created by loicstephan on 2018/02/12.
 */

public class MeetingHistory implements Serializable {

    int id,clientId,projectId ;

    String projectManagersRef,site, meetingDate;



    public MeetingHistory() {
    }

    public MeetingHistory(int id, int clientId, int projectId, String projectManagersRef, String site, String meetingDate) {
        this.id = id;
        this.clientId = clientId;
        this.projectId = projectId;
        this.projectManagersRef = projectManagersRef;
        this.site = site;
        this.meetingDate = meetingDate;

    }



    public MeetingHistory(int clientId, int projectId, String projectManagersRef, String site, String meetingDate) {
        this.clientId = clientId;
        this.projectId = projectId;
        this.projectManagersRef = projectManagersRef;
        this.site = site;
        this.meetingDate = meetingDate;

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

//    public String getProjectManagersRef() {
//        return projectManagersRef;
//    }

    public void setProjectManagersRef(String projectManagersRef) {
        this.projectManagersRef = projectManagersRef;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }



}
