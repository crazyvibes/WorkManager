package `in`.bk.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button=findViewById<Button>(R.id.button)
        val textView=findViewById<TextView>(R.id.textView)
        button.setOnClickListener {
            setOneTimeWorkRequest(textView)
        }
    }

    private fun setOneTimeWorkRequest(textView: TextView) {
        val workManager:WorkManager= WorkManager.getInstance(applicationContext)
        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .build()
        workManager.enqueue(uploadRequest)
        workManager.getWorkInfoByIdLiveData(uploadRequest.id)
                .observe(this, Observer {
                    textView.text=it.state.name
                })


    }
}

