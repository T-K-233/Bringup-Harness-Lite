import chisel3._
import chisel3.util._

import chisel3.util.HasBlackBoxInline

class SyncReset(val N: Int) extends BlackBox(Map("N" -> N)) with HasBlackBoxInline {
  val io = IO(new Bundle {
    val clk = Input(Clock())
    val rst = Input(Bool())
    val out = Output(Bool())
  })

  setInline("SyncReset.v",
    s"""module SyncReset #(
      |  parameter N = ${N}
      |)(
      |  input clk,
      |  input rst,
      |  output out
      |);
      |  
      |  sync_reset #(
      |    .N(${N})
      |  ) sync_reset_inst (
      |    .clk(clk),
      |    .rst(rst),
      |    .out(out)
      |  );
      |endmodule
    """.stripMargin
  )

}

