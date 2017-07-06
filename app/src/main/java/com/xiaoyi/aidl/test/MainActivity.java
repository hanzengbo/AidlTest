package com.xiaoyi.aidl.test;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import media.xiaoyi.com.aidlserver.Book;
import media.xiaoyi.com.aidlserver.IBookService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    IBookService mBookService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnGetBookCount).setOnClickListener(this);
        findViewById(R.id.btnAddBookIn).setOnClickListener(this);
        findViewById(R.id.btnAddBookOut).setOnClickListener(this);

        Intent intent = new Intent()
                        .setComponent(new ComponentName("media.xiaoyi.com.aidlserver",
                                        "media.xiaoyi.com.aidlserver.BookService"));
        startService(intent);
        bindService(intent,mConnection,BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("Client","onServiceConnected");
            mBookService = IBookService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("Client","onServiceDisconnected");
            mBookService = null;
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnGetBookCount:
                handleGetBookCount();
                break;
            case R.id.btnAddBookIn:
                handleAddBookIn();
                break;
            case R.id.btnAddBookOut:
                handleAddBookOut();
                break;
        }
    }

    private void handleGetBookCount(){
        try {
            int count = mBookService.getBookCount();
            Log.e("Client","book count = " + count);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private int newBookID = 1;
    private void handleAddBookIn(){
        if(!checkBookService()){
            return;
        }
        Book book = new Book("Book" + newBookID, newBookID * 10.0f);
        try {
            mBookService.addBookIn(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void handleAddBookOut(){
        if(!checkBookService()){
            return;
        }
        Book book = new Book("OldBookOut", 10.0f);
        try {
            mBookService.addBookOut(book);
            Log.e("Client","addBookOut finish and book's new name :" + book.getName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private boolean checkBookService(){
        return mBookService != null;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unbindService(mConnection);
    }
}
