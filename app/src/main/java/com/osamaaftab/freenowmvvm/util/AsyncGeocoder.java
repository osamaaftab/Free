package com.osamaaftab.freenowmvvm.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class AsyncGeocoder {
    private final Geocoder geocoder;

    public AsyncGeocoder(Context context) {
        geocoder = new Geocoder(context);
    }

    public void reverseGeocode(double lat, double lng, Callback callback) {
        BehaviorSubject<Geocoder> subject = BehaviorSubject.create(geocoder);
        subject.asObservable()
                .observeOn(Schedulers.io())
                .map(geo -> {
                    try {
                        return geo.getFromLocation(lat, lng, 1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).flatMap(Observable::from).first()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Address>() {
                    @Override
                    public void onCompleted() {
                        this.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.failure(e);
                    }

                    @Override
                    public void onNext(Address address) {
                        callback.success(address);
                    }
                });
    }

    public void geocode(String locationName, Callback callback) {
        geocode(locationName, callback, 0, 0, 0, 0);
    }

    public void geocode(String locationName, Callback callback,
                        double lowerLeftLatitude, double lowerLeftLongitude,
                        double upperRightLatitude, double upperRightLongitude) {
        BehaviorSubject<Geocoder> subject = BehaviorSubject.create();
        subject.onNext(geocoder);
        subject.asObservable()
                .observeOn(Schedulers.io())
                .map(geo -> {
                    try {
                        return geocoder.getFromLocationName(locationName, 1,
                                lowerLeftLatitude, lowerLeftLongitude,
                                upperRightLatitude, upperRightLongitude);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).flatMap(Observable::from).first()
                .filter(address -> address.getMaxAddressLineIndex() >= 1)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Address>() {
                    @Override
                    public void onCompleted() {
                        this.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.failure(e);
                    }

                    @Override
                    public void onNext(Address address) {
                        callback.success(address);
                    }
                });
    }

    public interface Callback {
        void success(Address address);
        void failure(Throwable e);
    }
}
