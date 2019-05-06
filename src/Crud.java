import java.io.*;
import java.util.Scanner;

public class Crud {

    File file;
    PrintWriter printW;
    String fileLocation;
    int RowNumber = 1;

    /**
     * this is the overwritten constructor
     *
     * @param filePath accepts string path where user saves the file in his computer
     * @param fileName accepts string name entered by user
     */
    public Crud(String filePath, String fileName) {// method to create the file
        try {
            //make the file
            file = new File(filePath + fileName + ".csv");
            //instantiate PrintWriter, give the file as param - to write into the file
            //PrintWriter = clasa folosita sa scriu in file
            printW = new PrintWriter(file);
            //create header - instantiate the StringBuffer to allocate
            // space for strings = anything we want to add (rows->headers)
            StringBuffer csvHeader = new StringBuffer("");
            //define header here
            csvHeader.append(" Row Number, Product Name, Price, Quantity,\n");
            //write into the file - converts to strings what we've added with String Buffer
            printW.write(csvHeader.toString());
            printW.flush();
            System.out.println("New file created now: " + fileName + " and open, remember to close it after you're done writing ");

            //make string so user saves file in a location,
            // it is composed of 2 parameters of constructor
            //and string with file location
            fileLocation = filePath + fileName + ".csv";

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createRow(String ProductName, double Price, int Quantity) {
        try {
            // create the row by instantiating the StringBuffer to be in met. scope
            StringBuffer csvData = new StringBuffer("");
            //first we add to string the value of first column in table, the RowNumber, determined previously
            csvData.append(RowNumber);
            csvData.append(',');
            csvData.append(ProductName);
            csvData.append(',');
            csvData.append(Price);
            csvData.append(',');
            csvData.append(Quantity);
            csvData.append(',');
            csvData.append('\n');

            //write into the file, convert to string the appended data
            printW.append(csvData.toString());
            printW.flush();

            //increments rows to keep track of rows
            // use row number in updating or deleting methods
            RowNumber++;
        } catch (Exception e) {
        }
    }

    private String readFile() {
        //it is private because it returns a String and I don't other classes to access it
        // Instantiate file in this met. I didn't instantiate file at the start of class so I need to create a new
        // instance here with file path generated in constructor.
        File file = new File(fileLocation);
        // instantiate StringBuffer to be used for printing lines from file
        StringBuffer dataRow = new StringBuffer();
        try {
            // instantiate Scanner to read file given as parameter
            Scanner inputScanner = new Scanner(file);
            //checks if the file has next line not empty, if it exists
            while (inputScanner.hasNext()) {
                //if true, it adds the line to Stringbuffer
                dataRow.append(inputScanner.nextLine());
                dataRow.append('\n');
            }
            inputScanner.close();
        } catch (FileNotFoundException e) {
        }
        //converts stringBuffer in String to be used later
        return dataRow.toString();
    }

    // prints  contents of file to console
    // call the method form this class

    public void printFile() {
        System.out.println(this.readFile());
    }

    private String[] getRowsAsStringArray() {
        String fileContent = this.readFile();
        //instantiate StringBuffer to replace the entire file content after updating a row
        StringBuffer result = new StringBuffer("");
        //make an array of strings from file content,
        // splits content based on a new line that represents a row in the file
        String splitRow[] = fileContent.split("\n");
        return splitRow;
    }

    /**
     * use  method to update the column values of certain row (@param rowNr) with new values from other param
     *
     * @param rowNr       number of the row to be updated
     * @param productName new value of the product name for the selected row
     * @param price       new price
     * @param quantity    update quantity
     */

    public void updateRow(int rowNr, String productName, double price, int quantity) {
        try {
            //instantiate StringBuffer to replace the entire file content after updating a row
            StringBuffer result = new StringBuffer("");
            //make an array of strings from file content, splits content based on a new line that represents a row in the file
            String splitRow[] = this.getRowsAsStringArray();
            //for loop goes through array length
            //i = no of row
            //j = no of cell
            for (int i = 0; i < splitRow.length; i++) {
                //makes array that splits each row based on ","
                String splitCell[] = splitRow[i].split(",");

                for (int j = 0; j < splitCell.length; j++) {
                    //if i is greater than 0, that is it is not the header
                    if (i > 0) {
                        // to see which row no we want to update
                        if (Integer.parseInt(splitCell[0]) == rowNr) {
                            splitCell[1] = productName;
                            splitCell[2] = String.valueOf(price);
                            splitCell[3] = String.valueOf(quantity);
                        }
                    } // recreate the content of the file used to replace current content
                    // use the "," that splits the content
                    result.append(splitCell[j] + ",");
                }
                result.append("\n");
            }
            printW.close();
            printW = new PrintWriter(file);
            //transform StringBuffer into String - if I don't do so, it will simply have an instance of StringBuffer

            printW.append(result.toString());
            printW.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeFile() {
        System.out.println("File closed here.");
        printW.close();
    }


    public void deleteRow(int rowNr) {
        try {
            //instantiate StringBuffer to replace the entire file content after updating a row
            StringBuffer result = new StringBuffer("");
            //make an array of strings from file content, splits content based on a new line that rpresents a row in the file
            String splitRow[] = this.getRowsAsStringArray();
            //int classRowNr2 = RowNumber;
            int rowToDelete = 0;
            for (int i = 0; i < splitRow.length; i++) {
                String splitCell[] = splitRow[i].split(",");

                for (int j = 0; j < splitCell.length; j++) {
                    //if i is greater than 0, that is it is not the header
                    if (i > 0) {
                        // to see which row no I want to update
                        if (Integer.parseInt(splitCell[0]) == rowNr) {
                            rowToDelete = i;
                        }
                    }
                }
                if (rowToDelete != 0) {
                    splitRow[rowToDelete] = "";
                }
                result.append(splitRow[i]);
                result.append("\n");
            }
            printW.close();
            printW = new PrintWriter(file);
            printW.append(result.toString());
            printW.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
