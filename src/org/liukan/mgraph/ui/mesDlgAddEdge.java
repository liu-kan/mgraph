/*
 * 
 */
package org.liukan.mgraph.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JCheckBox;
import java.awt.Component;
import java.awt.Dialog;

import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class mesDlgAddEdge.
 */
public class mesDlgAddEdge extends JDialog {

	/** The content panel. */
	private final JPanel contentPanel = new JPanel();
	
	/** The chckbx. */
	public JCheckBox chckbx;

	private ResourceBundle messagesRes;
	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	/*
	public static void main(String[] args) {
		try {
			mesDlgAddEdge dialog = new mesDlgAddEdge();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	private String i18n(String s){
		return messagesRes.getString(s);
	}
	/**
	 * Create the dialog.
	 */
	public mesDlgAddEdge(ResourceBundle messagesRes) {
		this.messagesRes=messagesRes;
		setBounds(100, 100, 450, 125);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel label = new JLabel(i18n("mesDlgAddEdge.label"));
			label.setFont(new Font("Dialog", Font.BOLD, 18));
			contentPanel.add(label);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
			{
				 chckbx = new JCheckBox(i18n("mesDlgAddEdge.nomore"));
				buttonPane.add(chckbx);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				buttonPane.add(horizontalGlue);
			}
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
