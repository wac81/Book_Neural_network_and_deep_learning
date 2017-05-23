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
/**
 *
 * @author Dr.V.Steinhauer
 */
public class Main_stock_china {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //read csv by line
        String root = System.getProperty("user.dir");
        BufferedReader reader = new BufferedReader(new FileReader("./test/sz.txt"));//换成你的文件名
        reader.readLine();       
        String singleline = null; //第一行信息，为标题信息，不用，如果需要，注释掉
        int lineCount=0;
        double szmax = 10000.0D;
        double max = 100000000.0D;
        ArrayList<double[]> elements = new ArrayList<double[]>();
        while((singleline=reader.readLine())!=null){
                String item[] = singleline.split("		");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String close_value = item[item.length-2];    
//                String cv[] = close_value.split("\"");//得到""里的数据
                
                String last = item[item.length-1];//这就是你要的数据了
//                String lastone[] = last.split("\"");
                double lastvalue = Double.parseDouble(last);//如果是数值，可以转化为数值
                double cvvalue = Double.parseDouble(close_value);//如果是数值，可以转化为数值

                

                elements.add(new double[]{cvvalue/szmax,lastvalue/max});
                System.out.println(last);
                System.out.println(close_value);  
                //double[] fourdays = new double[]{Integer.parseInt(cv[1]) / szmax, 3690.0D / szmax, 3890.0D / szmax, 3695.0D / szmax};  
            }
        
        TrainingSet trainingSet = new TrainingSet();
        System.out.println("Time stamp N1:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));

        int maxIterations = 10000;
//        NeuralNetwork neuralNet = new MultiLayerPerceptron(9, 19,9, 1);
                NeuralNetwork neuralNet = new MultiLayerPerceptron(4,50,1);

        ((LMS) neuralNet.getLearningRule()).setMaxError(0.001);//0-1
        ((LMS) neuralNet.getLearningRule()).setLearningRate(0.7);//0-1
        ((LMS) neuralNet.getLearningRule()).setMaxIterations(maxIterations);//0-1
        //load trainingSet
        for (int i=0; i<elements.size()-4;i++)
        {
            double[] i1=elements.get(i);   //第一天
            double[] i2=elements.get(i+1);  //第二天
            double[] i3=elements.get(i+2);  //第三天
            double[] i4=elements.get(i+3);  //第四天
            double[] i5=elements.get(i+4);  //第五天 结果
//            double[] i6=elements.get(i+5);
//            double[] i7=elements.get(i+6);
//            double[] i8=elements.get(i+7);
//            double[] i9=elements.get(i+8);
//            double[] i10=elements.get(i+9);
            trainingSet.addElement(new SupervisedTrainingElement(new double[]{i1[0], i2[0],i3[0],i4[0]}, new double[]{i5[0]}));

//            trainingSet.addElement(new SupervisedTrainingElement(new double[]{i1[0], i2[0],i3[0],i4[0],i5[0],i6[0],i7[0],i8[0],i9[0]}, new double[]{i10[0]}));
        }
        

       

//        double daxmax = 10000.0D;
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3710.0D / daxmax, 3690.0D / daxmax, 3890.0D / daxmax, 3695.0D / daxmax}, new double[]{3666.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3690.0D / daxmax, 3890.0D / daxmax, 3695.0D / daxmax, 3666.0D / daxmax}, new double[]{3692.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3890.0D / daxmax, 3695.0D / daxmax, 3666.0D / daxmax, 3692.0D / daxmax}, new double[]{3886.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3695.0D / daxmax, 3666.0D / daxmax, 3692.0D / daxmax, 3886.0D / daxmax}, new double[]{3914.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3666.0D / daxmax, 3692.0D / daxmax, 3886.0D / daxmax, 3914.0D / daxmax}, new double[]{3956.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3692.0D / daxmax, 3886.0D / daxmax, 3914.0D / daxmax, 3956.0D / daxmax}, new double[]{3953.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3886.0D / daxmax, 3914.0D / daxmax, 3956.0D / daxmax, 3953.0D / daxmax}, new double[]{4044.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3914.0D / daxmax, 3956.0D / daxmax, 3953.0D / daxmax, 4044.0D / daxmax}, new double[]{3987.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3956.0D / daxmax, 3953.0D / daxmax, 4044.0D / daxmax, 3987.0D / daxmax}, new double[]{3996.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3953.0D / daxmax, 4044.0D / daxmax, 3987.0D / daxmax, 3996.0D / daxmax}, new double[]{4043.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{4044.0D / daxmax, 3987.0D / daxmax, 3996.0D / daxmax, 4043.0D / daxmax}, new double[]{4068.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3987.0D / daxmax, 3996.0D / daxmax, 4043.0D / daxmax, 4068.0D / daxmax}, new double[]{4176.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{3996.0D / daxmax, 4043.0D / daxmax, 4068.0D / daxmax, 4176.0D / daxmax}, new double[]{4187.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{4043.0D / daxmax, 4068.0D / daxmax, 4176.0D / daxmax, 4187.0D / daxmax}, new double[]{4223.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{4068.0D / daxmax, 4176.0D / daxmax, 4187.0D / daxmax, 4223.0D / daxmax}, new double[]{4259.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{4176.0D / daxmax, 4187.0D / daxmax, 4223.0D / daxmax, 4259.0D / daxmax}, new double[]{4203.0D / daxmax}));
//        trainingSet.addElement(new SupervisedTrainingElement(new double[]{4187.0D / daxmax, 4223.0D / daxmax, 4259.0D / daxmax, 4203.0D / daxmax}, new double[]{3989.0D / daxmax}));
        neuralNet.learnInSameThread(trainingSet);
        System.out.println("Time stamp N2:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));

        TrainingSet testSet = new TrainingSet();
        int len=elements.size();
//        testSet.addElement(new TrainingElement(new double[]{elements.get(len-9)[0], 
//            elements.get(len-8)[0],
//            elements.get(len-7)[0],
//            elements.get(len-6)[0],
//            elements.get(len-5)[0],
//            elements.get(len-4)[0],
//            elements.get(len-3)[0], 
//            elements.get(len-2)[0], 
//            elements.get(len-1)[0]}));
         testSet.addElement(new TrainingElement(new double[]{
            elements.get(len-4)[0],
            elements.get(len-3)[0], 
            elements.get(len-2)[0], 
            elements.get(len-1)[0]}));

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
