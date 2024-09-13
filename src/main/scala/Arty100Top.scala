import chisel3._
import chisel3.util._


class Arty100Top extends RawModule {
  val CLK100MHZ = IO(Input(Clock()))
  val ck_rst = IO(Input(Bool()))

  val sw = IO(Input(UInt(4.W)))
  val btn = IO(Input(UInt(4.W)))
  val led = IO(Output(UInt(4.W)))

  val led0_b = IO(Output(Bool()))
  val led0_g = IO(Output(Bool()))
  val led0_r = IO(Output(Bool()))
  val led1_b = IO(Output(Bool()))
  val led1_g = IO(Output(Bool()))
  val led1_r = IO(Output(Bool()))
  val led2_b = IO(Output(Bool()))
  val led2_g = IO(Output(Bool()))
  val led2_r = IO(Output(Bool()))
  val led3_b = IO(Output(Bool()))
  val led3_g = IO(Output(Bool()))
  val led3_r = IO(Output(Bool()))

  val uart_rxd_out = IO(Output(Bool()))
  val uart_txd_in = IO(Input(Bool()))

  val eth_col = IO(Input(Bool()))
  val eth_crs = IO(Input(Bool()))
  val eth_ref_clk = IO(Output(Clock()))
  val eth_rstn = IO(Output(Bool()))
  val eth_rx_clk = IO(Input(Clock()))
  val eth_rx_dv = IO(Input(Bool()))
  val eth_rxd = IO(Input(UInt(4.W)))
  val eth_rxerr = IO(Input(Bool()))
  val eth_tx_clk = IO(Input(Clock()))
  val eth_tx_en = IO(Output(Bool()))
  val eth_txd = IO(Output(UInt(4.W)))

  val reset = Wire(Reset())
  val clock = Wire(Clock())

  val clk_ibufg = Wire(Clock())
  val clk_ibufg_inst = Module(new IBUFG())
  clk_ibufg_inst.io.I := CLK100MHZ

  clk_ibufg := clk_ibufg_inst.io.O

  val mmcm_clkfb = Wire(Clock())
  val clk_mmcm_out = Wire(Clock())
  val clk_25mhz_mmcm_out = Wire(Clock())
  val mmcm_locked = Wire(Bool())


  val mmcm = Module(new MMCME2_BASE(
    BANDWIDTH = "OPTIMIZED",
    CLKOUT0_DIVIDE_F = 8,
    CLKOUT0_DUTY_CYCLE = 0.5,
    CLKOUT0_PHASE = 0,
    CLKOUT1_DIVIDE = 40,
    CLKOUT1_DUTY_CYCLE = 0.5,
    CLKOUT1_PHASE = 0,
    CLKOUT2_DIVIDE = 1,
    CLKOUT2_DUTY_CYCLE = 0.5,
    CLKOUT2_PHASE = 0,
    CLKOUT3_DIVIDE = 1,
    CLKOUT3_DUTY_CYCLE = 0.5,
    CLKOUT3_PHASE = 0,
    CLKOUT4_DIVIDE = 1,
    CLKOUT4_DUTY_CYCLE = 0.5,
    CLKOUT4_PHASE = 0,
    CLKOUT5_DIVIDE = 1,
    CLKOUT5_DUTY_CYCLE = 0.5,
    CLKOUT5_PHASE = 0,
    CLKOUT6_DIVIDE = 1,
    CLKOUT6_DUTY_CYCLE = 0.5,
    CLKOUT6_PHASE = 0,
    CLKFBOUT_MULT_F = 10,
    CLKFBOUT_PHASE = 0,
    DIVCLK_DIVIDE = 1,
    REF_JITTER1 = 0.010,
    CLKIN1_PERIOD = 10.0,
    STARTUP_WAIT = "FALSE",
    CLKOUT4_CASCADE = "FALSE"
  ))
  mmcm.io.CLKIN1 := clk_ibufg
  mmcm.io.CLKFBIN := mmcm_clkfb
  mmcm.io.RST := ~ck_rst
  mmcm.io.PWRDWN := 0.U
  clk_mmcm_out := mmcm.io.CLKOUT0
  clk_25mhz_mmcm_out := mmcm.io.CLKOUT1
  mmcm_clkfb := mmcm.io.CLKFBOUT
  mmcm_locked := mmcm.io.LOCKED
  
  val clk_25mhz_bufg_inst = Module(new BUFG())
  clk_25mhz_bufg_inst.io.I := clk_25mhz_mmcm_out
  eth_ref_clk := clk_25mhz_bufg_inst.io.O

  val clk_bufg_inst = Module(new BUFG())
  clk_bufg_inst.io.I := clk_mmcm_out
  clock := clk_bufg_inst.io.O


  val sync_reset_inst = Module(new SyncReset(4))
  sync_reset_inst.io.clk := clk_mmcm_out
  sync_reset_inst.io.rst := ~mmcm_locked
  reset := sync_reset_inst.io.out



  withClockAndReset(clock, reset) {
    val eth = Module(new Ethernet())
    led := Cat(eth.io.led7, eth.io.led6, eth.io.led5, eth.io.led4)

    led0_r := eth.io.led0_r
    led0_g := eth.io.led0_g
    led0_b := eth.io.led0_b
    led1_r := eth.io.led1_r
    led1_g := eth.io.led1_g
    led1_b := eth.io.led1_b
    led2_r := eth.io.led2_r
    led2_g := eth.io.led2_g
    led2_b := eth.io.led2_b
    led3_r := eth.io.led3_r
    led3_g := eth.io.led3_g
    led3_b := eth.io.led3_b

    uart_rxd_out := uart_txd_in

    eth.io.phy_col := eth_col
    eth.io.phy_crs := eth_crs
    eth_rstn := eth.io.phy_reset_n
    eth.io.phy_rx_clk := eth_rx_clk
    eth.io.phy_rx_dv := eth_rx_dv
    eth.io.phy_rxd := eth_rxd
    eth.io.phy_rx_er := eth_rxerr
    eth.io.phy_tx_clk := eth_tx_clk
    eth_tx_en := eth.io.phy_tx_en
    eth_txd := eth.io.phy_txd
  }

}

