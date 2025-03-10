package com.ontariotechu.sofe3980U;

import java.io.FileReader;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.opencsv.*;

/**
 * Evaluate Single Variable Continuous Regression
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		String filePath="model.csv";
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
		int numClasses=4;
		double crossEntropy=0.0;
		int n = allData.size();
		int[][] confusionMatrix = new int[numClasses][numClasses];

		for(String[] row:allData){
			
			int y_true=Integer.parseInt(row[0])-1;
			double[] y_predicted = new double[numClasses];

			for(int i=0; i<numClasses; i++){
				y_predicted[i] = Double.parseDouble(row[i+1]);
			}

			crossEntropy += -Math.log(y_predicted[y_true]+ 1e-15);

			int y_pred_label=0;
			double maxProb = y_predicted[0];
			for(int i=1; i<numClasses;i++){
				if(y_predicted[i]>maxProb){
					maxProb=y_predicted[i];
					y_pred_label=i;
				}
			}

			confusionMatrix[y_pred_label][y_true]++;
		}

		crossEntropy /= n;
		System.out.println("CE Loss: "+crossEntropy);

		System.out.println("CM is: "+confusionMatrix);
		for(int i=0; i<numClasses;i++){
			System.out.println(Arrays.toString(confusionMatrix[i]));
		}

		 
		
		int count=0;
		float[] y_predicted=new float[5];
		for (String[] row : allData) { 
			int y_true=Integer.parseInt(row[0]);
			System.out.print(y_true);
			for(int i=0;i<5;i++){
				y_predicted[i]=Float.parseFloat(row[i+1]);
				System.out.print("  \t  "+y_predicted[i]); 
			}
			System.out.println(); 
			count++;
			if (count==10){
				break;
			}
		} 

	}
	
}
