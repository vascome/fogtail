package com.vascome.fogtail.models;

import android.support.annotation.NonNull;

import com.vascome.fogtail.api.FogtailRestApi;
import com.vascome.fogtail.api.entities.RecAreaItem;
import com.vascome.fogtail.di.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Model is not an entity. It's a container for business logic code. M(VC), M(VP), M(VVM).
 * <p>
 * Why create Model classes? Such classes hide complex logic required to fetch/cache/process/etc data.
 * So Presentation layer won't know the details of implementation and each class will do only one job (SOLID).
 */



@ActivityScope //allow to have only one model for activity
public class RecAreaItemsModel {

    @NonNull
    private final FogtailRestApi restApi;

    @Inject
    public RecAreaItemsModel(@NonNull FogtailRestApi restApi) {
        this.restApi = restApi;
    }

    @NonNull
    public Single<List<RecAreaItem>> getItems() {
        return restApi.items();
    }

}
