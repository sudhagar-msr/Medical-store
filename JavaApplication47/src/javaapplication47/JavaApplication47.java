package javaapplication47;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

//NODE like structure for each tablet
class One_Tablet{
    TextField t3,t4,t5,t6,t7,t8;
    One_Tablet(){
        t3=new TextField();
        t4=new TextField();
        t5=new TextField();
        t6=new TextField();
        t7=new TextField();
        t8=new TextField();
    }
    void addto(Frame f,int y){
        t3.setBounds(10,y,240,30);
        t4.setBounds(250,y,250,30);
        t5.setBounds(500,y,200,30);
        t6.setBounds(700,y,250,30);
        t7.setBounds(950,y,200,30);
        t8.setBounds(1150,y,250,30);
        
        f.add(t3);
        f.add(t4);
        f.add(t5);
        f.add(t6);
        f.add(t7);
        f.add(t8);
    }
}

class Operation extends KeyAdapter{
    One_Tablet ob;
    Frame f=new Frame("SKCT MEDICAL SHOP");
    TextField t1,t2;
    Label l1,l2,l3,l4,l5,l6,l7,l8,l10,l11,l12,l13,l14,l15,addeda;
    Button b1,b2,fina,printbill,nextt;
    Font mf=new Font("TimesRoman",Font.BOLD, 18);
    int y=150,dy=150,tablet_count=0,jj=0,ii=0;
    boolean sumflag=true,admin=true;
    SimpleDateFormat dob=new SimpleDateFormat("dd/MM/yyyy");
    Date newdate=new Date();
    ArrayList<One_Tablet> tb=new ArrayList<>();
    ArrayList<Integer> alist=new ArrayList<>();
    ArrayList<Integer> product_id=new ArrayList<>();
    ArrayList<Integer> sold_quantity=new ArrayList<>();
  
  //Funvtion to get string as parameter and return the same as integer
    public int atoi(String s){
        int num=0;
        for(int i=0;i<s.length();i++){
            num=(num*10)+(s.charAt(i)-'0');
        }
        return num;
    }
  //after integrtion, isAdmin of login module will return the boolen value(for reference)
    public boolean isAdmin() {
        return admin;
    }
  //returns the instance of onetablet class(new row)
    One_Tablet getNewTablet(){
        One_Tablet ob=new One_Tablet();
        y=y+30;
        ob.addto(f, y);
        return ob;      
    }
  //adding calculate properties to frame such as grandtotal,gst,etc.,
    void addCalculateProperties(){
        double sum=0;
        for(int i: alist){
            sum=sum+i;
        }
        double ddd=((sum*5)/100);
        double dfin=ddd+sum;
        l10=new Label(Double.toString(sum));
        l13=new Label("5%");                
        l15=new Label(Double.toString(dfin));
        l15.setFont(mf);
        
        f.add(l10);
        f.add(l11);
        f.add(l12);
        f.add(l14);
        f.add(l13);
        f.add(l15);
        
        l11.setBounds(900, y+60, 100, 30);
        l10.setBounds(1200, y+60, 100, 30);
        l13.setBounds(1200, y+90, 100, 30);
        l12.setBounds(900, y+90, 100, 30);
        l15.setBounds(1200, y+120, 100, 30);                
        l14.setBounds(900, y+120, 170, 30);
    }
  // to remove calculate properties such as grandtotal,gst,etc.,
    void removeCalculateProperties(Frame f){
                f.remove(l10);
                f.remove(l11);
                f.remove(l12);
                f.remove(l13);
                f.remove(l14);
                f.remove(l15);
    }
  //function to check the availability of stock before purchase
    boolean isQuantityAvailable(int tidnum,int tq) throws Exception{
        Connection con=DriverManager.getConnection("jdbc:sqlite:C://sqlite//samplestock.db");
        Statement stm=con.createStatement();
        String sp="select tcount from tquantity where tid="+Integer.toString(tidnum);
        ResultSet rs = stm.executeQuery(sp);
        int x=0;
        if(rs.next()){
            x=rs.getInt(1);
        }
        if(x<tq){
            JOptionPane.showMessageDialog(null,"only "+x+" available");
            return false;       //if stock wasnt available
        }
        con.close();
        return true;
    }
  //function to validate the entered data and to fill the remaining fields from db
    void displayTable(One_Tablet ob) throws Exception{
        Connection con=DriverManager.getConnection("jdbc:sqlite:C://sqlite//samplestock.db");
        int mmm=atoi(ob.t6.getText()),nnn=0;
    //check for empty row
        if(ob.t3.getText().length()==0&&ob.t4.getText().length()==0&&ob.t5.getText().length()==0&&ob.t6.getText().length()==0&&ob.t7.getText().length()==0){}
    //checking whether tid or tname has been given,as nothing could done without either of these fields
        else if((ob.t3.getText().length()==0&&ob.t4.getText().length()==0)){
            JOptionPane.showMessageDialog(null,"Kindly enter data");
            ob.t6.setText("~");
            return;
        }
    //checking whether quantity has been entered
        else if(ob.t6.getText().length()==0||ob.t6.getText().equals(" ")){
            JOptionPane.showMessageDialog(null,"Kindly enter quantity");
            ob.t6.setText("~");
            return;
        } 
        if(((ob.t3.getText().length()==0||ob.t3.getText().equals(" ")||ob.t3.getText().equals("  "))&&ob.t4.getText().length()>0)||(ob.t3.getText().length()>0&&ob.t4.getText().length()>0)){
    //using tid provided, getting data from db
            try(PreparedStatement psmt=con.prepareStatement("select * from tablets where tid=?");){
                psmt.setString(1,ob.t4.getText());
                ResultSet rs=psmt.executeQuery();
                if(rs.next()){
                    char ch[]=rs.getString("tname").toCharArray();
                    ch[0]=(char)(ch[0]-32);
                    for(int i=0;i<ch.length;i++){
                        if(ch[i]=='_'){
                            ch[i]=' ';
                        }
                    }
                    product_id.add(rs.getInt("tid"));
                    sold_quantity.add(atoi(ob.t6.getText()));
                    if(isQuantityAvailable(rs.getInt("tid"),atoi(ob.t6.getText()))){
                        ob.t5.setText(rs.getString("ttype"));
                        ob.t3.setText(new String(ch));
                        ob.t7.setText(Integer.toString(rs.getInt("tsellingprice")));
                        nnn=rs.getInt("tsellingprice");
                    }
                    else{
                        ob.t6.setText("~");
                        return;
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Kindly enter valid data");
                    ob.t6.setText("~");
                    return;
                }
                con.close();
            }
            catch(SQLException eee){
                System.out.print("caught in catch --> "+eee);
            } catch (Exception ex) {}
        }
        else if((ob.t4.getText().length()==0||ob.t4.getText().equals(" ")||ob.t4.getText().equals("  "))&&ob.t3.getText().length()>0){
    //using tname provided, getting data from db
            try(PreparedStatement psmt=con.prepareStatement("select * from tablets where tname=?");){
                char ch[]=ob.t3.getText().toCharArray();        //coverting enterd totanem to db uderstoodable tname(spaces to underscore)
                for(int i=0;i<ch.length;i++){
                    if(ch[i]==' '){
                        ch[i]='_';
                    }
                }
                psmt.setString(1,new String(ch).toLowerCase());
                ResultSet rs=psmt.executeQuery(); 
                if(rs.next()){
                    if(isQuantityAvailable(rs.getInt("tid"),atoi(ob.t6.getText()))){    
                    //checking for quantity ant then inserting the desired values to textfields
                        ob.t4.setText(Integer.toString(rs.getInt("tid")));
                        ob.t5.setText(rs.getString("ttype"));
                        ob.t7.setText(Integer.toString(rs.getInt("tsellingprice")));
                        nnn=rs.getInt("tsellingprice");
                        product_id.add(rs.getInt("tid"));
                        sold_quantity.add(atoi(ob.t6.getText()));
                    }
                    else{
                    //if quantity isnt available
                        ob.t6.setText("~");
                        return;
                    }
                }
                else{
                //if entered tname itself is not available then data must be wrong or not in stock
                    JOptionPane.showMessageDialog(null,"Kindly enter valid data");
                    ob.t6.setText("~");
                    return;
                }
                con.close();
            }
            catch(SQLException eee){
                System.out.print("caught in catch --> "+eee);
            } catch (Exception ex) {
                System.out.print("caught in catch --> "+ex);
            }
        }
    //setting total value and adding the same for grand total
        ob.t8.setText(Integer.toString(mmm*nnn));
        alist.add(mmm*nnn);
        if(ob.t3.getText().length()>0||ob.t4.getText().length()>0){
    // if total calculated, the row cant be edited
            ob.t3.setEditable(false);
            ob.t4.setEditable(false);
            ob.t5.setEditable(false);
            ob.t6.setEditable(false);
            ob.t7.setEditable(false);
            ob.t8.setEditable(false);
        }
    }
    
    void updateQuantity(String s,int q) throws Exception{
        Connection con=DriverManager.getConnection("jdbc:sqlite:C://sqlite//samplestock.db");
        Statement stm=con.createStatement();
        String sp="select * from tquantity where tid="+s;
        ResultSet rs1 = stm.executeQuery(sp);
        int myMaxId=0,soldcount=0;
        if(rs1.next()){
            myMaxId=rs1.getInt("tcount");
            soldcount=rs1.getInt("count");
        }
        System.out.println("before quantity change --> "+myMaxId);
        //for updaing quantity after purchase
        try(PreparedStatement psmt1=con.prepareStatement("update tquantity set tcount=? where tid=?");){
            psmt1.setString(1,Integer.toString(myMaxId-q));
            psmt1.setString(2,s);
            psmt1.executeUpdate();
        }
        catch(Exception eeeee){System.out.println("caught in update quant tcount "+eeeee);}
        //for review of products(number of tablets sold)
        try(PreparedStatement psmt1=con.prepareStatement("update tquantity set count=? where tid=?");){
            psmt1.setString(1,Integer.toString(soldcount+q));
            psmt1.setString(2,s);
            psmt1.executeUpdate();
            con.close();
        }
        catch(Exception eeeee){System.out.println("caught in update quant count "+eeeee);}
        
    }
    public boolean checkForCustomer(String s){
        
    // checking whether the customer number is already registered in db
        try(Connection con=DriverManager.getConnection("jdbc:sqlite:C://sqlite//samplestock.db");
            PreparedStatement psmt=con.prepareStatement("select * from customers where cphone=?");){
            psmt.setString(1,s);
            ResultSet rs=psmt.executeQuery();
            if(rs.next()){
                return true;
            }
            con.close();
        }
        catch(Exception eeeee){
            eeeee.printStackTrace();
        }
        return false;
    }
    void addaa(One_Tablet ob){
        System.out.println("Entered adaaaa");
        ob.t6.addKeyListener(this);
    }
    
    int getCustomerId() throws Exception{
        Connection con=DriverManager.getConnection("jdbc:sqlite:C://sqlite//samplestock.db");
    //getting customer id via db
        Statement stm=con.createStatement();
        ResultSet rs = stm.executeQuery("select max(cid) from customers");
        rs. next();
        int myMaxId = rs. getInt(1);
        con.close();
    //returning customer id
        return myMaxId;
    }
                
    void demo() throws Exception{
    //db connect
        Class.forName("org.sqlite.JDBC");
        Connection con=DriverManager.getConnection("jdbc:sqlite:C://sqlite//samplestock.db");
    
    //for menu bar
        DemoMenu obj = new DemoMenu(f);
    
    //labels for table headers   
        l1=new Label("customer phone");
        l2=new Label("customer name ");
        l3=new Label("PRODUCT NAME");
        l4=new Label("PRODUCT ID");
        l5=new Label("TYPE");
        l6=new Label("QUANTITY");
        l7=new Label("PRICE");        
        l8=new Label("TOTAL");
        l10=new Label("total_amount");
        l11=new Label("TOTAL AMOUNT  :");
        l12=new Label("GST           :");
        l13=new Label("gst amount: ");
        l14=new Label("GRAND TOTAL   :");
        l15=new Label("grand total ");
        l14.setFont(Font.getFont(Font.SANS_SERIF));
        Label dmy=new Label(dob.format(newdate));
    
    //for initial_row
        ob=getNewTablet();
        tb.add(ob);  
        addaa(ob);
        
    //font for grand total
        dmy.setFont(mf );
        l14.setFont(mf);
        
      
       nextt=new Button("Next");
       nextt.setEnabled(false);
       nextt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Entered actionLietener");
                for(int i=ii;i<=jj;i++){
                    f.remove(tb.get(i).t3);
                    f.remove(tb.get(i).t4);
                    f.remove(tb.get(i).t5);
                    f.remove(tb.get(i).t6);
                    f.remove(tb.get(i).t7);
                    f.remove(tb.get(i).t8);
                }
                y=dy;
                ii=jj+1;
                ob=getNewTablet();
                tb.add(ob);
                try {
                           displayTable(ob);
                       } catch (Exception ex) {
                           Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
                       }
                addaa(ob);
                jj++;
                System.out.println("COME BACK AFTER ADDAA");
            }
        });
       
       
       f.add(nextt);       
       nextt.setBounds(400, 640, 100, 30);
    //label for customer phone
       t1=new TextField();
    //buttons for customer details submissions
       b1=new Button("SUBMIT");
    //
       
    // final calculate button
        fina=new Button("CALCULATE");
        fina.setEnabled(false);
    //to update quantity and to print bill 
        printbill=new Button("PRINT BILL");
        printbill.setEnabled(false);
    
      //actionlistener for calculate button    
        b1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               
            //regex for checking valid phone number
                if(!(t1.getText().matches("[0-9]{10}"))){
                    JOptionPane.showMessageDialog(null,"Enter valid phone number");
                    return;
                }
                try {
                    //cheking customer number is already registered
                    if(checkForCustomer(t1.getText())){
                        fina.setEnabled(true);
                        t2=new TextField();
                        //display name for old customer from db
                        try(PreparedStatement psmt=con.prepareStatement("select * from customers where cphone=?");){
                            psmt.setString(1,t1.getText());
                            ResultSet rs=psmt.executeQuery();
                            if(rs.next()){
                                t2.setText(rs.getString("cname"));
                                t2.setEditable(false);
                            }
                        }
                        catch(Exception eeeee){
                            System.out.print("caught in catch --> "+eeeee);
                        }
                        
                        f.remove(b1);
                        f.add(l2);
                        f.add(t2);
                        
                        l2.setBounds(300,83,100,30);
                        t2.setBounds(400,83,130,30);
                    }
                    else{
                        t2=new TextField();
                        b2=new Button("ADD");
                        
                        f.remove(b1);
                        f.add(l2);
                        f.add(t2);
                        f.add(b2);
                        
                        l2.setBounds(300,83,100,30);
                        t2.setBounds(400,83,130,30);
                        b2.setBounds(550,83,130,30);
                        // action listener for above add button
                        b2.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                fina.setEnabled(true);
                                //adding new customer to db
                                try(PreparedStatement psmt=con.prepareStatement("insert into customers values(?,?,?)");){
                                    psmt.setString(1,t2.getText());
                                    psmt.setString(2,t1.getText());
                                    psmt.setString(3,Integer.toString(getCustomerId()+1));
                                    psmt.executeUpdate();
                                    addeda=new Label("Added Successfully");
                                }
                                catch(Exception eeeee){
                                    addeda=new Label("couldn't add");
                                    System.out.println("Exception --> "+eeeee);
                                }
                                f.add(addeda);
                                f.remove(b2);
                                addeda.setBounds(550,83,130,30);
                            }
                        });
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       
        
     //actionlistener for PREVIOUS CUSTOMER BUTTON
       
     
    //action listener for calculate button 
       fina.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCalculateProperties(f);
                
                
                try {                
                    displayTable(ob);
                } catch (Exception ex) {
                    Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(ob.t6.getText().equals("~")){
                    ob.t6.setText("");
                    return;
                }
                if(sumflag){
                    sumflag=!sumflag;
                }                
                printbill.setEnabled(true);
            // adds total, gst grandtotl and output labels to frame
                addCalculateProperties();
            }
        });
    
        
        printbill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fina.setEnabled(false);
                printbill.setEnabled(false);
                nextt.setEnabled(false);
                System.out.println("");
                
                try(PreparedStatement psmt=con.prepareStatement("insert into customerhistory values(?,?,?,?,?)");){
                    for(int i=0;i<alist.size();i++){
                        System.out.println("tid--> "+product_id.get(i)+" quantity--> "+sold_quantity.get(i)+" total--> "+alist.get(i));
                        
                        updateQuantity( Integer.toString(product_id.get(i)),sold_quantity.get(i));                        
                        psmt.setString(1,Integer.toString(product_id.get(i)));
                        psmt.setString(2,Integer.toString(sold_quantity.get(i)));
                        psmt.setString(3,Integer.toString(alist.get(i)));
                        psmt.setString(4,dob.format(newdate));
                        psmt.setString(5,t1.getText());
                        psmt.executeUpdate();
                    } 
                    con.close();
                }
                catch(Exception eeeee){
                   addeda=new Label("couldn't add");
                }
                
                
            }
        });
        
    //background color for head of billing table
        l3.setBackground(Color.yellow);
        l4.setBackground(Color.CYAN);
        l5.setBackground(Color.yellow);
        l6.setBackground(Color.CYAN);
        l7.setBackground(Color.yellow);
        l8.setBackground(Color.CYAN);
        
    //adding components to frame
        f.add(l3);
        f.add(l4);
        f.add(l5);
        f.add(l6);
        f.add(l7);
        f.add(l8);    
        f.add(b1);
        f.add(l1);
        f.add(t1);
        f.add(dmy);
        f.add(fina);
        f.add(printbill);
        
    // setting bounds for all components        
        l1.setBounds(30,83,100,30);
        t1.setBounds(130,83,130,30);
        b1.setBounds(266,83,130,30);
        l3.setBounds(10,150,240,30);
        l4.setBounds(250,150,250,30);
        l5.setBounds(500,150,200,30);
        l6.setBounds(700,150,250,30);
        l7.setBounds(950,150,200,30);
        l8.setBounds(1150,150,250,30);
        dmy.setBounds(700, 83, 100, 30);
        fina.setBounds(700, y+60, 130, 30);
        printbill.setBounds(700, y+120, 130, 30);
        
      // window closing event     
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
                System.exit(0);
            }
        });
        
    //frame properties
        f.setLayout(null);
        f.setResizable(true);
        f.setVisible(true);
        f.setSize(1365, 727);
    }

    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==10){
            if(tablet_count==12){
                tablet_count=0;
                nextt.setEnabled(true);
                try {
                    displayTable(ob);
                } catch (Exception ex) {
                    Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
                }
                ob.t6.removeKeyListener(this);
            }
            else{
            tablet_count++;
            if(sumflag){
                try {
                           displayTable(ob);
                       } catch (Exception ex) {
                           Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
                       }
                    if(ob.t6.getText().equals("~")){
                        ob.t6.setText("");
                        return;
                    }
                }
                else{
                    sumflag=true;
                }
                //creating new row
                ob=getNewTablet();
                
                jj++;
                if(jj==10){
                    nextt.setEnabled(true);
                }
                tb.add(ob);
                ob.t6.addKeyListener(this);
                fina.setBounds(700, y+60, 130, 30);
                printbill.setBounds(700, y+120, 130, 30);
                printbill.setEnabled(false);
                removeCalculateProperties(f);
            }
               System.out.print("e--> "+e.getKeyCode());
        }
    }
}

public class JavaApplication47 {
    public static void main(String[] args) throws Exception{
        new Operation().demo();
    }    
}