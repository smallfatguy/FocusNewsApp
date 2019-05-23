package com.belimov.FocusNewsApp.features.settings.loader;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.belimov.FocusNewsApp.features.settings.loader.data.NewsLoaderRepository;
import com.belimov.FocusNewsApp.features.settings.loader.data.NewsLoaderRepositoryImpl;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LoaderWorker extends Worker {

    private final static String TAG = "Worker";

    public final static String INPUT_URLS_KEY = "input_urls_key";
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
        final NewsLoaderRepository repository = new NewsLoaderRepositoryImpl();

        String[] urls = getInputData().getStringArray(INPUT_URLS_KEY);

        if (urls == null) {
            urls = repository.getChannelUrls();
        }

        for (final String url : urls) {
            try {
                FeedParser.parseFeed(url, repository);
            } catch (XmlPullParserException | IOException e) {
                Log.e(TAG, e.toString());
                return Result.failure();
            }
        }

        return Result.success();
    }
}
