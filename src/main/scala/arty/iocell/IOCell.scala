import chisel3._
import chisel3.util._


class IBUFG extends BlackBox {
  val io = IO(new Bundle {
    val I = Input(Clock())
    val O = Output(Clock())
  })
}



class BUFG extends BlackBox {
  val io = IO(new Bundle {
    val I = Input(Clock())
    val O = Output(Clock())
  })
}
