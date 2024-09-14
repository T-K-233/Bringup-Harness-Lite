import chisel3._


class Ethernet extends Module {
  val io = IO(new Bundle {
    val led0_r = Output(Bool())
    val led0_g = Output(Bool())
    val led0_b = Output(Bool())
    val led1_r = Output(Bool())
    val led1_g = Output(Bool())
    val led1_b = Output(Bool())
    val led2_r = Output(Bool())
    val led2_g = Output(Bool())
    val led2_b = Output(Bool())
    val led3_r = Output(Bool())
    val led3_g = Output(Bool())
    val led3_b = Output(Bool())
    val led4 = Output(Bool())
    val led5 = Output(Bool())
    val led6 = Output(Bool())
    val led7 = Output(Bool())


    val phy_col = Input(Bool())
    val phy_crs = Input(Bool())
    val phy_reset_n = Output(Bool())
    val phy_rx_clk = Input(Clock())
    val phy_rx_dv = Input(Bool())
    val phy_rxd = Input(UInt(4.W))
    val phy_rx_er = Input(Bool())
    val phy_tx_clk = Input(Clock())
    val phy_tx_en = Output(Bool())
    val phy_txd = Output(UInt(4.W))
  })


  val tx_axis_tvalid = Wire(Bool())
  val tx_axis_tready = Wire(Bool())
  val tx_axis_tdata = Wire(UInt(8.W))
  val tx_axis_tlast = Wire(Bool())
  val tx_axis_tuser = Wire(Bool())

  val rx_axis_tvalid = Wire(Bool())
  val rx_axis_tready = Wire(Bool())
  val rx_axis_tdata = Wire(UInt(8.W))
  val rx_axis_tlast = Wire(Bool())
  val rx_axis_tuser = Wire(Bool())


  val rx_eth_hdr_valid = Wire(Bool())
  val rx_eth_hdr_ready = Wire(Bool())
  val rx_eth_dest_mac = Wire(UInt(48.W))
  val rx_eth_src_mac = Wire(UInt(48.W))
  val rx_eth_type = Wire(UInt(16.W))
  val rx_eth_payload_axis_tdata = Wire(UInt(8.W))
  val rx_eth_payload_axis_tvalid = Wire(Bool())
  val rx_eth_payload_axis_tready = Wire(Bool())
  val rx_eth_payload_axis_tlast = Wire(Bool())
  val rx_eth_payload_axis_tuser = Wire(Bool())

  val tx_eth_hdr_valid = Wire(Bool())
  val tx_eth_hdr_ready = Wire(Bool())
  val tx_eth_dest_mac = Wire(UInt(48.W))
  val tx_eth_src_mac = Wire(UInt(48.W))
  val tx_eth_type = Wire(UInt(16.W))
  val tx_eth_payload_axis_tdata = Wire(UInt(8.W))
  val tx_eth_payload_axis_tvalid = Wire(Bool())
  val tx_eth_payload_axis_tready = Wire(Bool())
  val tx_eth_payload_axis_tlast = Wire(Bool())
  val tx_eth_payload_axis_tuser = Wire(Bool())



  val tx_fifo_udp_payload_axis_tdata = Wire(UInt(8.W))
  val tx_fifo_udp_payload_axis_tvalid = Wire(Bool())
  val tx_fifo_udp_payload_axis_tready = Wire(Bool())
  val tx_fifo_udp_payload_axis_tlast = Wire(Bool())
  val tx_fifo_udp_payload_axis_tuser = Wire(Bool())

  val rx_fifo_udp_payload_axis_tdata = Wire(UInt(8.W))
  val rx_fifo_udp_payload_axis_tvalid = Wire(Bool())
  val rx_fifo_udp_payload_axis_tready = Wire(Bool())
  val rx_fifo_udp_payload_axis_tlast = Wire(Bool())
  val rx_fifo_udp_payload_axis_tuser = Wire(Bool())



  val rx_ip_hdr_valid = Wire(Bool())
  val rx_ip_hdr_ready = Wire(Bool())
  val rx_ip_eth_dest_mac = Wire(UInt(48.W))
  val rx_ip_eth_src_mac = Wire(UInt(48.W))
  val rx_ip_eth_type = Wire(UInt(16.W))
  val rx_ip_version = Wire(UInt(4.W))
  val rx_ip_ihl = Wire(UInt(4.W))
  val rx_ip_dscp = Wire(UInt(6.W))
  val rx_ip_ecn = Wire(UInt(2.W))
  val rx_ip_length = Wire(UInt(16.W))
  val rx_ip_identification = Wire(UInt(16.W))
  val rx_ip_flags = Wire(UInt(3.W))
  val rx_ip_fragment_offset = Wire(UInt(13.W))
  val rx_ip_ttl = Wire(UInt(8.W))
  val rx_ip_protocol = Wire(UInt(8.W))
  val rx_ip_header_checksum = Wire(UInt(16.W))
  val rx_ip_source_ip = Wire(UInt(32.W))
  val rx_ip_dest_ip = Wire(UInt(32.W))
  val rx_ip_payload_axis_tdata = Wire(UInt(8.W))
  val rx_ip_payload_axis_tvalid = Wire(Bool())
  val rx_ip_payload_axis_tready = Wire(Bool())
  val rx_ip_payload_axis_tlast = Wire(Bool())
  val rx_ip_payload_axis_tuser = Wire(Bool())




  rx_ip_hdr_ready := 1.U(1.W)

  rx_ip_payload_axis_tready := 1.U(1.W)





  val udp_complete = Module(new udp_complete)
  val eth_axis_tx = Module(new eth_axis_tx)
  val eth_axis_rx = Module(new eth_axis_rx)


  // Configuration
  val local_mac   = 0x02_00_00_00_00_00L.U(48.W)
  val local_ip    = 0xac_1c_00_06L.U(32.W)
  val gateway_ip  = 0xac_17_20_01L.U(32.W)
  val subnet_mask = 0xff_ff_ff_00L.U(32.W)

  

  udp_complete.io.clk := clock
  udp_complete.io.rst := reset

  // Ethernet frame input
  udp_complete.io.s_eth_hdr_valid := rx_eth_hdr_valid
  rx_eth_hdr_ready := udp_complete.io.s_eth_hdr_ready
  udp_complete.io.s_eth_dest_mac := rx_eth_dest_mac
  udp_complete.io.s_eth_src_mac := rx_eth_src_mac
  udp_complete.io.s_eth_type := rx_eth_type
  udp_complete.io.s_eth_payload_axis_tdata := rx_eth_payload_axis_tdata
  udp_complete.io.s_eth_payload_axis_tvalid := rx_eth_payload_axis_tvalid
  rx_eth_payload_axis_tready := udp_complete.io.s_eth_payload_axis_tready
  udp_complete.io.s_eth_payload_axis_tlast := rx_eth_payload_axis_tlast
  udp_complete.io.s_eth_payload_axis_tuser := rx_eth_payload_axis_tuser

  // Ethernet frame output
  tx_eth_hdr_valid := udp_complete.io.m_eth_hdr_valid
  udp_complete.io.m_eth_hdr_ready := tx_eth_hdr_ready
  tx_eth_dest_mac := udp_complete.io.m_eth_dest_mac
  tx_eth_src_mac := udp_complete.io.m_eth_src_mac
  tx_eth_type := udp_complete.io.m_eth_type
  tx_eth_payload_axis_tdata := udp_complete.io.m_eth_payload_axis_tdata
  tx_eth_payload_axis_tvalid := udp_complete.io.m_eth_payload_axis_tvalid
  udp_complete.io.m_eth_payload_axis_tready := tx_eth_payload_axis_tready
  tx_eth_payload_axis_tlast := udp_complete.io.m_eth_payload_axis_tlast
  tx_eth_payload_axis_tuser := udp_complete.io.m_eth_payload_axis_tuser

  val tx_ip_hdr_valid = Wire(Bool())
  val tx_ip_hdr_ready = Wire(Bool())
  val tx_ip_dscp = Wire(UInt(6.W))
  val tx_ip_ecn = Wire(UInt(2.W))
  val tx_ip_length = Wire(UInt(16.W))
  val tx_ip_ttl = Wire(UInt(8.W))
  val tx_ip_protocol = Wire(UInt(8.W))
  val tx_ip_source_ip = Wire(UInt(32.W))
  val tx_ip_dest_ip = Wire(UInt(32.W))

  val tx_ip_payload_axis_tdata = Wire(UInt(8.W))
  val tx_ip_payload_axis_tvalid = Wire(Bool())
  val tx_ip_payload_axis_tready = Wire(Bool())
  val tx_ip_payload_axis_tlast = Wire(Bool())
  val tx_ip_payload_axis_tuser = Wire(Bool())


  tx_ip_hdr_valid := 0.U(1.W)
  tx_ip_dscp := 0.U(6.W)
  tx_ip_ecn := 0.U(2.W)
  tx_ip_length := 0.U(16.W)
  tx_ip_ttl := 0.U(8.W)
  tx_ip_protocol := 0.U(8.W)
  tx_ip_source_ip := 0.U(32.W)
  tx_ip_dest_ip := 0.U(32.W)

  tx_ip_payload_axis_tdata := 0.U(8.W)
  tx_ip_payload_axis_tvalid := 0.U(1.W)
  tx_ip_payload_axis_tlast := 0.U(1.W)
  tx_ip_payload_axis_tuser := 0.U(1.W)



  // IP frame input
  udp_complete.io.s_ip_hdr_valid := tx_ip_hdr_valid
  tx_ip_hdr_ready := udp_complete.io.s_ip_hdr_ready
  udp_complete.io.s_ip_dscp := tx_ip_dscp
  udp_complete.io.s_ip_ecn := tx_ip_ecn
  udp_complete.io.s_ip_length := tx_ip_length
  udp_complete.io.s_ip_ttl := tx_ip_ttl
  udp_complete.io.s_ip_protocol := tx_ip_protocol
  udp_complete.io.s_ip_source_ip := tx_ip_source_ip
  udp_complete.io.s_ip_dest_ip := tx_ip_dest_ip
  udp_complete.io.s_ip_payload_axis_tdata := tx_ip_payload_axis_tdata
  udp_complete.io.s_ip_payload_axis_tvalid := tx_ip_payload_axis_tvalid
  tx_ip_payload_axis_tready := udp_complete.io.s_ip_payload_axis_tready
  udp_complete.io.s_ip_payload_axis_tlast := tx_ip_payload_axis_tlast
  udp_complete.io.s_ip_payload_axis_tuser := tx_ip_payload_axis_tuser

  // IP frame output
  rx_ip_hdr_valid := udp_complete.io.m_ip_hdr_valid
  udp_complete.io.m_ip_hdr_ready := rx_ip_hdr_ready
  rx_ip_eth_dest_mac := udp_complete.io.m_ip_eth_dest_mac
  rx_ip_eth_src_mac := udp_complete.io.m_ip_eth_src_mac
  rx_ip_eth_type := udp_complete.io.m_ip_eth_type
  rx_ip_version := udp_complete.io.m_ip_version
  rx_ip_ihl := udp_complete.io.m_ip_ihl
  rx_ip_dscp := udp_complete.io.m_ip_dscp
  rx_ip_ecn := udp_complete.io.m_ip_ecn
  rx_ip_length := udp_complete.io.m_ip_length
  rx_ip_identification := udp_complete.io.m_ip_identification
  rx_ip_flags := udp_complete.io.m_ip_flags
  rx_ip_fragment_offset := udp_complete.io.m_ip_fragment_offset
  rx_ip_ttl := udp_complete.io.m_ip_ttl
  rx_ip_protocol := udp_complete.io.m_ip_protocol
  rx_ip_header_checksum := udp_complete.io.m_ip_header_checksum
  rx_ip_source_ip := udp_complete.io.m_ip_source_ip
  rx_ip_dest_ip := udp_complete.io.m_ip_dest_ip
  rx_ip_payload_axis_tdata := udp_complete.io.m_ip_payload_axis_tdata
  rx_ip_payload_axis_tvalid := udp_complete.io.m_ip_payload_axis_tvalid
  udp_complete.io.m_ip_payload_axis_tready := rx_ip_payload_axis_tready
  rx_ip_payload_axis_tlast := udp_complete.io.m_ip_payload_axis_tlast
  rx_ip_payload_axis_tuser := udp_complete.io.m_ip_payload_axis_tuser


  val tx_udp_hdr_valid = Wire(Bool())
  val rx_udp_hdr_valid = Wire(Bool())
  val rx_udp_hdr_ready = Wire(Bool())
  val rx_udp_dest_port = Wire(UInt(16.W))
  val match_cond = Wire(Bool())


  match_cond := rx_udp_dest_port === 1234.U

  rx_udp_hdr_valid :=rx_eth_hdr_valid

  tx_udp_hdr_valid := rx_udp_hdr_valid && match_cond
  rx_udp_hdr_ready := (tx_eth_hdr_ready && match_cond) || !match_cond;

  // UDP frame input
  udp_complete.io.s_udp_hdr_valid := tx_udp_hdr_valid
  udp_complete.io.s_udp_ip_dscp := 0.U(6.W)
  udp_complete.io.s_udp_ip_ecn := 0.U(2.W)
  udp_complete.io.s_udp_ip_ttl := 64.U(8.W)
  udp_complete.io.s_udp_ip_source_ip := local_ip
  udp_complete.io.s_udp_ip_dest_ip := udp_complete.io.m_ip_source_ip
  udp_complete.io.s_udp_source_port := udp_complete.io.m_udp_dest_port
  rx_udp_dest_port := udp_complete.io.m_udp_dest_port
  udp_complete.io.s_udp_dest_port := udp_complete.io.m_udp_source_port
  udp_complete.io.s_udp_length := udp_complete.io.m_udp_length
  udp_complete.io.s_udp_checksum := 0.U(16.W)
  
  udp_complete.io.s_udp_payload_axis_tdata := tx_fifo_udp_payload_axis_tdata
  udp_complete.io.s_udp_payload_axis_tvalid := tx_fifo_udp_payload_axis_tvalid
  tx_fifo_udp_payload_axis_tready := udp_complete.io.s_udp_payload_axis_tready
  udp_complete.io.s_udp_payload_axis_tlast := tx_fifo_udp_payload_axis_tlast
  udp_complete.io.s_udp_payload_axis_tuser := tx_fifo_udp_payload_axis_tuser


  
  // UDP frame output
  udp_complete.io.m_udp_hdr_ready := rx_udp_hdr_ready
  
  rx_fifo_udp_payload_axis_tdata := udp_complete.io.m_udp_payload_axis_tdata
  rx_fifo_udp_payload_axis_tvalid := udp_complete.io.m_udp_payload_axis_tvalid
  udp_complete.io.m_udp_payload_axis_tready := rx_fifo_udp_payload_axis_tready
  rx_fifo_udp_payload_axis_tlast := udp_complete.io.m_udp_payload_axis_tlast
  rx_fifo_udp_payload_axis_tuser := udp_complete.io.m_udp_payload_axis_tuser


  // Status signals


  // Configuration
  udp_complete.io.local_mac := local_mac
  udp_complete.io.local_ip := local_ip
  udp_complete.io.gateway_ip := gateway_ip
  udp_complete.io.subnet_mask := subnet_mask
  udp_complete.io.clear_arp_cache := 0.U(1.W)


  
  rx_udp_hdr_valid := rx_eth_hdr_valid




  eth_axis_tx.io.clk := clock
  eth_axis_tx.io.rst := reset

  eth_axis_tx.io.s_eth_hdr_valid := tx_eth_hdr_valid
  tx_eth_hdr_ready := eth_axis_tx.io.s_eth_hdr_ready
  eth_axis_tx.io.s_eth_dest_mac := tx_eth_dest_mac
  eth_axis_tx.io.s_eth_src_mac := tx_eth_src_mac
  eth_axis_tx.io.s_eth_type := tx_eth_type
  eth_axis_tx.io.s_eth_payload_axis_tdata := tx_eth_payload_axis_tdata
  eth_axis_tx.io.s_eth_payload_axis_tvalid := tx_eth_payload_axis_tvalid
  tx_eth_payload_axis_tready := eth_axis_tx.io.s_eth_payload_axis_tready
  eth_axis_tx.io.s_eth_payload_axis_tlast := tx_eth_payload_axis_tlast
  eth_axis_tx.io.s_eth_payload_axis_tuser := tx_eth_payload_axis_tuser

  tx_axis_tdata := eth_axis_tx.io.m_axis_tdata
  tx_axis_tvalid := eth_axis_tx.io.m_axis_tvalid
  eth_axis_tx.io.m_axis_tready := tx_axis_tready
  tx_axis_tlast := eth_axis_tx.io.m_axis_tlast
  tx_axis_tuser := eth_axis_tx.io.m_axis_tuser





  eth_axis_rx.io.clk := clock
  eth_axis_rx.io.rst := reset

  eth_axis_rx.io.s_axis_tdata := rx_axis_tdata
  eth_axis_rx.io.s_axis_tvalid := rx_axis_tvalid
  rx_axis_tready := eth_axis_rx.io.s_axis_tready
  eth_axis_rx.io.s_axis_tlast := rx_axis_tlast
  eth_axis_rx.io.s_axis_tuser := rx_axis_tuser

  rx_eth_hdr_valid := eth_axis_rx.io.m_eth_hdr_valid
  eth_axis_rx.io.m_eth_hdr_ready := rx_eth_hdr_ready
  rx_eth_dest_mac := eth_axis_rx.io.m_eth_dest_mac
  rx_eth_src_mac := eth_axis_rx.io.m_eth_src_mac
  rx_eth_type := eth_axis_rx.io.m_eth_type
  rx_eth_payload_axis_tdata := eth_axis_rx.io.m_eth_payload_axis_tdata
  rx_eth_payload_axis_tvalid := eth_axis_rx.io.m_eth_payload_axis_tvalid
  eth_axis_rx.io.m_eth_payload_axis_tready := rx_eth_payload_axis_tready
  rx_eth_payload_axis_tlast := eth_axis_rx.io.m_eth_payload_axis_tlast
  rx_eth_payload_axis_tuser := eth_axis_rx.io.m_eth_payload_axis_tuser

  val udp_payload_fifo = Module(new axis_fifo(
    DEPTH = 8192,
    DATA_WIDTH = 8,
    KEEP_ENABLE = 0,
    ID_ENABLE = 0,
    ID_WIDTH = 8,
    DEST_ENABLE = 0,
    DEST_WIDTH = 8,
    USER_ENABLE = 1,
    USER_WIDTH = 1,
  ))

  udp_payload_fifo.io.clk := clock
  udp_payload_fifo.io.rst := reset

  udp_payload_fifo.io.s_axis_tdata := rx_fifo_udp_payload_axis_tdata
  udp_payload_fifo.io.s_axis_tvalid := rx_fifo_udp_payload_axis_tvalid
  rx_fifo_udp_payload_axis_tready := udp_payload_fifo.io.s_axis_tready
  udp_payload_fifo.io.s_axis_tlast := rx_fifo_udp_payload_axis_tlast
  udp_payload_fifo.io.s_axis_tid := 0.U(8.W)
  udp_payload_fifo.io.s_axis_tdest := 0.U(8.W)
  udp_payload_fifo.io.s_axis_tuser := rx_fifo_udp_payload_axis_tuser

  tx_fifo_udp_payload_axis_tdata := udp_payload_fifo.io.m_axis_tdata
  tx_fifo_udp_payload_axis_tvalid := udp_payload_fifo.io.m_axis_tvalid
  udp_payload_fifo.io.m_axis_tready := tx_fifo_udp_payload_axis_tready
  tx_fifo_udp_payload_axis_tlast := udp_payload_fifo.io.m_axis_tlast
  tx_fifo_udp_payload_axis_tuser := udp_payload_fifo.io.m_axis_tuser


  val eth_mac_mii_fifo = Module(new eth_mac_mii_fifo(
    TARGET = "XILINX",
    CLOCK_INPUT_STYLE = "BUFR",
    AXIS_DATA_WIDTH = 8,
    ENABLE_PADDING = 1,
    MIN_FRAME_LENGTH = 64,
    TX_FIFO_DEPTH = 4096,
    TX_FRAME_FIFO = 1,
    RX_FIFO_DEPTH = 4096,
    RX_FRAME_FIFO = 1,
  ))

  eth_mac_mii_fifo.io.rst := reset
  eth_mac_mii_fifo.io.logic_clk := clock
  eth_mac_mii_fifo.io.logic_rst := reset

  eth_mac_mii_fifo.io.tx_axis_tdata := tx_axis_tdata
  eth_mac_mii_fifo.io.tx_axis_tvalid := tx_axis_tvalid
  tx_axis_tready := eth_mac_mii_fifo.io.tx_axis_tready
  eth_mac_mii_fifo.io.tx_axis_tlast := tx_axis_tlast
  eth_mac_mii_fifo.io.tx_axis_tuser := tx_axis_tuser

  rx_axis_tdata := eth_mac_mii_fifo.io.rx_axis_tdata
  rx_axis_tvalid := eth_mac_mii_fifo.io.rx_axis_tvalid
  eth_mac_mii_fifo.io.rx_axis_tready := rx_axis_tready
  rx_axis_tlast := eth_mac_mii_fifo.io.rx_axis_tlast
  rx_axis_tuser := eth_mac_mii_fifo.io.rx_axis_tuser

  eth_mac_mii_fifo.io.mii_rx_clk := io.phy_rx_clk
  eth_mac_mii_fifo.io.mii_rxd := io.phy_rxd
  eth_mac_mii_fifo.io.mii_rx_dv := io.phy_rx_dv
  eth_mac_mii_fifo.io.mii_rx_er := io.phy_rx_er
  eth_mac_mii_fifo.io.mii_tx_clk := io.phy_tx_clk
  io.phy_txd := eth_mac_mii_fifo.io.mii_txd
  io.phy_tx_en := eth_mac_mii_fifo.io.mii_tx_en
  // io.phy_tx_er := eth_mac_mii_fifo.io.mii_tx_er

  eth_mac_mii_fifo.io.cfg_ifg := 12.U(8.W)
  eth_mac_mii_fifo.io.cfg_tx_enable := 1.U(1.W)
  eth_mac_mii_fifo.io.cfg_rx_enable := 1.U(1.W)

  io.phy_reset_n := !(reset.asBool)


  val valid_last = RegInit(false.B)
  val led_reg = RegInit(0.U(8.W))

  when (tx_fifo_udp_payload_axis_tvalid) {
    when (!valid_last) {
      led_reg := tx_fifo_udp_payload_axis_tdata
      valid_last := true.B
    }
    when (tx_fifo_udp_payload_axis_tlast) {
      valid_last := false.B
    }
  }

  io.led0_g := led_reg(0)
  io.led1_g := led_reg(1)
  io.led2_g := led_reg(2)
  io.led3_g := led_reg(3)
  io.led4 := led_reg(4)
  io.led5 := led_reg(5)
  io.led6 := led_reg(6)
  io.led7 := led_reg(7)

  io.led0_r := 0.U(1.W)
  io.led1_r := 0.U(1.W)
  io.led2_r := 0.U(1.W)
  io.led3_r := 0.U(1.W)
  io.led0_b := 0.U(1.W)
  io.led1_b := 0.U(1.W)
  io.led2_b := 0.U(1.W)
  io.led3_b := 0.U(1.W)




}
