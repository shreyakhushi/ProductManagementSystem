import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class ProductManagement {
    private JPanel Main;
    private JTextField txtName;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField txtPrice;
    private JTextField txtpid;
    private JTextField txtQty;
    private JButton searchButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ProductManagement");
        frame.setContentPane(new ProductManagement().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;
    public ProductManagement() {
        Connect();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name,price,qty;

                name = txtName.getText();
                price = txtPrice.getText();
                qty = txtQty.getText();

                try {
                    pst = con.prepareStatement("insert into productsquery(Name,Price,Quantity)values(?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Added!");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String pid = txtpid.getText();
                    pst = con.prepareStatement("select Name,Price,Quantity from productsquery where pid = ?");
                    pst.setString(1, pid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);

                        txtName.setText(name);
                        txtPrice.setText(price);
                        txtQty.setText(qty);
                    }
                    else
                    {
                        txtName.setText("");
                        txtPrice.setText("");
                        txtQty.setText("");
                        JOptionPane.showMessageDialog(null,"Product ID NOT Found");

                    }
                }

                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid,name,price,qty;

                name = txtName.getText();
                price = txtPrice.getText();
                qty = txtQty.getText();
                pid = txtpid.getText();

                try {

                    pst = con.prepareStatement("update productsquery set Name = ?,Price = ?,Quantity = ? where PID = ?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                    txtpid.setText("");
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pId ;

                pId = txtpid.getText();


                try {
                    pst = con.prepareStatement("delete from productsquery  where PID= ?");
                    pst.setString(1, pId );

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                    txtpid.setText("");
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }
        });
    }



    public void Connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/gbproducts", "root","Shreya@06");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }


}