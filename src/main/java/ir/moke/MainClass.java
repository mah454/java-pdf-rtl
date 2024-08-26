package ir.moke;

import com.ibm.icu.text.ArabicShapingException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException {
        File f = new File("/usr/share/fonts/truetype/farsiweb/nazli.ttf");
        PDDocument doc = new PDDocument();
        PDPage Page = new PDPage();
        doc.addPage(Page);
        PDPageContentStream Writer = new PDPageContentStream(doc, Page);
        Writer.beginText();
        Writer.setFont(PDType0Font.load(doc, f), 20);
        Writer.newLineAtOffset(0, 700);
        String s = "سلام من مهدی است, آیا گچپژ هم میفهمم ؟!";
        Writer.showText(bidiReorder(s));
        Writer.endText();
        Writer.close();
        doc.save(new File("File_Test.pdf"));
        doc.close();
    }

    private static String bidiReorder(String text) {
        try {
            return new StringBuilder(new PersianShaping(PersianShaping.LETTERS_SHAPE).shape(text))
                    .reverse().toString();
        } catch (ArabicShapingException e) {
            throw new RuntimeException(e);
        }
    }

}