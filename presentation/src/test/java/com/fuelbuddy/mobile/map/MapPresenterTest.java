package com.fuelbuddy.mobile.map;

import android.content.Context;

import com.fuelbuddy.interactor.GetGasStationsUseCase;
import com.fuelbuddy.mobile.map.presenter.MapMainPresenter;
import com.fuelbuddy.mobile.map.view.MapMvpView;
import com.fuelbuddy.mobile.mapper.PositionMapper;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by zjuroszek on 18.11.16.
 */

@RunWith(MockitoJUnitRunner.class)
public class MapPresenterTest {

    @Mock
    private GetGasStationsUseCase mockGetGasStationsUseCase;

    @Mock
    private MapMvpView mockMapMvpView;


    MapMainPresenter mapMainPresenter;

    PositionMapper mPositionMapper;

    @Mock private Context mockContext;

    @Before
    public void setUp() {
        mPositionMapper = new PositionMapper();
        mapMainPresenter = new MapMainPresenter(mockGetGasStationsUseCase);
        mapMainPresenter.attachView(mockMapMvpView);
    }
  /*  userListPresenter.initialize();

    verify(mockUserListView).hideRetry();
    verify(mockUserListView).showLoading();
    verify(mockGetUserList).execute(any(DisposableObserver.class), any(Void.class));*/
    @Test
    public void testSubmitSearch()  {
        //given(mockMapMvpView.context()).willReturn(mockContext);
        verify(mockMapMvpView).showLoading();
        mapMainPresenter.loadFuelPriceList(TestDataFactory.makeLatLng(),TestDataFactory.makeFuelPriceMode());
        verify(mockGetGasStationsUseCase).execute(any(DisposableObserver.class), null);


    }
}
