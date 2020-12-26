package `in`.bk.workmanager

import android.content.Context
import android.util.Log
import androidx.work.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context:Context,params:WorkerParameters) : Worker(context,params) {
    companion object{
        const val  KEY_WORKER="key_worker"
    }
    override fun doWork(): Result {
        try {
            val count:Int=inputData.getInt(MainActivity.KEY_COUNT_VALUE,0) //getting input

            for(i:Int in 0 until count){
                Log.i("MyTag","Uploading $i")
            }

            val time=SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate:String=time.format(Date())

            val outputData:Data= Data.Builder()
                    .putString(KEY_WORKER,currentDate)
                    .build()

            return Result.success(outputData)  //sending output data in main activity

//           for(i:Int in 0..600000){
//               Log.i("MyTag","Uploading $i")
//           }
//            return Result.success()
        }catch (e:Exception){
            return Result.failure()
        }
    }


}