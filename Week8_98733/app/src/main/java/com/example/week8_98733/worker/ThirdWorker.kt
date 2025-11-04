package com.example.week8_98733.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class ThirdWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val id = inputData.getString(INPUT_DATA_ID)

        return try {
            Log.d("ThirdWorker", "Processing with ID $id")
            // Simulate some work
            Thread.sleep(3000)
            Log.d("ThirdWorker", "Processing done")
            Result.success()
        } catch (e: Exception) {
            Log.e("ThirdWorker", "Processing failed", e)
            Result.failure()
        }
    }

    companion object {
        const val INPUT_DATA_ID = "INPUT_DATA_ID"
    }
}