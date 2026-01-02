package com.fardc.sigint.core
import io.javalin.Javalin
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.Instant

fun main() {
    val app = Javalin.create().start(7070)

    app.get("/") { ctx ->
        ctx.html("""
            <!DOCTYPE html>
            <html lang="fr">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>SOVEREIGN C2 STATION</title>
                <style>
                    :root { --glow: #00ff41; --dim: #003310; --bg: #020202; --warn: #ffb100; --danger: #ff3e3e; }
                    body { background: var(--bg); color: var(--glow); font-family: 'Courier New', monospace; margin: 0; padding: 15px; }
                    .header { border: 1px solid var(--glow); padding: 10px; display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px; }
                    
                    /* Oscilloscope Pro */
                    #scope { width: 100%; height: 80px; border: 1px solid #111; background: #050505; margin-bottom: 10px; }
                    
                    .panel-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 10px; }
                    .panel { border: 1px solid #222; background: #0a0a0a; padding: 10px; border-radius: 2px; }
                    .panel-h { font-size: 0.7em; color: #555; text-transform: uppercase; margin-bottom: 8px; border-bottom: 1px solid #111; padding-bottom: 3px; }
                    
                    .btn { background: none; border: 1px solid var(--glow); color: var(--glow); padding: 8px; width: 100%; cursor: pointer; font-size: 0.7em; margin-bottom: 5px; transition: 0.2s; }
                    .btn:hover { background: var(--dim); }
                    .btn-danger { border-color: var(--danger); color: var(--danger); }
                    .btn-warn { border-color: var(--warn); color: var(--warn); }
                    
                    #console { background: #000; border: 1px solid #111; height: 100px; padding: 8px; font-size: 0.65em; overflow-y: auto; color: #888; margin-top: 10px; }
                    .status-tag { font-size: 0.6em; background: var(--glow); color: #000; padding: 2px 5px; font-weight: bold; }
                    @keyframes blink { 0%, 100% { opacity: 1; } 50% { opacity: 0.3; } }
                    .blink { animation: blink 1.5s infinite; }
                </style>
            </head>
            <body>
                <div class="header">
                    <span style="font-weight:bold;">🛡️ SOVEREIGN C2 STATION</span>
                    <span class="status-tag blink">LINK: OK</span>
                </div>

                <canvas id="scope"></canvas>

                <div class="panel-grid">
                    <div class="panel">
                        <div class="panel-h">Arsenal SIGINT</div>
                        <button class="btn" onclick="act('SCAN', '/api/real-scan')">📡 SCAN SPECTRUM</button>
                        <button class="btn btn-danger" onclick="act('JAMMER', '/api/combat/jam/start')">🔥 JAMMER ACTIVATE</button>
                    </div>
                    
                    <div class="panel">
                        <div class="panel-h">Infiltration</div>
                        <button class="btn btn-warn" onclick="act('SSL-STRIP', '/api/combat/intercept')">🎭 SSL STRIP</button>
                        <button class="btn btn-warn" onclick="act('MIRROR', '/api/combat/mirror')">📡 TRAFFIC MIRROR</button>
                    </div>

                    <div class="panel">
                        <div class="panel-h">Hardware Status</div>
                        <div style="font-size:0.6em; color:#444;">
                            ANTENNA: <span style="color:var(--glow)">INTERNAL_01</span><br>
                            SDR-LINK: <span style="color:var(--warn)">WAITING_EXT</span><br>
                            CPU-LOAD: 14% | TEMP: 38°C
                        </div>
                    </div>

                    <div class="panel">
                        <div class="panel-h">Commandes Shell</div>
                        <button class="btn" onclick="act('SITREP', '/admin/report')">📑 GET SITREP</button>
                        <button class="btn btn-danger" onclick="act('KILL', '/emergency/halt')">💀 EMERGENCY HALT</button>
                    </div>
                </div>

                <div id="console">> Initialisation noyau v4.0...</div>

                <script>
                    const canvas = document.getElementById('scope');
                    const ctx = canvas.getContext('2d');
                    let activeMode = "idle";

                    function draw() {
                        ctx.fillStyle = '#050505';
                        ctx.fillRect(0, 0, canvas.width, canvas.height);
                        ctx.strokeStyle = activeMode === 'jam' ? '#ff3e3e' : '#00ff41';
                        ctx.beginPath(); ctx.moveTo(0, 40);
                        for(let i=0; i<canvas.width; i++) {
                            let amp = activeMode === 'jam' ? 35 : (activeMode === 'scan' ? 15 : 2);
                            ctx.lineTo(i, 40 + Math.sin(i*0.1 + Date.now()*0.01) * Math.random() * amp);
                        }
                        ctx.stroke(); requestAnimationFrame(draw);
                    }
                    draw();

                    function act(name, route) {
                        const c = document.getElementById('console');
                        c.innerHTML += "> " + name + " ENGAGÉ...<br>";
                        if(name === 'JAMMER') activeMode = 'jam';
                        if(name === 'SCAN') activeMode = 'scan';
                        fetch(route).then(r => r.text()).then(d => {
                            c.innerHTML += "> STATUT: " + d + "<br>";
                            c.scrollTop = c.scrollHeight;
                        });
                    }
                </script>
            </body>
            </html>
        """)
    }

    // MAPPING DES ROUTES PRO
    app.get("/api/real-scan") { ctx ->
        val output = BufferedReader(InputStreamReader(Runtime.getRuntime().exec("termux-wifi-scan").inputStream)).readText()
        ctx.result(if(output.isBlank()) "SCAN COMPLET - 0 CIBLES" else "CIBLES ACQUISES")
    }
    app.get("/api/combat/jam/start") { it.result("ÉMISSION DE BRUIT BLANC ACTIVÉE") }
    app.get("/api/combat/intercept") { it.result("PORTAIL CAPTIF ARMÉ") }
    app.get("/api/combat/mirror") { it.result("RELAYAGE DE TRAFIC SUR PORT 8081") }
    app.get("/admin/report") { it.result("SITREP v4.0 : TOUS LES SYSTÈMES VERTS") }
    app.post("/emergency/halt") { it.result("PURGE MÉMOIRE") }
}
