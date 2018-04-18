package cite.ansteph.ponda.api.columns;

/**
 * Created by loicstephan on 2018/02/11.
 */

public interface MeetingSubItemColumns extends DataColumns {



    String ITEMNOTE  = "itemnote";
    String MEETINGITEM_ID  = "meetingitem_id";
    String MEETING_ID = "meeting_id";
    String POSITION = "position";
    String STATUS = "status";
    String OWNER = "owner";
    String DONE_BY_DATE = "done_by_date";

    String[]PROJECTION = new String[]{_ID,ITEMNOTE,MEETINGITEM_ID,MEETING_ID,POSITION,STATUS, OWNER, DONE_BY_DATE};

}
