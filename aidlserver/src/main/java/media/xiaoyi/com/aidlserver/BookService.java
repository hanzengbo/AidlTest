package media.xiaoyi.com.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanZengbo on 2017/7/6.
 */
public class BookService extends Service {
    public final String TAG = this.getClass().getSimpleName();
    private List<Book> mBooks = new ArrayList<>();

    @Override
    public void onCreate() {
        Log.e(TAG,"onCreate");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBookService.Stub mBinder = new IBookService.Stub(){

        @Override
        public List<Book> getBooks() throws RemoteException {
            return mBooks;
        }

        @Override
        public Book getBook() throws RemoteException {
            if(mBooks.size() > 0){
                return mBooks.get(0);
            }
            return null;
        }

        @Override
        public int getBookCount() throws RemoteException {
            return mBooks.size();
        }

        @Override
        public void setBookPrice(Book book, float price) throws RemoteException {
            for (Book b : mBooks){
                if(b.equals(book)){
                    b.setPrice(price);
                    break;
                }
            }
        }

        @Override
        public void setBookName(Book book, String name) throws RemoteException {
            //garbage function
            return;
        }

        @Override
        public void addBookIn(Book book) throws RemoteException {
            mBooks.add(book);
            Log.e("Server","add book:" + book.getName() + " list size = " + mBooks.size());
        }

        @Override
        public void addBookOut(Book book) throws RemoteException {
            Log.e("Server","add book:" + book.getName() + " list size = " + mBooks.size());
            book.setName("BookOut");
        }

        @Override
        public void addBookInout(Book book) throws RemoteException {
            Log.e(TAG,"addBookInout old name = " + book.getName());
            book.setName("BookInout");
        }
    };
}
