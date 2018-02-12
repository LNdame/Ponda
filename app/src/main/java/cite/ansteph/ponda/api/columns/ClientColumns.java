package cite.ansteph.ponda.api.columns;

/**
 * Created by loicstephan on 2018/02/12.
 */

public interface ClientColumns extends DataColumns {

    String NAME  = "name";
    String WEBSITE_URL  = "website_url";

    String TELEPHONE = "telephone";
    String CONTACT_PERSON = "contact_person";
    String CONTACT_PHONE = "contact_phone";

    String EMAIL = "email";


    String[]PROJECTION = new String[]{_ID,NAME,WEBSITE_URL,TELEPHONE,CONTACT_PERSON,CONTACT_PHONE,EMAIL};

}
