package network;

import android.content.Context;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import interfaces.WebApiResponseCallback;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Ashish.Kumar on 23-01-2018.
 */

public class WebApiCall {
    OkHttpClient client;
    public static final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Context context;
    static OkHttpClient clientForMP;

    public WebApiCall(Context context) {
        client = new OkHttpClient();
        this.context = context;
    }

//    public void getData(String url, final WebApiResponseCallback callback) {
//        client.newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS).readTimeout(60000, TimeUnit.MILLISECONDS).build();
//        final Request request = new Request.Builder().url(url).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                callback.onError(e.fillInStackTrace().toString());
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.code() == 200) {
//                    if (response != null) {
//                        callback.onSucess(response.body().string());
//                    } else {
//                        if (response.message() != null) {
//                            callback.onError(response.message());
//                        } else {
//                            callback.onError("No data found!");
//                        }
//
//                    }
//                }else{
//                    callback.onError(response.body().string());
//                }
//            }
//        });
//    }
public void getData(String url,String content ,final WebApiResponseCallback callback)
{
    client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    OkHttpClient client = new OkHttpClient();
    RequestBody body = RequestBody.create(mediaType, content);
//    RequestBody requestBody = new MultipartBody.Builder()
//            .setType(MultipartBody.FORM)
//            .addFormDataPart("UserId", "0")
//            .addFormDataPart("UserEmail", "10002@bottlecapps.com")
//            .build();

    Request request = new Request.Builder()
            .url(url)
            .post(body)
            .addHeader("content-type", "application/x-www-form-urlencoded")
            .build();
    client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            callback.onError(e.fillInStackTrace().toString());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.code() == 200) {
                if (response != null) {
                    callback.onSucess(response.body().string());
                } else {
                    if (response.message() != null) {
                        callback.onError(response.message());
                    } else {
                        callback.onError("No data found!");
                    }

                }
            }
        }
    });
}
    public String  getData(String url,String content )
    {
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, content);
//    RequestBody requestBody = new MultipartBody.Builder()
//            .setType(MultipartBody.FORM)
//            .addFormDataPart("UserId", "0")
//            .addFormDataPart("UserEmail", "10002@bottlecapps.com")
//            .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return response.body().string();

            } else {
                return getErrorData();
            }

        } catch (Exception ex) {
            ex.fillInStackTrace();
            return getErrorData();
        }
    }

    public String getData(String url) {
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS).readTimeout(60000, TimeUnit.MILLISECONDS).build();
        final Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return response.body().string();

            } else {
                return getErrorData();
            }

        } catch (Exception ex) {
            ex.fillInStackTrace();
            return getErrorData();
        }

    }

    public String getErrorData() {
        JSONObject object = new JSONObject();
        try {
            object.put("Status", false);
            object.put("Message", "Error occured");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return object.toString();
    }
//    public void getAcessToken(String url, final WebApiResponseCallback callback) {
//        client = new OkHttpClient.Builder()
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//        RequestBody formBody = null;
////        if (url.equalsIgnoreCase(Common.paymentGatewayUrlSandox)) {
////            formBody = new FormBody.Builder()
////                    .add("client_id", Common.sandBoxClientId)
////                    .add("client_secret", Common.sandboxClientSecret)
////                    .add("grant_type", "client_credentials").build();
////        } else {*/
//        formBody = new FormBody.Builder()
//                .add("client_id", Common.liveClientId)
//                .add("client_secret", Common.liveClientSecret)
//                .add("grant_type", "client_credentials").build();
//
//        okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(url)
//                .post(formBody)
//                .build();
//
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                callback.onError(e.fillInStackTrace().toString());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.code() == 200) {
//                    if (response != null) {
//                        callback.onSucess(response.body().string());
//                    } else {
//                        if (response.message() != null) {
//                            callback.onError(response.message());
//                        } else {
//                            callback.onError("No data found!");
//                        }
//
//                    }
//                }
//            }
//        });
//    }


    public void postData(String url, String json, final WebApiResponseCallback callback) {
        client.newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS).readTimeout(60000, TimeUnit.MILLISECONDS).build();
        RequestBody reqBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(reqBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callback.onError(e.fillInStackTrace().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
               // cancelProgressDialog(pd);
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        callback.onError(response.message());
                    }
                } else {
                    callback.onError(response.message());
                }
            }
        });
    }
    }

