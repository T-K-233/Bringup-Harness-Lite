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

  io.phy_tx_clk := eth_wrapper.io.phy_tx_clk
  io.phy_tx_en := eth_wrapper.io.phy_tx_en
  io.phy_txd := eth_wrapper.io.phy_txd

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
}
