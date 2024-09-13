// Generated by CIRCT firtool-1.62.0
module Ethernet(
  input        clock,
               reset,
  output       io_led0_r,
               io_led0_g,
               io_led0_b,
               io_led1_r,
               io_led1_g,
               io_led1_b,
               io_led2_r,
               io_led2_g,
               io_led2_b,
               io_led3_r,
               io_led3_g,
               io_led3_b,
               io_led4,
               io_led5,
               io_led6,
               io_led7,
  input        io_phy_col,
               io_phy_crs,
  output       io_phy_reset_n,
  input        io_phy_rx_clk,
               io_phy_rx_dv,
  input  [3:0] io_phy_rxd,
  input        io_phy_rx_er,
  output       io_phy_tx_clk,
               io_phy_tx_en,
  output [3:0] io_phy_txd
);

  wire        _eth_axis_tx_s_eth_hdr_ready;
  wire        _eth_axis_tx_s_eth_payload_axis_tready;
  wire [7:0]  _eth_axis_tx_m_axis_tdata;
  wire        _eth_axis_tx_m_axis_tvalid;
  wire        _eth_axis_tx_m_axis_tlast;
  wire        _eth_axis_tx_m_axis_tuser;
  wire        _eth_wrapper_tx_eth_hdr_valid;
  wire [47:0] _eth_wrapper_tx_eth_dest_mac;
  wire [47:0] _eth_wrapper_tx_eth_src_mac;
  wire [15:0] _eth_wrapper_tx_eth_type;
  wire [7:0]  _eth_wrapper_tx_eth_payload_axis_tdata;
  wire        _eth_wrapper_tx_eth_payload_axis_tvalid;
  wire        _eth_wrapper_tx_eth_payload_axis_tlast;
  wire        _eth_wrapper_tx_eth_payload_axis_tuser;
  wire        _eth_wrapper_tx_axis_tready;
  EthernetWrap eth_wrapper (
    .clock                      (clock),
    .reset                      (reset),
    .led0_r                     (io_led0_r),
    .led0_g                     (io_led0_g),
    .led0_b                     (io_led0_b),
    .led1_r                     (io_led1_r),
    .led1_g                     (io_led1_g),
    .led1_b                     (io_led1_b),
    .led2_r                     (io_led2_r),
    .led2_g                     (io_led2_g),
    .led2_b                     (io_led2_b),
    .led3_r                     (io_led3_r),
    .led3_g                     (io_led3_g),
    .led3_b                     (io_led3_b),
    .led4                       (io_led4),
    .led5                       (io_led5),
    .led6                       (io_led6),
    .led7                       (io_led7),
    .phy_col                    (io_phy_col),
    .phy_crs                    (io_phy_crs),
    .phy_reset_n                (io_phy_reset_n),
    .phy_rx_clk                 (io_phy_rx_clk),
    .phy_rx_dv                  (io_phy_rx_dv),
    .phy_rxd                    (io_phy_rxd),
    .phy_rx_er                  (io_phy_rx_er),
    .phy_tx_clk                 (io_phy_tx_clk),
    .phy_tx_en                  (io_phy_tx_en),
    .phy_txd                    (io_phy_txd),
    .tx_eth_hdr_valid           (_eth_wrapper_tx_eth_hdr_valid),
    .tx_eth_hdr_ready           (_eth_axis_tx_s_eth_hdr_ready),
    .tx_eth_dest_mac            (_eth_wrapper_tx_eth_dest_mac),
    .tx_eth_src_mac             (_eth_wrapper_tx_eth_src_mac),
    .tx_eth_type                (_eth_wrapper_tx_eth_type),
    .tx_eth_payload_axis_tdata  (_eth_wrapper_tx_eth_payload_axis_tdata),
    .tx_eth_payload_axis_tvalid (_eth_wrapper_tx_eth_payload_axis_tvalid),
    .tx_eth_payload_axis_tready (_eth_axis_tx_s_eth_payload_axis_tready),
    .tx_eth_payload_axis_tlast  (_eth_wrapper_tx_eth_payload_axis_tlast),
    .tx_eth_payload_axis_tuser  (_eth_wrapper_tx_eth_payload_axis_tuser),
    .tx_axis_tdata              (_eth_axis_tx_m_axis_tdata),
    .tx_axis_tvalid             (_eth_axis_tx_m_axis_tvalid),
    .tx_axis_tready             (_eth_wrapper_tx_axis_tready),
    .tx_axis_tlast              (_eth_axis_tx_m_axis_tlast),
    .tx_axis_tuser              (_eth_axis_tx_m_axis_tuser)
  );
  eth_axis_tx #(
    .DATA_WIDTH(8)
  ) eth_axis_tx (
    .clk                       (clock),
    .rst                       (reset),
    .s_eth_hdr_valid           (_eth_wrapper_tx_eth_hdr_valid),
    .s_eth_hdr_ready           (_eth_axis_tx_s_eth_hdr_ready),
    .s_eth_dest_mac            (_eth_wrapper_tx_eth_dest_mac),
    .s_eth_src_mac             (_eth_wrapper_tx_eth_src_mac),
    .s_eth_type                (_eth_wrapper_tx_eth_type),
    .s_eth_payload_axis_tdata  (_eth_wrapper_tx_eth_payload_axis_tdata),
    .s_eth_payload_axis_tvalid (_eth_wrapper_tx_eth_payload_axis_tvalid),
    .s_eth_payload_axis_tready (_eth_axis_tx_s_eth_payload_axis_tready),
    .s_eth_payload_axis_tlast  (_eth_wrapper_tx_eth_payload_axis_tlast),
    .s_eth_payload_axis_tuser  (_eth_wrapper_tx_eth_payload_axis_tuser),
    .m_axis_tdata              (_eth_axis_tx_m_axis_tdata),
    .m_axis_tvalid             (_eth_axis_tx_m_axis_tvalid),
    .m_axis_tready             (_eth_wrapper_tx_axis_tready),
    .m_axis_tlast              (_eth_axis_tx_m_axis_tlast),
    .m_axis_tuser              (_eth_axis_tx_m_axis_tuser),
    .busy                      (/* unused */)
  );
endmodule

