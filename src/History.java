import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import com.opencsv.CSVWriter;

public class History {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) 
	{

		Scanner keyboard = new Scanner(System.in);
		
		char answer;
		boolean repeat = true;
		int year;
		double date;
		String start;

		//Do-While loop to repeat
		do 
		{
			//Ask the user what he wants to do
			System.out.println("Please input what you would like to do (summary/store/view/adding)");
			start = input.next().toLowerCase();
		
			//Switch statement for different situations
			switch(start) 
			{	
				//If the user input summary
				case "summary": 	System.out.println("Summary information are based on your current manifest data:");
									System.out.println("The total stores you delivered are " + totalStore() +" stores." );
									System.out.println("The total stores without duplicate you delivered are " +totalStoresWithoutDuplicate() +" stores.");
                                    break;
                                    
                case "store": 	    
									System.out.println("Which is the store ID that you are looking for?");
									year = input.nextInt();
									break;
				
				case "manifest":	System.out.println("Which is the year of manifest that you are looking for? (YYYY)");
									year = input.nextInt();
									System.out.println("What is the date of manifest that you are looking for? (MM.DD)");
									date = input.nextDouble();
									System.out.println(manifest(year,date));
									break;
				
				//If the user input adding
				case "adding":		System.out.println(addManifest());

									break;
				
				//For other input
				default:			System.out.println("Wrong input. Please try again.");
									break;
		
			}
			//Ask the user to repeat or not
			System.out.println("Are you done? Y/N");
			answer = input.next().charAt(0);
			if(answer == 'Y') 
			{
				repeat = false;
			}
        } while(repeat);
        keyboard.close();
    }
    
    private static int totalStore()
	{
        int StoreNum = 0;

        Scanner input = null;
		try {
			input = new Scanner(new File("StoreInfo.csv"));
		} catch (FileNotFoundException e) {
			// did not find the file
			e.printStackTrace();
		}

		//get rid of the header
        input.nextLine();
        
		while(input.hasNextLine()) {

            String line = input.nextLine();

            String fields[] = line.split(",");
            
            StoreNum+= Integer.parseInt(fields[4]);
        }
		return StoreNum;
    }

    private static int totalStoresWithoutDuplicate()
	{
        int StoreNum = 0;

        Scanner input = null;
		try {
			input = new Scanner(new File("StoreInfo.csv"));
		} catch (FileNotFoundException e) {
			// did not find the file
			e.printStackTrace();
		}

		//to get rid of the header
        input.nextLine();
        
		while(input.hasNextLine()) {
            StoreNum++;
        }
		return StoreNum;
    }

    public static String manifest(int year,double date)
	{
		String fileName = year+"\\\\"+date+".txt";
		File checkFile = new File(fileName);
		
		Scanner inputStream = null;
		
		if(checkFile.exists()) 
		{
			try
			{
			inputStream = new Scanner(new File(fileName));
		
			}
			//catch exception
			catch(FileNotFoundException e)
			{
			System.out.println("Error opening the file " + fileName); 
			System.exit(0);

			}
			//Ouput every single line of the manifest
			System.out.println("This is the manifest for ");
			while(inputStream.hasNextLine()) 
			{

				String text = inputStream.nextLine();
				System.out.println(text);
			}

			//Close the file
			inputStream.close();
			
		}
		else 
		{
			return "There isn't a manifest file for " +date+"."+year;
		}
		return null;
	}
    
    public static String addManifest() 	{
		
		//declare some variables
		String Trip,Bill,trailerWeight,trailer;
		double stores,date;
		int year;
		
		//ask the user to input specific year and date
		System.out.println("Which year is this delivery? (YYYY)");
		year = input.nextInt();
        
        String folderName = year+"";
        File checkFolder = new File(folderName);

        if (!checkFolder.exists()) {
            File file = new File("Store"); 
            file.mkdir();
        }
        
		System.out.println("What date is this delivery ? (MM.DD)");
		date = input.nextDouble();
		
		String str = String.format("%.02f", date);
	
		//Create a new file with the date
		String fileName = year+"\\\\"+str + ".txt";
	
		PrintWriter outputStream = null;
	
		//Ask for manifest inforamtion
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
	
	
	
		try 
		{
			outputStream = new PrintWriter(fileName);
		
			int store;
            String city = "";
            String address = "";
            String phone = "";
		
			//output on the file
			outputStream.println(str);
			outputStream.println("");
			outputStream.println("Trip ID : " +Trip);
			outputStream.println("Trailer ID : " +trailer);
			outputStream.println("Bill ID : " +Bill);
			outputStream.println("Weight ID : " + trailerWeight);
		
			//ask for the each stop and store information
			for(int i = 1; i <=stores; i++) 
			{
				
				System.out.println("What is the Store ID for Stop " + i +" ?");
                store = input.nextInt();
                
                String line = checkCSV(store);
                if(line.equals("NS")){
                    String fields[] = line.split(",");
                    System.out.print("The City of Stop is " + fields[1]);
                    System.out.print("The address of Stop is " + fields[2]);
                    System.out.print("The phone number of Stop is " + fields[3]);
                    System.out.print("Are these info correct?(Y/N)");
                    if(input.next().charAt(0) == 'Y'){
                        city = fields[1];
                        address = fields[2];
                        phone = fields[3];
                    }else{
                        System.out.println("What is the City of Stop " +i +" area ?");
                        city = input.next();
                
                        System.out.println("What is the address of Stop " + i + " ? (use '-' instead of space)");
                        address = input.next();
                
                        System.out.println("What is the phone number of Stop " + i + " ?");
                        phone = input.next();

                    }
                }else{

				    System.out.println("What is the City of Stop " +i +" area ?");
				    city = input.next();
			
				    System.out.println("What is the address of Stop " + i + " ? (use '-' instead of space)");
				    address = input.next();
			
				    System.out.println("What is the phone number of Stop " + i + " ?");
                    phone = input.next();
                    addCSV(store,city,address,phone,1);
                }
			
				outputStream.println("");
				outputStream.println("Stop " + i + " :");
				outputStream.println("Store ID : " + store);
				outputStream.println("City : " + city);
				outputStream.println("Address  : " +address);
				outputStream.println("Phone : " +phone);
			
			}
		
		}
	
		catch(FileNotFoundException e)
		{
			System.out.println("Error opening the file " + fileName); 

			System.exit(0);
		}
		//Close the output file
		outputStream.close();
		return "The new manifest has successfully created.";
	}
    
    private static void addCSV(int ID, String address, String city, String phone, int visit) {
        File file = new File("StoreInfo.csv"); 
        try { 
        // create FileWriter object with file as parameter 
        FileWriter outputfile = new FileWriter(file); 
  
        // create CSVWriter object filewriter object as parameter 
        CSVWriter writer = new CSVWriter(outputfile); 
  
        // adding header to csv 
        String[] header = { "Store ID", "City", "Address","Phone","Visit Times"}; 
        writer.writeNext(header); 
  
        Store s = new Store(ID,address,city,phone,visit);
        // add data to csv
        String[] data1 = { ""+s.getId(),s.getAddress(), s.getCity(), s.getPhone(),""+s.getVisit() }; 
        writer.writeNext(data1); 
  
        // closing writer connection 
        writer.close(); 
        } 
        catch (IOException e) { 
            // did not find the file
            e.printStackTrace(); 
        } 
    }

    private static String checkCSV(int ID) {

        Scanner input = null;
        
		try {
			input = new Scanner(new File("Employee_data.csv"));
		} catch (FileNotFoundException e) {
			// did not find the file
			e.printStackTrace();
        }
        
		while(input.hasNextLine()) {

			String line = input.nextLine();
            String fields[] = line.split(",");
            if(Integer.parseInt(fields[0]) == ID){
                return line;
            }
        }
        return "NS";
    }

}
