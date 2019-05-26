package com.belimov.FocusNewsApp.features.newsloader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.belimov.FocusNewsApp.features.newsloader.data.NewsLoaderRepositoryImpl;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LoaderWorker extends Worker {

    public final static String INPUT_URL_KEY = "input_url_key";
    public final static String AUTOUPDATE_TAG = "autoupdate";

    public static OneTimeWorkRequest createOneTimeWork(final Data inputData) {
        return new OneTimeWorkRequest.Builder(LoaderWorker.class)
                .setInputData(inputData)
                .build();
    }

    public static PeriodicWorkRequest createPeriodicWork(final int repeatInterval) {
        return new PeriodicWorkRequest.Builder(LoaderWorker.class, repeatInterval,
                TimeUnit.MINUTES, 5, TimeUnit.MINUTES)
                .addTag(AUTOUPDATE_TAG)
                .build();
    }

    public LoaderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            new NewsLoaderRepositoryImpl().loadFeed(getInputData().getString(INPUT_URL_KEY));
        } catch (IOException | XmlPullParserException e) {
            return Result.failure();
        }
        return Result.success();
    }
}
