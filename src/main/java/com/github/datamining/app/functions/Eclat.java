package app.functions;

import scala.Int;

import java.lang.reflect.Array;
import java.util.*;

public class Eclat {
        //TODO: try to print the actual list in every step of the process.
        public ArrayList<ArrayList<String>> itemset;
        public  int minSup;
        public Eclat (ArrayList<Double[]> data, int support){
                //TODO: -non priority- coud've done some inheritance here

                //Needs the discretized dataset Arraylist<String[ ] >
                // suport is supposed to be given as a %
                this.itemset = this.preprocessData(data);
                this.minSup = itemset.get(0).size()*support/100;
        }

        public ArrayList<ArrayList<String>> preprocessData(ArrayList<Double[]> data){
                /**
                 * take the data and get the discretized one!
                 */
                ArrayList<ArrayList<String>> descritized_data = new ArrayList<ArrayList<String>>();
                for (int i = 0; i < 7; i++) {
                        descritized_data.add(MainFct.discretisationEqual(this.getColumn(data, i), 4, i+1));
                }
                return descritized_data;
        }

        public Boolean isInDocument(String item , int document) {
                //document is just a fancy way of saying ligne
                for (int i = 0; i < 7; i++) {
                        if (this.itemset.get(document).get(i).equals(item)){
                                return true;
                        }
                }
                return  false;
        }


        public ArrayList<Double> getColumn(ArrayList<Double[]> data, int column){
                ArrayList<Double> columnArray = new ArrayList<>();
                for(int i=0 ;i<data.size();i++) {
                        columnArray.add(data.get(i)[column]);
                }
                return  columnArray;
        }

        public  ArrayList<Integer> createArayZero(){
                ArrayList<Integer> ar = new ArrayList<>();
                for (int i = 0; i < this.itemset.size(); i++) {
                        ar.add(0) ;
                }
                return  ar;
        }

        public  HashMap<String,ArrayList<Integer> > getAllItems(){
                /**
                 * <item :  <list of Int> > index rep num de document
                 */
                HashMap<String, ArrayList<Integer> > listItems = new HashMap();

                for (int i = 0; i < this.itemset.size(); i++) {
                        // add all the itemset of the ligne if they dont exist!!
                        for (int j = 0; j < 7; j++) {
                                // check it the item exists!
                                if (listItems.get(this.itemset.get(i).get(j)) == null) {
                                        ArrayList<Integer> tmp = createArayZero();
                                        listItems.put(this.itemset.get(i).get(j),tmp);
                                }
                        }
                }


                int docCounter = 0;
                for (Map.Entry<String, ArrayList<Integer>> item : listItems.entrySet()) {

                        for (int i = 0; i < this.itemset.size(); i++) {
                                if (isInDocument(item.getKey(), i)) {
                                        //mettre 1 a l'index doucment
                                        ArrayList<Integer> tmp = item.getValue();
                                        tmp.set(i,1);
                                        listItems.put(item.getKey(), tmp);
                                }
                        }

                        //item.getKey();
                        //item.getValue();
                        // Printing all elements of a Map

                }

                return  listItems;


        }

        public void createList(){
                // Hashmap<String, Arraylist<> 210 taille>
                //
        }
        public int calculateSupport(ArrayList<Integer> docList){
                int res =0;
                for (Integer elem:docList) {
                        res = res + elem;
                }
                return res;
        }

        public void executEclat(){
                HashMap< String, ArrayList<Integer> > listItems = getAllItems();
                // calculer support pour chaque item
                ArrayList<String> itemset1 ;
//
//              for (Map.Entry<String, ArrayList<Integer>> item : listItems.entrySet()) {
//
//
//                     if ( item.getValue() < minSup ){
//                            //ajouter a la nouvelle liste?
//                            //supprimer elemnt
//                     }
//                     item.getKey();
//                     item.getValue();
//                     // Printing all elements of a Map
//              }
//
//



//
//
//
//
//
//              // boucles sur l'ensebme des item
//              // pour chaque item calculer le nombre de document dans leqelle il est apparu
//              for (int i = 0; i < this.itemset.size(); i++) {
//                     for (int j = 0; j < 7; j++) {
//                            if ( listItems.get(this.itemset.get(i).get(j)) != null ) {
//                                   // si il apparait dans une ligne (document) incrementer!
//                                   int tmpKey = listItems.get( this.itemset.get(i).get(j) ) + 1;
//                                   listItems.put(this.itemset.get(i).get(j), tmpKey);
//                            }
//                     }
//              }
//
//
//
//



                //check si c'est sup ou égale sinon nahi


                // check si le support est >= a minSup
                // si c'est le cas laisser, sinon supprimer



        }


        public static void main(String[] args){

        }

        //

}
