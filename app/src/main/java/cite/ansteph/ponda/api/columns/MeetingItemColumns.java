package cite.ansteph.ponda.api.columns;

/**
 * Created by loicstephan on 2018/02/11.
 */

public interface MeetingItemColumns extends DataColumns {

    String ITEM_NAME  = "item_name";
    String MEETING_ID  = "meeting_id";
    String POSITION = "position";
    String STATUS = "status";
    String MEEETINGITEM_TYPE_ID = "meetingitem_type_id";


    String[]PROJECTION = new String[]{_ID,ITEM_NAME,MEETING_ID,POSITION,STATUS,MEEETINGITEM_TYPE_ID };

}
