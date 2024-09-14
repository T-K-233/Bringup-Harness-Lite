module EthernetWrap (
// Language: Verilog 2001

    /*
     * Clock: 100MHz
     * Reset: Push button, active low
     */
    input  wire       clock,
    input  wire       reset,

    output wire       led0_r,
    output wire       led0_g,
    output wire       led0_b,
    output wire       led1_r,
    output wire       led1_g,
    output wire       led1_b,
    output wire       led2_r,
    output wire       led2_g,
    output wire       led2_b,
    output wire       led3_r,
    output wire       led3_g,
    output wire       led3_b,
    output wire       led4,
    output wire       led5,
    output wire       led6,
    output wire       led7,

    /*
     * Ethernet: 100BASE-T MII
     */
    output wire       phy_reset_n,



input tx_eth_hdr_ready,

output rx_udp_hdr_ready,

input rx_udp_dest_port,


output tx_udp_hdr_valid,


input [7:0] tx_fifo_udp_payload_axis_tdata,
input tx_fifo_udp_payload_axis_tvalid,
input tx_fifo_udp_payload_axis_tlast,
input tx_fifo_udp_payload_axis_tuser,


input rx_udp_hdr_valid,

input rx_udp_payload_axis_tvalid,
input rx_udp_payload_axis_tlast


);





wire rx_fifo_udp_payload_axis_tvalid;
wire rx_fifo_udp_payload_axis_tready;
wire rx_fifo_udp_payload_axis_tlast;

assign rx_fifo_udp_payload_axis_tvalid = rx_udp_payload_axis_tvalid && match_cond_reg;
assign rx_udp_payload_axis_tready = (rx_fifo_udp_payload_axis_tready && match_cond_reg) || no_match_reg;
assign rx_fifo_udp_payload_axis_tlast = rx_udp_payload_axis_tlast;

// Loop back UDP
wire match_cond = rx_udp_dest_port == 1234;
wire no_match = !match_cond;

reg match_cond_reg = 0;
reg no_match_reg = 0;

always @(posedge clock) begin
    if (reset) begin
        match_cond_reg <= 0;
        no_match_reg <= 0;
    end else begin
        if (rx_udp_payload_axis_tvalid) begin
            if ((!match_cond_reg && !no_match_reg) ||
                (rx_udp_payload_axis_tvalid && rx_udp_payload_axis_tready && rx_udp_payload_axis_tlast)) begin
                match_cond_reg <= match_cond;
                no_match_reg <= no_match;
            end
        end else begin
            match_cond_reg <= 0;
            no_match_reg <= 0;
        end
    end
end

assign tx_udp_hdr_valid = rx_udp_hdr_valid && match_cond;
assign rx_udp_hdr_ready = (tx_eth_hdr_ready && match_cond) || no_match;


// Place first payload byte onto LEDs
reg valid_last = 0;
reg [7:0] led_reg = 0;

always @(posedge clock) begin
    if (reset) begin
        led_reg <= 0;
    end else begin
        if (tx_fifo_udp_payload_axis_tvalid) begin
            if (!valid_last) begin
                led_reg <= tx_fifo_udp_payload_axis_tdata;
                valid_last <= 1'b1;
            end
            if (tx_fifo_udp_payload_axis_tlast) begin
                valid_last <= 1'b0;
            end
        end
    end
end

assign {led0_g, led1_g, led2_g, led3_g, led4, led5, led6, led7} = led_reg;


endmodule

`resetall
