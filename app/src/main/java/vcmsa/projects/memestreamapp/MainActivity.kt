package vcmsa.projects.memestreamapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser == null) {
            // Not signed in → go login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // Signed in → show welcome
        findViewById<TextView>(R.id.tvWelcome).text =
            "Welcome, ${currentUser.displayName ?: currentUser.email}"

        // Setup sign out (optional)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<Button>(R.id.btnSignOut).setOnClickListener {
            auth.signOut()
            googleSignInClient.signOut().addOnCompleteListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}
