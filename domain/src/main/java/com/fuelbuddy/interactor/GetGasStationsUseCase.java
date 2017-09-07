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

import com.fuelbuddy.data.FuelPriceMode;
import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.Position;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.GasStationsRepository;
import com.fuelbuddy.sorter.PriceSorter;
import com.fuelbuddy.sorter.Sorter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link com.fuelbuddy.data.GasStation}.
 */
public class GetGasStationsUseCase extends UseCase<List<GasStation>, GetGasStationsUseCase.Params> {


    private final GasStationsRepository gasStationsRepository;
    private Sorter sorter;

    @Inject
    public GetGasStationsUseCase(GasStationsRepository gasStationsRepository, ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.gasStationsRepository = gasStationsRepository;
        sorter = new PriceSorter();
    }

    @Override
    Observable<List<GasStation>> buildUseCaseObservable(final Params params) {
        return this.gasStationsRepository.gasStations(params.mCurrentPosition)
                .map(new Function<List<GasStation>, List<GasStation>>() {
                    @Override
                    public List<GasStation> apply(List<GasStation> gasStations) throws Exception {
                        return sorter.sort(gasStations,params.fuelPriceMode);
                    }
                });
    }

    public static final class Params {

        private Position mCurrentPosition;
        private FuelPriceMode fuelPriceMode;

        private Params(Position currentPosition, FuelPriceMode fuelPriceMode) {
            this.mCurrentPosition = currentPosition;
            this.fuelPriceMode = fuelPriceMode;
        }

        public static Params forPosition(Position position,FuelPriceMode fuelPriceMode) {
            return new Params(position,fuelPriceMode);
        }
    }
}
