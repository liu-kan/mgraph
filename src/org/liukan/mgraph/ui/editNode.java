/*
 * 
 */
package org.liukan.mgraph.ui;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.event.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import java.awt.Font;

// TODO: Auto-generated Javadoc
/**
 * The Class editNote.
 */
public class editNode extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7619204469923640883L;
	
	/** The content panel. */
	
	private final JPanel contentPanel = new JPanel();
	
	/** The editor pane. */
	private JEditorPane editorPane ;

	private ResourceBundle messagesRes;
	
	/** The rv. */
	private static String rv=null;
	
	/** The semp. */
	static Semaphore semp;
	
	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
/*	public static void main(String[] args) {
		try {
			editNode dialog = new editNode(true);
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
	    
	}*/
	
	/**
	 * Gets the node text.
	 *
	 * @return the node text
	 */
	public  String getNodeText() {
		
		String rv=editorPane.getText();
		
	   return rv;
	}
	private String i18n(String s){
		return messagesRes.getString(s);
	}
	/**
	 * Create the dialog.
	 * @param messagesRes 
	 */
	public editNode(boolean newline, ResourceBundle messagesRes) {
		
		//semp = new Semaphore(1);
		 /* try {
			semp.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		this.messagesRes=messagesRes;
		setTitle(i18n("editNode.inputNodeInfo"));
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
			((AbstractDocument) editorPane.getDocument()).setDocumentFilter(new DocumentFilter() {
	            @Override
	            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
	            		throws BadLocationException {
	            	if(!newline)
	            		fb.insertString(offset, string.replaceAll("\\n", ""), attr);
	            	else
	            		super.insertString(fb, offset, string, attr);
	            	
	            }
	            @Override
	           public void replace(FilterBypass fb, int offset, int length, String string, 
	        		   AttributeSet attr) throws BadLocationException {
	            	if(!newline){
	            		string = string.replaceAll("\\n", "");
	                    //TODO must do something here
	                    super.replace(fb, offset, length, string, attr);
	            	}
	            	else{
	            		super.replace(fb, offset, length, string, attr);
	            	}
	            }
	        });
			contentPanel.add(editorPane);
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
					public void actionPerformed(ActionEvent arg0) {
						editorPane.setText("");
						dispose();
					}
				});
				//cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	

}
