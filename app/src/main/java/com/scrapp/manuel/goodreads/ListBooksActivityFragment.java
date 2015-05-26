package com.scrapp.manuel.goodreads;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scrapp.manuel.goodreads.util.Book;
import com.scrapp.manuel.goodreads.util.ManuAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListBooksActivityFragment extends Fragment {

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private ManuAdapter adapter;

    View rootView;

    public ListBooksActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list_books, container, false);
        inicializarComponentes();
        return rootView;
    }

    private void inicializarComponentes() {
        progressDialog = new ProgressDialog(rootView.getContext());


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        adapter = new ManuAdapter(new ArrayList<Book>());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2));

        new CargarLibros().execute("");

    }

    private class CargarLibros extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Cargando...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                Document document = Jsoup.connect("https://www.goodreads.com/list/show/264.Books_That_Everyone_Should_Read_At_Least_Once").get();
                Elements elements = document.select("a.bookTitle[itemprop=url]");
                Elements elements1 = document.select("img.bookSmallImg");
                for (Element element : elements1) {
                    Book book = new Book();
                    book.setImage(element.attr("src"));
                    //book.setImage("https://d.gr-assets.com/books/1361975680s/2657.jpg");
                    adapter.add(book);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            adapter.notifyDataSetChanged();
        }
    }

}
