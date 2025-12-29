SHELL := /bin/bash

.PHONY: lock build release clean

lock:
	@echo "🔒 Lock wrapper 8.2"
	gradle --no-daemon --warning-mode=quiet wrapper --gradle-version 8.2 --distribution-type bin || true
	chmod +x gradlew || true
	git add gradlew gradlew.bat gradle/wrapper/gradle-wrapper.properties gradle/wrapper/gradle-wrapper.jar || true
	git commit -m "chore: lock Gradle wrapper 8.2" || true
	@echo "✅ Lock done"

build:
	./gradlew clean shadowJar --no-daemon

release: lock build
	@echo "📦 Release"
	mkdir -p release/signed
	cp build/libs/sigint-core-all.jar release/signed/
	sha256sum release/signed/sigint-core-all.jar | tee release/signed/sigint-core-all.sha256
	@echo "✅ Release ready: release/signed/sigint-core-all.jar"

clean:
	rm -rf build .gradle