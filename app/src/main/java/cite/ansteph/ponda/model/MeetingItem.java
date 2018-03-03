package cite.ansteph.ponda.model;

import java.io.Serializable;

/**
 * Created by loicstephan on 2018/02/12.
 */

public class MeetingItem implements Serializable {

int id, meetingId ;

    String itemName,position,status;


    public MeetingItem() {
    }

    //populate
    public MeetingItem(int id, int meetingId, String itemName) {
        this.id = id;
        this.meetingId = meetingId;
        this.itemName = itemName;
    }

    public MeetingItem(int id, int meetingId, String itemName, String position, String status) {
        this.id = id;
        this.meetingId = meetingId;
        this.itemName = itemName;
        this.position = position;
        this.status = status;
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
}
