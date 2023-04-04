import java.sql.*;

public class DBConnect{
    private Connection conn;
    private Statement st;
    private String url = "jdbc:mysql://localhost:3306/projectclass";
    private String utf_8 = "?useUnicode=true&characterEncoding=UTE-8";
    private String URL = "jdbc:mysql://localhost:3306/JCD_PROJECT" + utf_8;
    private String user = "root";
    private String password = "12345678";

    public ResultSet getResult(String query){
        ResultSet rs;
        try {
                conn = DriverManager.getConnection(url, user, password);
                st = conn.createStatement();
                rs = st.executeQuery(query);
            }catch (Exception ex) {
            rs = null;
        }
        return rs;
}
public boolean execute(String query){
    boolean rs;
    try{
        conn = DriverManager.getConnection(url, user, password);
        st = conn.createStatement();
        st.execute(query);
        rs = true;
    }catch(Exception ex){
        rs = false;
    }
    return rs;
}
public void close() {
    try{
        conn.close();
    }catch(Exception ex){
        //Not doing.
    }
}
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaulTableModel;
public class FrmList extends javax.swing.JFrame {
    public FrmList(){
        initComponents();
        setTable();
    }
}
    private void setTable(){
        String[]columnname={"#","PersonID","PersonName","PersonTel","Email"};
        DefaulTableModel model = new DefaulTableModel(columnname,0);
        try{
            String kw = this.jTextField1.getText();
            String query = "SELECT* FROM person WHERE pro_id ='"+kw+"' OR pro_name LIKE'%" +kw+ "&'";
            DBConnect conn = new DBConnect();
            ResultSet rs = conn.getResult(query);
            int i = 0;
            while(rs.next()){
                i++;
                String num = String.valueOF(i);
                String pro_id = rs.getString("pro_id");
                String pro_name = rs.getString("pro_name");
                String pro_tel = rs.getString("pro_tel");
                String pro_mail = rs.getString("pro_mail");
                String[] row = {num,pro_id,pro_name,pro_tel,pro_mail};
                model.addRow(row);
            }
            conn.close();
        }catch(Exception ex)
            JOptionPane.showMessageDialog(this,"Error : "+ex);
    }
    this.jTable.setModel(model);
@SuppressWarnings("unchecked")

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt){
    setTable();
}
private void jButton2ActionPerformed(java.awt.event.ActionEvent evt){
    this.jTextFieldl.setText(null);
    setTable();
}
private void jButton3ActionPerformed(java.awt.event.ActionEvent evt){
    FrmSql fs = new FrmSql(this,true);
    fs.setFrm();
    setTable();
}
private void jButton4ActionPerformed(java.awt.event.ActionEvent evt){
    try{
        String pro_id = this.jTable.getValueAt(this.jTable.gatSelactedRow(), 1).toString();
        FrmSql fs = new FrmSql(this,true);
        fs.setFrm(pro_id);
        setTable();
    }catch(Exception ex){
        JOptionPane.showMessageDialog(this,"Error : "+ex);
    }
}
private void jButton5ActionPerformed(java.awt.event.ActionEvent evt){
    try{
        String pro_id = this.jTable.getValueAt(this.jTable.gatSelactedRow(), 1).toString();
        String query = String.format("DELETE FROM person WHERE pro_id ='%s",pro_id);
        DBConnect conn = new DBConnect();
        boolean rs = conn.execute(query);
        if(rs){
            JOptionPane.showMessageDialog(this,"การลบข้อมูลเสร็จสิ้น");
        }else{
            JOptionPane.showMessageDialog(this,"การข้อผิดพลาดในการลบข้อมูล");
        }
    }catch(Exception ex){
        JOptionPane.showMessageDialog(this,"Error :"+ex);
    }
}
public static void main(String args[]) {
    java.awt.EventQueue.inivokeLater(new Runnerbble()) {
    public void run() {
        new FrmList().setVisible(true);
    }
    }
}

import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class FrmSql extends javax.swing.JDialog {
    private String pro_id;
    private String pro_name;
    private String pro_tel;
    private String pro_mail;
    private String otel;

    public FrmSql(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void setFrm(){
        otel = null;
        this.setVisible(true);
    }
}
public void setFrm(String prol){
    try{
        String query = String.format("SELECT* FROM person WHERE pro_id = '%s",prol);
        DBConnect conn =  new DBConnect();
        ResultSet rs = conn.getResult(query);
        rs.next();
        otel=rs.getString("pro_id");
        this.jTextField1.setText(rs.getString("pro_id"));
        this.jTextField2.setText(rs.getString("pro_name"));
        this.jTextField3.setText(rs.getString("pro_tel"));
        this.jTextField4.setText(rs.getString("pro_mail"));
        conn.close();
    }catch(Exception ex){
        JOptionPane.showMessageDialog(this,"Error :"+ex);
        otel = null;
    }
    this.setVisible(true);
}
private void jButton2ActionPerformed(java.awt.event.ActionEvent evt){
    this.dispose();
}
private void jButton1ActionPerformed(java.awt.event.ActionEvent evt){
    pro_id = this.jTextField1.getText();
    pro_name = this.jTextField2.getText();
    pro_tel = this.jTextField3.getText();
    pro_mail = this.jTextField4.getText();
    String query;
    boolean rs;
    if(otel==null) {
        query = String.format("INSERT INTO person(pro_id,pro_name,pro_tel,pro_mail"
         + "VALUES('%1$s','%2$s','%3$s','%4$s')",pro_id,pro_name,pro_tel,pro_mail);
    }else{
        query = String.format("UPDATE person SET pro_id ='%1$s',pro_name = '%2$s',"
        + "pro_tel = '%3$s', pro_mail = '%4$s'"
        + "WHERE pro_id = '%5$s'",pro_id,pro_name,pro_tel,pro_mail,otel);
    }try{
        DBConnect conn = new DBConnect();
        rs = conn.execute(query);
        if(rs){
            JOptionPane.showMessageDialog(this,"การบันทึกข้อมูลเสร็จสิ้น");
            otel = pro_id;
        }else{
            JOptionPane.showMessageDialog(this,"การบันทึกข้อมูลผิดพลาด");
        }
        conn.close();
    }catch(Exception ex){
        JOptionPane.showMessageDialog(this,"Error :"+ex);
    }
}
