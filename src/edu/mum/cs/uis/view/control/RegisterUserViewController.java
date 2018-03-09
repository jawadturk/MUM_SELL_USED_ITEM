/*
 * Copyright (c) 2011, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package edu.mum.cs.uis.view.control;
 
import java.io.IOException;

import edu.mum.cs.uis.factorymethods.OperationsFactory;
import edu.mum.cs.uis.model.User;
import edu.mum.cs.uis.ruleset.RuleException;
import edu.mum.cs.uis.view.UserHomeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
 
public class RegisterUserViewController {
//    @FXML private Text actiontarget;

	@FXML
	private Button registerBtn;
	@FXML
	private Button returnLoginBtn;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField userNameField;
	@FXML
	private TextField passwordField;
	@FXML
	private TextField confirmPasswordField;
	
	
    @FXML protected void handleRegisterButtonAction(ActionEvent event) {
		Stage stage = null;
//		Parent root = null;

		if (event.getSource() == registerBtn) {
			stage = (Stage) registerBtn.getScene().getWindow();
			try {
				
				/*if(!passwordField.getText().equals(confirmPasswordField.getText())) {
					throw new RuleException("Password and Confirm Password Should Match!");
				}*/
				
				User loggedinUser = OperationsFactory.register(userNameField.getText(), 
						passwordField.getText(), firstNameField.getText(), 
						lastNameField.getText());
				
				
				
				if(loggedinUser == null) {
					throw new RuleException("Invalid User Register!");
				}else {
					LoggedinSession.getInstance().setLoggedinUser(loggedinUser);
					
					if(loggedinUser.isAdmin()){
						
					}else {
						new UserHomeView(stage);
					}
					
				}
					
					

				
				
				
			} catch (RuleException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("User Register Error");
//				alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
			
			
		}
    }

    
    @FXML protected void handleReturnButtonAction(ActionEvent event) {
		Stage stage = null;
		Parent root = null;

		if (event.getSource() == returnLoginBtn) {
			stage = (Stage) returnLoginBtn.getScene().getWindow();
			try {
				root = FXMLLoader.load(getClass().getResource("../login_view.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
}
