package com.fuelbuddy.interactor;


import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.GasStationsRepository;

import java.io.File;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by zjuroszek on 20.11.16.
 */


public class UploadVideoUseCase extends UseCase {

    GasStationsRepository gasStationsRepository;
    File file;

    @Inject
    public UploadVideoUseCase(GasStationsRepository gasStationsRepository,  ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);

        this.gasStationsRepository = gasStationsRepository;
    }

    public void validateFuelPrices(File file) {
        this.file = file;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return gasStationsRepository.uploadVideo(file);

    }

}
