
package it.polito.tdp.borders;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.PatternSyntaxException;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    @FXML
    private ComboBox<Country> nazioneId;

 
    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	String anno= txtAnno.getText();
    	if (anno==null) {
    		txtResult.appendText("Inserire anni");
    		return;
    	}
    	try {
    		int anno1=Integer.parseInt(anno);
    		model.creaGrafo(anno1);
        	txtResult.appendText(model.grado());
    	}catch(NumberFormatException nfe) {
    		txtResult.appendText("Inserire anni correttamente");
    	}
    	
    	
    			

    }
    @FXML
    void doConfiniNazione(ActionEvent event) {
    	txtResult.clear();
    	Country nazione=nazioneId.getValue();
    	if(nazione==null) {
    		txtResult.appendText("Selezionare nazione");
    		return;
    	}
    	//model.creaGrafoSenzaAnno();
    	HashSet<Country> risultato=(HashSet<Country>) model.trovaNazioni(nazione);
    	if (risultato==null){
    			txtResult.appendText("Nessun confine");
    	}
    	String s="";
    	for(Country cc:risultato) {
    		if(cc!= null)
    		s=s+cc.toString()+"\n";
    	}
    	
    	txtResult.appendText(s);

    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<Country> lista=model.getCountryList();
    	nazioneId.getItems().addAll(lista);
    }
}
