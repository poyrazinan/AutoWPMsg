package tr.com.poyrazinan.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tr.com.poyrazinan.Main;
import tr.com.poyrazinan.objects.ExcelInput;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelInputReaderService {

    /**
     * It convert all the excel values to a object on java.
     * Then write them in a ArrayList for better data scanning.
     *
     * @throws IOException
     */
    public ExcelInputReaderService() throws IOException {
        long start = System.currentTimeMillis();

        // File Input Stream object creator
        FileInputStream file = new FileInputStream(
                new File("").getAbsolutePath().toString() + "/Data.xlsx");

        // Workbook catcher from input stream
        Workbook workbook = new XSSFWorkbook(file);

        // Sheet selector default 0
        Sheet sheet = workbook.getSheetAt(0);
        // Iterator the sheet for getting rows
        Iterator<Row> rowIterator = sheet.iterator();
        // Skip first row for descriptions
        rowIterator.next();

        // While loop for rows
        while (rowIterator.hasNext()) {
            // Next row catcher
            Row nextRow = rowIterator.next();
            // Cell iterator
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Values which will be fillen with cells
            String name = null;
            List<String> numbers = new ArrayList<>();
            String Message = null;
            Date date = null;
            boolean everyone = false;

            // While loop for cells
            while (cellIterator.hasNext()) {
                // Next cell selector
                Cell nextCell = cellIterator.next();

                // Cell index for catching which colmn we are looking for
                int columnIndex = nextCell.getColumnIndex();

                // Switch element for fill the values
                switch (columnIndex) {
                    case 0:
                        name = nextCell.getStringCellValue();
                        break;
                    case 1:
                        String numberRaw = nextCell.getStringCellValue();
                        // if numbers splitted with , then it collect them separately
                        // and making the task for each of them to execute all of them.
                        if (numberRaw.contains(",")) {
                            Arrays.stream(numberRaw.split(",")).forEach(phone -> {
                                // Removing + on numbers if exists
                                if (phone.contains("+"))
                                    numbers.add(phone.substring(1));
                                else
                                    numbers.add(phone);
                            });
                        }
                        // Else and Else if both of them saving only one task
                        // Because execute only one number exists.
                        else if (numberRaw.contains("+"))
                            numbers.add(nextCell.getStringCellValue().substring(1));
                        else
                            numbers.add(nextCell.getStringCellValue());
                        break;
                    case 2:
                        Message = nextCell.getStringCellValue();
                        break;
                    case 3:
                        date = nextCell.getDateCellValue();
                        break;
                    case 4:
                        // if "herkes" means everyone set to evet it make it everyone tagged
                        String value = nextCell.getStringCellValue();
                        if (value.equalsIgnoreCase("evet"))
                            everyone = true;
                        break;
                    default:
                        break;
                }

            }

            // Converting values to final to handle it async
            final String msg = Message;
            final Date date1 = date;
            final String name1 = name;

            // Check if message send to everyone
            if (everyone) {
                // Saving values to RAM for easy access
                ExcelInput input = ExcelInput.builder().name(name1).number(null)
                        .Message(msg).date(date1).everyone(true).build();
                Main.inputs.add(input);
            }
            // Else it will send it to a person
            else {
                // Saving values to RAM for easy access
                numbers.stream().forEach(number -> {
                    ExcelInput input = ExcelInput.builder().name(name1)
                            .number(number).Message(msg).date(date1).build();
                    Main.inputs.add(input);
                });
            }

        }

        // Closing workbook after collecting all the data
        workbook.close();

        // Calculating spended time on inserting values
        long end = System.currentTimeMillis();
        System.out.printf("Veriler içeri aktarıldı %d ms\n", (end - start));

    }
}
