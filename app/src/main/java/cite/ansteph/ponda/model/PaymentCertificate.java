package cite.ansteph.ponda.model;

/**
 * Created by loicstephan on 2018/02/12.
 */

public class PaymentCertificate {


    int id,projectId,meetingId,meetingItemId,meetingSubItemId;
    String paymentcertificate,issueDate,paid,dateDue,dayLate,signedCopy;
    double amount;


    public PaymentCertificate() {
    }

    public PaymentCertificate(int projectId, int meetingId, int meetingItemId, int meetingSubItemId, String paymentcertificate, String issueDate, String paid, String dateDue, String dayLate, String signedCopy, double amount) {
        this.projectId = projectId;
        this.meetingId = meetingId;
        this.meetingItemId = meetingItemId;
        this.meetingSubItemId = meetingSubItemId;
        this.paymentcertificate = paymentcertificate;
        this.issueDate = issueDate;
        this.paid = paid;
        this.dateDue = dateDue;
        this.dayLate = dayLate;
        this.signedCopy = signedCopy;
        this.amount = amount;
    }


    public PaymentCertificate(int id, int projectId, int meetingId, int meetingItemId, int meetingSubItemId, String paymentcertificate, String issueDate, String paid, String dateDue, String dayLate, String signedCopy, double amount) {
        this.id = id;
        this.projectId = projectId;
        this.meetingId = meetingId;
        this.meetingItemId = meetingItemId;
        this.meetingSubItemId = meetingSubItemId;
        this.paymentcertificate = paymentcertificate;
        this.issueDate = issueDate;
        this.paid = paid;
        this.dateDue = dateDue;
        this.dayLate = dayLate;
        this.signedCopy = signedCopy;
        this.amount = amount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getMeetingItemId() {
        return meetingItemId;
    }

    public void setMeetingItemId(int meetingItemId) {
        this.meetingItemId = meetingItemId;
    }

    public int getMeetingSubItemId() {
        return meetingSubItemId;
    }

    public void setMeetingSubItemId(int meetingSubItemId) {
        this.meetingSubItemId = meetingSubItemId;
    }

    public String getPaymentcertificate() {
        return paymentcertificate;
    }

    public void setPaymentcertificate(String paymentcertificate) {
        this.paymentcertificate = paymentcertificate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getDayLate() {
        return dayLate;
    }

    public void setDayLate(String dayLate) {
        this.dayLate = dayLate;
    }

    public String getSignedCopy() {
        return signedCopy;
    }

    public void setSignedCopy(String signedCopy) {
        this.signedCopy = signedCopy;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
