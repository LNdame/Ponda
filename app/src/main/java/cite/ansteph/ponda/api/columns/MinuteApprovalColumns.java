package cite.ansteph.ponda.api.columns;

/**
 * Created by loicstephan on 2018/02/11.
 */

public interface MinuteApprovalColumns extends DataColumns {


    String REPRESENTING  = "representing";
    String NAME  = "name";
    String SIGNATURE = "signature";
    String COMMENTS = "comments";
    String MEETING_ID = "meeting_id";
    String ATTENDEE_ID = "attendee_id";

    String[]PROJECTION = new String[]{_ID,REPRESENTING,NAME,SIGNATURE,COMMENTS,MEETING_ID,ATTENDEE_ID};

}
