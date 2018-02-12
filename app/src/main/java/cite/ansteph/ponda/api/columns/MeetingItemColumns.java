package cite.ansteph.ponda.api.columns;

/**
 * Created by loicstephan on 2018/02/11.
 */

public interface MeetingItemColumns extends DataColumns {

    String ITEM_NAME  = "item_name";
    String MEETING_ID  = "meeting_id";
    String POSITION = "position";
    String STATUS = "status";


    String[]PROJECTION = new String[]{_ID,ITEM_NAME,MEETING_ID,POSITION,STATUS};

}
