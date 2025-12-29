BIN_DIR := core/bin

.PHONY: all build clean

all: build

build:
	@echo "🏗️ Compilation du noyau SOVEREIGN-CORE..."
	chmod +x gradlew
	./gradlew clean shadowJar --no-daemon
	mkdir -p $(BIN_DIR)
	mv build/libs/sigint-core-all.jar $(BIN_DIR)/
	@echo "✅ Binaire généré : $(BIN_DIR)/sigint-core-all.jar"

clean:
	rm -rf build/ .gradle/ $(BIN_DIR)/*
