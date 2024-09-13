import chisel3._


class eth_axis_tx(
  val DATA_WIDTH: Int = 8
) extends BlackBox (
  Map(
    "DATA_WIDTH" -> DATA_WIDTH
  )
) {
  val io = IO(new Bundle {
    val clk = Input(Clock())
    val rst = Input(Reset())
    val s_eth_hdr_valid = Input(Bool())
    val s_eth_hdr_ready = Output(Bool())
    val s_eth_dest_mac = Input(UInt(48.W))
    val s_eth_src_mac = Input(UInt(48.W))
    val s_eth_type = Input(UInt(16.W))
    val s_eth_payload_axis_tdata = Input(UInt(DATA_WIDTH.W))
    val s_eth_payload_axis_tvalid = Input(Bool())
    val s_eth_payload_axis_tready = Output(Bool())
    val s_eth_payload_axis_tlast = Input(Bool())
    val s_eth_payload_axis_tuser = Input(Bool())

    val m_axis_tdata = Output(UInt(DATA_WIDTH.W))
    val m_axis_tvalid = Output(Bool())
    val m_axis_tready = Input(Bool()) 
    val m_axis_tlast = Output(Bool())
    val m_axis_tuser = Output(Bool())

    val busy = Output(Bool())
  })
}
