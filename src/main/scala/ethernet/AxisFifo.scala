import chisel3._


class axis_fifo(
  val DEPTH: Int = 8192,
  val DATA_WIDTH: Int = 8,
  val KEEP_ENABLE: Int = 0,
  val ID_ENABLE: Int = 0,
  val ID_WIDTH: Int = 8,
  val DEST_ENABLE: Int = 0,
  val DEST_WIDTH: Int = 8,
  val USER_ENABLE: Int = 1,
  val USER_WIDTH: Int = 1,
  val FRAME_FIFO: Int = 0
) extends BlackBox (
  Map(
    "DEPTH" -> DEPTH,
    "DATA_WIDTH" -> DATA_WIDTH,
    "KEEP_ENABLE" -> KEEP_ENABLE,
    "ID_ENABLE" -> ID_ENABLE,
    "ID_WIDTH" -> ID_WIDTH,
    "DEST_ENABLE" -> DEST_ENABLE,
    "DEST_WIDTH" -> DEST_WIDTH,
    "USER_ENABLE" -> USER_ENABLE,
    "USER_WIDTH" -> USER_WIDTH,
    "FRAME_FIFO" -> FRAME_FIFO
  )
) {
  val io = IO(new Bundle {
    val clk = Input(Clock())
    val rst = Input(Reset())

    val s_axis_tdata = Input(UInt(DATA_WIDTH.W))
    val s_axis_tvalid = Input(Bool())
    val s_axis_tready = Output(Bool()) 
    val s_axis_tlast = Input(Bool())
    val s_axis_tid = Input(UInt(ID_WIDTH.W))
    val s_axis_tdest = Input(UInt(DEST_WIDTH.W))
    val s_axis_tuser = Input(UInt(USER_WIDTH.W))

    val m_axis_tdata = Output(UInt(DATA_WIDTH.W))
    val m_axis_tvalid = Output(Bool())
    val m_axis_tready = Input(Bool()) 
    val m_axis_tlast = Output(Bool())
    val m_axis_tid = Output(UInt(ID_WIDTH.W))
    val m_axis_tdest = Output(UInt(DEST_WIDTH.W))
    val m_axis_tuser = Output(UInt(USER_WIDTH.W))

    // val pause_req = Input(Bool())
    // val pause_ack = Output(Bool())
    
    // val status_depth = Output(UInt(log2Ceil(DEPTH).W))
    // val status_depth_commit = Output(UInt(log2Ceil(DEPTH).W))
    // val status_overflow = Output(Bool())
    // val status_bad_frame = Output(Bool())
    // val status_good_frame = Output(Bool())
  })
}
