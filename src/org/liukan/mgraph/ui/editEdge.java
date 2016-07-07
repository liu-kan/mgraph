/*
 * 
 */
package org.liukan.mgraph.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JToggleButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

// TODO: Auto-generated Javadoc
/**
 * The Class editEdge.
 */
public class editEdge extends absEditEdge {

	/** The content panel. */
	private final JPanel contentPanel = new JPanel();
	
	/** The button group. */
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	/** The text field. */
	public JTextField textField;
	
	/** The start node1. */
	public JRadioButton startNode1;
	
	/** The cancel. */
	public boolean cancel;
	
	/** The action. */
	private final Action action;
	
	/** The chckbx. */
	public JCheckBox chckbx ;

	private ResourceBundle messagesRes;

	private JRadioButton startNode2;
	
	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	/*public static void main(String[] args) {
		try {
			editEdge dialog = new editEdge(null,null);
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
	 *
	 * @param graph the graph
	 * @param tmpCellList the tmp cell list
	 */
	public editEdge(mxGraph graph,ResourceBundle messagesRes) {
		this.messagesRes=messagesRes;
		action = new SwingAction(messagesRes);
		setTitle(i18n("editEdge.addEdge"));
		cancel=false;
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 560, 243);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{531, 0};
		gbl_contentPanel.rowHeights = new int[]{104, 104, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JPanel panelDir = new JPanel();
			GridBagConstraints gbc_panelDir = new GridBagConstraints();
			gbc_panelDir.fill = GridBagConstraints.BOTH;
			gbc_panelDir.insets = new Insets(0, 0, 5, 0);
			gbc_panelDir.gridx = 0;
			gbc_panelDir.gridy = 0;
			contentPanel.add(panelDir, gbc_panelDir);
			panelDir.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel lblNewLabel = new JLabel(i18n("editEdge.chooseStart"));
				panelDir.add(lblNewLabel);
				String ss="";
				String es=ss;
				
				startNode1 = new JRadioButton();
				panelDir.add(startNode1);
				buttonGroup.add(startNode1);
			
				
				startNode2 = new JRadioButton();
				buttonGroup.add(startNode2);
				panelDir.add(startNode2);
				buttonGroup.clearSelection();
				startNode1.setSelected(true);
			}
		}
		{
			JPanel panelCont = new JPanel();
			GridBagConstraints gbc_panelCont = new GridBagConstraints();
			gbc_panelCont.fill = GridBagConstraints.BOTH;
			gbc_panelCont.gridx = 0;
			gbc_panelCont.gridy = 1;
			contentPanel.add(panelCont, gbc_panelCont);
			GridBagLayout gbl_panelCont = new GridBagLayout();
			gbl_panelCont.columnWidths = new int[]{265, 265, 0};
			gbl_panelCont.rowHeights = new int[]{30, 0};
			gbl_panelCont.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panelCont.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panelCont.setLayout(gbl_panelCont);
			{
				chckbx = new JCheckBox(i18n("editEdge.editLabel"));
				chckbx.setAction(action);
				chckbx.setSelected(true);
				GridBagConstraints gbc_chckbx = new GridBagConstraints();
				gbc_chckbx.anchor = GridBagConstraints.WEST;
				gbc_chckbx.fill = GridBagConstraints.VERTICAL;
				gbc_chckbx.insets = new Insets(0, 0, 0, 5);
				gbc_chckbx.gridx = 0;
				gbc_chckbx.gridy = 0;
				panelCont.add(chckbx, gbc_chckbx);
			}
			{
				textField = new JTextField();
				textField.setText("");
				textField.setEditable(true);
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.fill = GridBagConstraints.BOTH;
				gbc_textField.gridx = 1;
				gbc_textField.gridy = 0;
				panelCont.add(textField, gbc_textField);
				textField.setColumns(10);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(i18n("ok"));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				//okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton(i18n("cancel"));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancel=true;
						dispose();
					}
				});
				//cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	/**
	 * The Class SwingAction.
	 */
	private class SwingAction extends AbstractAction {
		
		private ResourceBundle messagesRes;

		/**
		 * Instantiates a new swing action.
		 * @param messagesRes 
		 */
		public SwingAction(ResourceBundle messagesRes) {
			this.messagesRes=messagesRes;
			putValue(NAME, i18n("editEdge.editLabel"));
			//putValue(SHORT_DESCRIPTION, "如果边的权重不为1，可勾选编辑");
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			textField.setEditable(chckbx.isSelected());
		}
	}

	@Override
	public String getEdgeLabel() {
		return textField.getText().trim();
	}
	@Override
	public boolean isCanceled() {
		return cancel;
	}
	@Override
	public boolean editLabel() {
		return chckbx.isSelected();
	}
	@Override
	public boolean selectedNode1() {
		return startNode1.isSelected();
	}
	@Override
	public void setSE(String s, String e) {
		startNode1.setText(s);
		startNode2.setText(e);
		startNode1.setSelected(true);
	}
}
