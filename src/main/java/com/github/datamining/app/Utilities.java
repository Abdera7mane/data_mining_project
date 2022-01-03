package app;

import app.functions.MainFct;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/*
    this class is aimed to contain useful methods that we will need
    in the gui.

 */
public class Utilities {

        public  static  void acceptOnlyNumbersTextField(TextField txt){
      /*
         just accept Numbers in a specific textField
       */
                txt.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue,
                                            String newValue) {
                                if (!newValue.matches("\\d*")) {
                                        txt.setText(newValue.replaceAll("[^\\d]", ""));
                                }
                        }
                });
        }

        public String[] return_centrale(ArrayList<Double[]> data, int column){
                String[] centrale= new String[5];
                centrale[0]=String.format("%.2f", MainFct.getMoy(data,column)).replace(",", ".") ;
                centrale[1]=String.format("%.2f", MainFct.moyenneTranquee(data,column)).replace(",", ".");
                centrale[2]=MainFct.modeFct(data,column).toString();
                centrale[3]=String.format("%.2f", MainFct.getMediane(data,column)).replace(",", ".");
                //TODO; try to fix the .2f error
                centrale[4]=String.valueOf(MainFct.milieuEtendu(data,column));

                return centrale;
        }

        public String[] return_dispersion(ArrayList<Double[]> data, int column){
                String[] dispersion= new String[8];
                dispersion[0]=String.format("%.2f", MainFct.quartiles(data,1,column)).replace(",", ".") ;
                dispersion[1]=String.format("%.2f", MainFct.quartiles(data,2,column)).replace(",", ".") ;
                dispersion[2]=String.format("%.2f", MainFct.quartiles(data,3,column)).replace(",", ".") ;
                dispersion[3]=String.format("%.2f", MainFct.ecartType(data,column)).replace(",", ".");
                dispersion[4]=String.format("%.2f", MainFct.ecartInterquartile(data,column)).replace(",", ".");
                dispersion[5]=String.format("%.2f", MainFct.etendu(data,column)).replace(",", ".");
                dispersion[6]=String.format("%.2f", MainFct.variance(data,column)).replace(",", ".");
                dispersion[7]=MainFct.outliers(data,column).toString();

                return dispersion;
        }

        public static void addChoice(ComboBox cbx){
       /*
            when a user selects an item in the choice box and wants to go back
            we need to add a "" item that's what this function do
        */

                cbx.getItems().addAll("area","perimeter","compactness","length of kernel",
                        "width of kernel","assymmetry coeff","length of kernel groove");
        /*
        cbx_softwareNeeds.getItems().add("");
        cbx_desktopEnvirnment.getItems().add("");
        cbx_linuxDistro.getItems().add("");
        cbx_os.getItems().add("");
        */

        }

        public static Boolean isFilled(TextField input){
                //works !
                return  !(input.getText() == null || input.getText().trim().isEmpty()) ;
        }

        public void switchWindow(ActionEvent event, String fxmlWindow, Parent root, Stage stage, Scene scene) throws IOException {

                String cssPath = "/resources/views/style.css";
                String css = this.getClass().getResource(cssPath).toExternalForm();

                root = FXMLLoader.load(getClass().getResource(fxmlWindow));
                stage =(Stage) ((Node) event.getSource()).getScene().getWindow();

                scene = new Scene(root);
                scene.getStylesheets().add(css);

                stage.setScene(scene);

                stage.show();
        }

}
