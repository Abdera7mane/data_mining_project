package app.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

//Would be optimal to normalize the data before using this class,
//otherwise some columns might affect the result more than others
public class Knn {
        ArrayList<Double[]> training_data, testing_data;
        int k;

        public Knn(ArrayList training_data, ArrayList testing_data, int k) {
                this.training_data = training_data;
                this.testing_data = testing_data;
                this.k = k;
        }

        private Double distance_eucledienne(Double[] ins1, Double[] ins2){
                Double distance=0.0;
                for (int i = 0; i < ins1.length-1; i++) {
                        distance+=Math.pow(ins1[i]-ins2[i],2);
                }
                return Math.sqrt(distance);
        }

        //This function gives the nearest distances for every testing data
        public Double[][] calcul_knn(){
                Double[][] nearest_neighb= new Double[testing_data.size()][k];
                for (int i = 0; i < testing_data.size(); i++) {
                        Double[] distances= new Double[training_data.size()];
                        for (int j = 0; j < training_data.size(); j++) {
                                distances[j]=distance_eucledienne(testing_data.get(i),training_data.get(j));
                        }
                        Arrays.sort(distances);

                        for (int j = 0; j <k ; j++) {
                                nearest_neighb[i][j]=distances[j];
                        }

                }

                return nearest_neighb;
        }

        //This function predicts a class for every testing data
        public ArrayList<Double> predict_class(){
                ArrayList<Double> predicted_classes= new ArrayList<Double>();
                for (int i = 0; i < testing_data.size(); i++) {
                        Double[][] distances= new Double[training_data.size()][2];
                        for (int j = 0; j < training_data.size(); j++) {
                                distances[j][0]=distance_eucledienne(testing_data.get(i),training_data.get(j));
                                distances[j][1]=training_data.get(j)[7];
                        }
                        Arrays.sort(distances, Comparator.comparingDouble(o -> o[0]));
                        Double cmp1=0.0,cmp2=0.0,cmp3=0.0;
                        for (int j = 0; j <k ; j++) {
                                if (distances[j][1].equals(1.0)){
                                        cmp1++;
                                }
                                else if (distances[j][1].equals(2.0)){
                                        cmp2++;
                                }
                                else {
                                        cmp3++;
                                }
                        }
                        if(cmp1>cmp2 && cmp1>cmp3){
                                predicted_classes.add(1.0);
                        }
                        else if(cmp2>cmp1 && cmp2>cmp3){
                                predicted_classes.add(2.0);
                        }
                        else {
                                predicted_classes.add(3.0);
                        }

                }
                return predicted_classes;
        }
}
