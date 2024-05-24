package de.fra_uas.fb2.mobiledevices.bordasvotingmethod

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts



class MainActivity : AppCompatActivity() {

    private lateinit var getResult: ActivityResultLauncher<Intent>

    private var toVotingActivity = Bundle()
    private var fromVotingActivity = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val votingOpt = findViewById<EditText>(R.id.editVotingOpt)

        val addVoteButton: Button = findViewById<Button>(R.id.addVoteButton)

        addVoteButton.setOnClickListener {
            val intent = Intent(this, ActivityVote::class.java)

            // Read new voting options input
            val votingOptInput = votingOpt.text.toString()

            // Check if input is empty (avoid empty string)
            if (votingOptInput.isEmpty()) {
                val toast = Toast.makeText(this, "No voting options given!", Toast.LENGTH_SHORT)
                toast.show()
            } else {

                toVotingActivity.putString("votingOpts", votingOptInput)
                intent.putExtras(toVotingActivity)
                getResult.launch(intent)

            }
        }

        getResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                //  you will get result here in result.data
                val intent = result.data
                val b : Bundle? = intent?.extras
                val votingResults = b?.getIntArray("votingResults")
                val vR = Toast.makeText(this, votingResults?.get(1)?.toString(), Toast.LENGTH_SHORT)
                vR.show()

            }
            // if (result.resultCode == Activity.RESULT_CANCELED) {
        }
    }

        override fun onSaveInstanceState(outState: Bundle) {
            outState.putString("message", "This is my message to be reloaded")
            super.onSaveInstanceState(outState)
        }
    }