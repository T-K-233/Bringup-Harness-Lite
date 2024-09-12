module SyncReset #(
  parameter N = 4
)(
  input clk,
  input rst,
  output out
);
  
  sync_reset #(
    .N(4)
  ) sync_reset_inst (
    .clk(clk),
    .rst(rst),
    .out(out)
  );
endmodule
    
