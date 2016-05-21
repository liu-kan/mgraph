package org.liukan.mgraph.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import java.awt.Font;

public class editNote extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7619204469923640883L;
	/**
	 * 
	 */
	
	private final JPanel contentPanel = new JPanel();
	private JEditorPane editorPane ;
	private static String rv=null;
	static Semaphore semp;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			editNote dialog = new editNote();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			// add a window listener
			dialog.addWindowListener(new WindowAdapter()
		    {
		      public void windowClosed(WindowEvent e)
		      {
		        System.out.println("editorPane:"+dialog.editorPane.getText());
		      }
		 
		    });
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	}
	public  String getNodeText() {
		
		String rv=editorPane.getText();
		
	   return rv;
	}
	/**
	 * Create the dialog.
	 */
	public editNote() {
		setTitle("请输入节点信息");
		//semp = new Semaphore(1);
		 /* try {
			semp.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		rv=null;
		setAlwaysOnTop(true);
		setBounds(100, 100, 295, 354);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			editorPane = new JEditorPane();
			editorPane.setFont(new Font("Dialog", Font.PLAIN, 15));
			contentPanel.add(editorPane);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("确认");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						editorPane.setText("");
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	

}
