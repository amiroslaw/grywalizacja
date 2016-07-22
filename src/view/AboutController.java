package view;

import javafx.stage.Stage;

public class AboutController {
	private Stage dialogStage;
	private ViewManager manager; 
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setManager(ViewManager manager) {
		this.manager = manager;
	}
}

