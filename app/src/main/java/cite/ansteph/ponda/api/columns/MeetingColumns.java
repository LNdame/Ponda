package cite.ansteph.ponda.api.columns;

/**
 * Created by loicstephan on 2018/02/11.
 */

public interface MeetingColumns extends DataColumns {

    String PROJECT_MANAGER_REF="project_managers_ref"  ;
    String CLIENT_ID  = "client_id";
    String PROJECT_ID  = "project_id";
    String SITE = "site";
    String START_DATE = "start_date";
    String START_TIME = "end_time";
    String VENUE = "venue";

    String[]PROJECTION = new String[]{_ID,PROJECT_MANAGER_REF,CLIENT_ID,PROJECT_ID,SITE,START_DATE, START_TIME,VENUE};

}
