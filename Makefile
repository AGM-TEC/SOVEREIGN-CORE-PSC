# --- CONFIGURATION SOUVERAINE ---
SHELL      := /bin/bash
CONF_DIR   := core/config
BIN_DIR    := core/bin
SCRIPT_DIR := scripts/build
DOCKER_DIR := infra/docker

.PHONY: all prep build release clean lock

all: build

# 1. Préparation et Verrouillage
lock:
	@echo "🔒 Verrouillage du wrapper 8.2..."
	gradle wrapper --gradle-version 8.2 --distribution-type bin
	chmod +x gradlew

prep:
	@echo "🔧 Alignement tactique des configurations..."
	cp $(CONF_DIR)/build.gradle.kts . || true
	cp $(CONF_DIR)/settings.gradle.kts . || true
	cp $(CONF_DIR)/gradle.properties . || true
	chmod +x gradlew || true
	chmod +x $(SCRIPT_DIR)/deploy.sh || true

# 2. Build Réel (Second Release)
build: prep
	@echo "🏗️ Compilation du noyau SOVEREIGN-CORE..."
	./gradlew clean shadowJar --no-daemon
	mkdir -p $(BIN_DIR)
	mv build/libs/sigint-core-all.jar $(BIN_DIR)/
	@echo "✅ Binaire généré dans $(BIN_DIR)/"

# 3. Release Docker & Terrain
release: prep
	@echo "🚀 Lancement du Release (Environnement Isolé)..."
	docker-compose -f $(DOCKER_DIR)/docker-compose.yml up --build

clean:
	@echo "🧹 Nettoyage de la racine..."
	rm -f build.gradle.kts settings.gradle.kts gradle.properties
	rm -rf build/ .gradle/ $(BIN_DIR)/*
