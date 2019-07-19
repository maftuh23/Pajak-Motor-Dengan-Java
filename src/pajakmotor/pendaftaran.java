/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pajakmotor;

import koneksi.koneksi;
import java.sql.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author PC Live Streaming
 */
public class pendaftaran extends javax.swing.JFrame {
      //membuat objek    
    private DefaultTableModel model;
    
    //deklarasi variabel
    String id_pendaftaran,plat_nomor, nama_penduduk,merk, pilih_plat_nomor,nik ;

    /**
     * Creates new form pendaftaran
     */
    public pendaftaran() {
        initComponents();
        
        nopendaftaran();
        tampil_nik();
        
        model = new DefaultTableModel();
        
        //memberi nama header pada tabel
        tblpendaftaran.setModel(model);
        model.addColumn("ID Pendaftaran");
        model.addColumn("NIK");        
        model.addColumn("Plat Nomor");
        
        //fungsi ambil data
        getDataPendaftaran();
    }
    
    public void getDataPendaftaran(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            Statement stat = (Statement) koneksi.getKoneksi().createStatement();        
            String sql = "SELECT * FROM pendaftaran";
            ResultSet res = stat.executeQuery(sql);
            while(res.next()){
                Object[] obj = new Object[3];
                obj[0]=res.getString("id_pendaftaran");
                obj[1]=res.getString("nik");
                obj[2]=res.getString("plat_nomor");
                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void nopendaftaran()
    {
       try {
            Statement stat = (Statement) koneksi.getKoneksi().createStatement(); 
            String sql = "select id_pendaftaran from pendaftaran";  
            ResultSet res = stat.executeQuery(sql);
            if (res.next()) {
                String nofak = res.getString("id_pendaftaran").substring(1);
                String AN = "" + (Integer.parseInt(nofak) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "000";}
                else if(AN.length()==2)
                {Nol = "00";}
                else if(AN.length()==3)
                {Nol = "0";}
                else if(AN.length()==4)
                {Nol = "";}

               IDPendaftaran.setText("P" + Nol + AN);
            } else {
               IDPendaftaran.setText("P0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
     }
    
    public void tampil_nik(){
        try
        {
            
            Statement stat = (Statement) koneksi.getKoneksi().createStatement(); 
            String sql = "select nik,nama_penduduk from penduduk";  
            ResultSet res = stat.executeQuery(sql);
            while(res.next())
            {
                Object[] ob = new Object[1];
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
            
            NamaPenduduk.setText((String) ob[0]);
        }
        res.close(); stat.close();
         }catch(Exception ex){
         }
     }
     
     public void tampil_plat_nomor(){
        try
        {
            
            Statement stat = (Statement) koneksi.getKoneksi().createStatement(); 
            String sql = "select plat_nomor from motor where nik='"+jComboBox1.getSelectedItem()+"'";  
            ResultSet res = stat.executeQuery(sql);
            while(res.next())
            {
                Object[] ob = new Object[1];
                jComboBox2.addItem(res.getString("plat_nomor"));
                //NamaPemilik.setText(res.getString("nama_pendaftar"));
            }
        }
        catch(Exception ex)
        {
            
        }
    }
     
     public void tampil_merk(){
         try {
            Statement stat = (Statement) koneksi.getKoneksi().createStatement(); 
            String sql = "select merk from motor where plat_nomor='"+jComboBox2.getSelectedItem()+"'"; 
            ResultSet res = stat.executeQuery(sql);
            
             while(res.next()){
            Object[] ob = new Object[1];
            ob[0]=  res.getString(1);
            
            Merk.setText((String) ob[0]);
        }
        res.close(); stat.close();
         }catch(Exception ex){
         }
     }
     
     public void loadDataPendaftaran(){
        //Dari textbox
        id_pendaftaran = IDPendaftaran.getText();
        nik = (String) jComboBox1.getSelectedItem();
        nama_penduduk = NamaPenduduk.getText();
        plat_nomor = (String) jComboBox2.getSelectedItem();
        merk = Merk.getText();
    
    }
     
     public void dataSelect(){
        int i = tblpendaftaran.getSelectedRow();
        if(i == -1){
            return;
        }
        IDPendaftaran.setText(""+model.getValueAt(i,0));
        jComboBox1.setSelectedItem(""+model.getValueAt(i,1));
        jComboBox2.setSelectedItem(""+model.getValueAt(i,2));    
    }
     
     public void reset(){
        plat_nomor = "- Pilih Plat Nomor -";
        nik = "- Pilih Penduduk -";
        merk = "";
        nama_penduduk = "";
        
        jComboBox1.setSelectedItem(nik);
        NamaPenduduk.setText(nama_penduduk);
        jComboBox2.setSelectedItem(plat_nomor);
        Merk.setText(merk);
    }
    
    public void EditDataPendaftaran(){
        loadDataPendaftaran();
        
        try{
            //uji koneksi
            Statement stat = (Statement) koneksi.getKoneksi().createStatement();
            
            //kirim perintah sql
            String sql = "UPDATE pendaftaran SET nik='"
                    +jComboBox1.getSelectedItem()+"',plat_nomor='"+jComboBox2.getSelectedItem()+"' WHERE id_pendaftaran = '"+ id_pendaftaran+"'";
            PreparedStatement p =(PreparedStatement)koneksi.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getDataPendaftaran();
            
            //kosongkan data
            reset();
            JOptionPane.showMessageDialog(null, "PERUBAHAN DATA PENDAFTARAN BERHASIL");
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
        
    public void SimpanDataPendaftaran(){
        loadDataPendaftaran();
        try{
            Statement stat = (Statement) koneksi.getKoneksi().createStatement();
            String  sql =   "INSERT INTO pendaftaran(id_pendaftaran, nik, plat_nomor)"+"VALUES('"+ id_pendaftaran +"','"+ jComboBox1.getSelectedItem() +"','"+ 
                              jComboBox2.getSelectedItem() +"')";
            PreparedStatement p = (PreparedStatement) koneksi.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            getDataPendaftaran();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        IDPendaftaran = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        NamaPenduduk = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        Merk = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpendaftaran = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Pendaftaran Pajak Motor");

        jLabel2.setText("ID Pendaftaran");

        jLabel3.setText("Data Penduduk");

        jLabel4.setText("Data Motor");

        IDPendaftaran.setEnabled(false);
        IDPendaftaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDPendaftaranActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Pilih Penduduk -" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel5.setText("NIK Penduduk");

        jLabel6.setText("Nama Penduduk");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Pilih Plat Nomor -" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Plat Nomor");

        jLabel8.setText("Merk");

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        tblpendaftaran.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblpendaftaran);

        jButton1.setText("Simpan");

        jButton2.setText("Reset");

        jButton3.setText("Ubah");

        jButton4.setText("Keluar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("+");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("+");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(133, 133, 133))
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(27, 27, 27)
                                .addComponent(jButton2)
                                .addGap(38, 38, 38)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(IDPendaftaran, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(NamaPenduduk)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, 115, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel4)
                                .addGap(47, 47, 47))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Merk, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)
                                .addComponent(jLabel8)))
                        .addComponent(jButton6)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IDPendaftaran, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NamaPenduduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Merk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void IDPendaftaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDPendaftaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDPendaftaranActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
    tampil_nama();        // TODO add your handling code here:
    tampil_plat_nomor();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
tampil_merk();        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
dataSelect();        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
        new login().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
        new penduduk().setVisible(true);             // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
 this.dispose();
        new motor().setVisible(true);          // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(pendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pendaftaran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IDPendaftaran;
    private javax.swing.JTextField Merk;
    private javax.swing.JTextField NamaPenduduk;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblpendaftaran;
    // End of variables declaration//GEN-END:variables
}
