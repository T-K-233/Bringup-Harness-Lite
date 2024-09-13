import chisel3._


class eth_mac_mii_fifo (
  val TARGET: String = "XILINX",
  val CLOCK_INPUT_STYLE: String = "BUFR",
  val AXIS_DATA_WIDTH: Int = 8,
  val ENABLE_PADDING: Int = 1,
  val MIN_FRAME_LENGTH: Int = 64,
  val TX_FIFO_DEPTH: Int = 4096,
  val TX_FRAME_FIFO: Int = 1,
  val RX_FIFO_DEPTH: Int = 4096,
  val RX_FRAME_FIFO: Int = 1,
) extends BlackBox (
  Map(
    "TARGET" -> TARGET,
    "CLOCK_INPUT_STYLE" -> CLOCK_INPUT_STYLE,
    "AXIS_DATA_WIDTH" -> AXIS_DATA_WIDTH,
    "ENABLE_PADDING" -> ENABLE_PADDING,
    "MIN_FRAME_LENGTH" -> MIN_FRAME_LENGTH,
    "TX_FIFO_DEPTH" -> TX_FIFO_DEPTH,
    "TX_FRAME_FIFO" -> TX_FRAME_FIFO,
    "RX_FIFO_DEPTH" -> RX_FIFO_DEPTH,
    "RX_FRAME_FIFO" -> RX_FRAME_FIFO
  )
) {
  val io = IO(new Bundle {
    val rst = Input(Reset())

    val logic_clk = Input(Clock())
    val logic_rst = Input(Reset())

    val tx_axis_tdata = Input(UInt(AXIS_DATA_WIDTH.W))
    val tx_axis_tvalid = Input(Bool())
    val tx_axis_tready = Output(Bool()) 
    val tx_axis_tlast = Input(Bool())
    val tx_axis_tuser = Input(UInt(1.W))

    val rx_axis_tdata = Output(UInt(AXIS_DATA_WIDTH.W))
    val rx_axis_tvalid = Output(Bool())
    val rx_axis_tready = Input(Bool()) 
    val rx_axis_tlast = Output(Bool())
    val rx_axis_tuser = Output(UInt(1.W))
    
    val mii_rx_clk = Input(Clock())
    val mii_rxd = Input(UInt(8.W))
    val mii_rx_dv = Input(Bool())
    val mii_rx_er = Input(Bool())
    val mii_tx_clk = Input(Clock())
    val mii_txd = Output(UInt(8.W))
    val mii_tx_en = Output(Bool())
    val mii_tx_er = Output(Bool())


    val tx_error_underflow = Output(Bool())
    val tx_fifo_overflow = Output(Bool())
    val tx_fifo_bad_frame = Output(Bool())
    val tx_fifo_good_frame = Output(Bool())
    val rx_error_bad_frame = Output(Bool())
    val rx_error_bad_fcs = Output(Bool())
    val rx_fifo_overflow = Output(Bool())
    val rx_fifo_bad_frame = Output(Bool())
    val rx_fifo_good_frame = Output(Bool())

    val cfg_ifg = Input(UInt(8.W))
    val cfg_tx_enable = Input(Bool())
    val cfg_rx_enable = Input(Bool())
  })
}
