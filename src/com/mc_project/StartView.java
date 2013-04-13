/**
 * 
 */
package com.mc_project;

import java.sql.ResultSet;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

/**
 * @author Glen
 * 
 */
@SuppressWarnings("serial")
public class StartView extends VerticalLayout implements View {

	Label errorMessage;

	public StartView() {
		setSizeFull();
		setMargin(true);

		// Create a panel called login
		Panel panel = new Panel("Login");
		panel.setSizeUndefined(); // Shrink to fit content
		addComponent(panel);

		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

		// Create the content
		FormLayout content = new FormLayout();
		final TextField username = new TextField("Username :");
		username.setWidth("300px");
		username.setRequired(true);
		username.setInputPrompt("Your username(eg. name@email.com)");

		content.addComponent(username);

		final PasswordField password = new PasswordField("Password :");
		password.setWidth("300px");
		password.setRequired(true);

		content.addComponent(password);

		HorizontalLayout buttonArea = new HorizontalLayout();
		buttonArea.setSizeFull();
		Button submit = new Button("Submit");
		Button clear = new Button("Clear");
		clear.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				username.setValue("");
				password.setValue("");

			}
		});

		submit.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				checkDatabase(username.getValue(), password.getValue());
			}
		});

		buttonArea.addComponent(clear);
		buttonArea.addComponent(submit);

		content.addComponent(buttonArea);
		errorMessage = new Label("");
		content.addComponent(errorMessage);
		content.setSizeFull();
		content.setMargin(true);

		panel.setContent(content);
	}

	protected void checkDatabase(String uname, String pword) {
		MyDatabaseHandler mydbh = new MyDatabaseHandler();
		try {
			System.out.print("TRY");
			ResultSet rs = mydbh
					.retQuery("select * from staff where S_uname = '" + uname
							+ "' and S_pword = '" + pword + "'");	
			if (rs.next()) {
				
				SharedValues.StaffID = rs.getString(1).toString();
				SharedValues.StaffName = rs.getString(2);
				SharedValues.StaffType = rs.getString(6);
				

				Mc_projectUI.navigator.navigateTo(Mc_projectUI.MAINVIEW);
			} else if (!rs.next()) {
				errorMessage.setValue("Invalid Username/Password Combination");
			}
			rs.close();
		} catch (Exception e) {
			System.out.println("StaffID =" + SharedValues.StaffID);
			System.out.println("StaffName =" + SharedValues.StaffName);
			System.out.println("StaffType =" + SharedValues.StaffType);
			System.out.println("username =" + uname);
			System.out.println("password =" + pword);
			System.out.println("Login Error");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener
	 * .ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		Notification notification = new Notification("\n"
				+ "Welcome to DrSoft Appointment Solutions" + "\n",
				"by MourceCode");

		notification.setDelayMsec(10000);
		notification.setPosition(Position.TOP_CENTER);
		notification.show(Page.getCurrent());
		notification.setIcon(new ThemeResource("img/DrSoftLogo1.png"));

	}

}
