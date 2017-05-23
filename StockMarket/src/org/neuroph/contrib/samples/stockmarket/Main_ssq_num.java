/***
 * The Example is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Example is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Neuroph. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neuroph.contrib.samples.stockmarket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.LMS;
import java.io.BufferedReader; 
import java.io.FileNotFoundException;
import java.io.FileReader; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author Dr.V.Steinhauer
 */
public class Main_ssq_num {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //read csv by line
        String root = System.getProperty("user.dir");
        BufferedReader reader = new BufferedReader(new FileReader("./test/ssq.csv"));//换成你的文件名
        reader.readLine();       
        String singleline = null; //第一行信息，为标题信息，不用，如果需要，注释掉
        int lineCount=0;
        double szmax = 10000.0D;
        double max = 100000000.0D;
        ArrayList<double[]> elements = new ArrayList<double[]>();
        while((singleline=reader.readLine())!=null){
            
            if(1 < lineCount)   //从第二行后开始处理
            {
                System.out.println("3: " + singleline.trim());
                 String item[] = singleline.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String ssqRow = item[3];    //得到号码那个cell
                String ssqNumbers[]= ssqRow.split(" ");
                
                double ssqNums[] = new double[7];
////                处理前6个红球,排序
//                for (int i=0; i<ssqNumbers.length-1;i++)
//                {
//                    ssqNums[i] = Double.parseDouble(ssqNumbers[i])/100;
//                } 
//                ssqNums[6] = 100;  //保证最后一个数字排在最后
//                Arrays.sort(ssqNums);
////                处理最后一个篮球                
//                ssqNums[6] = Double.parseDouble(ssqNumbers[6])/100;
                
                for (int i=0; i<ssqNumbers.length;i++)
                {
                    ssqNums[i] = Double.parseDouble(ssqNumbers[i])/100;
                } 
                
                elements.add(ssqNums);
                System.out.println(ssqNums);
//                System.out.println(close_value);  

            }   
            lineCount++;
        }
        
        TrainingSet trainingSet = new TrainingSet();
        System.out.println("Time stamp N1:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));

        int maxIterations = 20000;
//        NeuralNetwork neuralNet = new MultiLayerPerceptron(9, 19,9, 1);
//      NeuralNetwork neuralNet = new MultiLayerPerceptron(7,18,7);
        NeuralNetwork neuralNet = new MultiLayerPerceptron(7,18,7);


        ((LMS) neuralNet.getLearningRule()).setMaxError(0.02);//0-1
        ((LMS) neuralNet.getLearningRule()).setLearningRate(0.05);//0-1
        ((LMS) neuralNet.getLearningRule()).setMaxIterations(maxIterations);//0-1
        //load trainingSet
        
        for (int i=0; i<elements.size()-1;i++)
        {
            trainingSet.addElement(new SupervisedTrainingElement(elements.get(i), elements.get(i+1)));

        }
        
        neuralNet.learnInSameThread(trainingSet);
        System.out.println("Time stamp N2:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));

        TrainingSet testSet = new TrainingSet();
        int len=elements.size();

         testSet.addElement(new TrainingElement(elements.get(len-3)));

        for (TrainingElement testElement : testSet.trainingElements()) {
            neuralNet.setInput(testElement.getInput());
            neuralNet.calculate();
            Vector<Double> networkOutput = neuralNet.getOutput();
            System.out.print("Input: " + testElement.getInput());
            System.out.println(" Output: " + networkOutput);
        }

        //Experiments:
        //                   calculated
        //31;3;2009;4084,76 -> 4121 Error=0.01 Rate=0.7 Iterat=100
        //31;3;2009;4084,76 -> 4096 Error=0.01 Rate=0.7 Iterat=1000
        //31;3;2009;4084,76 -> 4093 Error=0.01 Rate=0.7 Iterat=10000
        //31;3;2009;4084,76 -> 4108 Error=0.01 Rate=0.7 Iterat=100000
        //31;3;2009;4084,76 -> 4084 Error=0.001 Rate=0.7 Iterat=10000

        System.out.println("Time stamp N3:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));
        System.exit(0);
    }
}
