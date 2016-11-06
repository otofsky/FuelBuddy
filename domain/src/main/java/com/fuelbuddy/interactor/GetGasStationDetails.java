/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fuelbuddy.interactor;


import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.GasStationsRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link GasStation} that represents a use case for
 * retrieving data related to an specific {@link GasStation}.
 */
public class GetGasStationDetails extends UseCase {

    private final int gasStationId;
    private final GasStationsRepository gasStationsRepository;

    @Inject
    public GetGasStationDetails(int gasStationId, GasStationsRepository gasStationsRepository,
                                ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.gasStationId = gasStationId;
        this.gasStationsRepository = gasStationsRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.gasStationsRepository.gasStation(this.gasStationId);
    }
}
