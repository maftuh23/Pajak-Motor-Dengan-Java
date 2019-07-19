/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 100134
 Source Host           : localhost:3306
 Source Schema         : db_pajakmotor

 Target Server Type    : MySQL
 Target Server Version : 100134
 File Encoding         : 65001

 Date: 19/07/2019 14:01:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for motor
-- ----------------------------
DROP TABLE IF EXISTS `motor`;
CREATE TABLE `motor`  (
  `plat_nomor` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nik` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `merk` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `tahun` int(50) NOT NULL,
  `no_rangka` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `no_mesin` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `asal` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`plat_nomor`) USING BTREE,
  INDEX `nik_motor`(`nik`) USING BTREE,
  CONSTRAINT `nik_motor` FOREIGN KEY (`nik`) REFERENCES `penduduk` (`nik`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of motor
-- ----------------------------
INSERT INTO `motor` VALUES ('AA 1234 BB', '0002', 'Yamaha', 2007, 'MH32P', '2P2689113', 'Solo');
INSERT INTO `motor` VALUES ('AD 8888 CC', '0002', 'Yamaha', 2009, '2392839', '493849', 'Solo');

-- ----------------------------
-- Table structure for pendaftaran
-- ----------------------------
DROP TABLE IF EXISTS `pendaftaran`;
CREATE TABLE `pendaftaran`  (
  `id_pendaftaran` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nik` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `plat_nomor` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`id_pendaftaran`) USING BTREE,
  INDEX `nik`(`nik`) USING BTREE,
  INDEX `plat_nomor`(`plat_nomor`) USING BTREE,
  CONSTRAINT `nik` FOREIGN KEY (`nik`) REFERENCES `penduduk` (`nik`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `plat_nomor` FOREIGN KEY (`plat_nomor`) REFERENCES `motor` (`plat_nomor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pendaftaran
-- ----------------------------
INSERT INTO `pendaftaran` VALUES ('P0001', '0002', 'AA 1234 BB');

-- ----------------------------
-- Table structure for penduduk
-- ----------------------------
DROP TABLE IF EXISTS `penduduk`;
CREATE TABLE `penduduk`  (
  `nik` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nama_penduduk` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `alamat` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `no_hp` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`nik`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of penduduk
-- ----------------------------
INSERT INTO `penduduk` VALUES ('0001', 'Maftuh Ichsan', 'Solo', '081226316749');
INSERT INTO `penduduk` VALUES ('0002', 'Ichsan', 'Mbuh', '23');

-- ----------------------------
-- Table structure for pengguna
-- ----------------------------
DROP TABLE IF EXISTS `pengguna`;
CREATE TABLE `pengguna`  (
  `id_user` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `username` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `password` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `level` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`id_user`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pengguna
-- ----------------------------
INSERT INTO `pengguna` VALUES ('1', 'admin', 'admin', 'admin');
INSERT INTO `pengguna` VALUES ('2', 'operator', 'operator', 'operator');
INSERT INTO `pengguna` VALUES ('3', 'kasir', 'kasir', 'kasir');

-- ----------------------------
-- Table structure for transaksi
-- ----------------------------
DROP TABLE IF EXISTS `transaksi`;
CREATE TABLE `transaksi`  (
  `kode_transaksi` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `id_pendaftaran` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nik` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `plat_nomor` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `tgl_bayar` date NOT NULL,
  `tgl_jatuh_tempo` date NOT NULL,
  `jumlah_pajak` int(50) NOT NULL,
  `bayar_di` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `proses` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `total_membayar` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `kembalian` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`kode_transaksi`) USING BTREE,
  INDEX `id_pendaftaran`(`id_pendaftaran`) USING BTREE,
  INDEX `nik_transaksi`(`nik`) USING BTREE,
  INDEX `plat_nomor_transaksi`(`plat_nomor`) USING BTREE,
  CONSTRAINT `id_pendaftaran` FOREIGN KEY (`id_pendaftaran`) REFERENCES `pendaftaran` (`id_pendaftaran`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `nik_transaksi` FOREIGN KEY (`nik`) REFERENCES `penduduk` (`nik`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `plat_nomor_transaksi` FOREIGN KEY (`plat_nomor`) REFERENCES `motor` (`plat_nomor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of transaksi
-- ----------------------------
INSERT INTO `transaksi` VALUES ('T0001', 'P0001', '0001', 'AA 1234 BB', '2019-07-14', '2019-07-15', 30000, 'Solo', 'Balik Nama', '40000', '10000');
INSERT INTO `transaksi` VALUES ('T0002', 'P0001', '0002', 'AA 1234 BB', '2019-07-11', '2019-07-15', 885000, 'solo', 'Tahunan', '', '');

SET FOREIGN_KEY_CHECKS = 1;
