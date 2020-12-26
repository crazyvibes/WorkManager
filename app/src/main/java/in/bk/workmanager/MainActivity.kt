package `in`.bk.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val KEY_COUNT_VALUE = "key_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)
        button.setOnClickListener {
            setOneTimeWorkRequest(textView)
        }
    }

    private fun setOneTimeWorkRequest(textView: TextView) {
        val workManager: WorkManager = WorkManager.getInstance(applicationContext)


        //passing input value
        val data:Data=Data.Builder()
                .putInt(KEY_COUNT_VALUE,125)
                .build()


        val constraints:Constraints=Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
                .setConstraints(constraints)//set constraints
                .setInputData(data)
                .build()
        workManager.enqueue(uploadRequest)

        //getting status
        workManager.getWorkInfoByIdLiveData(uploadRequest.id)
                .observe(this, Observer {
                    textView.text = it.state.name


                    if(it.state.isFinished) //receiving output data from worker class
                    {
                        val data:Data=it.outputData
                        val message:String? = data.getString(UploadWorker.KEY_WORKER)
                        Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
                    }
                })

    }
}

