import chisel3._


class eth_axis_rx(
  val DATA_WIDTH: Int = 8
) extends BlackBox (
  Map(
    "DATA_WIDTH" -> DATA_WIDTH
  )
) {
  val io = IO(new Bundle {
    val clk = Input(Clock())
    val rst = Input(Reset())

    val s_axis_tdata = Input(UInt(DATA_WIDTH.W))
    val s_axis_tvalid = Input(Bool())
    val s_axis_tready = Output(Bool()) 
    val s_axis_tlast = Input(Bool())
    val s_axis_tuser = Input(Bool())

    val m_eth_hdr_valid = Output(Bool())
    val m_eth_hdr_ready = Input(Bool())
    val m_eth_dest_mac = Output(UInt(48.W))
    val m_eth_src_mac = Output(UInt(48.W))
    val m_eth_type = Output(UInt(16.W))
    val m_eth_payload_axis_tdata = Output(UInt(DATA_WIDTH.W))
    val m_eth_payload_axis_tvalid = Output(Bool())
    val m_eth_payload_axis_tready = Input(Bool())
    val m_eth_payload_axis_tlast = Output(Bool())
    val m_eth_payload_axis_tuser = Output(Bool())

    val busy = Output(Bool())
    val error_header_early_termination = Output(Bool())
  })
}
