package com.labouralerts.webservice;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

public class WSRequest {

    private static String TAG = WSRequest.class.getSimpleName();

//    public static RequestBody getRequestBody(final String text) {
//
//        return RequestBody.create(MediaType.parse(WSConstants.MIME_TYPE_TEXT), text);
//
//    }

    private static RequestBody getRequestBodyFromJSON(final String jsonString) {

        return RequestBody.create(MediaType.parse("application/json"), jsonString);
    }

    public static MultipartBody.Part getImageMultiPart(final String partName, final File file) {

        if (file != null && file.exists()) {
            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            // MultipartBody.Part is used to send also the actual file name
            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
        } else {
            return null;
        }

    }

    public static MultipartBody.Part getFileMultiPart(final String partName, final File file) {

        if (file != null && file.exists()) {
            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            // MultipartBody.Part is used to send also the actual file name
            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
        } else {
            return null;
        }

    }




}
