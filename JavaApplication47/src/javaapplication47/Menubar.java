package javaapplication47;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

class DemoMenu
{
    Menu bill,stock,customer,employee,supplier,help;
    MenuItem newBill,addProducts,viewStock,viewCustomer,addEmployee,deleteEmployee,viewEmployee,updateEmployee,addSupplier,viewSupplier,viewHistory,contact;
    DemoMenu(Frame f)
    {
        newBill = new MenuItem("New");
        addProducts = new MenuItem("Add/Update");
        viewStock = new MenuItem("View");
        viewCustomer = new MenuItem("View");
        addEmployee = new MenuItem("Add");
        deleteEmployee = new MenuItem("Delete");
        viewEmployee = new MenuItem("View");
        updateEmployee = new MenuItem("Update");
        addSupplier = new MenuItem("Add");
        viewSupplier = new MenuItem("View");
        viewHistory = new MenuItem("History");
        contact = new MenuItem("Contact Admin");
       
       
        bill = new Menu("Bill");
        stock = new Menu("Stock");
        customer = new Menu("Customer");
        employee = new Menu("Employee");
        supplier = new Menu("Supplier");
        help = new Menu("Help");
       
       
        bill.add(newBill);
        stock.add(addProducts);
        stock.add(viewStock);
        customer.add(viewCustomer);
        employee.add(addEmployee);
        employee.add(deleteEmployee);
        employee.add(viewEmployee);
        employee.add(updateEmployee);
        supplier.add(addSupplier);
        supplier.add(viewSupplier);
        supplier.add(viewHistory);
        
        help.add(contact);
       
       
        MenuBar mb = new MenuBar();
        mb.add(bill);
        mb.add(stock);
        mb.add(customer);
        mb.add(employee);
        mb.add(supplier);
        mb.add(help);
       
        f.setMenuBar(mb);
        newBill.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)  {
                try{
                    f.dispose();
                    new Operation().demo();
                }
                catch(Exception eeeee){}
            }
        });
        contact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(null,"To contact admin : \n \n 19tuit113@skct.edu.in \n 19tuit137@skct.edu.in \n 19tuit138@skct.edu.in \n 19tuit150@skct.edu.in  \n 19tuit155@skct.edu.in \n");
            }
        });
        viewCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                f.dispose();
                try {
                    new Customer().demo();
                } catch (Exception ex) {
                    Logger.getLogger(DemoMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}



