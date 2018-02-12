package cite.ansteph.ponda.api.columns;

/**
 * Created by loicstephan on 2018/02/11.
 */

public interface VariationOrderColumns extends DataColumns {

    String VARIATIONORDER  = "variationorder";
    String MOTIVATION  = "motivation";
    String APPROVED  = "approved";
    String OMIT  = "omit";
    String ADD = "add";
    String BALANCE = "balance";

    String PROJECT_ID = "project_id";

    String MEETING_ID = "meeting_id";
    String MEETINGITEM_ID  = "meetingitem_id";
    String MEETINGSUBITEM_ID  = "meetingsubitem_id";

    String[]PROJECTION = new String[]{_ID,VARIATIONORDER,MOTIVATION,APPROVED,OMIT,ADD,BALANCE,PROJECT_ID,
            MEETING_ID,MEETINGITEM_ID,MEETINGSUBITEM_ID};

}
