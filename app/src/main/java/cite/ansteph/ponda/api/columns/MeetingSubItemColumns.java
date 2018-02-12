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


    String[]PROJECTION = new String[]{_ID,ITEMNOTE,MEETINGITEM_ID,MEETING_ID,POSITION,STATUS};

}
