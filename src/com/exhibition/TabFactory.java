package com.exhibition;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

public class TabFactory implements TabContentFactory {  
    private Context con;  
    public TabFactory(Context c){  
        con=c.getApplicationContext();  
    }  
    @Override  
    public View createTabContent(String arg0) {  
        TextView text=new TextView(con);  
        text.setText(arg0);  
        return text;  
    }  
}  

