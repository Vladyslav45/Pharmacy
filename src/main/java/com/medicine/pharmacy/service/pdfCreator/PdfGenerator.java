package com.medicine.pharmacy.service.pdfCreator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.medicine.pharmacy.model.Basket;
import com.medicine.pharmacy.model.User;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

@Component
public class PdfGenerator {

    public ByteArrayInputStream generatePdf(List<Basket> basketList, User user) throws DocumentException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        addParagraphMainHeader("FAKTURA : " , LocalDate.now(), document);
        addParagraph("Data wystawienia : ", LocalDate.now(), document);
        addParagraph("Data sprzedazy : ", LocalDate.now(), document);
        document.add(new Phrase(""));
        addTablePeople(user, document);
        document.add(new Phrase(""));
        document.add(new Phrase(""));
        addTable(basketList, document);

        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addTable(List<Basket> basketList, Document document) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        PdfPTable table = new PdfPTable(6);

        addTableHeader(table, "Name");
        addTableHeader(table, "Price");
        addTableHeader(table, "INN");
        addTableHeader(table, "Recipe");
        addTableHeader(table, "Count");
        addTableHeader(table, "AllPrice");

        basketList.forEach(basket -> {
            addRow(table, basket.getPreparation().getName());
            addRow(table, String.valueOf(basket.getPreparation().getPrice()));
            addRow(table, basket.getPreparation().getINN());
            addRow(table, String.valueOf(basket.getPreparation().isRecipe()));
            addRow(table, String.valueOf(basket.getCount()));
            addRow(table, String.valueOf(basket.getPrice()));
        });

        paragraph.add(table);
        document.add(paragraph);
    }

    private void addTablePeople(User user, Document document) throws DocumentException {
        Paragraph paragraph = new Paragraph();

        PdfPTable pdfPTable = new PdfPTable(2);

        addTableHeader(pdfPTable, "Sprzedawca");
        addTableHeader(pdfPTable, "Nadbywca");

        addRow(pdfPTable, "Gallenica");
        addRow(pdfPTable, user.getName());
        addRow(pdfPTable, "ul. Dolna Panny Marii 21a");
        addRow(pdfPTable, user.getEmail());
        addRow(pdfPTable, "20-010 Lublin");
        addRow(pdfPTable, user.getPhoneNumber());

        paragraph.add(pdfPTable);

        document.add(paragraph);
    }

    private void addParagraphMainHeader(String text, LocalDate localDate, Document document) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.TIMES, 14,BaseColor.BLACK);
        Chunk chunk = new Chunk(text + localDate, font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
    }

    private void addParagraph(String text, LocalDate localDate, Document document) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.TIMES, 14, BaseColor.BLACK);
        Chunk textChunk = new Chunk(text + localDate, font);
        Paragraph paragraph = new Paragraph(textChunk);

        paragraph.setAlignment(Element.ALIGN_RIGHT);

        document.add(paragraph);
    }

    private void addRow(PdfPTable table, String text) {
        table.addCell(text);
    }

    private void addTableHeader(PdfPTable table, String headerTitle) {
        PdfPCell header = new PdfPCell();
//        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(2);
        header.setPhrase(new Phrase(headerTitle));
        table.addCell(header);
    }
}
