/***
 * TrainingData is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * TrainingData is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Neuroph. If not, see <http://www.gnu.org/licenses/>.
 */


package org.neuroph.contrib.samples.stockmarket;

import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingSet;

/**
 *
 * @author Dr.V.Steinhauer
 */
public class TrainingData {

    private String[] valuesRow;
    private TrainingSet trainingSet = new TrainingSet();
    private double normolizer = 10000.0D;
    private double minlevel = 0.0D;

    public String[] getValuesRow() {
        return valuesRow;
    }

    public void setValuesRow(String[] valuesRow) {
        this.valuesRow = valuesRow;
    }

    public double getNormolizer() {
        return normolizer;
    }

    public void setNormolizer(double normolizer) {
        this.normolizer = normolizer;
    }

    public TrainingData() {
    }

    public TrainingSet getTrainingSet() {
        int length = valuesRow.length;
        if (length < 5) {
            System.out.println("valuesRow.length < 5");
            return null;
        }
        try {
            for (int i = 0; i + 4 < valuesRow.length; i++) {
                String[] s1 = valuesRow[i].split(",");
                String[] s2 = valuesRow[i + 1].split(",");
                String[] s3 = valuesRow[i + 2].split(",");
                String[] s4 = valuesRow[i + 3].split(",");
                String[] s5 = valuesRow[i + 4].split(",");
                double d1 = (Double.parseDouble(s1[1]) - minlevel) / normolizer;
                double d2 = (Double.parseDouble(s2[1]) - minlevel) / normolizer;
                double d3 = (Double.parseDouble(s3[1]) - minlevel) / normolizer;
                double d4 = (Double.parseDouble(s4[1]) - minlevel) / normolizer;
                double d5 = (Double.parseDouble(s5[1]) - minlevel) / normolizer;
                System.out.println(i + " " + d1 + " " + d2 + " " + d3 + " " + d4 + " ->" + d5);
                trainingSet.addElement(new SupervisedTrainingElement(new double[]{d1, d2, d3, d4}, new double[]{d5}));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return trainingSet;
    }

    public void setTrainingSet(TrainingSet trainingSet) {
        this.trainingSet = trainingSet;
    }

    public TrainingData(String[] valuesRow) {
        this.setValuesRow(valuesRow);
    }
}
