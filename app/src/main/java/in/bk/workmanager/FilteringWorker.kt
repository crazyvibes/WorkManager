package `in`.bk.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception


class FilteringWorker(context: Context, params: WorkerParameters) : Worker(context,params) {

    override fun doWork(): Result {
        try {


            for(i:Int in 0 until 3000){
                Log.i("MyTag","Filtering $i")
            }
            return Result.success()

        }catch (e: Exception){
            return Result.failure()
        }
    }


}