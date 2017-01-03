package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Instrument;

public class AboutController implements Initializable {
	private Stage dialogStage;
	private ViewManager manager; 
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setManager(ViewManager manager) {
		this.manager = manager;
	}
	@FXML
	private TextField tfTest;
	@FXML
	private Button btnTest;
	Instrument instr = new Instrument() ; 
	@FXML
	private void addName(){
	    instr.setName(tfTest.getText());
	} 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       System.out.println("name "+instr.getName()); 
    }

    public void init(Instrument inst) {
        // TODO Auto-generated method stub
       instr = inst; 
       System.out.println("name init "+instr.getName()); 
    }
	
    
	
}

