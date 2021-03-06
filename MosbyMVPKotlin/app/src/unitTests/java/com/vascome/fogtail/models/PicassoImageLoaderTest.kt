package com.vascome.fogtail.models

import android.widget.ImageView

import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.vascome.fogtail.FogtailRobolectricUnitTestRunner
import com.vascome.fogtail.data.network.PicassoImageLoader

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@RunWith(FogtailRobolectricUnitTestRunner::class)
class PicassoImageLoaderTest {

    private lateinit var imageView: ImageView
    private lateinit var picasso: Picasso
    private lateinit var picassoBitmapClient: PicassoImageLoader
    private lateinit var loadRequestCreator: RequestCreator
    private lateinit var fitRequestCreator: RequestCreator
    private lateinit var centerCropRequestCreator: RequestCreator

    @Before
    fun setUp() {
        picasso = mock(Picasso::class.java)
        imageView = mock(ImageView::class.java)

        loadRequestCreator = mock(RequestCreator::class.java)
        fitRequestCreator = mock(RequestCreator::class.java)
        centerCropRequestCreator = mock(RequestCreator::class.java)

        `when`(picasso.load(FAKE_URL)).thenReturn(loadRequestCreator)
        `when`(loadRequestCreator.fit()).thenReturn(fitRequestCreator)
        `when`(fitRequestCreator.centerCrop()).thenReturn(centerCropRequestCreator)

        picassoBitmapClient = PicassoImageLoader(picasso)
    }

    @Test
    fun downloadInto_shouldLoadThenFitThenCenterCrop() {
        picassoBitmapClient.downloadInto(FAKE_URL, imageView)

        verify<Picasso>(picasso).load(FAKE_URL)
        verify<RequestCreator>(loadRequestCreator).fit()
        verify<RequestCreator>(fitRequestCreator).centerCrop()
        verify<RequestCreator>(centerCropRequestCreator).into(imageView)
    }

    companion object {

        private val FAKE_URL = "http://fakeUrl.com"
    }
}