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
    val phy_rx_clk = Input(Bool())
    val phy_rx_dv = Input(Bool())
    val phy_rxd = Input(UInt(4.W))
    val phy_rx_er = Input(Bool())
    val phy_tx_clk = Output(Bool())
    val phy_tx_en = Output(Bool())
    val phy_txd = Output(UInt(4.W))
  })

  val eth_wrapper = Module(new EthernetWrap)

  eth_wrapper.io.clock := clock
  eth_wrapper.io.reset := reset

  io.led0_r := eth_wrapper.io.led0_r
  io.led0_g := eth_wrapper.io.led0_g
  io.led0_b := eth_wrapper.io.led0_b
  io.led1_r := eth_wrapper.io.led1_r
  io.led1_g := eth_wrapper.io.led1_g
  io.led1_b := eth_wrapper.io.led1_b
  io.led2_r := eth_wrapper.io.led2_r
  io.led2_g := eth_wrapper.io.led2_g
  io.led2_b := eth_wrapper.io.led2_b
  io.led3_r := eth_wrapper.io.led3_r
  io.led3_g := eth_wrapper.io.led3_g
  io.led3_b := eth_wrapper.io.led3_b
  io.led4 := eth_wrapper.io.led4
  io.led5 := eth_wrapper.io.led5
  io.led6 := eth_wrapper.io.led6
  io.led7 := eth_wrapper.io.led7

  eth_wrapper.io.phy_col := io.phy_col  
  eth_wrapper.io.phy_crs := io.phy_crs
  io.phy_reset_n := eth_wrapper.io.phy_reset_n
  eth_wrapper.io.phy_rx_clk := io.phy_rx_clk
  eth_wrapper.io.phy_rx_dv := io.phy_rx_dv
  eth_wrapper.io.phy_rxd := io.phy_rxd
  eth_wrapper.io.phy_rx_er := io.phy_rx_er
  io.phy_tx_clk := eth_wrapper.io.phy_tx_clk
  io.phy_tx_en := eth_wrapper.io.phy_tx_en
  io.phy_txd := eth_wrapper.io.phy_txd

  val eth_axis_tx = Module(new eth_axis_tx)

  eth_axis_tx.io.clk := clock
  eth_axis_tx.io.rst := reset

  eth_axis_tx.io.s_eth_hdr_valid := eth_wrapper.io.tx_eth_hdr_valid
  eth_wrapper.io.tx_eth_hdr_ready := eth_axis_tx.io.s_eth_hdr_ready
  eth_axis_tx.io.s_eth_dest_mac := eth_wrapper.io.tx_eth_dest_mac
  eth_axis_tx.io.s_eth_src_mac := eth_wrapper.io.tx_eth_src_mac
  eth_axis_tx.io.s_eth_type := eth_wrapper.io.tx_eth_type
  eth_axis_tx.io.s_eth_payload_axis_tdata := eth_wrapper.io.tx_eth_payload_axis_tdata
  eth_axis_tx.io.s_eth_payload_axis_tvalid := eth_wrapper.io.tx_eth_payload_axis_tvalid
  eth_wrapper.io.tx_eth_payload_axis_tready := eth_axis_tx.io.s_eth_payload_axis_tready
  eth_axis_tx.io.s_eth_payload_axis_tlast := eth_wrapper.io.tx_eth_payload_axis_tlast
  eth_axis_tx.io.s_eth_payload_axis_tuser := eth_wrapper.io.tx_eth_payload_axis_tuser

  eth_wrapper.io.tx_axis_tdata := eth_axis_tx.io.m_axis_tdata
  eth_wrapper.io.tx_axis_tvalid := eth_axis_tx.io.m_axis_tvalid
  eth_axis_tx.io.m_axis_tready := eth_wrapper.io.tx_axis_tready
  eth_wrapper.io.tx_axis_tlast := eth_axis_tx.io.m_axis_tlast
  eth_wrapper.io.tx_axis_tuser := eth_axis_tx.io.m_axis_tuser

  val eth_axis_rx = Module(new eth_axis_rx)

  eth_axis_rx.io.clk := clock
  eth_axis_rx.io.rst := reset

  eth_axis_rx.io.s_axis_tdata := eth_wrapper.io.rx_axis_tdata
  eth_axis_rx.io.s_axis_tvalid := eth_wrapper.io.rx_axis_tvalid
  eth_wrapper.io.rx_axis_tready := eth_axis_rx.io.s_axis_tready
  eth_axis_rx.io.s_axis_tlast := eth_wrapper.io.rx_axis_tlast
  eth_axis_rx.io.s_axis_tuser := eth_wrapper.io.rx_axis_tuser

  eth_wrapper.io.rx_eth_hdr_valid := eth_axis_rx.io.m_eth_hdr_valid
  eth_axis_rx.io.m_eth_hdr_ready := eth_wrapper.io.rx_eth_hdr_ready
  eth_wrapper.io.rx_eth_dest_mac := eth_axis_rx.io.m_eth_dest_mac
  eth_wrapper.io.rx_eth_src_mac := eth_axis_rx.io.m_eth_src_mac
  eth_wrapper.io.rx_eth_type := eth_axis_rx.io.m_eth_type
  eth_wrapper.io.rx_eth_payload_axis_tdata := eth_axis_rx.io.m_eth_payload_axis_tdata
  eth_wrapper.io.rx_eth_payload_axis_tvalid := eth_axis_rx.io.m_eth_payload_axis_tvalid
  eth_axis_rx.io.m_eth_payload_axis_tready := eth_wrapper.io.rx_eth_payload_axis_tready
  eth_wrapper.io.rx_eth_payload_axis_tlast := eth_axis_rx.io.m_eth_payload_axis_tlast
  eth_wrapper.io.rx_eth_payload_axis_tuser := eth_axis_rx.io.m_eth_payload_axis_tuser

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

  udp_payload_fifo.io.s_axis_tdata := eth_wrapper.io.rx_fifo_udp_payload_axis_tdata
  udp_payload_fifo.io.s_axis_tvalid := eth_wrapper.io.rx_fifo_udp_payload_axis_tvalid
  eth_wrapper.io.rx_fifo_udp_payload_axis_tready := udp_payload_fifo.io.s_axis_tready
  udp_payload_fifo.io.s_axis_tlast := eth_wrapper.io.rx_fifo_udp_payload_axis_tlast
  udp_payload_fifo.io.s_axis_tid := 0.U(8.W)
  udp_payload_fifo.io.s_axis_tdest := 0.U(8.W)
  udp_payload_fifo.io.s_axis_tuser := eth_wrapper.io.rx_fifo_udp_payload_axis_tuser

  eth_wrapper.io.tx_fifo_udp_payload_axis_tdata := udp_payload_fifo.io.m_axis_tdata
  eth_wrapper.io.tx_fifo_udp_payload_axis_tvalid := udp_payload_fifo.io.m_axis_tvalid
  udp_payload_fifo.io.m_axis_tready := eth_wrapper.io.tx_fifo_udp_payload_axis_tready
  eth_wrapper.io.tx_fifo_udp_payload_axis_tlast := udp_payload_fifo.io.m_axis_tlast
  eth_wrapper.io.tx_fifo_udp_payload_axis_tuser := udp_payload_fifo.io.m_axis_tuser


}
