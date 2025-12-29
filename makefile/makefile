# Makefile PSC - Commandement Souverain
SHELL := /bin/bash

.PHONY: all lock build release clean

all: build

# Verrouille l'environnement et prépare le wrapper
lock:
	@echo "🔒 Verrouillage du wrapper et de la toolchain..."
	gradle wrapper --gradle-version 8.2 --distribution-type bin
	git add gradlew gradlew.bat gradle/wrapper/gradle-wrapper.properties
	git commit -m "chore: lock Gradle wrapper 8.2 and toolchain provisioning" || true
	@echo "✅ Environnement verrouillé."

# Build simple
build:
	./gradlew clean shadowJar --no-daemon

# Déploiement complet via ton script deploy.sh
release: lock
	@echo "📦 Lancement du déploiement souverain..."
	chmod +x deploy.sh
	./deploy.sh
	@echo "🚀 Release terminée dans release/signed/"

clean:
	rm -rf build/ .gradle/ release/

# --- CHEMINS TACTIQUES ---
SCRIPT_DIR := scripts/build
DOCKER_DIR := infra/docker
BIN_DIR    := core/bin

.PHONY: all clean build release

all: build

# 1. Nettoyage des résidus empoisonnés (Fix Java 25)
clean:
	@echo "🧹 Purge des artefacts corrompus..."
	rm -rf .gradle build $(BIN_DIR)/*
	
# 2. Compilation via la toolchain verrouillée
build:
	@echo "🏗️ Compilation du noyau SOVEREIGN-CORE..."
	chmod +x $(SCRIPT_DIR)/deploy.sh
	./$(SCRIPT_DIR)/deploy.sh

# 3. Release Docker (Indépendance totale)
release:
	@echo "🚀 Lancement du Second Release (Environnement Isolé)..."
	docker-compose -f $(DOCKER_DIR)/docker-compose.yml up --build

# --- CONFIGURATION SOUVERAINE ---
CONF_DIR := core/config
BIN_DIR  := core/bin

.PHONY: all clean build release

all: build

# 1. Préparation de l'environnement (Restauration des liens)
prep:
	@echo "🔧 Alignement des fichiers de configuration..."
	cp $(CONF_DIR)/build.gradle.kts .
	cp $(CONF_DIR)/settings.gradle.kts .
	cp $(CONF_DIR)/gradle.properties .
	chmod +x gradlew

# 2. Build réel (Second Release)
build: prep
	@echo "🏗️ Compilation du binaire SOVEREIGN-CORE..."
	./gradlew clean shadowJar --no-daemon
	mkdir -p $(BIN_DIR)
	mv build/libs/sigint-core-all.jar $(BIN_DIR)/
	@echo "✅ Binaire généré dans $(BIN_DIR)/"

# 3. Nettoyage de la racine (Maintien de l'ordre)
clean:
	@echo "🧹 Nettoyage de la racine et des artefacts..."
	rm -f build.gradle.kts settings.gradle.kts gradle.properties
	rm -rf build/ .gradle/

