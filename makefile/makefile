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
