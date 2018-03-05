package cite.ansteph.ponda.template;

/**
 * Created by loicstephan on 2018/03/02.
 */

public class MeetingTemplate {


    public MeetingTemplate() {
    }

    static String ATTENDANCE_REGISTER ="ATTENDANCE REGISTER";
    static String APOLOGIES ="APOLOGIES";
    static String MINUTES_PREVIOUS_MEETING="MINUTES OF PREVIOUS MEETING";
    static String MATTERS_ARISING="MATTERS ARISING";
    static String CONTRACT_DETAILS="CONTRACT DETAILS";
    static String PROGRAMME="PROGRAMME";
    static String DELAYS="DELAYS";
    static String CASH_FLOW="CASH FLOW";
    static  String PAYMENT_CERTIFICATES="PAYMENT CERTIFICATES";
    static  String PROGRESS="PROGRESS";
    static  String DAILY_WORK_SCHEDULES="DAILY WORK SCHEDULES";
    static  String PLANT_LABOUR_REPORT="PLANT AND LABOUR REPORT";
    static  String DRAWINGS_ISSUED="DRAWINGS ISSUED";
    static  String INFORMATION_REQUIRED="INFORMATION REQUIRED";
    static  String NOMINATED_SUB_CONTRACTORS="NOMINATED SUB CONTRACTORS";
    static  String DOMESTIC_SUB_CONTRACTORS="DOMESTIC SUB CONTRACTORS";
    static  String STRUCTURAL="STRUCTURAL";
    static  String INDEPENDENT_DEVELOPMENT_TRUST="INDEPENDENT DEVELOPMENT TRUST";
    static  String SOCIAL_FACILITATION="SOCIAL FACILITATION";
    static  String CONTRACT_INSTRUCTIONS="CONTRACT INSTRUCTIONS";
    static  String VARIATION_ORDERS="VARIATION ORDERS";
    static  String HEALTH_SAFETY="HEALTH & SAFETY";
    static  String COMMISSIONING_TESTING="COMMISSIONING & TESTING";
    static  String GENERAL="GENERAL";
    static  String APPROVAL_MINUTES="APPROVAL OF MINUTES";

    static  String MEETINGS="MEETINGS";





   public static String [] Template = new String[]{ATTENDANCE_REGISTER,
     APOLOGIES ,
     MINUTES_PREVIOUS_MEETING,
     MATTERS_ARISING,
     CONTRACT_DETAILS,
     PROGRAMME,
     DELAYS,
     CASH_FLOW,
     PAYMENT_CERTIFICATES,
     PROGRESS,
     DAILY_WORK_SCHEDULES,
     PLANT_LABOUR_REPORT,
     DRAWINGS_ISSUED,
     INFORMATION_REQUIRED,
     NOMINATED_SUB_CONTRACTORS,
     DOMESTIC_SUB_CONTRACTORS,
     STRUCTURAL,
     INDEPENDENT_DEVELOPMENT_TRUST,
     SOCIAL_FACILITATION,
     CONTRACT_INSTRUCTIONS,
     VARIATION_ORDERS,
     HEALTH_SAFETY,
     COMMISSIONING_TESTING,
     GENERAL,
     APPROVAL_MINUTES,

     MEETINGS};

    public interface TemplateType{

        public static final int COMMON_ITEM=0;
        public static final int ATTENDEE_ITEM=1;
        public static final int PAYMENT_CERTIFICATE_ITEM=2;
        public static final int VARIOUS_ORDER_ITEM=3;

    }


   public static int[] TemplateTypeOrder= new int[]{
          TemplateType.ATTENDEE_ITEM,
          TemplateType.ATTENDEE_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.PAYMENT_CERTIFICATE_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.VARIOUS_ORDER_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM,
            TemplateType.COMMON_ITEM


    };








}
