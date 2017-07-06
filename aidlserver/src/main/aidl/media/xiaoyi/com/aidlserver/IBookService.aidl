// IBookService.aidl
package media.xiaoyi.com.aidlserver;
import media.xiaoyi.com.aidlserver.Book;
// Declare any non-default types here with import statements

interface IBookService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBooks();
    Book getBook();
    int getBookCount();
    void setBookPrice(in Book book , float price);
    void setBookName(in Book book , String name);
    void addBookIn(in Book book);
    void addBookOut(out Book book);
    void addBookInout(inout Book book);
}
