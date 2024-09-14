import chisel3._
import chisel3.util.HasBlackBoxInline


class EthernetWrap extends BlackBox with HasBlackBoxInline {
  val io = IO(new Bundle {
    val clock = Input(Clock())
    val reset = Input(Reset())

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


    val phy_reset_n = Output(Bool())

    val tx_eth_hdr_ready = Input(Bool())

    val rx_udp_hdr_ready = Output(Bool())

    
    val tx_ip_hdr_valid = Output(Bool())
    val tx_ip_hdr_ready = Input(Bool())
    val tx_ip_dscp = Output(UInt(6.W))
    val tx_ip_ecn = Output(UInt(2.W))
    val tx_ip_length = Output(UInt(16.W))
    val tx_ip_ttl = Output(UInt(8.W))
    val tx_ip_protocol = Output(UInt(8.W))
    val tx_ip_source_ip = Output(UInt(32.W))
    val tx_ip_dest_ip = Output(UInt(32.W))
    val tx_ip_payload_axis_tdata = Output(UInt(8.W))
    val tx_ip_payload_axis_tvalid = Output(Bool())
    val tx_ip_payload_axis_tready = Input(Bool())
    val tx_ip_payload_axis_tlast = Output(Bool())
    val tx_ip_payload_axis_tuser = Output(Bool())



    val tx_udp_hdr_valid = Output(Bool())
    val tx_udp_ip_dscp = Output(UInt(6.W))
    val tx_udp_ip_ecn = Output(UInt(2.W))
    val tx_udp_ip_ttl = Output(UInt(8.W))
    val tx_udp_ip_source_ip = Output(UInt(32.W))
    val tx_udp_ip_dest_ip = Output(UInt(32.W))
    val tx_udp_source_port = Output(UInt(16.W))
    val tx_udp_dest_port = Output(UInt(16.W))
    val tx_udp_length = Output(UInt(16.W))
    val tx_udp_checksum = Output(UInt(16.W))



    val tx_fifo_udp_payload_axis_tdata = Input(UInt(8.W))
    val tx_fifo_udp_payload_axis_tvalid = Input(Bool())
    val tx_fifo_udp_payload_axis_tlast = Input(Bool())
    val tx_fifo_udp_payload_axis_tuser = Input(Bool())


    val rx_udp_hdr_valid = Input(Bool())

    val rx_udp_payload_axis_tvalid = Input(Bool())
    val rx_udp_payload_axis_tlast = Input(Bool())
    
  })

  setInline("EthernetWrap.v",
    """module EthernetWrap (
      |// Language: Verilog 2001
      |
      |    /*
      |     * Clock: 100MHz
      |     * Reset: Push button, active low
      |     */
      |    input  wire       clock,
      |    input  wire       reset,
      |
      |    output wire       led0_r,
      |    output wire       led0_g,
      |    output wire       led0_b,
      |    output wire       led1_r,
      |    output wire       led1_g,
      |    output wire       led1_b,
      |    output wire       led2_r,
      |    output wire       led2_g,
      |    output wire       led2_b,
      |    output wire       led3_r,
      |    output wire       led3_g,
      |    output wire       led3_b,
      |    output wire       led4,
      |    output wire       led5,
      |    output wire       led6,
      |    output wire       led7,
      |
      |    /*
      |     * Ethernet: 100BASE-T MII
      |     */
      |    output wire       phy_reset_n,
      |
      |
      |
      |input tx_eth_hdr_ready,
      |
      |output rx_udp_hdr_ready,
      |
      |
      |output tx_ip_hdr_valid,
      |input tx_ip_hdr_ready,
      |output [5:0] tx_ip_dscp,
      |output [1:0] tx_ip_ecn,
      |output [15:0] tx_ip_length,
      |output [7:0] tx_ip_ttl,
      |output [7:0] tx_ip_protocol,
      |output [31:0] tx_ip_source_ip,
      |output [31:0] tx_ip_dest_ip,
      |output [7:0] tx_ip_payload_axis_tdata,
      |output tx_ip_payload_axis_tvalid,
      |input tx_ip_payload_axis_tready,
      |output tx_ip_payload_axis_tlast,
      |output tx_ip_payload_axis_tuser,
      |
      |
      |output tx_udp_hdr_valid,
      |input tx_udp_hdr_ready,
      |output [5:0] tx_udp_ip_dscp,
      |output [1:0] tx_udp_ip_ecn,
      |output [7:0] tx_udp_ip_ttl,
      |output [31:0] tx_udp_ip_source_ip,
      |output [31:0] tx_udp_ip_dest_ip,
      |output [15:0] tx_udp_source_port,
      |output [15:0] tx_udp_dest_port,
      |output [15:0] tx_udp_length,
      |output [15:0] tx_udp_checksum,
      |
      |
      |input [7:0] tx_fifo_udp_payload_axis_tdata,
      |input tx_fifo_udp_payload_axis_tvalid,
      |input tx_fifo_udp_payload_axis_tlast,
      |input tx_fifo_udp_payload_axis_tuser,
      |
      |
      |input rx_udp_hdr_valid,
      |
      |input rx_udp_payload_axis_tvalid,
      |input rx_udp_payload_axis_tlast
      |
      |
      |);
|
|
|// IP frame connections
|wire rx_ip_hdr_valid;
|wire rx_ip_hdr_ready;
|wire [47:0] rx_ip_eth_dest_mac;
|wire [47:0] rx_ip_eth_src_mac;
|wire [15:0] rx_ip_eth_type;
|wire [3:0] rx_ip_version;
|wire [3:0] rx_ip_ihl;
|wire [5:0] rx_ip_dscp;
|wire [1:0] rx_ip_ecn;
|wire [15:0] rx_ip_length;
|wire [15:0] rx_ip_identification;
|wire [2:0] rx_ip_flags;
|wire [12:0] rx_ip_fragment_offset;
|wire [7:0] rx_ip_ttl;
|wire [7:0] rx_ip_protocol;
|wire [15:0] rx_ip_header_checksum;
|wire [31:0] rx_ip_source_ip;
|wire [31:0] rx_ip_dest_ip;
|wire [7:0] rx_ip_payload_axis_tdata;
|wire rx_ip_payload_axis_tvalid;
|wire rx_ip_payload_axis_tready;
|wire rx_ip_payload_axis_tlast;
|wire rx_ip_payload_axis_tuser;
|
|
|// UDP frame connections
|wire [47:0] rx_udp_eth_dest_mac;
|wire [47:0] rx_udp_eth_src_mac;
|wire [15:0] rx_udp_eth_type;
|wire [3:0] rx_udp_ip_version;
|wire [3:0] rx_udp_ip_ihl;
|wire [5:0] rx_udp_ip_dscp;
|wire [1:0] rx_udp_ip_ecn;
|wire [15:0] rx_udp_ip_length;
|wire [15:0] rx_udp_ip_identification;
|wire [2:0] rx_udp_ip_flags;
|wire [12:0] rx_udp_ip_fragment_offset;
|wire [7:0] rx_udp_ip_ttl;
|wire [7:0] rx_udp_ip_protocol;
|wire [15:0] rx_udp_ip_header_checksum;
|wire [31:0] rx_udp_ip_source_ip;
|wire [31:0] rx_udp_ip_dest_ip;
|wire [15:0] rx_udp_source_port;
|wire [15:0] rx_udp_dest_port;
|wire [15:0] rx_udp_length;
|wire [15:0] rx_udp_checksum;
|
|
|// Configuration
|wire [47:0] local_mac   = 48'h02_00_00_00_00_00;
|wire [31:0] local_ip    = {8'd172, 8'd28,  8'd0,   8'd6};
|wire [31:0] gateway_ip  = {8'd172, 8'd28,  8'd0,   8'd1};
|wire [31:0] subnet_mask = {8'd255, 8'd255, 8'd255, 8'd0};
|
|// IP ports not used
|assign rx_ip_hdr_ready = 1;
|assign rx_ip_payload_axis_tready = 1;
|
|assign tx_ip_hdr_valid = 0;
|assign tx_ip_dscp = 0;
|assign tx_ip_ecn = 0;
|assign tx_ip_length = 0;
|assign tx_ip_ttl = 0;
|assign tx_ip_protocol = 0;
|assign tx_ip_source_ip = 0;
|assign tx_ip_dest_ip = 0;
|assign tx_ip_payload_axis_tdata = 0;
|assign tx_ip_payload_axis_tvalid = 0;
|assign tx_ip_payload_axis_tlast = 0;
|assign tx_ip_payload_axis_tuser = 0;
|
|
|wire rx_fifo_udp_payload_axis_tvalid;
|wire rx_fifo_udp_payload_axis_tready;
|wire rx_fifo_udp_payload_axis_tlast;
|
|assign rx_fifo_udp_payload_axis_tvalid = rx_udp_payload_axis_tvalid && match_cond_reg;
|assign rx_udp_payload_axis_tready = (rx_fifo_udp_payload_axis_tready && match_cond_reg) || no_match_reg;
|assign rx_fifo_udp_payload_axis_tlast = rx_udp_payload_axis_tlast;
|
|// Loop back UDP
|wire match_cond = rx_udp_dest_port == 1234;
|wire no_match = !match_cond;
|
|reg match_cond_reg = 0;
|reg no_match_reg = 0;
|
|always @(posedge clock) begin
|    if (reset) begin
|        match_cond_reg <= 0;
|        no_match_reg <= 0;
|    end else begin
|        if (rx_udp_payload_axis_tvalid) begin
|            if ((!match_cond_reg && !no_match_reg) ||
|                (rx_udp_payload_axis_tvalid && rx_udp_payload_axis_tready && rx_udp_payload_axis_tlast)) begin
|                match_cond_reg <= match_cond;
|                no_match_reg <= no_match;
|            end
|        end else begin
|            match_cond_reg <= 0;
|            no_match_reg <= 0;
|        end
|    end
|end
|
|assign tx_udp_hdr_valid = rx_udp_hdr_valid && match_cond;
|assign rx_udp_hdr_ready = (tx_eth_hdr_ready && match_cond) || no_match;
|
|
|// Place first payload byte onto LEDs
|reg valid_last = 0;
|reg [7:0] led_reg = 0;
|
|always @(posedge clock) begin
|    if (reset) begin
|        led_reg <= 0;
|    end else begin
|        if (tx_fifo_udp_payload_axis_tvalid) begin
|            if (!valid_last) begin
|                led_reg <= tx_fifo_udp_payload_axis_tdata;
|                valid_last <= 1'b1;
|            end
|            if (tx_fifo_udp_payload_axis_tlast) begin
|                valid_last <= 1'b0;
|            end
|        end
|    end
|end
|
|assign {led0_g, led1_g, led2_g, led3_g, led4, led5, led6, led7} = led_reg;
|
|
|// udp_complete
|// udp_complete_inst (
|//     .clk(clock),
|//     .rst(reset),
|//     // Ethernet frame input
|//     .s_eth_hdr_valid(rx_eth_hdr_valid),
|//     .s_eth_hdr_ready(rx_eth_hdr_ready),
|//     .s_eth_dest_mac(rx_eth_dest_mac),
|//     .s_eth_src_mac(rx_eth_src_mac),
|//     .s_eth_type(rx_eth_type),
|//     .s_eth_payload_axis_tdata(rx_eth_payload_axis_tdata),
|//     .s_eth_payload_axis_tvalid(rx_eth_payload_axis_tvalid),
|//     .s_eth_payload_axis_tready(rx_eth_payload_axis_tready),
|//     .s_eth_payload_axis_tlast(rx_eth_payload_axis_tlast),
|//     .s_eth_payload_axis_tuser(rx_eth_payload_axis_tuser),
|//     // Ethernet frame output
|//     .m_eth_hdr_valid(tx_eth_hdr_valid),
|//     .m_eth_hdr_ready(tx_eth_hdr_ready),
|//     .m_eth_dest_mac(tx_eth_dest_mac),
|//     .m_eth_src_mac(tx_eth_src_mac),
|//     .m_eth_type(tx_eth_type),
|//     .m_eth_payload_axis_tdata(tx_eth_payload_axis_tdata),
|//     .m_eth_payload_axis_tvalid(tx_eth_payload_axis_tvalid),
|//     .m_eth_payload_axis_tready(tx_eth_payload_axis_tready),
|//     .m_eth_payload_axis_tlast(tx_eth_payload_axis_tlast),
|//     .m_eth_payload_axis_tuser(tx_eth_payload_axis_tuser),
|//     // IP frame input
|//     .s_ip_hdr_valid(tx_ip_hdr_valid),
|//     .s_ip_hdr_ready(tx_ip_hdr_ready),
|//     .s_ip_dscp(tx_ip_dscp),
|//     .s_ip_ecn(tx_ip_ecn),
|//     .s_ip_length(tx_ip_length),
|//     .s_ip_ttl(tx_ip_ttl),
|//     .s_ip_protocol(tx_ip_protocol),
|//     .s_ip_source_ip(tx_ip_source_ip),
|//     .s_ip_dest_ip(tx_ip_dest_ip),
|//     .s_ip_payload_axis_tdata(tx_ip_payload_axis_tdata),
|//     .s_ip_payload_axis_tvalid(tx_ip_payload_axis_tvalid),
|//     .s_ip_payload_axis_tready(tx_ip_payload_axis_tready),
|//     .s_ip_payload_axis_tlast(tx_ip_payload_axis_tlast),
|//     .s_ip_payload_axis_tuser(tx_ip_payload_axis_tuser),
|//     // IP frame output
|//     .m_ip_hdr_valid(rx_ip_hdr_valid),
|//     .m_ip_hdr_ready(rx_ip_hdr_ready),
|//     .m_ip_eth_dest_mac(rx_ip_eth_dest_mac),
|//     .m_ip_eth_src_mac(rx_ip_eth_src_mac),
|//     .m_ip_eth_type(rx_ip_eth_type),
|//     .m_ip_version(rx_ip_version),
|//     .m_ip_ihl(rx_ip_ihl),
|//     .m_ip_dscp(rx_ip_dscp),
|//     .m_ip_ecn(rx_ip_ecn),
|//     .m_ip_length(rx_ip_length),
|//     .m_ip_identification(rx_ip_identification),
|//     .m_ip_flags(rx_ip_flags),
|//     .m_ip_fragment_offset(rx_ip_fragment_offset),
|//     .m_ip_ttl(rx_ip_ttl),
|//     .m_ip_protocol(rx_ip_protocol),
|//     .m_ip_header_checksum(rx_ip_header_checksum),
|//     .m_ip_source_ip(rx_ip_source_ip),
|//     .m_ip_dest_ip(rx_ip_dest_ip),
|//     .m_ip_payload_axis_tdata(rx_ip_payload_axis_tdata),
|//     .m_ip_payload_axis_tvalid(rx_ip_payload_axis_tvalid),
|//     .m_ip_payload_axis_tready(rx_ip_payload_axis_tready),
|//     .m_ip_payload_axis_tlast(rx_ip_payload_axis_tlast),
|//     .m_ip_payload_axis_tuser(rx_ip_payload_axis_tuser),
|//     // UDP frame input
|//     .s_udp_hdr_valid(tx_udp_hdr_valid),
|//     .s_udp_hdr_ready(tx_udp_hdr_ready),
|//     .s_udp_ip_dscp(tx_udp_ip_dscp),
|//     .s_udp_ip_ecn(tx_udp_ip_ecn),
|//     .s_udp_ip_ttl(tx_udp_ip_ttl),
|//     .s_udp_ip_source_ip(tx_udp_ip_source_ip),
|//     .s_udp_ip_dest_ip(tx_udp_ip_dest_ip),
|//     .s_udp_source_port(tx_udp_source_port),
|//     .s_udp_dest_port(tx_udp_dest_port),
|//     .s_udp_length(tx_udp_length),
|//     .s_udp_checksum(tx_udp_checksum),
|//     .s_udp_payload_axis_tdata(tx_fifo_udp_payload_axis_tdata),
|//     .s_udp_payload_axis_tvalid(tx_fifo_udp_payload_axis_tvalid),
|//     .s_udp_payload_axis_tready(tx_udp_payload_axis_tready),
|//     .s_udp_payload_axis_tlast(tx_fifo_udp_payload_axis_tlast),
|//     .s_udp_payload_axis_tuser(tx_udp_payload_axis_tuser),
|//     // UDP frame output
|//     .m_udp_hdr_valid(rx_udp_hdr_valid),
|//     .m_udp_hdr_ready(rx_udp_hdr_ready),
|//     .m_udp_eth_dest_mac(rx_udp_eth_dest_mac),
|//     .m_udp_eth_src_mac(rx_udp_eth_src_mac),
|//     .m_udp_eth_type(rx_udp_eth_type),
|//     .m_udp_ip_version(rx_udp_ip_version),
|//     .m_udp_ip_ihl(rx_udp_ip_ihl),
|//     .m_udp_ip_dscp(rx_udp_ip_dscp),
|//     .m_udp_ip_ecn(rx_udp_ip_ecn),
|//     .m_udp_ip_length(rx_udp_ip_length),
|//     .m_udp_ip_identification(rx_udp_ip_identification),
|//     .m_udp_ip_flags(rx_udp_ip_flags),
|//     .m_udp_ip_fragment_offset(rx_udp_ip_fragment_offset),
|//     .m_udp_ip_ttl(rx_udp_ip_ttl),
|//     .m_udp_ip_protocol(rx_udp_ip_protocol),
|//     .m_udp_ip_header_checksum(rx_udp_ip_header_checksum),
|//     .m_udp_ip_source_ip(rx_udp_ip_source_ip),
|//     .m_udp_ip_dest_ip(rx_udp_ip_dest_ip),
|//     .m_udp_source_port(rx_udp_source_port),
|//     .m_udp_dest_port(rx_udp_dest_port),
|//     .m_udp_length(rx_udp_length),
|//     .m_udp_checksum(rx_udp_checksum),
|//     .m_udp_payload_axis_tdata(rx_udp_payload_axis_tdata),
|//     .m_udp_payload_axis_tvalid(rx_udp_payload_axis_tvalid),
|//     .m_udp_payload_axis_tready(rx_udp_payload_axis_tready),
|//     .m_udp_payload_axis_tlast(rx_udp_payload_axis_tlast),
|//     .m_udp_payload_axis_tuser(rx_udp_payload_axis_tuser),
|//     // Status signals
|//     .ip_rx_busy(),
|//     .ip_tx_busy(),
|//     .udp_rx_busy(),
|//     .udp_tx_busy(),
|//     .ip_rx_error_header_early_termination(),
|//     .ip_rx_error_payload_early_termination(),
|//     .ip_rx_error_invalid_header(),
|//     .ip_rx_error_invalid_checksum(),
|//     .ip_tx_error_payload_early_termination(),
|//     .ip_tx_error_arp_failed(),
|//     .udp_rx_error_header_early_termination(),
|//     .udp_rx_error_payload_early_termination(),
|//     .udp_tx_error_payload_early_termination(),
|//     // Configuration
|//     .local_mac(local_mac),
|//     .local_ip(local_ip),
|//     .gateway_ip(gateway_ip),
|//     .subnet_mask(subnet_mask),
|//     .clear_arp_cache(0)
|// );
      |
      |endmodule
      |
      |`resetall
      |""".stripMargin)


}
