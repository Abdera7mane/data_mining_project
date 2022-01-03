package app.functions;

import app.controller.MainWindowController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBoxAndWhiskerRenderer;
import org.jfree.data.statistics.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import smile.plot.swing.PlotCanvas;
import smile.plot.swing.QQPlot;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class MainFct {
        public static String filePath;

        public static ArrayList readFile(String path) throws Exception {
                /**
                 * Method used to read the file as txt or csv
                 * @param path This is the path to the file
                 * @return Arraylist
                 */
                filePath = path;
                BufferedReader reader = new BufferedReader(new FileReader(path));
                ArrayList<Double[]> matrice = new ArrayList<>();

                String line = reader.readLine();
                while (line != null) {
                        String[] line_table;
                        if (MainWindowController.fileExtension.equals("txt")) {
                                line_table = line.split("\t+");
                        } else {
                                line_table = line.split(",");
                        }


                        Double[] line_double = new Double[line_table.length];

                        for (int i = 0; i < line_table.length; i++) {
                                line_double[i] = Double.parseDouble(line_table[i]);
                        }
                        matrice.add(line_double);

                        line = reader.readLine();
                }
                reader.close();

                return matrice;
        }

        public static void printData(ArrayList<Double[]> data) {
                /** previously named print_data
                 * Print the data read by @see readFile
                 * @deprecated
                 */
                for (int i = 0; i < data.size(); i++) {
                        for (int j = 0; j < 8; j++) {
                                System.out.println(data.get(i)[j]);
                        }
                        System.out.println("---------------");
                }
        }

        public static ChartPanel scatterDiagram(ArrayList<Double[]> data, int a, int b) {
                /** previously named  scatter_diagram
                 * Create the scatter diagram of a and b variables (columns)
                 * @param data The data that we got from @see readFile
                 * @param a the index of the first column(attribute) in data(the first variable selected in the ui)
                 * @param b the index of the second column(attribute) in data (the second variable selected in the ui)
                 * @return the ChartPanel object that will be displayed in the gui
                 */
                XYSeriesCollection collection = new XYSeriesCollection();
                XYSeries series = new XYSeries("");
                for (int i = 0; i < data.size(); i++) {
                        series.add(data.get(i)[a], data.get(i)[b]);
                }
                collection.addSeries(series);
                JFreeChart scatter_plot = ChartFactory.createScatterPlot("Scatter Plot",
                        "attribute a", "Attribute b", collection);

                ChartPanel chart = new ChartPanel(scatter_plot);
                chart.setPreferredSize(new Dimension(518, 186));

                return (chart);
        }

        public static HashMap getFrequencies(ArrayList<Double[]> data) {
                /** previously named get_frequencies.
                 *
                 * @deprecated at least i didnt find use case yet
                 * @param data: i suppose it's the matrix  returned by @see readFile
                 * TODO: find use case!
                 */
                HashMap<Double, Integer> hash = new HashMap();
                for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < data.size(); j++) {
                                hash.put(data.get(j)[i], hash.get(data.get(j)[i]) + 1);
                        }
                        System.out.println(hash);
                }
                return hash;
        }

        public static ChartPanel histogramFct(ArrayList<Double[]> data, int a) {
                /** previously named 'histogram_fct'
                 * Create the histogram  diagram of the a'th index attribute
                 * @param data The data that we got from @see readFile
                 * @param a the attribute(column) from which we want to compute the frequecy for each value and plot
                the histogram
                 * @return the ChartPanel object that will display the histogram in the gui
                 */
                HistogramDataset histogram = new HistogramDataset();
                double[] values = new double[data.size()];

                for (int i = 0; i < data.size(); i++) {
                        values[i] = data.get(i)[a];
                }
                histogram.addSeries("key", values, 20);
                JFreeChart chart = ChartFactory.createHistogram("Histogram",
                        "Data", "Frequency", histogram);

                ChartPanel panel = new ChartPanel(chart);
                panel.setPreferredSize(new Dimension(436, 148));

                return panel;
        }

        public static ChartPanel boxplotFct(ArrayList<Double[]> dataset, int a) {
                /** previously named 'boxplot_fct'
                 * Create the boxplot  diagram of the a'th index attribute
                 * @param data The data that we got from @see readFile
                 * @param a the attribute(column) from which we want to  display the boxplot
                 * @return the ChartPanel object that will display the boxplot in the gui
                 */

                ArrayList<Double> col = new ArrayList<>();
                for (int i = 0; i < dataset.size(); i++) {
                        col.add(dataset.get(i)[a]);
                }

                DefaultBoxAndWhiskerXYDataset data = new DefaultBoxAndWhiskerXYDataset("");
                BoxAndWhiskerItem item = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(col);

                data.add(new Date(), item);

                Double upper = maxList(dataset, a);
                Double lower = minList(dataset, a);

                JFreeChart plot = ChartFactory.createBoxAndWhiskerChart("BOXPLOT", "", "Column" + a, data, true);

                XYPlot idfPlot = (XYPlot) plot.getPlot();

                ValueAxis y_axis = idfPlot.getRangeAxis();
                y_axis.setRange(lower - 1, upper + 1);
                XYBoxAndWhiskerRenderer renderer = new XYBoxAndWhiskerRenderer();
                idfPlot.setRenderer(renderer);

                ChartPanel chartPan = new ChartPanel(plot);
                chartPan.setPreferredSize(new Dimension(275, 333));
                return chartPan;

        }

        public static JPanel qqplotFct(ArrayList<Double[]> dataset, int x, int y) {
                /** previously named 'qqplot_fct'
                 * Create the qq-plot  diagram of the a'th and b'th index attribute
                 * @param data The data that we got from @see readFile
                 * @param a the first attribute(column) from which we want to  display the qqplot
                 * @param b the second attribute(column) from which we want to  display the qqplot
                 * @return JPanel object that will display the qqplot in the gui
                 */
                int size = dataset.size();
                double att1[] = new double[size];
                double att2[] = new double[size];

                for (int i = 0; i < dataset.size(); i++) {
                        att1[i] = (dataset.get(i)[x]);
                        att2[i] = (dataset.get(i)[y]);
                }

                PlotCanvas canvas = QQPlot.plot(att1, att2);
                JPanel pan = new JPanel(new BorderLayout());
                pan.setPreferredSize(new Dimension(461, 186));
                pan.add(canvas, BorderLayout.CENTER);

                return pan;
        }

        public static double getMoy(ArrayList<Double[]> data, int column) {
                /** previously named 'get_moy'
                 * Compute the "mean" of a column (attribute)
                 * @param data The data that we got from @see readFile
                 * @param column the column from which we want to compute the mean
                 * @return Double ,the mean of the column column
                 */

                double moy = 0;
                for (int i = 0; i < data.size(); i++) {
                        moy += data.get(i)[column];
                }
                moy = moy / data.size();
                return moy;
        }

        public static Double getMediane(ArrayList<Double[]> data, int column) {
                /** previously named 'get_mediane'
                 * Compute the "median" of a column (attribute)
                 * @param data The data that we got from @see readFile
                 * @param column the column from which we want to comute the meadian
                 * @return Double , the median of the column column
                 */


                Double[] values = sort(data, column);
                if (values.length % 2 == 1) {
                        return values[(values.length) / 2];
                } else {
                        return (values[(values.length - 1) / 2] + values[(values.length + 1) / 2]) / 2;
                }
        }

        public static Double[] sort(ArrayList<Double[]> data, int col) {
                /**
                 * Sort a specific column (col) in the data
                 * @param data The data that we got from @see readFile
                 * @param col the column that we want to sort.
                 * @return Double[] , an array of the sorted column values.
                 */
                Double[] values = new Double[data.size()];
                for (int i = 0; i < data.size(); i++) {
                        values[i] = data.get(i)[col];
                }
                Arrays.sort(values);
                return values;
        }

        public static Double milieuEtendu(ArrayList<Double[]> data, int col) {
                /** previously named 'milieu_etendu'
                 * Compute the "midrange"(milieu ed gamme) of a column (attribute)
                 * @param data The data that we got from @see readFile
                 * @param column the column from which we want to comute the midrange
                 * @return Double , the midrange of the column column
                 * TODO -low priority- :optimise when have time
                 */
                Double max = 0.0;
                Double min = data.get(0)[col];

                for (int i = 0; i < data.size(); i++) {
                        if (max < data.get(i)[col]) {
                                max = data.get(i)[col];
                        }
                        if (min > data.get(i)[col]) {
                                min = data.get(i)[col];
                        }
                }
                return (max + min) / 2;
        }

        public static ArrayList<Double> modeFct(ArrayList<Double[]> data, int col) {
                /** previously named 'mode_fct'
                 * Compute the "mode" of the a'th column (attribute)
                 * @param data The data that we got from @see readFile
                 * @param col the column from which we want to compute the mode
                 * @return Arraylist<Double>, the list of the modes of the column col.
                 */
                int i, j;
                Double max = 0.0, maxcount = 0.0;
                ArrayList<Double> returnList = new ArrayList<Double>();
                int count;

                for (i = 0; i < data.size(); i++) {
                        count = 0;
                        for (j = 0; j < data.size(); j++) {
                                if (data.get(j)[col].equals(data.get(i)[col])) {
                                        count++;
                                }
                        }
                        if (count > maxcount) {
                                maxcount = Double.parseDouble(String.valueOf(count));
                                max = data.get(i)[col];

                        }
                }
                returnList.add(max);

                for (i = 0; i < data.size(); i++) {
                        count = 0;
                        for (j = 0; j < data.size(); j++) {
                                if (data.get(j)[col].equals(data.get(i)[col])) {
                                        ++count;
                                }
                        }
                        if (count == maxcount && !returnList.contains(data.get(i)[col])) {
                                returnList.add(data.get(i)[col]);
                        }
                }

                return returnList;
        }

        public static Double moyenneTranquee(ArrayList<Double[]> data, int col) {
                /** previously named 'moyenne_tranquee'
                 * Compute the "trimmed mean" of the a'th column (attribute)
                 * @param data The data that we got from @see readFile
                 * @param col the column from which we want to compute the trimmed mean
                 * @return Double, the trimmed mean of the column col.
                 * TODO: i don't get this one!
                 */

                Double[] values = sort(data, col);
                Double dbl = values.length * 0.025;
                int min = dbl.intValue() + 1;
                dbl = values.length * 0.975;
                int max = dbl.intValue();

                Double moyenne = 0.0;
                for (int i = min; i < max; i++) {
                        moyenne += values[i];
                }
                return moyenne / values.length;
        }

        static Double maxList(ArrayList<Double[]> data, int column) {
                /** previously named 'max_list'
                 * Compute the "max" of the a'th column (attribute)
                 * @param data The data that we got from @see readFile
                 * @param column the column from which we want to get the max value of
                 * @return Double, the maximum value of the elements of the column column.
                 */
                int i;
                Double max = data.get(0)[column];
                for (i = 1; i < data.size(); i++) {
                        Double element = data.get(i)[column];
                        if (element > max) {
                                max = element;
                        }
                }
                return max;
        }

        static Double minList(ArrayList<Double[]> data, int column) {
                /** previously named 'min_list'
                 * Compute the "min" of the a'th column (attribute)
                 * @param data The data that we got from @see readFile
                 * @param column the column from which we want to get the min value of
                 * @return Double, the minimum value of the elements of the column column.
                 */
                int i;
                Double min = data.get(0)[column];
                for (i = 1; i < data.size(); i++) {
                        if (data.get(i)[column] < min) {
                                min = data.get(i)[column];
                        }
                }
                return min;
        }

        static Double[] sort(Double[] array) {
                /**
                 * Sort a Double[] list
                 * @param array the array we want to sort
                 * @return Double[], the array sorted
                 * @note i guess it can be optimised using another sorting method mais bon.
                 */

                int i, j, indexMin = 0;
                double min, temp;
                int t = array.length;

                for (i = 0; i < t - 1; i++) {
                        min = array[i];

                        for (j = i + 1; j < t; j++) {

                                if (array[j] < min) {
                                        min = array[j];
                                        temp = array[i];
                                        array[i] = min;
                                        array[j] = temp;
                                }
                        }
                }
                return array;
        }

        public static Double etendu(ArrayList<Double[]> data, int column) {
                /**
                 * Compute etendu
                 * @param data The data that we got from @see readFile
                 * @param column the column from which we want to get "entendu"
                 * @return Double, etendue value.
                 */
                Double max = maxList(data, column);
                Double min = minList(data, column);
                return max - min;

        }

        public static Double quartiles(ArrayList<Double[]> data, int quartile, int column) {
                /**
                 * Compute quartile number @param quartile
                 * @param data the dataset (matrix)
                 * @param quartile the quartile number we want to compute (1 2 3 )
                 * @param column the columns from which we want to compute the quartile
                 * @return Double, the quartile value
                 */
                double result = 0;
                ;
                //////////////////Not Getting the right column
                Double[] mycol = new Double[data.size()];
                int i;

                for (i = 0; i < data.size(); i++) {
                        mycol[i] = data.get(i)[column];
                }
                Double[] sorted = sort(mycol);
                int taille = sorted.length, index;
                if (quartile == 1) {
                        index = taille / 4;
                        System.out.println("index = " + index);
                        /// pair
                        if (taille % 4 == 0) {
                                return ((sorted[index] + sorted[index - 1]) / 2);
                        } else return sorted[index - 1];
                } else {
                        if (quartile == 2) {
                                index = taille / 2;
                                System.out.println("index = " + index);
                                /// pair
                                if (taille % 2 == 0) {
                                        return ((sorted[index] + sorted[index - 1]) / 2);
                                } else return sorted[index - 1];

                        } else if (quartile == 3) {
                                index = taille * 3 / 4;
                                System.out.println("index = " + index);
                                /// pair
                                if (taille * 3 % 4 == 0) {
                                        return ((sorted[index] + sorted[index - 1]) / 2);
                                } else return sorted[index - 1];
                        } else {
                                //TODO: throw some exception here
                                System.out.println("Unexpected quartile value: " + quartile);
                                System.exit(0);
                        }
                }

                return result;
        }

        public static Double ecartInterquartile(ArrayList<Double[]> data, int column) {
                /** previously named ecart_interquartile
                 * Compute ecart type
                 * @param data the dataset (matrix)
                 * @param column the columns from which we want to compute the "ecart type"
                 * @return Double, the "equart type" value
                 */
                double q1 = quartiles(data, 1, column);
                double q3 = quartiles(data, 3, column);
                return q3 - q1;

        }

        public static Double variance(ArrayList<Double[]> data, int column) {
                /**
                 * Compute variance of the column
                 * @param data the dataset (matrix)
                 * @param column the columns from which we want to compute the "variance"
                 * @rturn Double, the value of the variance
                 */
                Double ecarttype = ecartType(data, column);
                return ecarttype * ecarttype;
        }

        public static Double ecartType(ArrayList<Double[]> data, int column) {

                /**
                 * Compute "ecart type" of the column
                 * @param data the dataset (matrix)
                 * @param column the columns from which we want to compute the "ecart type"
                 * @rturn Double, the value of the "ecart type"
                 */
                //boucle de somme de (x-moyenne)carre
                //return racine(1/n(somme))
                Double element, somme = 0.0, moyenne = getMoy(data, column);
                Double difference;
                int i;
                for (i = 0; i < data.size(); i++) {
                        element = data.get(i)[column];
                        difference = (element - moyenne);
                        somme = somme + difference * difference;
                }
                return Math.sqrt(somme / data.size());
        }

        public static ArrayList<Double> outliers(ArrayList<Double[]> data, int column) {
                /**
                 * Calculate outliers of the column column
                 * @param data the dataset (matrix)
                 * @param column the columns from which we want to compute the outliers
                 * @rturn Double[], list of outliers
                 */
                //calculate outliers
                int i, taille = data.size();
                Double outlier1, outlier2, q1, q3, iqr, element;
                q1 = quartiles(data, 1, column);
                q3 = quartiles(data, 3, column);
                iqr = ecartInterquartile(data, column);
                outlier1 = q1 - 1.5 * iqr;
                outlier2 = q3 + 1.5 * iqr;
                ArrayList<Double> outliers = new ArrayList<>();
                for (i = 0; i < taille; i++) {
                        element = data.get(i)[column];
                        if (element > outlier2) {
                                outliers.add(element);
                        } else {
                                if (element < outlier1) {
                                        outliers.add(element);
                                }
                        }
                }
                return outliers;

        }

        public static double correlationCoef(ArrayList<Double[]> data, int column1, int column2) {
                /**
                 * Compute "coefficient de correlation" of the @param column1 and @param column2
                 * @return Double the computed corelation coef between the two columns(variables)
                 * @note apparently double and Double isn't the same this @see https://stackoverflow.com/questions/20437003/what-is-the-difference-between-double-and-double-in-java
                 */
                Double somme = new Double(0.0);
                Double nAB = new Double(0.0);
                Double denomin = new Double(0.0);
                for (int i = 0; i < data.size(); i++) {
                        somme += data.get(i)[column1] * data.get(i)[column2];
                }

                nAB = data.size() * getMoy(data, column1) * getMoy(data, column2);
                denomin = (data.size() - 1) * ecartType(data, column1) * ecartType(data, column2);

                return (somme - nAB) / denomin;
        }

        //--------------------------------part2--------------------------
        public static Double[] getColumn(ArrayList<Double[]> data, int column) {
                /**
                 * get the entire column of the data(matrix) and return it as Double[]
                 * @note the thing is that some functions used after in KNN and others
                       are using another type which is an ArrayList<>.
                 * @return the entire column specified in @param column
                 */

                Double[] columnArray = new Double[data.size()];
                for (int i = 0; i < data.size(); i++) {
                        columnArray[i] = data.get(i)[column];
                }
                return columnArray;

        }

        public static Double getMin(Double[] columnArray) {
                /**
                 * Get the minimum value
                 * @note i just lost too much time looking for "the min function"
                 */
                Arrays.sort(columnArray);
                return columnArray[0];
        }

        public static Double getMax(Double[] columnArray) {
                /**
                 * Get the minimum value
                 * @note i just lost too much time looking for "the min function"
                 */
                Arrays.sort(columnArray);
                return columnArray[columnArray.length - 1];
        }

        public static Double getNormalizedMinMaxVal(Double value, Double min, Double max) {
                /**
                 * get the normalized value
                 */
                return (value - min) / (max - min);
        }

        public static Double[] normalizeMinMaxCol(ArrayList<Double[]> data, int column) {
                /**
                 * Normalize value of the column @param column
                 * @param data the dataset (matrix)
                 * @param column the columns from which we want to compute the "ecart type"
                 * @return Double[], the new values for the column column
                 */
                Double[] normalizedColumn = getColumn(data, column);
                Double min, max;

                min = getMin(normalizedColumn);
                max = getMax(normalizedColumn);
                for (int i = 0; i < normalizedColumn.length; i++) {
                        //normalize the thing
                        normalizedColumn[i] = (normalizedColumn[i] - min) / (max - min);
                }
                return normalizedColumn;
        }

        public static ArrayList<Double[]> minMaxNormalization(ArrayList<Double[]> data) {
                /**
                 * Normalize all the dataset  using the minMax method
                 * @param data the dataset returned by @see readData
                 * @return the normalized dataset!
                 * @note i know the solution is kindof stupid now and it takes a little bit too much ram
                but since the dataset is small we can work with that, and any changes are welcome.
                 * @note2 it was tested and the value of the first instances are
                [0.40509915014164316, 0.44628099173553726, 0.6624319419237747, 0.3688063063063065,
                0.5010691375623664, 0.03288301759221938, 0.21516494337764666, 1.0]
                it matches the teacher's result
                 */
                ArrayList<Double[]> tmpNormalizedData = new ArrayList<>();
                ArrayList<Double[]> NormalizedData = new ArrayList<>();
                int maxNumCol = 8;

                Double[] minList = new Double[maxNumCol];
                Double[] maxList = new Double[maxNumCol];
                for (int i = 0; i < maxNumCol; i++) {
                        minList[i] = getMin(getColumn(data, i));
                        maxList[i] = getMax(getColumn(data, i));
                }

                for (int i = 0; i < data.size(); i++) {
                        for (int j = 0; j < data.get(i).length - 1; j++) {//the -1 is here because we don't need the last column
                                data.get(i)[j] = getNormalizedMinMaxVal(data.get(i)[j], minList[j], maxList[j]);
                        }
                }
                return data;
        }

        public static Double getSValue(ArrayList<Double[]> data, double moyenne, int column) {
                /**
                 * compute the S value for zsocre normalization
                 */
                double sValue = 0.0;
                int n = data.size();
                for (int i = 0; i < data.size(); i++) {
                        sValue = sValue + (data.get(i)[column] - moyenne);
                }
                sValue = sValue / n;
                return sValue;
        }

        public static Double getNormalizedzScoreValue(Double value, Double moyenne, Double sValue) {
                /**
                 * get the normalized zscore value
                 */
                return (value - moyenne) / sValue;
        }

        public static ArrayList<Double[]> zScoreNormalization(ArrayList<Double[]> data) {
                /**
                 * Normalize all the dataset  using the zScore method
                 * @param data the dataset returned by @see readData
                 * @return the normalized dataset!
                 *
                 */
                int maxNumCol = 8;
                Double[] sList = new Double[maxNumCol];
                Double[] meanList = new Double[maxNumCol];
                for (int i = 0; i < maxNumCol; i++) {
                        meanList[i] = getMoy(data, i);
                        sList[i] = getSValue(data, meanList[i], i);
                }
                for (int i = 0; i < data.size(); i++) {
                        for (int j = 0; j < data.get(i).length - 1; j++) {//the -1 is here because we don't need the last column
                                data.get(i)[j] = getNormalizedzScoreValue(data.get(i)[j], meanList[j], sList[j]);
                        }
                }
                return data;

        }

        //----------------------- discrétisation ----------------------
        public static int getIntervalEqual(ArrayList<double[]> listInt, double value){
                /**
                 * gets in which interval a value is
                 */
                int i=0;
                for ( i = 0; i < listInt.size(); i++) {
                        if ( (value >= listInt.get(i)[0] ) && (value < listInt.get(i)[1] ) ){
                               return i;

                        }
                }
                return i-1;
        }

        public static ArrayList<String> discretisationEqual(ArrayList<Double> column, int q, int c) {
                /**
                 * Compute new discretized values for a column in the dataset using the equal method
                 * @param column the column in ArrayList<Double> type which means that we need to
                        make an extra step to loop through the data and then transform and get each
                        column in this new type
                        * TODO:create a function to do this transformation to have a better dry code.
                 * @param q number of intervals
                 * @param c column number
                 */
                double min = Collections.min(column);
                double max = Collections.max(column);
                double intervalLength = (max - min) / q;

                //chaque element de listeIntervale continet un intervale [x,y[
                ArrayList<double[]> listeInvervales = new ArrayList<>() ;

                // remplir la liste des intervalles
                double debutIntervalle = min;
                for (int i = 0; i < q ; i++) {
                        double [] tmp = {debutIntervalle , debutIntervalle + intervalLength};
                        listeInvervales.add(tmp);
                        debutIntervalle = debutIntervalle +intervalLength;
                }


                ArrayList<String> result = new ArrayList<>(column.size());
                for (int i = 0; i < column.size(); i++) {
                        int category = getIntervalEqual(listeInvervales, column.get(i)) + 1 ;
                        if (category==-1){
                                System.out.println("error in discretisationEqual");
                                System.exit(0);
                        }
                        result.add("I" + String.valueOf(c) + String.valueOf(category) );
                }
                return result;
        }

        public static ArrayList<String> discretisationEffectif(ArrayList<Double> column, int q, int c) {
                /** TODO -priority- FIX: the function doesn't return all the 210 values for some reason

                 * Compute new discretized values for a column in the dataset using the effectif method.
                 * @param column the column in ArrayList<Double> type which means that we need to
                make an extra step to loop through the data and then transform and get each
                column in this new type
                 * TODO:create a function to do this transformation to have a better dry code.
                 * @param q number of intervals
                 * @param c column number
                 */

                Double[] ar = new Double[column.size()];
                for (int i = 0; i < column.size(); i++) {
                        ar[i] = column.get(i);
                }
                Double[] sorted = sort(ar);
                Double[] quartiles = new Double[q];
                for (int i = 0; i < q; i++) {
                        quartiles[i] = sorted[(Math.round(column.size() / q * (i + 1)))];
                }
                //System.out.println(quartiles[0]+"   "+quartiles[1]+"   "+quartiles[2]+"   "+quartiles[3]);
                ArrayList<String> result = new ArrayList<>();
                for (int i = 0; i < column.size(); i++) {
                        for (int j = 0; j < q; j++) {
                                if (column.get(i) <= quartiles[j]) {
                                        result.add('I' + String.valueOf(c) + String.valueOf(j + 1));
                                        break;
                                }
                        }
                }
                return result;
        }

        public static void frequentItem(double minSup, ArrayList<String> column, int c) {
                /**
                 * TODO:find why this function exists and where it can be used and how it works
                 */
                String[] items = {"I" + c + "1", "I" + c + "2", "I" + c + "3", "I" + c + "4"};
                String[][] frequency = new String[2][column.size()];
                int j = 0;
                for (String d : items) {
                        int i = 0;
                        for (String col : column) {

                                if (d.equals(col)) {
                                        i++;
                                }
                        }
                        if (Double.valueOf(i) / Double.valueOf(column.size()) > minSup) {
                                frequency[0][j] = d;
                                frequency[1][j] = String.valueOf(i);

                                System.out.println(frequency[0][j] + ", freq" + frequency[1][j]);
                        }
                        j++;
                }
        }

        public static ArrayList<ArrayList<ArrayList<String>>> splitNormalizedData(ArrayList<ArrayList<String>> data) {
                /**
                 * @param data: the discretized data NOT THE NORMAL ONE
                 * and to disretize, you'll need some extra efforts, to create the new dataStructure
                 *  TODO: create a method to DRY the code more according to this issue.
                 * @return we return 2 ArrayList<ArrayList<String> the one at index 0 is the trainData which have 150 lines
                 *      and the second is the testData which have 60 element 20 for each class.
                 *@note THE THING IS there was a paradigm shift between @param data, and the trainData and testData
                 *       in @param `data` when we do data.get(i) we get the i'th column
                 *       Wheras is trainData and testData returned, when we do trainData.get(i) we get the i'th line.
                 *       and i think it's very poorly and badly done having to switch like this.
                 *       TODO: maybe try to find a way to have ONE manner to work with
                 */
                // 20 instances -> test , Cid =  50 -> apprentissage  , D=150
                int i, j;
                String class1 = "1.0", class2 = "2.0", class3 = "3.0";
                int targetIndex = 7;
                ArrayList<ArrayList<ArrayList<String>>> total = new ArrayList();

                ArrayList<ArrayList<String>> testData = new ArrayList();
                ArrayList<ArrayList<String>> trainingData = new ArrayList();


                //for each  get 20 first instance  (each class) put it in test , rest 50 put it in training
                int training1 = 0;
                int training2 = 0;
                int training3 = 0;
                int test1 = 0;
                int test2 = 0;
                int test3 = 0;

                ArrayList<String> ligne = new ArrayList<String>();
                for (i = 0; i < data.get(targetIndex).size(); i++)//TODO:why the -1??
                        //FIX: THIS IS WEIRD! the size is supposed to be 210 so the mex index is 209
                        // BUTT the 209 is noon existant!!!!
                {
                        //System.out.println(data.get(i)[column]);
                        //if class 1 -> 20 test 50 training
                        ligne = new ArrayList<>();
                        for (j = 0; j < 8; j++) {
                                ligne.add(data.get(j).get(i));
                        }

                        if ( (data.get(targetIndex).get(i).equals(class1) ) && (test1 < 20) )  {
                                testData.add(ligne);
                                test1++;
                        }
                        if ( ( data.get(targetIndex).get(i).equals(class1) ) && (training1 < 50) && (test1 == 20) ) {
                                trainingData.add(ligne);
                                training1++;
                        }
                        if ((data.get(targetIndex).get(i).equals(class2) && (test2 < 20))) {
                                testData.add(ligne);
                                test2++;
                        }
                        if ((data.get(targetIndex).get(i).equals(class2) && (training2 < 50) && (test2 == 20))) {
                                trainingData.add(ligne);
                                training2++;
                        }
                        if ((data.get(targetIndex).get(i).equals(class3) && (test3 < 20))) {
                                testData.add(ligne);
                                test3++;
                        }
                        if ((data.get(targetIndex).get(i).equals(class3) && (training3 < 50) && (test3 == 20))) {
                                trainingData.add(ligne);
                                training3++;
                        }
                }
                total.add(trainingData);
                total.add(testData);
                return total;
        }


        public static java.util.ArrayList<java.util.ArrayList<Double[]>> split_data_knn(ArrayList<Double[]> data){
                /**
                 * Split the data into test and train!
                 * @param data the dataset returned by @see readData
                 *  TODO: create a method to DRY the code more according to this issue.
                 *  TODO: understand how this work!
                 * @return i dont know! TODO:know!
                 */
                // 20 instances -> test , Cid =  50 -> apprentissage  , D=150
                int i,j;double class1 = 1.0, class2 = 2.0, class3 = 3.0; int column=7;
                ArrayList <ArrayList<Double[]>> total = new ArrayList();
                ArrayList<Double[]> testData = new ArrayList();
                ArrayList<Double[]> trainingData=new ArrayList();

                //for each  get 20 first instance  (each class) put it in test , rest 50 put it in training
                int training1=0;int training2=0;int training3=0;
                int test1=0;int test2=0;int test3=0;

                for ( i = 0; i < data.size(); i++) {
                        //System.out.println(data.get(i)[column]);
                        //if class 1 -> 20 test 50 training
                        if( ( data.get(i)[column] == class1 ) && (test1 < 20) ){
                                testData.add(data.get(i));
                                test1++;
                        }
                        if( (data.get(i)[column] == class1) && (training1 < 50) && ( test1 == 20) ){
                                trainingData.add(data.get(i));
                                training1++;
                        }
                        if( (data.get(i)[column] == class2) && (test2<20) ){
                                testData.add(data.get(i));
                                test2++;
                        }
                        if( (data.get(i)[column] == class2) && (training2 < 50) && (test2==20) ){
                                trainingData.add(data.get(i));
                                training2++;
                        }
                        if( ( data.get(i)[column] == class3) && (test3<20) ){
                                testData.add(data.get(i));
                                test3++;
                        }
                        if( (data.get(i)[column] == class3) && (training3<50) && (test3==20) ){
                                trainingData.add(data.get(i));
                                training3++;
                        }
                }

                total.add(trainingData);
                total.add(testData);
                return total;
        }


        public static void main(String[] args) throws Exception {
                ArrayList<Double[]> data = MainFct.readFile("datasets/seeds_dataset.txt");
                data = MainFct.minMaxNormalization(data);
                System.out.println(data.size());
                System.out.println(data.get(data.size()-1)[0]);
                /*  Test discretisationEqual

                //create the data!
                ArrayList<Double> ar = new ArrayList<>();
                for(int i=0 ; i< data.size() ;i++){
                        ar.add(data.get(i)[1]);
                }
                //put it into the function
                ArrayList<String> arResult = new ArrayList<>();
                arResult = discretisationEqual(ar,6,1);
                //check the output
                for (String val:arResult ) {
                        System.out.println(val);
                }

                 */



        }
}