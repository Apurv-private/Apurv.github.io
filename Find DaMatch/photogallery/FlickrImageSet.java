package cmpt276.project.photogallery;


import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

public class FlickrImageSet {
    private Vector<String> urls;
    final private String TAG = "FlickrImages.java";
    final private int MAX_NUM_IMGS = 31;

    // SINGLETON CODE
    private static FlickrImageSet instance;
    private FlickrImageSet() {
        urls = new Vector<>();
    }
    public static FlickrImageSet getInstance() {
        if (instance == null) {
            instance = new FlickrImageSet();
        }
        return instance;
    }

    // OBJECT CODE
    public Boolean addUrl(String url) {
        if(!urls.contains(url)){
            urls.add(url);
            Log.i("TAG ", "New Url Added: " + getUrlAtIndex(urls.size() - 1));
            return true;
        }
        return false;
    }

    public String getUrlAtIndex(int index) {
        if (index > urls.size() - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Given index does not exist.");
        }
        return urls.get(index);
    }

    public void removeUrlAtIndex(int index) {
        if (index > urls.size() - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Given index does not exist.");
        }
        String toDeleteUrl = urls.get(index);
        urls.remove(index);
        Log.i(TAG, "Removed Url: " + toDeleteUrl);
    }

    public int getSize() {
        return this.urls.size();
    }

    public void clearAll() {
        urls.clear();
    }

    public void printToConsole() throws IOException {
        Log.i(TAG, "Size: " + urls.size());
        FlickrFetchr flickrFetchr = new FlickrFetchr();
        for (int i = 0; i < urls.size(); i++) {
            Log.i(TAG, "Index " + i + ", Url: " + this.getUrlAtIndex(i));
            String url = this.getUrlAtIndex(i);
//            String ius = flickrFetchr.getUrlString(url);

        }
    }

    public Vector<String> getUrls() {
        return urls;
    }

    public void shuffleURLs(){
        Collections.shuffle(urls);
    }
}
