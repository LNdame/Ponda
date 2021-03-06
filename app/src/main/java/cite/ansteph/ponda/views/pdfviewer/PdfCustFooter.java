package cite.ansteph.ponda.views.pdfviewer;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Created by loicstephan on 2017/03/27.
 */

public class PdfCustFooter extends PdfPageEventHelper {


    Font ffont = new Font(Font.FontFamily.COURIER ,8, Font.NORMAL);

    String headerMsg , footerMsg;

    public PdfCustFooter(String headerMsg, String footerMsg) {
        this.headerMsg = headerMsg;
        this.footerMsg = footerMsg;
    }

    public PdfCustFooter() {
        headerMsg = "Ponda";

        this.footerMsg ="This report was generated by Ponda for more info http://citesa.co.za";
    }




    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();

        Phrase header = new Phrase("Ponda", ffont);
        Phrase footer = new Phrase("This report was generated by Ponda for more info http://citesa.co.za", ffont);

        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                header,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.top() + 10, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                footer,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 10, 0);


    }

    public String getHeaderMsg() {
        return headerMsg;
    }

    public void setHeaderMsg(String headerMsg) {
        this.headerMsg = headerMsg;
    }

    public String getFooterMsg() {
        return footerMsg;
    }

    public void setFooterMsg(String footerMsg) {
        this.footerMsg = footerMsg;
    }
}
