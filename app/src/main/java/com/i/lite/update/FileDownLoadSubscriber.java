package com.i.lite.update;


import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;

public abstract class FileDownLoadSubscriber extends DisposableSubscriber<ResponseBody> {
    private File file;

    public FileDownLoadSubscriber(File file) {
        this.file = file;
    }

    @Override
    public void onNext(ResponseBody body) {
        new WriteFile(file,body,this).execute();
    }

    @Override
    public void onError(Throwable t) {
        onFail("下载失败");
    }

    @Override
    public void onComplete() {

    }

    private class WriteFile extends AsyncTask<String, Long, Boolean> {
        private File file;
        private ResponseBody body;
        private FileDownLoadSubscriber subscriber;

        public WriteFile(File file, ResponseBody body, FileDownLoadSubscriber subscriber) {
            this.file = file;
            this.body = body;
            this.subscriber = subscriber;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            InputStream input = null;
            byte[] buf = new byte[2048];
            int len = 0;
            FileOutputStream fos = null;
            try{
                input = body.byteStream();
                final long total = body.contentLength();
                long sum = 0;

                File dir = file.getParentFile();
                if (!dir.exists()){
                    if (!dir.mkdirs()) return false;
                }
                fos = new FileOutputStream(file);
                while ((len = input.read(buf))!=-1){
                    sum += len;
                    fos.write(buf,0,len);
                    final long finalSum = sum;
                    publishProgress(finalSum, total);

                    Log.i("503", "循环下载   while   finalSum =="+finalSum + "     ----    " + "total=="+ total);

                }
                fos.flush();
                return true;
            } catch (Exception ex){
                return false;
            }finally {
                try{
                    if (input != null) input.close();
                    if (fos != null) fos.close();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            subscriber.onProgress(values[0], values[1]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result){
                subscriber.onProgress(file.length(), file.length());
                subscriber.onSuccess(file);
            } else {
                subscriber.onFail("下载失败");
            }
        }
    }

    public void onSuccess(File file){}

    public void onFail(String msg){}

    public void onProgress(long current,long total){}
}
