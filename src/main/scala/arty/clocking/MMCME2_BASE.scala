import chisel3._
import chisel3.util._


// MMCM instance
// 100 MHz in, 125 MHz out
// PFD range: 10 MHz to 550 MHz
// VCO range: 600 MHz to 1200 MHz
// M = 10, D = 1 sets Fvco = 1000 MHz (in range)
// Divide by 8 to get output frequency of 125 MHz
// Divide by 40 to get output frequency of 25 MHz
// 1000 / 5 = 200 MHz
class MMCME2_BASE (
  val BANDWIDTH: String = "OPTIMIZED",
  val CLKOUT0_DIVIDE_F: Int = 10,
  val CLKOUT0_DUTY_CYCLE: Double = 0.5,
  val CLKOUT0_PHASE: Int = 0,
  val CLKOUT1_DIVIDE: Int = 40,
  val CLKOUT1_DUTY_CYCLE: Double = 0.5,
  val CLKOUT1_PHASE: Int = 0,
  val CLKOUT2_DIVIDE: Int = 2,
  val CLKOUT2_DUTY_CYCLE: Double = 0.5,
  val CLKOUT2_PHASE: Int = 0,
  val CLKOUT3_DIVIDE: Int = 2,
  val CLKOUT3_DUTY_CYCLE: Double = 0.5,
  val CLKOUT3_PHASE: Int = 0,
  val CLKOUT4_DIVIDE: Int = 1,
  val CLKOUT4_DUTY_CYCLE: Double = 0.5,
  val CLKOUT4_PHASE: Int = 0,
  val CLKOUT5_DIVIDE: Int = 1,
  val CLKOUT5_DUTY_CYCLE: Double = 0.5,
  val CLKOUT5_PHASE: Int = 0,
  val CLKOUT6_DIVIDE: Int = 1,
  val CLKOUT6_DUTY_CYCLE: Double = 0.5,
  val CLKOUT6_PHASE: Int = 0,
  val CLKFBOUT_MULT_F: Int = 10,
  val CLKFBOUT_PHASE: Int = 0,
  val DIVCLK_DIVIDE: Int = 1,
  val REF_JITTER1: Double = 0.015,
  val CLKIN1_PERIOD: Double = 10.0,
  val STARTUP_WAIT: String = "FALSE",
  val CLKOUT4_CASCADE: String = "FALSE"
) extends BlackBox(
  Map(
    "BANDWIDTH" -> BANDWIDTH,
    "CLKOUT0_DIVIDE_F" -> CLKOUT0_DIVIDE_F,
    "CLKOUT0_DUTY_CYCLE" -> CLKOUT0_DUTY_CYCLE,
    "CLKOUT0_PHASE" -> CLKOUT0_PHASE,
    "CLKOUT1_DIVIDE" -> CLKOUT1_DIVIDE,
    "CLKOUT1_DUTY_CYCLE" -> CLKOUT1_DUTY_CYCLE,
    "CLKOUT1_PHASE" -> CLKOUT1_PHASE,
    "CLKOUT2_DIVIDE" -> CLKOUT2_DIVIDE,
    "CLKOUT2_DUTY_CYCLE" -> CLKOUT2_DUTY_CYCLE,
    "CLKOUT2_PHASE" -> CLKOUT2_PHASE,
    "CLKOUT3_DIVIDE" -> CLKOUT3_DIVIDE,
    "CLKOUT3_DUTY_CYCLE" -> CLKOUT3_DUTY_CYCLE,
    "CLKOUT3_PHASE" -> CLKOUT3_PHASE,
    "CLKOUT4_DIVIDE" -> CLKOUT4_DIVIDE,
    "CLKOUT4_DUTY_CYCLE" -> CLKOUT4_DUTY_CYCLE,
    "CLKOUT4_PHASE" -> CLKOUT4_PHASE,
    "CLKOUT5_DIVIDE" -> CLKOUT5_DIVIDE,
    "CLKOUT5_DUTY_CYCLE" -> CLKOUT5_DUTY_CYCLE,
    "CLKOUT5_PHASE" -> CLKOUT5_PHASE,
    "CLKOUT6_DIVIDE" -> CLKOUT6_DIVIDE,
    "CLKOUT6_DUTY_CYCLE" -> CLKOUT6_DUTY_CYCLE,
    "CLKOUT6_PHASE" -> CLKOUT6_PHASE,
    "CLKFBOUT_MULT_F" -> CLKFBOUT_MULT_F,
    "CLKFBOUT_PHASE" -> CLKFBOUT_PHASE,
    "DIVCLK_DIVIDE" -> DIVCLK_DIVIDE,
    "REF_JITTER1" -> REF_JITTER1,
    "CLKIN1_PERIOD" -> CLKIN1_PERIOD,
    "STARTUP_WAIT" -> STARTUP_WAIT,
    "CLKOUT4_CASCADE" -> CLKOUT4_CASCADE
  )) {

  val io = IO(new Bundle {
    val CLKIN1 = Input(Clock())
    val CLKFBIN = Input(Clock())
    val RST = Input(Bool())
    val PWRDWN = Input(Bool())
    val CLKOUT0 = Output(Clock())
    val CLKOUT0B = Output(Clock())
    val CLKOUT1 = Output(Clock())
    val CLKOUT1B = Output(Clock())
    val CLKOUT2 = Output(Clock())
    val CLKOUT2B = Output(Clock())
    val CLKOUT3 = Output(Clock())
    val CLKOUT3B = Output(Clock())
    val CLKOUT4 = Output(Clock())
    val CLKOUT5 = Output(Clock())
    val CLKOUT6 = Output(Clock())
    val CLKFBOUT = Output(Clock())
    val CLKFBOUTB = Output(Clock())
    val LOCKED = Output(Bool())
  })

}

