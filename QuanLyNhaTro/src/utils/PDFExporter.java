package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import model.Rental;
import model.Room;
import model.Customer;

import java.io.FileOutputStream;

public class PDFExporter {

    public static void exportRentalToPDF(Rental rental, Room room, Customer customer) {
        try {
            Document document = new Document();
            String fileName = "hop_dong_" + rental.getRentalId() + ".pdf";

            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("RENTAL CONTRACT", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));

            // Content
            document.add(new Paragraph("Contract ID: " + rental.getRentalId()));
            document.add(new Paragraph("Room: " + room.getRoomName()));
            document.add(new Paragraph("Price: " + room.getPrice()));
            document.add(new Paragraph("Customer: " + customer.getName()));
            document.add(new Paragraph("Phone: " + customer.getPhone()));
            document.add(new Paragraph("CCCD: " + customer.getCccd()));
            document.add(new Paragraph("Rent Date: " + rental.getDateRent()));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Signature: ____________________"));

            document.close();

            System.out.println("PDF created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}