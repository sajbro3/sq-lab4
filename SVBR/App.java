package com.ontariotechu.sofe3980U;


import java.io.FileReader; 
import java.util.List;
import com.opencsv.*;

/**
 * Evaluate Single Variable Continuous Regression
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		String filePath="model_1.csv";
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

		int TP=0, FP=0, TN=0, FN=0;
		double BCE =0.0;
		int count =0;
		double threshold=0.5;

		for (String[] row : allData){
			int y_y_true = Integer.parseInt(row[0]);
			double y_predicted = Double.parseDouble(row[1]);

			//Calculate BCE
			BCE += y_true*Math.log(y_predicted)+(1- y_true)*Math.log(1-y_predicted);

			//calculating confusion maxtrix
			int y_pred_label = (y_predicted >= threshold)? 1:0;
			if(y_true==1&& y_pred_label==1) TP++;
			else if (y_true == 0 && y_pred_label == 1) FP++;
            else if (y_true == 0 && y_pred_label == 0) TN++;
            else if (y_true == 1 && y_pred_label == 0) FN++;
			count++;
		}
		//get final value of BCE
		BCE= -BCE / count;

		//calculating accuracy,precision,recall,and f1_score formulas

		double accuracy = (double)(TP+TN)/(TP+TN+FP+FN);
		double precision=(TP+FP)>0? (double) TP/(TP+FP):0;
		double recall=(TP+FN)>0? (double) TP/(TP+FN):0;
		double f1_score=(precision+recall)>0?2*(precision*recall)/(precision+recall):0;

		//calcuating AUC-ROC
		double auc = computeAuc(allData);

		//printing the results

		System.out.println("BCE is: "+BCE);
		System.out.println("CM is: TP= "+TP+"FP= "+FP+ "TN= "+TN + "FN= "+FN);
		System.out.println("Accuracy is: "+accuracy);
		System.out.println("precision is: "+precision);
		System.out.println("recall is: "+recall);
		System.out.println("F1 score is: "+f1_score);
		System.out.println("AUC-ROC: "+auc);

		private static double computeAuc(List<String[]> allData) {
        double auc = 0.0;
        int n_positive = 0;
        int n_negative = 0;
        for (String[] row : allData) {
            if (Integer.parseInt(row[0]) == 1) n_positive++;
            else n_negative++;
        }

        double[] x = new double[101];
        double[] y = new double[101];

        for (int i = 0; i <= 100; i++) {
            double threshold = i / 100.0;
            int TP = 0, FP = 0;
            for (String[] row : allData) {
                int y_true = Integer.parseInt(row[0]);
                double y_predicted = Double.parseDouble(row[1]);
                if (y_true == 1 && y_predicted >= threshold) TP++;
                if (y_true == 0 && y_predicted >= threshold) FP++;
            }
            double TPR = (double) TP / n_positive;
            double FPR = (double) FP / n_negative;
            x[i] = FPR;
            y[i] = TPR;
        }

        for (int i = 1; i <= 100; i++) {
            auc += (y[i - 1] + y[i]) * Math.abs(x[i - 1] - x[i]) / 2;
        }
        return auc;
    }
	
		
		int count=0;
		for (String[] row : allData) { 
			int y_true=Integer.parseInt(row[0]);
			float y_predicted=Float.parseFloat(row[1]);
			System.out.print(y_true + "  \t  "+y_predicted); 
			System.out.println(); 
			count++;
			if (count==10){
				break;
			}
		} 