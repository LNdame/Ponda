package cite.ansteph.ponda.api.columns;

;

/**
 * Created by loicstephan on 2018/02/11.
 */

public interface AttendeeColumns extends DataColumns {

        String FIRSTNAME  = "firstname";
        String SURNAME  = "surname";
        String ORGANSATION = "organisation";
         String TELEPHONE = "telephone";
        String CELLPHONE = "cellphone";
        String FAX = "fax";
        String EMAIL = "email";


        String[]PROJECTION = new String[]{_ID,FIRSTNAME,SURNAME,ORGANSATION,TELEPHONE,CELLPHONE,FAX,EMAIL};

}
