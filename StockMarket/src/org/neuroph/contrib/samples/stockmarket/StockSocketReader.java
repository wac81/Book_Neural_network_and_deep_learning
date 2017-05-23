/***
 * StockSocketReader is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * StockSocketReader is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Neuroph. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neuroph.contrib.samples.stockmarket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Dr.V.Steinhauer
 */
public class StockSocketReader implements Runnable {

    private int maxCounter;
    private long tsleep = 5000;
    private String[] valuesRow;

    public String[] getValuesRow() {
        return valuesRow;
    }

    public void setValuesRow(String[] valuesRow) {
        this.valuesRow = valuesRow;
    }

    public int getMaxCounter() {
        return maxCounter;
    }

    public void setMaxCounter(int maxCounter) {
        this.maxCounter = maxCounter;
    }

    public long getTsleep() {
        return tsleep;
    }

    public void setTsleep(long tsleep) {
        this.tsleep = tsleep;
    }

    public StockSocketReader() {
        //this.setTsleep(10000);
        this.setMaxCounter(100);
    }

    public StockSocketReader(int maxCounter) {
        //this.setTsleep(10000);
        this.setMaxCounter(maxCounter);
    }

    @SuppressWarnings("static-access")
    public void run() {
        valuesRow = new String[this.getMaxCounter()];
        for (int i = 0; i < this.getMaxCounter(); i++) {
            InputStream is = null;
            try {
                String surl = "http://download.finance.yahoo.com/d/quotes.csv?s=^GDAXI&f=sl1d1t1c1ohgv&e=.csv";
                URL url = new URL(surl);
                is = url.openStream();
                BufferedReader dis = new BufferedReader(new InputStreamReader(is));
                String s = dis.readLine();
                System.out.println(s);
                valuesRow[i] = s;
                is.close();
            } catch (MalformedURLException mue) {
                System.out.println("Ouch - a MalformedURLException happened.");
                mue.printStackTrace();
                System.exit(1);
            } catch (IOException ioe) {
                System.out.println("Oops- an IOException happened.");
                ioe.printStackTrace();
                System.exit(1);
            }
            try {
                Thread.currentThread().sleep(this.getTsleep());
            } catch (InterruptedException e) {
            }
        }//end of for
        System.out.println("valuesRow.length=" + valuesRow.length);

    }
}
