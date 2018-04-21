package cite.ansteph.ponda.api.columns;

/**
 * Created by loicstephan on 2018/04/19.
 */

public interface ApologyColumns extends DataColumns {

    String MEETING_ID  = "meeting_id";
    String MEETINGSUBITEM_ID  = "meetingsubitem_id";
    String ATTENDEE_ID = "attendee_id";



    String[]PROJECTION = new String[]{_ID,MEETING_ID,MEETINGSUBITEM_ID,ATTENDEE_ID};

}
