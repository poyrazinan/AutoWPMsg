package tr.com.poyrazinan.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import tr.com.poyrazinan.Main;
import tr.com.poyrazinan.model.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelInputReader {

    /**
     * It convert all the excel values to a object on java.
     * Then write them in a ArrayList for better data scanning.
     *
     * @throws IOException
     */
    public ExcelInputReader() throws IOException {

        long start = System.currentTimeMillis();

        FileInputStream file = new FileInputStream(
                new File("").getAbsolutePath().toString() + "/Data.xlsx");

        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        rowIterator.next();

        while (rowIterator.hasNext()) {
            Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
            inputToTask(cellIterator);
        }

        // Closing workbook after collecting all the data
        workbook.close();

        // Calculating spended time on inserting values
        long end = System.currentTimeMillis();
        System.out.printf("Veriler içeri aktarıldı %d ms\n", (end - start));

    }

    /**
     * Converting excel inputs to Task object.
     *
     * @param cellIterator
     * @return
     */
    private void inputToTask(@NotNull Iterator<Cell> cellIterator) {

        String name = null;
        List<String> numbers = new ArrayList<>();
        String message = null;
        Date eventDate = null;
        boolean everyone = false;

        while (cellIterator.hasNext()) {

            Cell cell = cellIterator.next();

            String cellInputString = null;
            if (cell.getColumnIndex() != 3
                    && cell.getColumnIndex() < 5)
                cellInputString = cell.getStringCellValue();

            else if (cell.getColumnIndex() == 3)
                eventDate = cell.getDateCellValue();

            else break;

            // Added this because i've added explanation on excel file
            // FileReader thought they are data and trying to insert null tasks.
            if (cellInputString == null)
                break;

            switch (cell.getColumnIndex()) {
                case 0:
                    name = cellInputString;
                    break;
                case 1:
                    if (cellInputString.contains("+"))
                        cellInputString.replace("+", "");

                    if (cellInputString.contains(","))
                        Arrays.stream(cellInputString.split(",")).forEach(numbers::add);
                    else
                        numbers.add(cellInputString);
                    break;

                case 2:
                    message = cellInputString;
                    break;
                case 4:
                    if (cellInputString.equalsIgnoreCase("evet"))
                        everyone = true;
                    break;

                default:
                    break;
            }

        }

        // This is added with same reason about excel explanations.
        if (message == null)
            return;

        Task task = Task.builder()
                .name(name)
                .date(eventDate)
                .message(message)
                .numbers(numbers)
                .everyone(everyone)
                .build();
        Main.inputs.add(task);

    }
}
