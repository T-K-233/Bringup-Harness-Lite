// Generated by CIRCT firtool-1.62.0
module Ethernet(
  input        clock,
               reset,
  output       io_led0_g,
               io_led1_g,
               io_led2_g,
               io_led3_g,
               io_led4,
               io_led5,
               io_led6,
               io_led7,
               io_phy_reset_n,
  input        io_phy_rx_clk,
               io_phy_rx_dv,
  input  [3:0] io_phy_rxd,
  input        io_phy_rx_er,
               io_phy_tx_clk,
  output       io_phy_tx_en,
  output [3:0] io_phy_txd
);

  wire        _eth_mac_mii_fifo_tx_axis_tready;
  wire [7:0]  _eth_mac_mii_fifo_rx_axis_tdata;
  wire        _eth_mac_mii_fifo_rx_axis_tvalid;
  wire        _eth_mac_mii_fifo_rx_axis_tlast;
  wire        _eth_mac_mii_fifo_rx_axis_tuser;
  wire [7:0]  _eth_mac_mii_fifo_mii_txd;
  wire        _udp_payload_fifo_s_axis_tready;
  wire [7:0]  _udp_payload_fifo_m_axis_tdata;
  wire        _udp_payload_fifo_m_axis_tvalid;
  wire        _udp_payload_fifo_m_axis_tlast;
  wire        _udp_payload_fifo_m_axis_tuser;
  wire        _eth_axis_rx_s_axis_tready;
  wire        _eth_axis_rx_m_eth_hdr_valid;
  wire [47:0] _eth_axis_rx_m_eth_dest_mac;
  wire [47:0] _eth_axis_rx_m_eth_src_mac;
  wire [15:0] _eth_axis_rx_m_eth_type;
  wire [7:0]  _eth_axis_rx_m_eth_payload_axis_tdata;
  wire        _eth_axis_rx_m_eth_payload_axis_tvalid;
  wire        _eth_axis_rx_m_eth_payload_axis_tlast;
  wire        _eth_axis_rx_m_eth_payload_axis_tuser;
  wire        _eth_axis_tx_s_eth_hdr_ready;
  wire        _eth_axis_tx_s_eth_payload_axis_tready;
  wire [7:0]  _eth_axis_tx_m_axis_tdata;
  wire        _eth_axis_tx_m_axis_tvalid;
  wire        _eth_axis_tx_m_axis_tlast;
  wire        _eth_axis_tx_m_axis_tuser;
  wire        _udp_complete_s_eth_hdr_ready;
  wire        _udp_complete_s_eth_payload_axis_tready;
  wire        _udp_complete_m_eth_hdr_valid;
  wire [47:0] _udp_complete_m_eth_dest_mac;
  wire [47:0] _udp_complete_m_eth_src_mac;
  wire [15:0] _udp_complete_m_eth_type;
  wire [7:0]  _udp_complete_m_eth_payload_axis_tdata;
  wire        _udp_complete_m_eth_payload_axis_tvalid;
  wire        _udp_complete_m_eth_payload_axis_tlast;
  wire        _udp_complete_m_eth_payload_axis_tuser;
  wire [31:0] _udp_complete_m_ip_source_ip;
  wire        _udp_complete_s_udp_payload_axis_tready;
  wire [15:0] _udp_complete_m_udp_source_port;
  wire [15:0] _udp_complete_m_udp_dest_port;
  wire [15:0] _udp_complete_m_udp_length;
  wire [7:0]  _udp_complete_m_udp_payload_axis_tdata;
  wire        _udp_complete_m_udp_payload_axis_tvalid;
  wire        _udp_complete_m_udp_payload_axis_tlast;
  wire        _udp_complete_m_udp_payload_axis_tuser;
  wire        match_cond = _udp_complete_m_udp_dest_port == 16'h4D2;
  reg         valid_last;
  reg  [7:0]  led_reg;
  always @(posedge clock) begin
    if (reset) begin
      valid_last <= 1'h0;
      led_reg <= 8'h0;
    end
    else begin
      if (_udp_payload_fifo_m_axis_tvalid)
        valid_last <= ~_udp_payload_fifo_m_axis_tlast;
      if (_udp_payload_fifo_m_axis_tvalid & ~valid_last)
        led_reg <= _udp_payload_fifo_m_axis_tdata;
    end
  end // always @(posedge)
  udp_complete #(
    .ARP_CACHE_ADDR_WIDTH(9),
    .ARP_REQUEST_RETRY_COUNT(4),
    .ARP_REQUEST_RETRY_INTERVAL(250000000),
    .ARP_REQUEST_TIMEOUT(-544967296),
    .UDP_CHECKSUM_GEN_ENABLE(1),
    .UDP_CHECKSUM_HEADER_FIFO_DEPTH(8),
    .UDP_CHECKSUM_PAYLOAD_FIFO_DEPTH(2048)
  ) udp_complete (
    .clk                                    (clock),
    .rst                                    (reset),
    .s_eth_hdr_valid                        (_eth_axis_rx_m_eth_hdr_valid),
    .s_eth_hdr_ready                        (_udp_complete_s_eth_hdr_ready),
    .s_eth_dest_mac                         (_eth_axis_rx_m_eth_dest_mac),
    .s_eth_src_mac                          (_eth_axis_rx_m_eth_src_mac),
    .s_eth_type                             (_eth_axis_rx_m_eth_type),
    .s_eth_payload_axis_tdata               (_eth_axis_rx_m_eth_payload_axis_tdata),
    .s_eth_payload_axis_tvalid              (_eth_axis_rx_m_eth_payload_axis_tvalid),
    .s_eth_payload_axis_tready              (_udp_complete_s_eth_payload_axis_tready),
    .s_eth_payload_axis_tlast               (_eth_axis_rx_m_eth_payload_axis_tlast),
    .s_eth_payload_axis_tuser               (_eth_axis_rx_m_eth_payload_axis_tuser),
    .m_eth_hdr_valid                        (_udp_complete_m_eth_hdr_valid),
    .m_eth_hdr_ready                        (_eth_axis_tx_s_eth_hdr_ready),
    .m_eth_dest_mac                         (_udp_complete_m_eth_dest_mac),
    .m_eth_src_mac                          (_udp_complete_m_eth_src_mac),
    .m_eth_type                             (_udp_complete_m_eth_type),
    .m_eth_payload_axis_tdata               (_udp_complete_m_eth_payload_axis_tdata),
    .m_eth_payload_axis_tvalid              (_udp_complete_m_eth_payload_axis_tvalid),
    .m_eth_payload_axis_tready              (_eth_axis_tx_s_eth_payload_axis_tready),
    .m_eth_payload_axis_tlast               (_udp_complete_m_eth_payload_axis_tlast),
    .m_eth_payload_axis_tuser               (_udp_complete_m_eth_payload_axis_tuser),
    .s_ip_hdr_valid                         (1'h0),
    .s_ip_hdr_ready                         (/* unused */),
    .s_ip_dscp                              (6'h0),
    .s_ip_ecn                               (2'h0),
    .s_ip_length                            (16'h0),
    .s_ip_ttl                               (8'h0),
    .s_ip_protocol                          (8'h0),
    .s_ip_source_ip                         (32'h0),
    .s_ip_dest_ip                           (32'h0),
    .s_ip_payload_axis_tdata                (8'h0),
    .s_ip_payload_axis_tvalid               (1'h0),
    .s_ip_payload_axis_tready               (/* unused */),
    .s_ip_payload_axis_tlast                (1'h0),
    .s_ip_payload_axis_tuser                (1'h0),
    .m_ip_hdr_valid                         (/* unused */),
    .m_ip_hdr_ready                         (1'h1),
    .m_ip_eth_dest_mac                      (/* unused */),
    .m_ip_eth_src_mac                       (/* unused */),
    .m_ip_eth_type                          (/* unused */),
    .m_ip_version                           (/* unused */),
    .m_ip_ihl                               (/* unused */),
    .m_ip_dscp                              (/* unused */),
    .m_ip_ecn                               (/* unused */),
    .m_ip_length                            (/* unused */),
    .m_ip_identification                    (/* unused */),
    .m_ip_flags                             (/* unused */),
    .m_ip_fragment_offset                   (/* unused */),
    .m_ip_ttl                               (/* unused */),
    .m_ip_protocol                          (/* unused */),
    .m_ip_header_checksum                   (/* unused */),
    .m_ip_source_ip                         (_udp_complete_m_ip_source_ip),
    .m_ip_dest_ip                           (/* unused */),
    .m_ip_payload_axis_tdata                (/* unused */),
    .m_ip_payload_axis_tvalid               (/* unused */),
    .m_ip_payload_axis_tready               (1'h1),
    .m_ip_payload_axis_tlast                (/* unused */),
    .m_ip_payload_axis_tuser                (/* unused */),
    .s_udp_hdr_valid                        (_eth_axis_rx_m_eth_hdr_valid & match_cond),
    .s_udp_hdr_ready                        (/* unused */),
    .s_udp_ip_dscp                          (6'h0),
    .s_udp_ip_ecn                           (2'h0),
    .s_udp_ip_ttl                           (8'h40),
    .s_udp_ip_source_ip                     (32'hAC1C0006),
    .s_udp_ip_dest_ip                       (_udp_complete_m_ip_source_ip),
    .s_udp_source_port                      (_udp_complete_m_udp_dest_port),
    .s_udp_dest_port                        (_udp_complete_m_udp_source_port),
    .s_udp_length                           (_udp_complete_m_udp_length),
    .s_udp_checksum                         (16'h0),
    .s_udp_payload_axis_tdata               (_udp_payload_fifo_m_axis_tdata),
    .s_udp_payload_axis_tvalid              (_udp_payload_fifo_m_axis_tvalid),
    .s_udp_payload_axis_tready              (_udp_complete_s_udp_payload_axis_tready),
    .s_udp_payload_axis_tlast               (_udp_payload_fifo_m_axis_tlast),
    .s_udp_payload_axis_tuser               (_udp_payload_fifo_m_axis_tuser),
    .m_udp_hdr_valid                        (/* unused */),
    .m_udp_hdr_ready
      (_eth_axis_tx_s_eth_hdr_ready & match_cond | ~match_cond),
    .m_udp_eth_dest_mac                     (/* unused */),
    .m_udp_eth_src_mac                      (/* unused */),
    .m_udp_eth_type                         (/* unused */),
    .m_udp_ip_version                       (/* unused */),
    .m_udp_ip_ihl                           (/* unused */),
    .m_udp_ip_dscp                          (/* unused */),
    .m_udp_ip_ecn                           (/* unused */),
    .m_udp_ip_length                        (/* unused */),
    .m_udp_ip_identification                (/* unused */),
    .m_udp_ip_flags                         (/* unused */),
    .m_udp_ip_fragment_offset               (/* unused */),
    .m_udp_ip_ttl                           (/* unused */),
    .m_udp_ip_protocol                      (/* unused */),
    .m_udp_ip_header_checksum               (/* unused */),
    .m_udp_ip_source_ip                     (/* unused */),
    .m_udp_ip_dest_ip                       (/* unused */),
    .m_udp_source_port                      (_udp_complete_m_udp_source_port),
    .m_udp_dest_port                        (_udp_complete_m_udp_dest_port),
    .m_udp_length                           (_udp_complete_m_udp_length),
    .m_udp_checksum                         (/* unused */),
    .m_udp_payload_axis_tdata               (_udp_complete_m_udp_payload_axis_tdata),
    .m_udp_payload_axis_tvalid              (_udp_complete_m_udp_payload_axis_tvalid),
    .m_udp_payload_axis_tready              (_udp_payload_fifo_s_axis_tready),
    .m_udp_payload_axis_tlast               (_udp_complete_m_udp_payload_axis_tlast),
    .m_udp_payload_axis_tuser               (_udp_complete_m_udp_payload_axis_tuser),
    .ip_rx_busy                             (/* unused */),
    .ip_tx_busy                             (/* unused */),
    .udp_rx_busy                            (/* unused */),
    .udp_tx_busy                            (/* unused */),
    .ip_rx_error_header_early_termination   (/* unused */),
    .ip_rx_error_payload_early_termination  (/* unused */),
    .ip_rx_error_invalid_header             (/* unused */),
    .ip_rx_error_invalid_checksum           (/* unused */),
    .ip_tx_error_payload_early_termination  (/* unused */),
    .ip_tx_error_arp_failed                 (/* unused */),
    .udp_rx_error_header_early_termination  (/* unused */),
    .udp_rx_error_payload_early_termination (/* unused */),
    .udp_tx_error_payload_early_termination (/* unused */),
    .local_mac                              (48'h20000000000),
    .local_ip                               (32'hAC1C0006),
    .gateway_ip                             (32'hAC172001),
    .subnet_mask                            (32'hFFFFFF00),
    .clear_arp_cache                        (1'h0)
  );
  eth_axis_tx #(
    .DATA_WIDTH(8)
  ) eth_axis_tx (
    .clk                       (clock),
    .rst                       (reset),
    .s_eth_hdr_valid           (_udp_complete_m_eth_hdr_valid),
    .s_eth_hdr_ready           (_eth_axis_tx_s_eth_hdr_ready),
    .s_eth_dest_mac            (_udp_complete_m_eth_dest_mac),
    .s_eth_src_mac             (_udp_complete_m_eth_src_mac),
    .s_eth_type                (_udp_complete_m_eth_type),
    .s_eth_payload_axis_tdata  (_udp_complete_m_eth_payload_axis_tdata),
    .s_eth_payload_axis_tvalid (_udp_complete_m_eth_payload_axis_tvalid),
    .s_eth_payload_axis_tready (_eth_axis_tx_s_eth_payload_axis_tready),
    .s_eth_payload_axis_tlast  (_udp_complete_m_eth_payload_axis_tlast),
    .s_eth_payload_axis_tuser  (_udp_complete_m_eth_payload_axis_tuser),
    .m_axis_tdata              (_eth_axis_tx_m_axis_tdata),
    .m_axis_tvalid             (_eth_axis_tx_m_axis_tvalid),
    .m_axis_tready             (_eth_mac_mii_fifo_tx_axis_tready),
    .m_axis_tlast              (_eth_axis_tx_m_axis_tlast),
    .m_axis_tuser              (_eth_axis_tx_m_axis_tuser),
    .busy                      (/* unused */)
  );
  eth_axis_rx #(
    .DATA_WIDTH(8)
  ) eth_axis_rx (
    .clk                            (clock),
    .rst                            (reset),
    .s_axis_tdata                   (_eth_mac_mii_fifo_rx_axis_tdata),
    .s_axis_tvalid                  (_eth_mac_mii_fifo_rx_axis_tvalid),
    .s_axis_tready                  (_eth_axis_rx_s_axis_tready),
    .s_axis_tlast                   (_eth_mac_mii_fifo_rx_axis_tlast),
    .s_axis_tuser                   (_eth_mac_mii_fifo_rx_axis_tuser),
    .m_eth_hdr_valid                (_eth_axis_rx_m_eth_hdr_valid),
    .m_eth_hdr_ready                (_udp_complete_s_eth_hdr_ready),
    .m_eth_dest_mac                 (_eth_axis_rx_m_eth_dest_mac),
    .m_eth_src_mac                  (_eth_axis_rx_m_eth_src_mac),
    .m_eth_type                     (_eth_axis_rx_m_eth_type),
    .m_eth_payload_axis_tdata       (_eth_axis_rx_m_eth_payload_axis_tdata),
    .m_eth_payload_axis_tvalid      (_eth_axis_rx_m_eth_payload_axis_tvalid),
    .m_eth_payload_axis_tready      (_udp_complete_s_eth_payload_axis_tready),
    .m_eth_payload_axis_tlast       (_eth_axis_rx_m_eth_payload_axis_tlast),
    .m_eth_payload_axis_tuser       (_eth_axis_rx_m_eth_payload_axis_tuser),
    .busy                           (/* unused */),
    .error_header_early_termination (/* unused */)
  );
  axis_fifo #(
    .DATA_WIDTH(8),
    .DEPTH(8192),
    .DEST_ENABLE(0),
    .DEST_WIDTH(8),
    .FRAME_FIFO(0),
    .ID_ENABLE(0),
    .ID_WIDTH(8),
    .KEEP_ENABLE(0),
    .USER_ENABLE(1),
    .USER_WIDTH(1)
  ) udp_payload_fifo (
    .clk           (clock),
    .rst           (reset),
    .s_axis_tdata  (_udp_complete_m_udp_payload_axis_tdata),
    .s_axis_tvalid (_udp_complete_m_udp_payload_axis_tvalid),
    .s_axis_tready (_udp_payload_fifo_s_axis_tready),
    .s_axis_tlast  (_udp_complete_m_udp_payload_axis_tlast),
    .s_axis_tid    (8'h0),
    .s_axis_tdest  (8'h0),
    .s_axis_tuser  (_udp_complete_m_udp_payload_axis_tuser),
    .m_axis_tdata  (_udp_payload_fifo_m_axis_tdata),
    .m_axis_tvalid (_udp_payload_fifo_m_axis_tvalid),
    .m_axis_tready (_udp_complete_s_udp_payload_axis_tready),
    .m_axis_tlast  (_udp_payload_fifo_m_axis_tlast),
    .m_axis_tid    (/* unused */),
    .m_axis_tdest  (/* unused */),
    .m_axis_tuser  (_udp_payload_fifo_m_axis_tuser)
  );
  eth_mac_mii_fifo #(
    .AXIS_DATA_WIDTH(8),
    .CLOCK_INPUT_STYLE("BUFR"),
    .ENABLE_PADDING(1),
    .MIN_FRAME_LENGTH(64),
    .RX_FIFO_DEPTH(4096),
    .RX_FRAME_FIFO(1),
    .TARGET("XILINX"),
    .TX_FIFO_DEPTH(4096),
    .TX_FRAME_FIFO(1)
  ) eth_mac_mii_fifo (
    .rst                (reset),
    .logic_clk          (clock),
    .logic_rst          (reset),
    .tx_axis_tdata      (_eth_axis_tx_m_axis_tdata),
    .tx_axis_tvalid     (_eth_axis_tx_m_axis_tvalid),
    .tx_axis_tready     (_eth_mac_mii_fifo_tx_axis_tready),
    .tx_axis_tlast      (_eth_axis_tx_m_axis_tlast),
    .tx_axis_tuser      (_eth_axis_tx_m_axis_tuser),
    .rx_axis_tdata      (_eth_mac_mii_fifo_rx_axis_tdata),
    .rx_axis_tvalid     (_eth_mac_mii_fifo_rx_axis_tvalid),
    .rx_axis_tready     (_eth_axis_rx_s_axis_tready),
    .rx_axis_tlast      (_eth_mac_mii_fifo_rx_axis_tlast),
    .rx_axis_tuser      (_eth_mac_mii_fifo_rx_axis_tuser),
    .mii_rx_clk         (io_phy_rx_clk),
    .mii_rxd            ({4'h0, io_phy_rxd}),
    .mii_rx_dv          (io_phy_rx_dv),
    .mii_rx_er          (io_phy_rx_er),
    .mii_tx_clk         (io_phy_tx_clk),
    .mii_txd            (_eth_mac_mii_fifo_mii_txd),
    .mii_tx_en          (io_phy_tx_en),
    .mii_tx_er          (/* unused */),
    .tx_error_underflow (/* unused */),
    .tx_fifo_overflow   (/* unused */),
    .tx_fifo_bad_frame  (/* unused */),
    .tx_fifo_good_frame (/* unused */),
    .rx_error_bad_frame (/* unused */),
    .rx_error_bad_fcs   (/* unused */),
    .rx_fifo_overflow   (/* unused */),
    .rx_fifo_bad_frame  (/* unused */),
    .rx_fifo_good_frame (/* unused */),
    .cfg_ifg            (8'hC),
    .cfg_tx_enable      (1'h1),
    .cfg_rx_enable      (1'h1)
  );
  assign io_led0_g = led_reg[0];
  assign io_led1_g = led_reg[1];
  assign io_led2_g = led_reg[2];
  assign io_led3_g = led_reg[3];
  assign io_led4 = led_reg[4];
  assign io_led5 = led_reg[5];
  assign io_led6 = led_reg[6];
  assign io_led7 = led_reg[7];
  assign io_phy_reset_n = ~reset;
  assign io_phy_txd = _eth_mac_mii_fifo_mii_txd[3:0];
endmodule

