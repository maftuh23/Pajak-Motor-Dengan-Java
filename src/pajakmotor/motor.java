/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pajakmotor;

/**
 *
 * @author PC Live Streaming
 */

import koneksi.koneksi;
import java.sql.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class motor extends javax.swing.JFrame {
    
    //membuat objek    
    private DefaultTableModel model;
    
    //deklarasi variabel
    String nik, pilih_pemilik, plat_nomor, merk, no_rangka, no_mesin, asal, nama_penduduk;
    int tahun;

    /**
     * Creates new form motor
     */
    public motor() {
        initComponents();
        tampil_pemilik(); 
        
        model = new DefaultTableModel();
        
        //memberi nama header pada tabel
        tblmotor.setModel(model);
        model.addColumn("Plat Nomor");
        model.addColumn("NIK");        
        model.addColumn("Merk");
        model.addColumn("Tahun");
        model.addColumn("No Rangka");
        model.addColumn("No Mesin");
        model.addColumn("Asal");
        
        //fungsi ambil data
        getDataMotor();
    }
    
    public void getDataMotor(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            Statement stat = (Statement) koneksi.getKoneksi().createStatement();        
            String sql = "SELECT * FROM motor";
            ResultSet res = stat.executeQuery(sql);
            while(res.next()){
                Object[] obj = new Object[7];
                obj[0]=res.getString("plat_nomor");
                obj[1]=res.getString("nik");
                obj[2]=res.getString("merk");
                obj[3]=res.getString("tahun");
                obj[4]=res.getString("no_rangka");
                obj[5]=res.getString("no_mesin");
                obj[6]=res.getString("asal");
                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
     public void tampil_pemilik(){
        try
        {
            
            Statement stat = (Statement) koneksi.getKoneksi().createStatement(); 
            String sql = "select nik,nama_penduduk from penduduk";  
            ResultSet res = stat.executeQuery(sql);
            while(res.next())
            {
                Object[] ob = new Object[3];
                jComboBox1.addItem(res.getString("nik"));
                //NamaPemilik.setText(res.getString("nama_pendaftar"));
            }
        }
        catch(Exception ex)
        {
            
        }
    }
     
     public void tampil_nama(){
         try {
            Statement stat = (Statement) koneksi.getKoneksi().createStatement(); 
            String sql = "select nama_penduduk from penduduk where nik='"+jComboBox1.getSelectedItem()+"'"; 
            ResultSet res = stat.executeQuery(sql);
            
             while(res.next()){
            Object[] ob = new Object[1];
            ob[0]=  res.getString(1);
            
            NamaPemilik.setText((String) ob[0]);
        }
        res.close(); stat.close();
         }catch(Exception ex){
         }
     }
     

     
    public void loadDataMotor(){
        //Dari textbox
        plat_nomor = PlatNomor.getText();
        nik = (String) jComboBox1.getSelectedItem();
        merk = Merk.getText();
        tahun = Integer.parseInt(Tahun.getText());
        no_rangka = NoRangka.getText();
        no_mesin = NoMesin.getText();
        asal = Asal.getText();
    
    }
    
    public void dataSelect(){
        int i = tblmotor.getSelectedRow();
        if(i == -1){
            return;
        }
        PlatNomor.setText(""+model.getValueAt(i,0));
        jComboBox1.setSelectedItem(""+model.getValueAt(i,1));
        Merk.setText(""+model.getValueAt(i,2));
        Tahun.setText(""+model.getValueAt(i,3));        
        NoRangka.setText(""+model.getValueAt(i,4));
        NoMesin.setText(""+model.getValueAt(i,5));
        Asal.setText(""+model.getValueAt(i,6));
    }
    
    public void reset(){
        plat_nomor = "";
        pilih_pemilik = "- Pilih Pemilik -";
        merk = "";
        no_rangka = "";
        no_mesin = "";
        asal = "";
        
        PlatNomor.setText(plat_nomor);
        jComboBox1.setSelectedItem(pilih_pemilik);
        Merk.setText(merk);
        Tahun.setText("");
        NoRangka.setText(no_rangka);
        NoMesin.setText(no_mesin);
        Asal.setText(asal);
        NamaPemilik.setText("");
    }
    
    public void EditDataMotor(){
        loadDataMotor();
        
        try{
            //uji koneksi
            Statement stat = (Statement) koneksi.getKoneksi().createStatement();
            
            //kirim perintah sql
            String sql = "UPDATE motor SET nik='"+jComboBox1.getSelectedItem()+"',merk='"+merk+"',tahun='"+tahun+"',no_rangka='"+no_rangka+"',no_mesin='"+no_mesin+"',asal='"+asal+"' WHERE plat_nomor = '"+plat_nomor+"'";
            PreparedStatement p =(PreparedStatement)koneksi.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getDataMotor();
            
            //kosongkan data
            reset();
            JOptionPane.showMessageDialog(null, "PERUBAHAN DATA MOTOR BERHASIL");
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void HapusDataMotor(){
         loadDataMotor(); 
        int pesan = JOptionPane.showConfirmDialog(null, "HAPUS DATA"+ plat_nomor +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);
       
        if(pesan == JOptionPane.OK_OPTION){
            try{
                Statement stat = (Statement) koneksi.getKoneksi().createStatement();
                String sql = "DELETE FROM motor WHERE plat_nomor='"+ plat_nomor +"'";
                PreparedStatement p =(PreparedStatement)koneksi.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                
                getDataMotor();
                
                reset();
                JOptionPane.showMessageDialog(null, "DATA MOTOR BERHASIL DIHAPUS");
            }catch(SQLException err){
                JOptionPane.showMessageDialog(null, err.getMessage());
            }
        }
    }
    
    public void SimpanDataMotor(){
        loadDataMotor();
        try{
            Statement stat = (Statement) koneksi.getKoneksi().createStatement();
            String  sql =   "INSERT INTO motor(plat_nomor, nik, merk, tahun, no_rangka, no_mesin, asal)"+"VALUES('"+ plat_nomor +"','"+ jComboBox1.getSelectedItem() +"','"+ 
                              merk +"','"+ tahun +"','"+ no_rangka +"','"+ no_mesin +"','"+ asal +"')";
            PreparedStatement p = (PreparedStatement) koneksi.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            getDataMotor();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        PlatNomor = new javax.swing.JTextField();
        Merk = new javax.swing.JTextField();
        Tahun = new javax.swing.JTextField();
        NoRangka = new javax.swing.JTextField();
        NoMesin = new javax.swing.JTextField();
        Asal = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblmotor = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        NamaPemilik = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Data Motor");

        jLabel3.setText("Nama Pemilik");

        jLabel4.setText("Plat Nomor");

        jLabel5.setText("Merk");

        jLabel6.setText("Tahun");

        jLabel7.setText("No Rangka");

        jLabel8.setText("No Mesin");

        jLabel9.setText("Asal");

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        tblmotor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblmotor);

        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Ubah");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Hapus");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Keluar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        NamaPemilik.setEditable(false);
        NamaPemilik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NamaPemilikActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Pilih Pemilik -" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jButton1)
                        .addGap(29, 29, 29)
                        .addComponent(jButton2)
                        .addGap(32, 32, 32)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(29, 29, 29)
                        .addComponent(jButton5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Merk, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(Asal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                    .addComponent(NoMesin, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(NoRangka, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Tahun, javax.swing.GroupLayout.Alignment.LEADING))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(NamaPemilik, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(PlatNomor))
                        .addGap(143, 143, 143))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(221, 221, 221))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(PlatNomor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(NamaPemilik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(Merk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(Tahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(NoRangka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(NoMesin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(Asal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        tampil_nama();
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SimpanDataMotor();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
        new pendaftaran().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
            dataSelect();        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
            reset(); 
            // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
            EditDataMotor();
            // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
            HapusDataMotor();
            // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void NamaPemilikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NamaPemilikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NamaPemilikActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(motor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(motor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(motor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(motor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new motor().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Asal;
    private javax.swing.JTextField Merk;
    private javax.swing.JTextField NamaPemilik;
    private javax.swing.JTextField NoMesin;
    private javax.swing.JTextField NoRangka;
    private javax.swing.JTextField PlatNomor;
    private javax.swing.JTextField Tahun;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblmotor;
    // End of variables declaration//GEN-END:variables
}
