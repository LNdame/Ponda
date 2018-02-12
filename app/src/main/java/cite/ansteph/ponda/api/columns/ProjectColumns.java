package cite.ansteph.ponda.api.columns;

/**
 * Created by loicstephan on 2018/02/11.
 */

public interface ProjectColumns extends DataColumns {


    String NAME  = "name";
    String CLIENT_ID  = "client_id";
    String START_DATE = "start_date";
    String END_DATE = "end_date";
    String PROJ_MAN_NAME = "proj_man_name";


    String[]PROJECTION = new String[]{_ID,NAME,CLIENT_ID,START_DATE,END_DATE,PROJ_MAN_NAME};

}
