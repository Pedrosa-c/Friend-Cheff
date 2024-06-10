package com.example.aplicacion_1.conexion;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // Variable estática para almacenar la instancia de Retrofit
    private static Retrofit retrofit = null;
    // URL base del servidor al que se conectarán las solicitudes
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    // Método estático para obtener una instancia de Retrofit
    public static Retrofit getClient() {
        // Verifica si la instancia de Retrofit ya está creada
        if (retrofit == null) {
            // Crea un interceptor de registro para registrar las solicitudes y respuestas HTTP
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            // Establece el nivel de detalle del interceptor de registro en BODY para registrar todo el cuerpo de la solicitud y la respuesta
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            // Crea un cliente HTTP con el interceptor de registro
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            // Construye una instancia de Retrofit con la URL base, el cliente HTTP y el convertidor Gson
            retrofit = new Retrofit.Builder()
                    // Establece la URL base para todas las solicitudes
                    .baseUrl(BASE_URL)
                    // Asigna el cliente HTTP creado anteriormente para manejar las solicitudes
                    .client(client)
                    // Agrega un convertidor para manejar la conversión de JSON a objetos Java y viceversa
                    .addConverterFactory(GsonConverterFactory.create())
                    // Construye la instancia de Retrofit
                    .build();
        }
        // Retorna la instancia de Retrofit
        return retrofit;
    }
}


