package be.gamepath.projectgamepath.utility;

import be.gamepath.projectgamepath.entities.Order;
import be.gamepath.projectgamepath.entities.ProductKey;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.primefaces.model.file.UploadedFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManaging {

    //location of folder for file.
    private static final String FOLDER_PATH_DOWNLOAD = "C:/wamp/www/imageFolderLocalHost/";
    private static final String FOLDER_PATH_APPLY = "http://localhost/imageFolderLocalHost/";
    private static final String DEFAULT_FILE = "default.png";

    private static final String FOLDER_DOCUMENT = "pdf/";




    //save a file (from input file, like image).
    public static boolean saveNewFile(UploadedFile uploadingFile, String pathFolder){
        boolean out = false;

        //instance file and path.
        File file = new File(
                FOLDER_PATH_DOWNLOAD +
                (pathFolder == null || pathFolder.equals("")? "": pathFolder + "/") +
                uploadingFile.getFileName()
        );
        Path filePath = file.toPath();

        //delete previous file if existing.
        if(Files.exists(filePath)){
            file.deleteOnExit();
        }

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            outputStream.write(uploadingFile.getContent());
            out = true;
        } catch (IOException e) {
            Utility.debug("error into saveNewFile : " + e.getMessage());
        }

        return out;
    }

    public static boolean saveNewFile(UploadedFile uploadingFile){
        return FileManaging.saveNewFile(uploadingFile, null);
    }



    //get seed of file in folder download.
    public static String getUrlForApply(String nameFile, String pathFolder){
        return getUrlForApply(pathFolder + "/" + nameFile);
    }
    public static String getUrlForApply(String nameFile){
        return FOLDER_PATH_APPLY+nameFile;
    }
    public static String getUrlForApply(UploadedFile uploadingFile){
        return getUrlForApply(uploadingFile.getFileName());
    }
    public static String getUrlForApply(UploadedFile uploadingFile, String pathFolder){
        return getUrlForApply(pathFolder + "/" + uploadingFile.getFileName());
    }
    public static String getDefaultUrlForApply(){
        return getUrlForApply(DEFAULT_FILE);
    }



    /**
     * function to create a file PDF.
     * @param order entity order (already in DB), used for get data to print in pdf.
     * @return full path of file PDF create (C://---/---.pdf).
     */
    public static String createPDFOrder(Order order) throws Exception {

        if(order.getId() == 0){
            Utility.debug("error catch in createPDFOrder, order id equals 0.");
            throw new Exception("order invalid");
        }

        //prepare file name and path.
        String namePDF = "commande_"+order.getId();
        String pathPDF = FOLDER_PATH_DOWNLOAD + FOLDER_DOCUMENT + namePDF + ".pdf";

        //prepare document.
        Document document = new Document();

        try {

            //prepare to write on document.
            PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(pathPDF)));
            document.open();

            //create font need for the pdf.
            Font fontTitle = new Font(Font.FontFamily.HELVETICA); //font for title.
            fontTitle.setSize(18);
            fontTitle.setStyle(Font.BOLD);
            fontTitle.isUnderlined();
            Font fontTable = new Font(Font.FontFamily.HELVETICA); //font for text in table data.
            fontTable.setSize(12);
            Font fontTableHeader = new Font(Font.FontFamily.HELVETICA); //font for text table header.
            fontTableHeader.setSize(12);
            fontTableHeader.setStyle(Font.BOLD);
            Font fontTableCrossed = new Font(Font.FontFamily.HELVETICA); //font for text table money (crossed).
            fontTableCrossed.setSize(12);
            fontTableCrossed.setStyle(Font.STRIKETHRU);
            Font fontAddress = new Font(Font.FontFamily.HELVETICA); //font for text address game path.
            fontAddress.setSize(9);

            //create spacing elements.
            Paragraph spacingElements = new Paragraph(" ");
            spacingElements.setExtraParagraphSpace(10f);


            //logo of society.
            Image imageLogo = Image.getInstance(FOLDER_PATH_DOWNLOAD + "logoGamePath.png");
            imageLogo.scaleAbsolute(125f, 60f);

            //address game path.
            Paragraph addressGamePath = new Paragraph("Boulevard Joseph Tirou, 42", fontAddress);
            addressGamePath.add("\n");
            addressGamePath.add("6000 Charleroi (Belgique)");
            addressGamePath.setExtraParagraphSpace(10f);

            //title.
            Paragraph titleParagraph = new Paragraph("Détailles de commande "+order.getId(), fontTitle);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            titleParagraph.setMultipliedLeading(1.5f); //spacing top.

            //paragraph user name.
            Paragraph paragraphUser = new Paragraph("Commande de "+order.getUser().getLastName()+" "+order.getUser().getFirstName()+".", fontTable);
            paragraphUser.add("\n");
            paragraphUser.add("Passée le "+Utility.castDateToString(order.getValidateBasketDate(), "dd/MM/yyyy"));

            //create table of data.
            float[] tableColumnWidth = {150f, 220f, 130f, 100f}; //width.
            PdfPTable table = new PdfPTable(tableColumnWidth);
            String[] tableTitles = {"Produit", "Clef d'activation", "Réduction", "Prix"}; //th.
            PdfPCell cell;
            Paragraph paragraph;
            for(String tableTitle : tableTitles){ //make table header.
                cell = new PdfPCell();
                paragraph = new Paragraph(tableTitle, fontTableHeader);
                cell.addElement(paragraph);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setPadding(6f);
                table.addCell(cell);
            }
            for(ProductKey productKey : order.getListProductKey()){ //lines of td.
                cell = new PdfPCell(); //product column.
                cell.addElement(new Paragraph(productKey.getProductTheoric().getTitle(), fontTable));
                cell.setPadding(6f);
                table.addCell(cell);

                cell = new PdfPCell(); //key column.
                cell.addElement(new Paragraph(productKey.getKey(), fontTable));
                cell.setPadding(6f);
                table.addCell(cell);

                cell = new PdfPCell(); //reduction column.
                paragraph = new Paragraph(" ", fontTable);
                if(productKey.getProductTheoric().hasReduction())
                    paragraph.add(productKey.getProductTheoric().getReduction()+"%");
                paragraph.setAlignment(Element.ALIGN_RIGHT);
                cell.addElement(paragraph);
                cell.setPadding(6f);
                table.addCell(cell);

                cell = new PdfPCell(); //price column.
                if(productKey.getProductTheoric().hasReduction()){ //line with price crossed (before reduction).
                    paragraph = new Paragraph(Utility.castFloatToString(productKey.getProductTheoric().getPrice(), 2)+" €", fontTableCrossed);
                    paragraph.setAlignment(Element.ALIGN_RIGHT);
                    cell.addElement(paragraph);
                }
                paragraph = new Paragraph(Utility.castFloatToString(productKey.getProductTheoric().getPriceWithReduction(), 2)+" €", fontTable);
                paragraph.setAlignment(Element.ALIGN_RIGHT);
                cell.addElement(paragraph);
                cell.setPadding(6f);
                table.addCell(cell);
            }

            //create second table for total.
            float[] tableTotalColumnWidth = {0f}; //width.
            for(float columnWidth : tableColumnWidth)
                tableTotalColumnWidth[0] += columnWidth;
            PdfPTable tableTotal = new PdfPTable(tableTotalColumnWidth);
            cell = new PdfPCell();
            float total = order.getListProductKey().stream() //sum of order.
                    .map(pk -> pk.getProductTheoric().getPriceWithReduction())
                    .reduce(0f, Float::sum);
            paragraph = new Paragraph("Total : "+Utility.castFloatToString(total,2)+" €", fontTable);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(paragraph);
            cell.setPadding(6f);
            tableTotal.addCell(cell);

            //footer page.
            Paragraph footerParagraph = new Paragraph("L'équipe de GamePath vous remercie.", fontTable);

            //write in document.
            document.add(imageLogo);
            document.add(addressGamePath);
            document.add(spacingElements);
            document.add(titleParagraph);
            document.add(spacingElements);
            document.add(paragraphUser);
            document.add(spacingElements);
            document.add(table);
            document.add(spacingElements);
            document.add(tableTotal);
            document.add(spacingElements);
            document.add(footerParagraph);

        }catch(Exception e){
            Utility.debug("error catch in createPDF : " + e.getMessage());
            throw e; //throw the error before catch, only catch for print in console if the error is from createPDF function.
        }finally{
            document.close(); //close at end (in finally).
        }

        return pathPDF;
    }

}
