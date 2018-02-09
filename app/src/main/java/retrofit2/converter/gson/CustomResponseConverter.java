package retrofit2.converter.gson;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class CustomResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public CustomResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }


    @Override
    public T convert(ResponseBody value) throws IOException {

        try {
            String body = value.string();

            String name = adapter.fromJson(body).getClass().getName();
            String simpleName = adapter.fromJson(body).getClass().getSimpleName();
            Log.i("cccccc","name-------->  "+name);
            Log.i("cccccc","bean-------->  "+simpleName+"            jsonStr-------->  "+body);
            return adapter.fromJson(body);
        } finally {
            value.close();
        }
    }



    /*
    @Override     //https://www.jianshu.com/p/2263242fa02d
    public T convert(ResponseBody value) throws IOException {
        try {
            String body = value.string();

            JSONObject json = new JSONObject(body);

            int    ret = json.optInt("ret");
            String msg = json.optString("msg", "");

            if (ret == 0) {
                if (json.has("data")) {
                    Object data = json.get("data");

                    body = data.toString();

                    return adapter.fromJson(body);
                } else {
                    return (T) msg;
                }
            } else {
                throw new RuntimeException(msg);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }

    */
}
