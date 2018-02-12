package cite.ansteph.ponda.model;

/**
 * Created by loicstephan on 2018/02/12.
 */

public class VariationOrder {

    int id,projectId,meetingId,meetingItemId,meetingSubItemId;


    String variationOrder,motivation,approved;
            double omit,add,balance;


    public VariationOrder() {
    }


    public VariationOrder(int id, int projectId, int meetingId, int meetingItemId, int meetingSubItemId, String variationOrder, String motivation, String approved, double omit, double add, double balance) {
        this.id = id;
        this.projectId = projectId;
        this.meetingId = meetingId;
        this.meetingItemId = meetingItemId;
        this.meetingSubItemId = meetingSubItemId;
        this.variationOrder = variationOrder;
        this.motivation = motivation;
        this.approved = approved;
        this.omit = omit;
        this.add = add;
        this.balance = balance;
    }


    public VariationOrder(int projectId, int meetingId, int meetingItemId, int meetingSubItemId, String variationOrder, String motivation, String approved, double omit, double add, double balance) {
        this.projectId = projectId;
        this.meetingId = meetingId;
        this.meetingItemId = meetingItemId;
        this.meetingSubItemId = meetingSubItemId;
        this.variationOrder = variationOrder;
        this.motivation = motivation;
        this.approved = approved;
        this.omit = omit;
        this.add = add;
        this.balance = balance;
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

    public String getVariationOrder() {
        return variationOrder;
    }

    public void setVariationOrder(String variationOrder) {
        this.variationOrder = variationOrder;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public double getOmit() {
        return omit;
    }

    public void setOmit(double omit) {
        this.omit = omit;
    }

    public double getAdd() {
        return add;
    }

    public void setAdd(double add) {
        this.add = add;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
