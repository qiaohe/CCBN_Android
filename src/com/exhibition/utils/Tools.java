package com.exhibition.utils;

public class Tools {
	public static String transformToHtml(String data) {
		StringBuilder html = new StringBuilder();  
		html.append("<html>");  
		html.append("<body>");   
		html.append(data);   
		html.append("</body>");
		html.append("</html>");
		return html.toString();
	}   
}
