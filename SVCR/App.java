package com.ontariotechu.sofe3980U;
import java.io.FileReader; 
import java.util.List;
import com.opencsv.*;
import java.lang.Math;

/**
 * Evaluate Single Variable Continuous Regression
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		String filePath="model_3.csv";
		FileReader filereader;
		List<String[]> allData;
		try{
			filereader = new FileReader(filePath); 
			CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build(); 
			allData = csvReader.readAll();
		}
		catch(Exception e){
			System.out.println( "Error reading the CSV file" );
			return;
		}

		int n = 0;
		double mse=0.0, mae=0.0, mare=0.0, epsilon= 1e-8;
		for(String[] row : allData){
			try{
				double y_true= Double.parseDouble(row[0]);
				double y_pred= Double.parseDouble(row[1]);

				mse += Math.pow(y_true - y_pred,2);
				mae += Math.abs(y_true - y_pred);
				mare += Math.abs(y_pred)/ (Math.abs(y_true)+ epsilon);
				n++;
			}catch (NumberFormatException e){
				System.out.println("Skipping invalid row: "+ String.join(",",row));
			}
		}
		if(n>0){
			mse /=n;
			mae /=n;
			mare =(mare /n)*100;
		}

		System.out.println("MSE: "+mse);
		System.out.println("MAE: "+mae);
		System.out.println("MARE: "+mare);


		
		int count=0;
		for (String[] row : allData) { 
			float y_true=Float.parseFloat(row[0]);
			float y_predicted=Float.parseFloat(row[1]);
			System.out.print(y_true + "  \t  "+y_predicted); 
			System.out.println(); 
			count++;
			if (count==10){
				break;
			}
		} 
    }
}
