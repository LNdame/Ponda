package cite.ansteph.ponda.model;

/**
 * Created by loicstephan on 2018/02/12.
 */

public class MeetingSubItem {

    int id, meetingId, meetingItemId;

    String itemNote,position,status;


    public MeetingSubItem() {
    }

    public MeetingSubItem(int meetingId, int meetingItemId, String itemNote, String position, String status) {
        this.meetingId = meetingId;
        this.meetingItemId = meetingItemId;
        this.itemNote = itemNote;
        this.position = position;
        this.status = status;
    }

    public MeetingSubItem(int id, int meetingId, int meetingItemId, String itemNote, String position, String status) {
        this.id = id;
        this.meetingId = meetingId;
        this.meetingItemId = meetingItemId;
        this.itemNote = itemNote;
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
}
