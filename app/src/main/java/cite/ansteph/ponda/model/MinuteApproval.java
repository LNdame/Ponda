package cite.ansteph.ponda.model;

/**
 * Created by loicstephan on 2018/02/12.
 */

public class MinuteApproval {

    int id,meetingId,attendeeId;


    String representing, name,signature,comments;


    public MinuteApproval() {
    }


    public MinuteApproval(int meetingId, int attendeeId, String representing, String name, String signature, String comments) {
        this.meetingId = meetingId;
        this.attendeeId = attendeeId;
        this.representing = representing;
        this.name = name;
        this.signature = signature;
        this.comments = comments;
    }

    public MinuteApproval(int id, int meetingId, int attendeeId, String representing, String name, String signature, String comments) {
        this.id = id;
        this.meetingId = meetingId;
        this.attendeeId = attendeeId;
        this.representing = representing;
        this.name = name;
        this.signature = signature;
        this.comments = comments;
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

    public int getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(int attendeeId) {
        this.attendeeId = attendeeId;
    }

    public String getRepresenting() {
        return representing;
    }

    public void setRepresenting(String representing) {
        this.representing = representing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
