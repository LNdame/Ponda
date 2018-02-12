package cite.ansteph.ponda.api.columns;

/**
 * Created by loicstephan on 2018/02/11.
 */

public interface PaymentCertificateColumns extends DataColumns {


    String PAYMENTCERTIFICATE  = "paymentcertificate";
    String ISSUE_DATE  = "issue_date";
    String PAID = "paid";
    String DATE_DUE = "date_due";
    String DAY_LATE = "day_late";
    String SIGNED_COPY = "signed_copy";
    String AMOUNT = "amount";
    String PROJECT_ID = "project_id";

    String MEETING_ID = "meeting_id";
    String MEETINGITEM_ID  = "meetingitem_id";
    String MEETINGSUBITEM_ID  = "meetingsubitem_id";



    String[]PROJECTION = new String[]{_ID,PAYMENTCERTIFICATE,ISSUE_DATE,PAID,DATE_DUE,DAY_LATE,SIGNED_COPY,AMOUNT,PROJECT_ID,
            MEETING_ID,MEETINGITEM_ID,MEETINGSUBITEM_ID};

}
