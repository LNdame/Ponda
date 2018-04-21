package cite.ansteph.ponda.model;

import java.io.Serializable;

/**
 * Created by loicstephan on 2018/02/12.
 */

public class MeetingSubItem implements Serializable{

    int id, meetingId, meetingItemId;

    String itemNote,position,status, owner, done_by_date;

    Meeting meeting; MeetingItem meetingItem;


    public MeetingSubItem() {
    }


    public MeetingSubItem(int id,int meetingId, int meetingItemId, String itemNote, String position, String owner, String done_by_date) {
        this.meetingId = meetingId;
        this.meetingItemId = meetingItemId;
        this.itemNote = itemNote;
        this.position = position;
        this.owner = owner;
        this.done_by_date = done_by_date;

    }

    public MeetingSubItem(int meetingId, int meetingItemId, String itemNote, String position, String owner, String done_by_date) {
        this.meetingId = meetingId;
        this.meetingItemId = meetingItemId;
        this.itemNote = itemNote;
        this.position = position;
        this.owner = owner;
        this.done_by_date = done_by_date;

    }

    public MeetingSubItem(int meetingId, int meetingItemId, String itemNote, String position) {
        this.meetingId = meetingId;
        this.meetingItemId = meetingItemId;
        this.itemNote = itemNote;
        this.position = position;


    }


    public MeetingSubItem(int meetingId, int meetingItemId, String itemNote, String position, String status, String owner, String done_by_date) {
        this.meetingId = meetingId;
        this.meetingItemId = meetingItemId;
        this.itemNote = itemNote;
        this.position = position;
        this.status = status;
        this.owner = owner;
        this.done_by_date = done_by_date;
    }

    public MeetingSubItem(int id, int meetingId, int meetingItemId, String itemNote, String position, String status, String owner, String done_by_date) {
        this.id = id;
        this.meetingId = meetingId;
        this.meetingItemId = meetingItemId;
        this.itemNote = itemNote;
        this.position = position;
        this.status = status;
        this.owner = owner;
        this.done_by_date = done_by_date;

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

    public int getMeetingItemId() {
        return meetingItemId;
    }

    public void setMeetingItemId(int meetingItemId) {
        this.meetingItemId = meetingItemId;
    }

    public String getItemNote() {
        return itemNote;
    }

    public void setItemNote(String itemNote) {
        this.itemNote = itemNote;
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


    public String getDoneByDate() {
        return done_by_date;
    }

    public void setDoneByDate(String done_by_date) {
        this.done_by_date = done_by_date;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }



    public MeetingItem getMeetingItem() {
        return meetingItem;
    }

    public void setMeetingItem(MeetingItem meetingItem) {
        this.meetingItem = meetingItem;
    }
}
