package com.example.jaloliddin.bestblog.networks;

import com.example.jaloliddin.bestblog.iface.ServerApiR1;

import retrofit.RestAdapter;

public class ServerConnnectionServiceR1 {
    private static ServerConnnectionServiceR1 instance;
    private static final String BASE_URL= "http://mendil.zzz.com.ua/";
    private RestAdapter restAdapter;

    public static ServerConnnectionServiceR1 getInstance(){
        if (instance ==null){
            instance= new ServerConnnectionServiceR1();
        }
        return instance;
    }
    public ServerConnnectionServiceR1(){
        if (restAdapter==null){
           restAdapter=new RestAdapter.Builder()
                    .setEndpoint(BASE_URL)
                    .build();
        }
    }

    public ServerApiR1 getServerApiR1(){
        return  restAdapter.create(ServerApiR1.class);
    }
}
