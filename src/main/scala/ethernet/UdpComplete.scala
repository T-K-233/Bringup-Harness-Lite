import chisel3._


class udp_complete (
    val ARP_CACHE_ADDR_WIDTH: Int = 9,
    val ARP_REQUEST_RETRY_COUNT: Int = 4,
    val ARP_REQUEST_RETRY_INTERVAL: Int = 125000000*2,
    val ARP_REQUEST_TIMEOUT: Int = 125000000*30,
    val UDP_CHECKSUM_GEN_ENABLE: Int = 1,
    val UDP_CHECKSUM_PAYLOAD_FIFO_DEPTH: Int = 2048,
    val UDP_CHECKSUM_HEADER_FIFO_DEPTH: Int = 8
) extends BlackBox (
  Map(
    "ARP_CACHE_ADDR_WIDTH" -> ARP_CACHE_ADDR_WIDTH,
    "ARP_REQUEST_RETRY_COUNT" -> ARP_REQUEST_RETRY_COUNT,
    "ARP_REQUEST_RETRY_INTERVAL" -> ARP_REQUEST_RETRY_INTERVAL,
    "ARP_REQUEST_TIMEOUT" -> ARP_REQUEST_TIMEOUT,
    "UDP_CHECKSUM_GEN_ENABLE" -> UDP_CHECKSUM_GEN_ENABLE,
    "UDP_CHECKSUM_PAYLOAD_FIFO_DEPTH" -> UDP_CHECKSUM_PAYLOAD_FIFO_DEPTH,
    "UDP_CHECKSUM_HEADER_FIFO_DEPTH" -> UDP_CHECKSUM_HEADER_FIFO_DEPTH
  )
) {
  val io = IO(new Bundle {
    val clk = Input(Clock())
    val rst = Input(Reset())

    
    /*
     * Ethernet frame input
     */
    val s_eth_hdr_valid = Input(Bool())
    val s_eth_hdr_ready = Output(Bool())
    val s_eth_dest_mac = Input(UInt(48.W))
    val s_eth_src_mac = Input(UInt(48.W))
    val s_eth_type = Input(UInt(16.W))
    val s_eth_payload_axis_tdata = Input(UInt(8.W))
    val s_eth_payload_axis_tvalid = Input(Bool())
    val s_eth_payload_axis_tready = Output(Bool())
    val s_eth_payload_axis_tlast = Input(Bool())
    val s_eth_payload_axis_tuser = Input(Bool())

    /*
     * Ethernet frame output
     */
    val m_eth_hdr_valid = Output(Bool())
    val m_eth_hdr_ready = Input(Bool())
    val m_eth_dest_mac = Output(UInt(48.W))
    val m_eth_src_mac = Output(UInt(48.W))
    val m_eth_type = Output(UInt(16.W))
    val m_eth_payload_axis_tdata = Output(UInt(8.W))
    val m_eth_payload_axis_tvalid = Output(Bool())
    val m_eth_payload_axis_tready = Input(Bool())
    val m_eth_payload_axis_tlast = Output(Bool())
    val m_eth_payload_axis_tuser = Output(Bool())
    


    /*
     * IP input
     */
    val s_ip_hdr_valid = Input(Bool())
    val s_ip_hdr_ready = Output(Bool())
    val s_ip_dscp = Input(UInt(6.W))
    val s_ip_ecn = Input(UInt(2.W))
    val s_ip_length = Input(UInt(16.W))
    val s_ip_ttl = Input(UInt(8.W))
    val s_ip_protocol = Input(UInt(8.W))
    val s_ip_source_ip = Input(UInt(32.W))
    val s_ip_dest_ip = Input(UInt(32.W))
    val s_ip_payload_axis_tdata = Input(UInt(8.W))
    val s_ip_payload_axis_tvalid = Input(Bool())
    val s_ip_payload_axis_tready = Output(Bool())
    val s_ip_payload_axis_tlast = Input(Bool())
    val s_ip_payload_axis_tuser = Input(Bool())
    


    /*
     * IP output
     */
    val m_ip_hdr_valid = Output(Bool())
    val m_ip_hdr_ready = Input(Bool())
    val m_ip_eth_dest_mac = Output(UInt(48.W))
    val m_ip_eth_src_mac = Output(UInt(48.W))
    val m_ip_eth_type = Output(UInt(16.W))
    val m_ip_version = Output(UInt(4.W))
    val m_ip_ihl = Output(UInt(4.W))
    val m_ip_dscp = Output(UInt(6.W))
    val m_ip_ecn = Output(UInt(2.W))
    val m_ip_length = Output(UInt(16.W))
    val m_ip_identification = Output(UInt(16.W))
    val m_ip_flags = Output(UInt(2.W))
    val m_ip_fragment_offset = Output(UInt(13.W))
    val m_ip_ttl = Output(UInt(8.W))
    val m_ip_protocol = Output(UInt(8.W))
    val m_ip_header_checksum = Output(UInt(16.W))
    val m_ip_source_ip = Output(UInt(32.W))
    val m_ip_dest_ip = Output(UInt(32.W))
    val m_ip_payload_axis_tdata = Output(UInt(8.W))
    val m_ip_payload_axis_tvalid = Output(Bool())
    val m_ip_payload_axis_tready = Input(Bool())
    val m_ip_payload_axis_tlast = Output(Bool())
    val m_ip_payload_axis_tuser = Output(Bool())


    /*
     * UDP input
     */
    val s_udp_hdr_valid = Input(Bool())
    val s_udp_hdr_ready = Output(Bool())
    val s_udp_ip_dscp = Input(UInt(6.W))
    val s_udp_ip_ecn = Input(UInt(2.W))
    val s_udp_ip_ttl = Input(UInt(8.W))
    val s_udp_ip_source_ip = Input(UInt(32.W))
    val s_udp_ip_dest_ip = Input(UInt(32.W))
    val s_udp_source_port = Input(UInt(16.W))
    val s_udp_dest_port = Input(UInt(16.W))
    val s_udp_length = Input(UInt(16.W))
    val s_udp_checksum = Input(UInt(16.W))
    val s_udp_payload_axis_tdata = Input(UInt(8.W))
    val s_udp_payload_axis_tvalid = Input(Bool())
    val s_udp_payload_axis_tready = Output(Bool())
    val s_udp_payload_axis_tlast = Input(Bool())
    val s_udp_payload_axis_tuser = Input(Bool())
    
    /*
     * UDP output
     */
    val m_udp_hdr_valid = Output(Bool())
    val m_udp_hdr_ready = Input(Bool())
    val m_udp_eth_dest_mac = Output(UInt(48.W))
    val m_udp_eth_src_mac = Output(UInt(48.W))
    val m_udp_eth_type = Output(UInt(16.W))
    val m_udp_ip_version = Output(UInt(4.W))
    val m_udp_ip_ihl = Output(UInt(4.W))
    val m_udp_ip_dscp = Output(UInt(6.W))
    val m_udp_ip_ecn = Output(UInt(2.W))
    val m_udp_ip_length = Output(UInt(16.W))
    val m_udp_ip_identification = Output(UInt(16.W))
    val m_udp_ip_flags = Output(UInt(2.W))
    val m_udp_ip_fragment_offset = Output(UInt(13.W))
    val m_udp_ip_ttl = Output(UInt(8.W))
    val m_udp_ip_protocol = Output(UInt(8.W))
    val m_udp_ip_header_checksum = Output(UInt(16.W))
    val m_udp_ip_source_ip = Output(UInt(32.W))
    val m_udp_ip_dest_ip = Output(UInt(32.W))
    val m_udp_source_port = Output(UInt(16.W))
    val m_udp_dest_port = Output(UInt(16.W))
    val m_udp_length = Output(UInt(16.W))
    val m_udp_checksum = Output(UInt(16.W))
    val m_udp_payload_axis_tdata = Output(UInt(8.W))
    val m_udp_payload_axis_tvalid = Output(Bool())
    val m_udp_payload_axis_tready = Input(Bool())
    val m_udp_payload_axis_tlast = Output(Bool())
    val m_udp_payload_axis_tuser = Output(Bool())


    /*
     * Status
     */
    val ip_rx_busy = Output(Bool())
    val ip_tx_busy = Output(Bool())
    val udp_rx_busy = Output(Bool())
    val udp_tx_busy = Output(Bool())
    val ip_rx_error_header_early_termination = Output(Bool())
    val ip_rx_error_payload_early_termination = Output(Bool())
    val ip_rx_error_invalid_header = Output(Bool())
    val ip_rx_error_invalid_checksum = Output(Bool())
    val ip_tx_error_payload_early_termination = Output(Bool())
    val ip_tx_error_arp_failed = Output(Bool())
    val udp_rx_error_header_early_termination = Output(Bool())
    val udp_rx_error_payload_early_termination = Output(Bool())
    val udp_tx_error_payload_early_termination = Output(Bool())


    /*
     * Configuration
     */
    val local_mac = Input(UInt(48.W))
    val local_ip = Input(UInt(32.W))
    val gateway_ip = Input(UInt(32.W))
    val subnet_mask = Input(UInt(32.W))
    val clear_arp_cache = Input(Bool())
  })
}
