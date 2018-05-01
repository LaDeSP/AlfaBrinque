package com.book.app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.book.app.data.AppDAO;

public class PopularQuestoesTask extends AsyncTask<Context, Void, Integer> {

    private static final String MY_TAG = "insertTask";
    private AppDAO dao;

    public PopularQuestoesTask() {
    }

    @Override
    protected Integer doInBackground(Context... params) {
        dao = AppDAO.newInstance(params[0]);

        return dao.popularQuestoesAnimais();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        if (integer == 1) {
            Log.i(MY_TAG, "QUESTÕES POPULADAS COM SUCESSO!");
        }
    }
}
