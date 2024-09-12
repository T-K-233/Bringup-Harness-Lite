BUILD_DIR = ./build

PRJ = ChiselProject

verilog:
	$(call git_commit, "generate verilog")
	mkdir -p $(BUILD_DIR)
	mill -i $(PRJ).runMain Elaborate --target-dir $(BUILD_DIR)

test:
	mill -i $(PRJ).Test
