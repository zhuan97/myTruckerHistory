import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

public class History {

    static Scanner input = new Scanner(System.in);

    public static final String Store_FILE = "StoreInfo.csv";

    public static void main(String[] args) throws IOException, CsvException {

        Scanner keyboard = new Scanner(System.in);

        char answer;
        boolean repeat = true;
        String storeID;
        double date;
        int year;
        String start;

        File checkStoreInfo = new File(Store_FILE);

        // Check storeInfo.cvs
        // Create one if it doesn't exist
        if (!checkStoreInfo.exists()) 
        {
            createCSV();
        }

        do {
            System.out.println("Please input what you would like to do");
            System.out.println("To check summary, type 'summary' ");
            System.out.println("To check store infomation, type 'store'");
            System.out.println("To check delivery infomation, type 'view'");
            System.out.println("To add delivery infomation, type 'add'");
            start = input.next().toLowerCase();

            switch (start) {
                case "summary":
                    System.out.println("Summary information are based on your current manifest data:");
                    System.out.println("The total stores you delivered are " + totalStore() + " stores.");
                    System.out.println("The total stores without duplicate you delivered are "
                            + totalStoresWithoutDuplicate() + " stores.");
                    break;

                case "store":
                    System.out.println("what is the store's ID that you are looking for?");
                    storeID = input.next();

                    String storeline = checkCSV(storeID);
                    if (!storeline.equals("NS")) {
                        String storefields[] = storeline.split(",");
                        System.out.println("Store " + storefields[0].replace("\"", "") + " :");
                        System.out.println("The City of Store is " + storefields[1].replace("\"", "")+ ".");
                        System.out.println("The address of Store is " + storefields[2].replace("\"", "")+ ".");
                        System.out.println("The phone number of Store is " + storefields[3].replace("\"", "")+ ".");
                    }else{
                        System.out.println("There isn't a store with ID "+storeID+" on file.");
                    }
                    break;

                case "view":
                    System.out.println("Which is the year of manifest that you are looking for? (YYYY)");
                    year = input.nextInt();
                    System.out.println("What is the date of manifest that you are looking for? (MM.DD)");
                    date = input.nextDouble();
                    System.out.println(view(year, date));
                    break;

                case "add":
                    System.out.println(addManifest());
                    break;

                default:
                    System.out.println("Wrong input. Please try again.");
                    break;

            }
            System.out.println("Are you done? Y/N");
            answer = input.next().charAt(0);
            if (answer == 'Y') {
                repeat = false;
            }
        } while (repeat);
        keyboard.close();
    }

    private static int totalStore() {
		int totalStore = 0;
		
		for(int i = 2020;i<2100;i++) 
		{
            String folderName = i + "";
            File checkFolder = new File(folderName);
    
            if (!checkFolder.exists()) {
                break;
            }

			for(int k = 1;k<13;k++) 
			{
				for(double j = 0.01;j<0.32;j+=0.01) 
				{
					String date = String.format("%.02f", k+j);
					String fileName = i+"\\\\"+date+".txt";
					File checkFile = new File(fileName);
		
					Scanner inputStream = null;

					if(checkFile.exists()) 
					{
						try
						{
						inputStream = new Scanner(new File(fileName));
					
						}
						catch(FileNotFoundException e)
						{
						System.out.println("Error opening the file " + fileName); 
						System.exit(0);

						}
						//Get rid of the header
						inputStream.nextLine();
						inputStream.nextLine();
						inputStream.nextLine();
						inputStream.nextLine();
						inputStream.nextLine();
						inputStream.nextLine();
						inputStream.nextLine();

						while(inputStream.hasNextLine()) 
						{
							String text = inputStream.nextLine();
							if(text.contains("Store"))
							{
								totalStore ++;
							}
						}
					inputStream.close();
					}
				}
			}
		}
		return totalStore;
	}

    private static int totalStoresWithoutDuplicate() {
        int StoreNum = 0;

        Scanner totalinput = null;
        try {
            totalinput = new Scanner(new File(Store_FILE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // get rid of the header
        totalinput.nextLine();

        while (totalinput.hasNextLine()) {
            StoreNum++;
            totalinput.nextLine();
        }
        return StoreNum;
    }

    private static String view(int year, double date) {
        String fileName = year + "\\\\" + date + ".txt";
        File checkFile = new File(fileName);

        Scanner inputStream = null;

        if (checkFile.exists()) {
            try {
                inputStream = new Scanner(new File(fileName));
            }
            catch (FileNotFoundException e) {
                System.out.println("Error opening the file " + fileName);
                System.exit(0);
            }

            System.out.println("This is the manifest for " + date + " in " + year);
            while (inputStream.hasNextLine()) {
                String text = inputStream.nextLine();
                System.out.println(text);
            }
            inputStream.close();
        } else {
            return "There isn't a record for " + date + " in " + year;
        }
        return null;
    }

    private static String addManifest() throws IOException ,CsvException{
        String Trip, Bill, trailerWeight, trailer;
        double stores, date;
        int year;

        System.out.println("Which year is this delivery? (YYYY)");
        year = input.nextInt();

        String folderName = year + "";
        File checkFolder = new File(folderName);

        if (!checkFolder.exists()) {
            File file = new File(folderName);
            file.mkdir();
        }

        System.out.println("What date is this delivery ? (MM.DD)");
        date = input.nextDouble();

        String str = String.format("%.02f", date);

        String fileName = year + "\\\\" + str + ".txt";

        PrintWriter outputStream = null;

        System.out.println("What is the Trip ID? ");
        Trip = input.next();

        System.out.println("What is the Trailer ID? ");
        trailer = input.next();

        System.out.println("What is the Bill ID? ");
        Bill = input.next();

        System.out.println("What is the weight of this trailer?");
        trailerWeight = input.next();

        System.out.println("How many stores in the delivery? ");
        stores = input.nextDouble();

        try {
            outputStream = new PrintWriter(fileName);

            String store;
            String city ;
            String address;
            String phone;

            outputStream.println(str);
            outputStream.println("");
            outputStream.println("Trip ID : " + Trip);
            outputStream.println("Trailer ID : " + trailer);
            outputStream.println("Bill ID : " + Bill);
            outputStream.println("Weight ID : " + trailerWeight);

            for (int i = 1; i <= stores; i++) {
                System.out.println("What is the Store ID for Stop " + i + " ?");
                store = input.next();

                String line = checkCSV(store);
                
                if (!line.equals("NS")) {
                    String fields[] = line.split(",");
                    System.out.println("The City of Store " + store +" is " + fields[1].replace("\"", ""));
                    System.out.println("The address of Store " + store +" is " + fields[2].replace("\"", ""));
                    System.out.println("The phone number of Store " + store +" is " + fields[3].replace("\"", ""));
                    System.out.println("Are these info correct?(Y/N)");
                    if (input.next().charAt(0) == 'Y') {
                        city = fields[1].replace("\"", "");
                        address = fields[2].replace("\"", "");
                        phone = fields[3].replace("\"", "");
                    } else {
                        System.out.println("What is the City of Store " + i + " ?");
                        city = input.next();

                        System.out.println("What is the address of Store " + i + " ? (use '-' instead of space)");
                        address = input.next();

                        System.out.println("What is the phone number of Store " + i + " ?");
                        phone = input.next();
                        deleteCSV(store);
                        addCSV(store, city, address, phone);

                    }
                }
                else {
                    System.out.println("What is the City of this Store " + i + " ?");
                    city = input.next();

                    System.out.println("What is the address of Store " + i + " ? (use '-' instead of space)");
                    address = input.next();

                    System.out.println("What is the phone number of Store " + i + " ?");
                    phone = input.next();
                    addCSV(store, city, address, phone);
                }

                outputStream.println("");
                outputStream.println("Store " + i + " :");
                outputStream.println("ID : " + store);
                outputStream.println("City : " + city);
                outputStream.println("Address  : " + address);
                outputStream.println("Phone : " + phone);

            }

        }
        catch (FileNotFoundException e) {
            System.out.println("Error opening the file " + fileName);

            System.exit(0);
        }

        outputStream.close();
        return "The new manifest has successfully created.";
    }

    private static void createCSV() throws IOException{
        File file = new File(Store_FILE);

        FileWriter outputfile = new FileWriter(file);

        CSVWriter createwriter = new CSVWriter(outputfile);

        String[] header = { "Store ID", "City", "Address", "Phone" };
        createwriter.writeNext(header);

        createwriter.close();
    }

    private static void addCSV(String ID, String address, String city, String phone) throws IOException {
        CSVWriter addwriter = new CSVWriter(new FileWriter(Store_FILE, true));

        String[] record = (ID + "," + address + "," + city + "," + phone).split(",");

        addwriter.writeNext(record);

        addwriter.close();
    }

    private static String checkCSV(String ID) {
        Scanner checkinput = null;
        try {
            checkinput = new Scanner(new File("StoreInfo.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       
        while (checkinput.hasNextLine()) {
            
            String line = checkinput.nextLine();
            String fields[] = line.split(",");
            
            if (fields[0].replace("\"", "").equals(ID)){
                return line;
            }
            
        }
        return "NS";
    }

    private static void deleteCSV(String ID) throws IOException, CsvException {
        int rowNumber = 0;

        Scanner deleteinput = null;
        try {
            deleteinput = new Scanner(new File("StoreInfo.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       
        while (deleteinput.hasNextLine()) {
            
            String line = deleteinput.nextLine();
            String fields[] = line.split(",");
            
            if (fields[0].replace("\"", "").equals(ID)){
                break;
            }
            rowNumber++;
        }

        CSVReader deletereader = new CSVReader(new FileReader(Store_FILE));
        List<String[]> allElements = deletereader.readAll();
        allElements.remove(rowNumber);
        FileWriter sw = new FileWriter(Store_FILE);
        CSVWriter deletewriter = new CSVWriter(sw);
        deletewriter.writeAll(allElements);
        deletewriter.close();

    }

}
