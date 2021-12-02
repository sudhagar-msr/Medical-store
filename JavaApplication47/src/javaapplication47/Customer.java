package javaapplication47;

import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Customer {
    Frame f=new Frame("SKCT MEDICAL SHOP");
    TextField t1,t2,t3,t4,pnt,pqt,ptt,pdt;
    Label l1,l2,l3,l4,l5,l6,pn,pq,pt,pn1,pd,custname;
    Button b1,b2,b3,b4,b5,clearb;
    TextField a[][];
    TextField a2[][];
    int y1=190,dy1=190,j=0;
    long pno;
    ArrayList<Integer> alist;
    ArrayList<Integer> sold_quantity;
    ArrayList<String> product_name;
    ArrayList<String> date_of_purchase;
    void setOfRows(){
        y1=dy1;
        for(int i=0;i<15;i++){
            ptt=new TextField();
            pnt=new TextField();
            pqt=new TextField();
            if(j>=alist.size()){
                break;
            }
            char ch[]=product_name.get(j).toCharArray();
            ch[0]=(char)(ch[0]-32);
            for(int l=0;l<ch.length;l++){
                if(ch[l]=='_'){
                    ch[l]=' ';
                }
            }
            addTextBox(i,ch);
            y1=y1+30;
            j++;
        }
    }
    void addTextBox(int i,char ch[]){
        ptt.setText(new String(ch));
        pnt.setText(Integer.toString(sold_quantity.get(j)));
        pqt.setText(Integer.toString(alist.get(j)));
        
        ptt.setBounds(100, y1, 200, 30);
        pnt.setBounds(300,y1,100,30);
        pqt.setBounds(400,y1,80,30);
        f.add(ptt);
        f.add(pnt);
        f.add(pqt);
        a[i][0]=ptt;
        a[i][1]=pqt;
        a[i][2]=pnt;
    }
    void setOfRows2(){
        y1=dy1;
        for(int i=0;i<15;i++){
            ptt=new TextField();
            pnt=new TextField();
            pqt=new TextField();
            pdt=new TextField();
            if(j>=alist.size()){
                break;
            }
            char ch[]=product_name.get(j).toCharArray();
            ch[0]=(char)(ch[0]-32);
            for(int l=0;l<ch.length;l++){
                if(ch[l]=='_'){
                    ch[l]=' ';
                }
            }
            addTextBox2(i,ch);
            y1=y1+30;
            j++;
        }
    }
    void addTextBox2(int i,char ch[]){
        ptt.setText(new String(ch));
        pnt.setText(Integer.toString(sold_quantity.get(j)));
        pqt.setText(Integer.toString(alist.get(j)));
        pdt.setText(date_of_purchase.get(j));
        
        pdt.setBounds(100, y1, 100, 30);
        ptt.setBounds(200, y1, 200, 30);
        pnt.setBounds(400,y1,100,30);
        pqt.setBounds(500,y1,80,30);
        
        f.add(pdt);
        f.add(ptt);
        f.add(pnt);
        f.add(pqt);
        
        a2[i][0]=ptt;
        a2[i][1]=pqt;
        a2[i][2]=pnt;
        a2[i][3]=pdt;
    }
    void addMainComponents(){
         
        b2.setEnabled(false);
        l1.setBounds(30,83,100,30);
        t1.setBounds(130,83,130,30);
        b1.setBounds(266,83,130,30);
        l2.setBounds(410,83,100,30);
        t2.setBounds(510,83,130,30);
        b2.setBounds(640,83,130,30);
        clearb.setBounds(1140,83,130,30);
        
        f.add(l1);
        f.add(t1);
        f.add(b1);
        f.add(l2);
        f.add(b2);
        f.add(t2);
        f.add(clearb);
        
        a=new TextField[15][3];
        a2=new TextField[15][4];
        alist=new ArrayList<>();
        sold_quantity=new ArrayList<>();
        product_name=new ArrayList<>();
        date_of_purchase=new ArrayList<>();
    }
    public long atoi(String s){
        long num=0;
        for(int i=0;i<s.length();i++){
            num=(num*10)+(s.charAt(i)-'0');
        }
        return num;
    }
    void demo() throws Exception{
        DemoMenu obj=new DemoMenu(f);
        //for db connectionctivity
        Connection con=DriverManager.getConnection("jdbc:sqlite:C://sqlite//samplestock.db");
        
        l1=new Label("Enter Phone");
        t1=new TextField();
        b1=new Button("SEARCH");
        l2=new Label("Enter Date :");
        t2=new TextField();
        b2=new Button("SEARCH");
        clearb=new Button("CLEAR");
        addMainComponents();
        clearb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                f.removeAll();
                addMainComponents();
                y1=190;dy1=190;j=0;
                t1.setText(Long.toString(pno));
                b1.setEnabled(true);
            }
        });
       
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!t1.getText().matches("[0-9]{10}")){
                    JOptionPane.showMessageDialog(null,"Enter valid phone number");
                    return;
                }
                pno=atoi(t1.getText());
                b3=new Button("Last Transaction");
                b4=new Button("All Transactions");
                b2.setEnabled(true);
                f.add(b3);
                f.add(b4);
                b3.setBounds(30, 127, 130, 30);
                b4.setBounds(180, 127, 130, 30);
                try {
                    Statement stm=con.createStatement();
                    ResultSet rs = stm.executeQuery("select cname from customers where cphone="+t1.getText());
                    rs.next();
                    Font mf=new Font("TimesRoman",Font.BOLD, 18);
                    custname=new Label("CUSTOMER NAME: "+rs.getString(1).toUpperCase());
                    custname.setBounds(820,83,400,30);
                    f.add(custname);
                    custname.setFont(mf);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                b3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        b1.setEnabled(false);
                        b4.setEnabled(false);
                        b2.setEnabled(false);
                        pn=new Label("PRODUCT NAME ");
                        pq=new Label("QUANTITY");
                        pt=new Label("TOTAL");
                        f.add(pn);
                        f.add(pq);
                        f.add(pt);
                        pn.setBounds(100,160,200,30);
                        pq.setBounds(300,160,100,30);
                        pt.setBounds(400,160,80,30);
                        
                        try {
                            System.out.println("entered block");
                            Statement stm=con.createStatement();
                            String sp="select max(date) from customerhistory where cphone="+(t1.getText());
                            ResultSet rs = stm.executeQuery(sp);
                            String ddd = null;
                            if(rs.next()){
                                System.out.println("has max values-->"+rs.getString(1)+" ddd ");
                                ddd=new String(rs.getString(1));
                                System.out.println(ddd);
                            }
                            String sp2=new String("select * from customerhistory where date='"+ddd+"' and cphone="+t1.getText());
                            System.out.println(sp2);
                            ResultSet rs1=stm.executeQuery(sp2);
                            int y=190,dfaulty=190;
                            int count=0;
                            while(rs1.next()&&count<16){
                                System.out.println("eheheh");
                                alist.add(rs1.getInt("total"));
                                sold_quantity.add(rs1.getInt("quantity"));
                                Statement stm3=con.createStatement();
                                String sdd="select tname from tablets where tid="+(Integer.toString(rs1.getInt("tid")));
                                ResultSet rs3=stm3.executeQuery(sdd);
                                rs3.next();
                                product_name.add(rs3.getString(1));                                
                                System.out.println(rs1.getInt("total")+(rs3.getString(1)));                                
                            }
                            SimpleDateFormat dob=new SimpleDateFormat("dd/MM/yyyy");
                            Date newdate=new Date();
                            System.out.println("today date"+dob.format(newdate));
                            Button nextt=new Button("Next");
                            Button prev=new Button("Previous");
                            nextt.setBounds(500,430,100,30);
                            prev.setBounds(500,380,100,30);
                            f.add(nextt);
                            f.add(prev);
                            prev.setEnabled(false);
                            if(alist.size()<=15){
                                nextt.setEnabled(false);
                            }
                            setOfRows();
                            nextt.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent ae) {
                                    prev.setEnabled(true);
                                    if(alist.size()<=15||alist.size()-j<=15){
                                        nextt.setEnabled(false);
                                    }
                                    for(int i=0;i<15;i++){
                                        for(int k=0;k<3;k++){
                                            f.remove(a[i][k]);
                                        }
                                    }
                                    setOfRows();
                                }
                            });
                            prev.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent ae) {
                                    nextt.setEnabled(true);
                                    
                                    int c=j;
                                    if(j<=15){
                                        return;
                                    }
                                    while(c>15){
                                        c=c-15;
                                    }                                    
                                    if(j-15<0){    
                                        j=0;
                                    }
                                    else{
                                        j=j-c-15;
                                    }
                                    
                                    for(int i=0;i<15;i++){
                                        for(int k=0;k<3;k++){
                                            f.remove(a[i][k]);
                                        }
                                    }
                                   setOfRows();
                                  if(j<=15){
                                        prev.setEnabled(false);
                                    }
                                }
                            });
                            
                        } catch (SQLException ex) {
                            System.out.println("caught in catch --> "+ex);
                            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                b4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        b1.setEnabled(false);
                        b3.setEnabled(false);
                        b2.setEnabled(false);
                        pn=new Label("PRODUCT NAME ");
                        pq=new Label("QUANTITY");
                        pt=new Label("TOTAL");
                        pd=new Label("DATE");
                        f.add(pn);
                        f.add(pq);
                        f.add(pt);
                        f.add(pd);
                        pd.setBounds(100,160,100,30);
                        pn.setBounds(200,160,200,30);
                        pq.setBounds(400,160,100,30);
                        pt.setBounds(500,160,80,30);
                        
                        try {
                            System.out.println("entered block");
                            Statement stm=con.createStatement();
                            String sp2=new String("select * from customerhistory where cphone="+t1.getText());
                            System.out.println(sp2);
                            ResultSet rs1=stm.executeQuery(sp2);
                            int y=190,dfaulty=190;
                            while(rs1.next()){
                                alist.add(rs1.getInt("total"));
                                sold_quantity.add(rs1.getInt("quantity"));
                                date_of_purchase.add(rs1.getString("date"));
                                Statement stm3=con.createStatement();
                                String sdd="select tname from tablets where tid="+(Integer.toString(rs1.getInt("tid")));
                                ResultSet rs3=stm3.executeQuery(sdd);
                                rs3.next();
                                product_name.add(rs3.getString(1));                                
                                System.out.println(rs1.getInt("total")+(rs3.getString(1)));                                
                            }
                            SimpleDateFormat dob=new SimpleDateFormat("dd/MM/yyyy");
                            Date newdate=new Date();
                            System.out.println("today date"+dob.format(newdate));
                            Button nextt=new Button("Next");
                            Button prev=new Button("Previous");
                            nextt.setBounds(600,430,100,30);
                            prev.setBounds(600,380,100,30);
                            f.add(nextt);
                            f.add(prev);
                            prev.setEnabled(false);
                            if(alist.size()<=15){
                                nextt.setEnabled(false);
                            }
                            setOfRows2();
                            nextt.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent ae) {
                                    prev.setEnabled(true);
                                    if(alist.size()<=15||alist.size()-j<=15){
                                        nextt.setEnabled(false);
                                    }
                                    for(int i=0;i<15;i++){
                                        for(int k=0;k<4;k++){
                                            f.remove(a2[i][k]);
                                        }
                                    }
                                    setOfRows2();
                                }
                            });
                            prev.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent ae) {
                                    nextt.setEnabled(true);
                                    
                                    int c=j;
                                    if(j<=15){
                                        return;
                                    }
                                    while(c>15){
                                        c=c-15;
                                    }                                    
                                    if(j-15<0){    
                                        j=0;
                                    }
                                    else{
                                        j=j-c-15;
                                    }
                                    
                                    for(int i=0;i<15;i++){
                                        for(int k=0;k<4;k++){
                                            f.remove(a2[i][k]);
                                        }
                                    }
                                   setOfRows2();
                                  if(j<=15){
                                        prev.setEnabled(false);
                                    }
                                }
                            });
                            
                        } catch (SQLException ex) {
                            System.out.println("caught in catch --> "+ex);
                            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        });
        
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                b3.setEnabled(false);
                b4.setEnabled(false);
                    pn=new Label("PRODUCT NAME ");
                    pq=new Label("QUANTITY");
                    pt=new Label("TOTAL");
                    f.add(pn);
                    f.add(pq);
                    f.add(pt);
                    pn.setBounds(100,160,200,30);
                    pq.setBounds(300,160,100,30);
                    pt.setBounds(400,160,80,30);
                    try {
                            System.out.println("entered block");
                            Statement stm=con.createStatement();
                            
                            String sp2=new String("select * from customerhistory where date='"+t2.getText()+"' and cphone="+t1.getText());
                            System.out.println(sp2);
                            ResultSet rs1=stm.executeQuery(sp2);
                            int y=190,dfaulty=190;
                            int count=0;
                            while(rs1.next()&&count<16){
                                System.out.println("eheheh");
                                alist.add(rs1.getInt("total"));
                                sold_quantity.add(rs1.getInt("quantity"));
                                Statement stm3=con.createStatement();
                                String sdd="select tname from tablets where tid="+(Integer.toString(rs1.getInt("tid")));
                                ResultSet rs3=stm3.executeQuery(sdd);
                                rs3.next();
                                product_name.add(rs3.getString(1));                                
                                System.out.println(rs1.getInt("total")+(rs3.getString(1)));                                
                            }
                            SimpleDateFormat dob=new SimpleDateFormat("dd/MM/yyyy");
                            Date newdate=new Date();
                            System.out.println("today date"+dob.format(newdate));
                            Button nextt=new Button("Next");
                            Button prev=new Button("Previous");
                            nextt.setBounds(500,430,100,30);
                            prev.setBounds(500,380,100,30);
                            f.add(nextt);
                            f.add(prev);
                            prev.setEnabled(false);
                            if(alist.size()<=15){
                                nextt.setEnabled(false);
                            }
                            setOfRows();
                            nextt.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent ae) {
                                    prev.enable(true);
                                    if(alist.size()<=15||alist.size()-j<=15){
                                        nextt.setEnabled(false);
                                    }
                                    for(int i=0;i<15;i++){
                                        for(int k=0;k<3;k++){
                                            f.remove(a[i][k]);
                                        }
                                    }
                                    setOfRows();
                                }
                            });
                            prev.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent ae) {
                                    nextt.setEnabled(true);
                                    
                                    int c=j;
                                    if(j<=15){
                                        return;
                                    }
                                    while(c>15){
                                        c=c-15;
                                    }                                    
                                    if(j-15<0){    
                                        j=0;
                                    }
                                    else{
                                        j=j-c-15;
                                    }
                                    
                                    for(int i=0;i<15;i++){
                                        for(int k=0;k<3;k++){
                                            f.remove(a[i][k]);
                                        }
                                    }
                                   setOfRows();
                                  if(j<=15){
                                        prev.setEnabled(false);
                                    }
                                }
                            });
                            
                        } catch (SQLException ex) {
                            System.out.println("caught in catch --> "+ex);
                            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                
            }
        });
        
        
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
                System.exit(0);
            }
        });    
        
    //FRAME PROPERTIES
        f.setLayout(null);
        f.setResizable(true);
        f.setVisible(true);
        f.setSize(1365, 727);
    }
}
