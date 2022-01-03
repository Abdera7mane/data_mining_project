package app.functions;

import java.util.ArrayList;

import static app.functions.MainFct.*;

public class NaiveBays {

        public NaiveBays (ArrayList<Double[]> data) {
                /**
                 * TODO: write something here!
                 */
        }


        public static ArrayList<Double> th_naiveBayes(ArrayList<ArrayList<String>> train, ArrayList<ArrayList<String>> test )
        {
                float p_de_classes[] = {(float)50/150, (float)50/150,(float)50/150};

                double max;
                float  pX_givenC, Xk_given_Ci;
                int att, c, i;
                ArrayList <Double> predictions = new ArrayList  <Double> ();

                double classe = 0;
                // for each row of the test set, calculate the proba of belonging to the 3classes and choice max
                for ( i = 0; i < test.size(); i++)
                {
                        max =0;
                        for (c = 0; c<3; c++)
                        {
                                pX_givenC  = 1;
                                for (att =0; att<7 ;att++)
                                {

                                        Xk_given_Ci = find_prob_Xk_given_Ci(String.valueOf(Double.valueOf(c+1)),att,test.get(i).get(att), train);
                                        pX_givenC = pX_givenC* Xk_given_Ci;
                                }
                                pX_givenC = pX_givenC * p_de_classes[c];
                                if(pX_givenC> max){max = pX_givenC; classe = c+1;}

                        }

                        predictions.add( Double.valueOf(classe));
                }
                return predictions;
        }

        public static float find_prob_Xk_given_Ci(String classe, int att, String val, ArrayList<ArrayList<String>> data){
                int count = 0,i ;

                for ( i = 0; i < data.size(); i++) {
                        if((data.get(i).get(7).equals(classe) && (data.get(i).get(att).equals(val)))) { count++; } }

                float rslt = (float)count/50;
                return rslt;
        }

        public static float find_prob_Xk_given_Ci_laplace(int classe, int att, float val, ArrayList<Double[]> data){
                int count = 0,i ;
                ArrayList <Double> unique_vals = new ArrayList<Double>();

                for ( i = 0; i < data.size(); i++) {

                        if((data.get(i)[7] == classe) && (data.get(i)[att] == val)) { count++; }
                        // chercher # de valeurs possibles de l'attribut att
                        if ( unique_vals.contains(data.get(i)[att]) == false ) { unique_vals.add(data.get(i)[att]); }
                }
                return (float) (count+1)/(30+unique_vals.size());
        }


        // CONFUSION MATRIX

        public static int[][] getConfusion_Matrix (ArrayList <Double> predictions, ArrayList <String> y ){
                int[][] confusion_matrix = new int[3][3];
                int i,j,k;


                for(i=0;i<3;i++){
                        for(j=0;j<3;j++){
                                confusion_matrix[i][j] = 0;

                                // pour chaque celule, parcourir la liste des Y et Y'
                                for(k= 0; k<predictions.size(); k++)
                                {
                                        if ((y.get(k).equals( String.valueOf(Double.valueOf(i+1)))) && (predictions.get(k).equals(Double.valueOf(j+1))) ){
                                                confusion_matrix[i][j] +=1;
                                        }
                                }

                        }
                }

                return confusion_matrix;
        }


        public static float accuracy(int classe, int[][] confusion_matrix){
                int tp, fn = 0, fp = 0,tn = 0,i,j;
                float acc;

                // 1, TP
                tp = confusion_matrix[classe-1][classe-1];
                // 2, FN

                for(i= 0; i<3;i++){
                        fn = fn+ confusion_matrix[classe-1][i];
                        fp = fp +  confusion_matrix[i][classe-1];
                }
                fn = fn - tp;

                // 3, TP
                fp=  fp - tp;

                // 4, TN
                for(i= 0; i<3;i++){
                        for(j= 0; j<3;j++){
                                tn = tn+ confusion_matrix[i][j];
                        }
                }
                tn = tn -(tp+fn+fp);

                System.out.println("TP : "+tp);
                System.out.println("TN : "+tn);
                System.out.println("FP : "+fp);
                System.out.println("FN : "+fn);

                acc = (float)(tp+tn)/ 60;
                return acc;

        }

        public  static void mainTest(String[] args) throws Exception {
                /**
                 * Function to test the naiveBays things!
                 * confusion matrix,
                 * ect
                 * dont forget to change the name from mainTest to main to run it.
                 */
                ArrayList<Double[]> data= MainFct.readFile("datasets/seeds_dataset.txt");
                ArrayList<Double> columnInDataset = new ArrayList();
                ArrayList<String> y = new ArrayList();
                Double[] instance = {};
                ArrayList <Double> labels = new ArrayList<>();
                // discretize every  column.
                ArrayList<ArrayList<String>> discretizedData = new ArrayList<ArrayList<String>>(); //we will have 8 colum, each column beign ar Arraylist<String>

                for (int i = 0; i < 7; i++) {
                        for (int j= 0; j < data.size(); j++) {
                                columnInDataset.add(data.get(j)[i]);
                        }
                        discretizedData.add(discretisationEqual(columnInDataset,4,i+1));
                        columnInDataset.clear();
                }

                // add the labels(8th column) to the new dataset to be able to call NB fct
                for (int j= 0; j < data.size(); j++) {
                        y.add(data.get(j)[7].toString());
                }
                discretizedData.add(y);
                //concretement quand on fait discretizedData.get(i) i:fait reference au numero de  la colonne.




                //////// USAGE OF NB, CONFUSION MATRIX AND ACCURACY////////////

                ArrayList<ArrayList<ArrayList<String>>> total = new ArrayList();

                ArrayList<Double> predictions = new ArrayList<Double>();
                ArrayList<ArrayList<String>> train;
                ArrayList<ArrayList<String>> test ;
                ArrayList<String> yTrain  = new ArrayList<String>();
                ArrayList<String > yTest = new ArrayList<String>();

                total = splitNormalizedData(discretizedData);



                train = total.get(0);
                System.out.println(train.get(0));

                // get xtrainset and yTrainset
                train = total.get(0);
                for (int i=0; i<train.size();i++)
                {
                        yTrain.add(train.get(i).get(7));
                }

                // get xtestset and yTestset
                test  = total.get(1);
                for (int i=0; i<test.size();i++)
                {
                        yTest.add(test.get(i).get(7));
                }



                System.out.println("////////////////////////");


                System.out.println("Test predictions");
                predictions = th_naiveBayes(train,test);

                System.out.println(predictions);



                System.out.println("//// CONFUSION MATRIX //////");
                int[][] mat = getConfusion_Matrix(predictions, yTest);

                for (int i =0; i<3;i++)
                {
                        String row = "";
                        for(int j=0; j<3; j++){
                                row = row+" "+mat[i][j];
                        }
                        System.out.println(row);
                }
                System.out.println("//// TEST ACCURACY //////");
                float acc = accuracy(1, mat);
                System.out.println("Accuracy of classe 1 : " +acc);

        }

}
