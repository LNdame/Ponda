package cite.ansteph.ponda.model;

import java.io.Serializable;

/**
 * Created by loicstephan on 2018/02/12.
 */

public class MeetingItem implements Serializable {

    int id, meetingId , type_id;

    String itemName,position,status;

    Meeting meeting;

    public MeetingItem() {
    }

    public MeetingItem(String itemName, String position) {
        this.itemName = itemName;
        this.position = position;
    }


    public MeetingItem( String itemName, String position, Meeting meeting ) {

        this.itemName = itemName;
        this.position = position;
        this.meeting = meeting;
    }

    //populate
    public MeetingItem(int id, int meetingId, String itemName) {
        this.id = id;
        this.meetingId = meetingId;
        this.itemName = itemName;
    }

    public MeetingItem(int id, int meetingId, String itemName, String position, String status, int type_id) {
        this.id = id;
        this.meetingId = meetingId;
        this.itemName = itemName;
        this.position = position;
        this.status = status;
        this.type_id = type_id;
    }


    public MeetingItem(int meetingId, String itemName, String position, String status) {
        this.meetingId = meetingId;
        this.itemName = itemName;
        this.position = position;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }
}
