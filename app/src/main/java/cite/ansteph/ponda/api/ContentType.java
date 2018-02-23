package cite.ansteph.ponda.api;

import android.net.Uri;

/**
 * Created by loicstephan on 2018/02/11.
 */

public class ContentType {

    public static final Uri ATTENDEE_CONTENT_URI = Uri.parse("content://cite.ansteph.ponda.contentprovider.attendeecontentprovider/ponda");
    public static final Uri CLIENT_CONTENT_URI = Uri.parse("content://cite.ansteph.ponda.contentprovider.clientcontentprovider/ponda");
    public static final Uri PROJECT_CONTENT_URI = Uri.parse("content://cite.ansteph.ponda.contentprovider.clientcontentprovider/ponda");
}
