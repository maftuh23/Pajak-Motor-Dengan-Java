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
import java.text.SimpleDateFormat;
/**
 *
 * @author PC Live Streaming
 */
public class transaksi extends javax.swing.JFrame {
    private DefaultTableModel model;
    
    //deklarasi variabel
    String kode_transaksi,id_pendaftaran, nik, tgl_bayar,tgl_jatuh_tempo, nama_penduduk, plat_nomor, bayar_di, proses, kembalian, jumlah_pajak, biaya_pkb, pilih_jenis_pembayaran, total_membayar ;
    Double total;
    
    


    /**
     * Creates new form transaksi
     */
    public transaksi() {
        initComponents();
        kodetransaksi();
        
        model = new DefaultTableModel();
        
        //memberi nama header pada tabel
        tbltransaksi.setModel(model);
        model.addColumn("Kode Transaksi");
        model.addColumn("ID Pendaftaran");        
        model.addColumn("NIK");        
        model.addColumn("Plat Nomor");
        model.addColumn("Tanggal Bayar");  
        model.addColumn("Tanggal Jatuh Tempo");  
        model.addColumn("Jumlah Pajak");   
        model.addColumn("Bayar Di");    
        model.addColumn("Proses");                             
        model.addColumn("Total Membayar");  
        model.addColumn("Kembalian");  
        
        //fungsi ambil data
        getDataTransaksi();
    }
    
    public void getDataTransaksi(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            Statement stat = (Statement) koneksi.getKoneksi().createStatement();        
            String sql = "SELECT * FROM transaksi";
            ResultSet res = stat.executeQuery(sql);
            while(res.next()){
                Object[] obj = new Object[11];
                obj[0]=res.getString("kode_transaksi");
                obj[1]=res.getString("id_pendaftaran");
                obj[2]=res.getString("nik");
                obj[3]=res.getString("plat_nomor");
                obj[4]=res.getString("tgl_bayar");
                obj[5]=res.getString("tgl_jatuh_tempo");
                obj[6]=res.getString("jumlah_pajak");
                obj[7]=res.getString("bayar_di");
                obj[8]=res.getString("proses");
                obj[9]=res.getString("total_membayar");
                obj[10]=res.getString("kembalian");
                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void kodetransaksi()
    {
       try {
            Statement stat = (Statement) koneksi.getKoneksi().createStatement(); 
            String sql = "select kode_transaksi from transaksi";  
            ResultSet res = stat.executeQuery(sql);
            if (res.next()) {
                String nofak = res.getString("kode_transaksi").substring(1);
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

               KodeTransaksi.setText("T" + Nol + AN);
            } else {
               KodeTransaksi.setText("T0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
     }
    
    public void cari () {
        try {
            Statement stat = (Statement) koneksi.getKoneksi().createStatement();
            String sql = "select nik, plat_nomor from pendaftaran where id_pendaftaran='"+IDPendaftaran.getText()+"'";
            ResultSet res = stat.executeQuery(sql);
            while (res.next())
            {   Object[] ob = new Object[2];
                NIK.setText(res.getString("nik"));
                PlatNomor.setText(res.getString("plat_nomor"));
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        } 
    }
       
    public void tampil_nama(){
         try {
           Statement stat = (Statement) koneksi.getKoneksi().createStatement();
            String sql = "select nama_penduduk from penduduk where nik='"+NIK.getText()+"'";
            ResultSet res = stat.executeQuery(sql);
            while (res.next())
            {   Object[] ob = new Object[1];
                NamaPenduduk.setText(res.getString("nama_penduduk"));
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        } 
     }
    
    public void jumlah_pajak() {
        int nilai1,nilai2,jumlah ;
        nilai1=Integer.parseInt(TotalMembayar.getText());
        nilai2=Integer.parseInt(JumlahPajak.getText());
        jumlah = nilai1 - nilai2;
        
        Kembalian.setText(String.valueOf(jumlah));        
    }
    
    public void total (){
        int nilai1,nilai2,jumlah ;
        
        nilai1=Integer.parseInt(BiayaPKB.getText());
        nilai2=2*nilai1/3;
        jumlah = nilai2+35000+100000+nilai1 ;
        // BBN KB = 2/3*PKB (BBN KB (Bea Balik Nama Kendaraan Bermotor))
        // SWDKLLJ = Rp35.000 (SWDKLJJ (Sumbangan Wajib Dana Kecelakaan Lalu-Lintas Jalan)
        // Biaya ADM STNK = Rp100.000 (Biaya Adm TNKB (Administrasi Tanda Nomor Kendaraan Bermotor)
        
        JumlahPajak.setText(String.valueOf(jumlah));
    }
    
      
    public void kembalian (){
          
        if(jComboBox1.getSelectedItem().equals("Tahunan") || jComboBox1.getSelectedItem().equals("Tahunan"))
        {
            JumlahPajak.setText("");
            JumlahPajak.setEditable(true);
            jLabel12.setVisible(true);
            BiayaPKB.setVisible(true);
        }else  if(jComboBox1.getSelectedItem().equals("Mutasi") || jComboBox1.getSelectedItem().equals("Mutasi"))
        {
            JumlahPajak.setText("100000");
            JumlahPajak.setEditable(false);
            jLabel12.setVisible(false);
            BiayaPKB.setVisible(false);
        }else  if(jComboBox1.getSelectedItem().equals("Balik Nama") || jComboBox1.getSelectedItem().equals("Balik Nama"))
        {
            JumlahPajak.setText("30000");
            jLabel12.setVisible(false);
            JumlahPajak.setEditable(false);
            BiayaPKB.setVisible(false);
        }
    }
    
    public void loadDataTransaksi(){
        //Dari textbox
        
        String tampilan ="yyyy-MM-dd" ; 
        SimpleDateFormat fm = new SimpleDateFormat(tampilan); 
        
        kode_transaksi = KodeTransaksi.getText();
        id_pendaftaran = IDPendaftaran.getText();
        nik = NIK.getText();
        plat_nomor = PlatNomor.getText();
        tgl_bayar = String.valueOf(fm.format(jDateChooser1.getDate()));
        tgl_jatuh_tempo = String.valueOf(fm.format(jDateChooser2.getDate()));
        jumlah_pajak = JumlahPajak.getText();
        bayar_di = BayarDi.getText();
        proses = (String) jComboBox1.getSelectedItem();
        total_membayar = TotalMembayar.getText();
        kembalian = Kembalian.getText();
    
    }
    
    public void dataSelect(){
        int i = tbltransaksi.getSelectedRow();
        if(i == -1){
            return;
        }
        KodeTransaksi.setText(""+model.getValueAt(i,0));
        IDPendaftaran.setText(""+model.getValueAt(i,1));
        NIK.setText(""+model.getValueAt(i,2));
        PlatNomor.setText(""+model.getValueAt(i,3));        
        //jDateChooser1.setDate(""+model.getValueAt(i,4));
        //tgl_jatuh_tempo.setText(""+model.getValueAt(i,5));
        JumlahPajak.setText(""+model.getValueAt(i,6));
        BayarDi.setText(""+model.getValueAt(i,7));
        jComboBox1.setSelectedItem(""+model.getValueAt(i,8));
        TotalMembayar.setText(""+model.getValueAt(i,9));
        Kembalian.setText(""+model.getValueAt(i,10));
    }
    
    public void SimpanDataTransaksi(){
        loadDataTransaksi();
        try{
            Statement stat = (Statement) koneksi.getKoneksi().createStatement();
            String  sql =   "INSERT INTO transaksi(kode_transaksi, id_pendaftaran, nik, plat_nomor, tgl_bayar, tgl_jatuh_tempo, jumlah_pajak, bayar_di, proses, total_membayar, kembalian)"+"VALUES('"+ kode_transaksi +"','"+ id_pendaftaran +"','"+ 
                              nik +"','"+ plat_nomor +"','"+ tgl_bayar +"','"+ tgl_jatuh_tempo +"','"+ jumlah_pajak +"','"+ bayar_di +"','"+ proses +"','"+ total_membayar +"','"+ kembalian +"')";
            PreparedStatement p = (PreparedStatement) koneksi.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            getDataTransaksi();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        
        
    }
    
     public void reset(){
        pilih_jenis_pembayaran ="-Pilih Jenis Pemmbayaran";
                
        IDPendaftaran.setText("");
        NIK.setText("");
        NamaPenduduk.setText("");
        PlatNomor.setText("");
        //jDateChooser1.setDate("");
        //jDateChooser2.setDate("");
        jComboBox1.setSelectedItem(pilih_jenis_pembayaran);
        JumlahPajak.setText("");
        BiayaPKB.setText("");
        TotalMembayar.setText("");
        BayarDi.setText("");
        Kembalian.setText("");
   }
     
     public void EditDataTransaksi(){
        loadDataTransaksi();
        
        try{
            //uji koneksi
            Statement stat = (Statement) koneksi.getKoneksi().createStatement();
            
            //kirim perintah sql
            String sql = "UPDATE transaksi SET id_pendaftaran='"+id_pendaftaran+"', nik='"+nik+"', plat_nomor='"+plat_nomor+"', tgl_bayar='"+tgl_bayar+"', tgl_jatuh_tempo='"+tgl_jatuh_tempo+"', jumlah_pajak='"+jumlah_pajak+"', bayar_di='"+bayar_di+"', proses='"+proses+"', total_membayar='"+total_membayar+"', kembalian='"+kembalian+"' WHERE kode_transaksi = '"+kode_transaksi+"'";
            PreparedStatement p =(PreparedStatement)koneksi.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getDataTransaksi();
            
            //kosongkan data
            reset();
            JOptionPane.showMessageDialog(null, "PERUBAHAN DATA TRANSAKSI BERHASIL");
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
     public void HapusDataTransaksi(){
         loadDataTransaksi(); 
        int pesan = JOptionPane.showConfirmDialog(null, "HAPUS DATA"+ kode_transaksi +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);
       
        if(pesan == JOptionPane.OK_OPTION){
            try{
                Statement stat = (Statement) koneksi.getKoneksi().createStatement();
                String sql = "DELETE FROM transaksi WHERE kode_transaksi='"+ kode_transaksi +"'";
                PreparedStatement p =(PreparedStatement)koneksi.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                
                getDataTransaksi();
                
                reset();
                JOptionPane.showMessageDialog(null, "DATA TRANSAKSI BERHASIL DIHAPUS");
            }catch(SQLException err){
                JOptionPane.showMessageDialog(null, err.getMessage());
            }
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltransaksi = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        KodeTransaksi = new javax.swing.JTextField();
        IDPendaftaran = new javax.swing.JTextField();
        NIK = new javax.swing.JTextField();
        NamaPenduduk = new javax.swing.JTextField();
        PlatNomor = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        JumlahPajak = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        BiayaPKB = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TotalMembayar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        BayarDi = new javax.swing.JTextField();
        Kembalian = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Transaksi");

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

        tbltransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tbltransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbltransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbltransaksi);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 201, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 193, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton6.setText("Cari");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        KodeTransaksi.setEditable(false);

        NIK.setEditable(false);
        NIK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NIKActionPerformed(evt);
            }
        });

        NamaPenduduk.setEditable(false);

        PlatNomor.setEditable(false);

        jDateChooser1.setToolTipText("");
        jDateChooser1.setDateFormatString("yyyy-MM-dd");

        jDateChooser2.setDateFormatString("yyyy-MM-dd");

        jLabel2.setText("Kode Transaksi");

        jLabel4.setText("NIK");

        jLabel3.setText("ID Pendaftaran");

        jLabel5.setText("Plat Nomor");

        jLabel6.setText("Tanggal Bayar");

        jLabel7.setText("Tanggal Jatuh Tempo");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setText("Jenis Pajak");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Pilih Jenis Pembayaran -", "Tahunan", "Mutasi", "Balik Nama" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Jumlah Pajak");

        JumlahPajak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JumlahPajakActionPerformed(evt);
            }
        });

        jLabel12.setText("Biaya PKB");

        BiayaPKB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BiayaPKBActionPerformed(evt);
            }
        });

        jLabel11.setText("Total Membayar");

        TotalMembayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalMembayarActionPerformed(evt);
            }
        });

        jLabel9.setText("Bayar Di");

        Kembalian.setEditable(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Kembalian");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(91, 91, 91)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(JumlahPajak)
                                            .addComponent(BiayaPKB)
                                            .addComponent(jComboBox1, 0, 153, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(92, 92, 92)
                                        .addComponent(TotalMembayar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BayarDi, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(28, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(JumlahPajak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BiayaPKB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(TotalMembayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BayarDi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3))
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(KodeTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(NIK, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(NamaPenduduk))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(IDPendaftaran, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PlatNomor, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)))
                .addGap(36, 36, 36)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(KodeTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(IDPendaftaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(NIK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(NamaPenduduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PlatNomor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(676, 676, 676)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(49, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(427, 427, 427))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        cari();  
        tampil_nama(); // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void NIKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NIKActionPerformed
       // TODO add your handling code here:
    }//GEN-LAST:event_NIKActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
    kembalian ();        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void BiayaPKBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BiayaPKBActionPerformed
    total ();     
    }//GEN-LAST:event_BiayaPKBActionPerformed

    private void TotalMembayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalMembayarActionPerformed
    jumlah_pajak();        // TODO add your handling code here:
    }//GEN-LAST:event_TotalMembayarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    SimpanDataTransaksi();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    kodetransaksi();        // TODO add your handling code here:
    reset();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        EditDataTransaksi();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tbltransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltransaksiMouseClicked
dataSelect();        // TODO add your handling code here:
    }//GEN-LAST:event_tbltransaksiMouseClicked

    private void JumlahPajakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JumlahPajakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JumlahPajakActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
        new login().setVisible(true);          // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
HapusDataTransaksi();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

     
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
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BayarDi;
    private javax.swing.JTextField BiayaPKB;
    private javax.swing.JTextField IDPendaftaran;
    private javax.swing.JTextField JumlahPajak;
    private javax.swing.JTextField Kembalian;
    private javax.swing.JTextField KodeTransaksi;
    private javax.swing.JTextField NIK;
    private javax.swing.JTextField NamaPenduduk;
    private javax.swing.JTextField PlatNomor;
    private javax.swing.JTextField TotalMembayar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbltransaksi;
    // End of variables declaration//GEN-END:variables
}
