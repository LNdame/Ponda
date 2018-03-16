package cite.ansteph.ponda.api;

import android.net.Uri;

/**
 * Created by loicstephan on 2018/02/11.
 */

public class ContentType {

    public static final Uri ATTENDEE_CONTENT_URI = Uri.parse("content://cite.ansteph.ponda.contentprovider.attendeecontentprovider/ponda");
    public static final Uri CLIENT_CONTENT_URI = Uri.parse("content://cite.ansteph.ponda.contentprovider.clientcontentprovider/ponda");
    public static final Uri PROJECT_CONTENT_URI = Uri.parse("content://cite.ansteph.ponda.contentprovider.projectcontentprovider/ponda");
    public static final Uri MEETING_CONTENT_URI = Uri.parse("content://cite.ansteph.ponda.contentprovider.meetingcontentprovider/ponda");
    public static final Uri MEETING_HISTORY_CONTENT_URI = Uri.parse("content://cite.ansteph.ponda.contentprovider.meetinghistorycontentprovider/ponda");

    public static final Uri MEETINGITEM_CONTENT_URI =  Uri.parse("content://cite.ansteph.ponda.contentprovider.meetingitemcontentprovider/ponda");
    public static final Uri MEETINGSUBITEM_CONTENT_URI = Uri.parse("content://cite.ansteph.ponda.contentprovider.meetingsubitemcontentprovider/ponda");
    public static final Uri PAYMENTCERTIFICATE_CONTENT_URI = Uri.parse("content://cite.ansteph.ponda.contentprovider.paymentcertificatecontentprovider/ponda");
    public static final Uri VARIATIONCERTIFICATE_CONTENT_URI = Uri.parse("content://cite.ansteph.ponda.contentprovider.variationordercontentprovider/ponda");



}
