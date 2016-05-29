/*
 * 
 */
package org.liukan.mgraph.util;

import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JLabel;

// TODO: Auto-generated Javadoc
/**
 * The Class strUtil.
 */
public class strUtil {
	
	/**
	 * Treat string.
	 *
	 * @param str the str
	 * @param nodeFontSize the node font size
	 * @return the str parts
	 */
	public static strParts treatString(String str,int nodeFontSize){
		strParts sp = null;
		String[] parts;
		Font f = new Font("SimSun", Font.PLAIN, nodeFontSize);
		JLabel vertexRenderer = new JLabel();		
		FontMetrics fm = vertexRenderer.getFontMetrics(f);
		int h = fm.getHeight(); 
		if (str.contains("\n")) {
		    // Split it.
			parts = str.split("\n");
			
			int maxlen=0;
			int tmp=0;
			for(String strpart:parts){
				tmp=fm.stringWidth(strpart);
				if(tmp>maxlen)
					maxlen=tmp;
			}
			h=(int) (h*parts.length*1.0);
			sp=new strParts(maxlen,h,parts);
			//System.out.println(tmp+","+h+','+parts);
			
		} else {
			
			h=(int) (h*1.0);
			int tmp=fm.stringWidth(str);
			sp=new strParts(tmp,h,new String[]{str});
			//System.out.println(tmp+","+h+','+str);
		}
		return sp;
		
	}

}
