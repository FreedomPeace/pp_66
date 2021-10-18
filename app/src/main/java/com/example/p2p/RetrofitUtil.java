//package com.example.p2p;
//
//import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class RetrofitUtil {
//
//    private final Retrofit retrofit;
//
//    public Retrofit getRetrofit() {
//        return retrofit;
//    }
//
//    public RetrofitUtil() {
//        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
//        okHttpBuilder/*.cookieJar(cookieJar)*/
////                    .cache(cache)
////                        .sslSocketFactory(new CustomSSLSocketFactory(),new CustomX509TrustManager())
////                        .authenticator(new ClientAuthenticator())
////                    .addInterceptor(new CacheControlInterceptor())
////                    .addInterceptor(new GzipRequestInterceptor())
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(15, TimeUnit.SECONDS)
//                .writeTimeout(15, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true);
////                .eventListenerFactory(ConcurrentPrintingEventListener.FACTORY);
//        //LOG拦截器的配置
////            if (BuildConfig.DEBUG) {
////        okHttpBuilder = SDKManager.initInterceptor(okHttpBuilder);
////            }
//
//        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
//        retrofitBuilder.client(okHttpBuilder.build());
//        retrofit = retrofitBuilder.baseUrl(FrameworkConstant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
//    }
//}
