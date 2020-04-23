package org.fundacion.nabelle.utils;

import android.os.Build;
import android.util.Base64;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;

import java.nio.ByteBuffer;

import androidx.annotation.NonNull;

public class Base64DataFetcher implements DataFetcher<ByteBuffer> {

    private final String model;

    Base64DataFetcher(String model) {
        this.model = model;
    }

    private String getBase64SectionOfModel() {
        // See https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/Data_URIs.
        int startOfBase64Section = model.indexOf(',');
        return model.substring(startOfBase64Section + 1);
    }


    @Override
    public void loadData(Priority priority, DataCallback<? super ByteBuffer> callback) {
        String base64Section = getBase64SectionOfModel();
        byte[] data = new byte[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            data = Base64.decode(base64Section, Base64.DEFAULT);
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        callback.onDataReady(byteBuffer);

    }

    @Override
    public void cleanup() {}

    @Override
    public void cancel() {}

    @NonNull
    @Override
    public Class<ByteBuffer> getDataClass() {
        return ByteBuffer.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.LOCAL;
    }
}
