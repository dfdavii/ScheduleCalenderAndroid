package com.example.calendarscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkerNode extends Worker {

    public WorkerNode(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        sendNotice("The Remove button is not working", "Need to fix it");
        return Result.failure();
    }

    private void sendNotice(String job, String message){

        NotificationManager manager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("OperatingSystems", "OperatingSystems", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "OperatingSystems")
                .setContentTitle(job)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_round);
        manager.notify(1, builder.build());
    }
}
