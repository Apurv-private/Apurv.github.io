package cmpt276.project.photogallery;

import android.app.DownloadManager;
import android.content.SharedPreferences;
import android.os.PowerManager;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.widget.SearchView;
import android.os.AsyncTask;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import androidx.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.SearchView;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.SearchView;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cmpt276.project.GUI.Options;
import cmpt276.project.R;

public class PhotoGalleryFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";

    private RecyclerView mPhotoRecyclerView;
    private List<GalleryItem> mItems = new ArrayList<>();
    private ThumbnailDownloader<PhotoHolder> mThumbnailDownloader;
    private FlickrImageSet flickrImageSet;
    private Toast toast;

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        updateItems();

        flickrImageSet = FlickrImageSet.getInstance();

        Handler responseHandler = new Handler();
        mThumbnailDownloader = new ThumbnailDownloader<>(responseHandler);
        mThumbnailDownloader.setThumbnailDownloadListener(
            new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
                @Override
                public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap bitmap) {
                    Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                    photoHolder.bindDrawable(drawable);
                }
            }
        );
        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();
        Log.i(TAG, "Background thread started");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.photo_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));



        setupAdapter();

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbnailDownloader.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_photo_gallery, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d(TAG, "QueryTextSubmit: " + s);
                    QueryPreferences.setStoredQuery(getActivity(), s);
                    updateItems();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d(TAG, "QueryTextChange: " + s);
                    return false;
                }
            });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = QueryPreferences.getStoredQuery(getActivity());
                searchView.setQuery(query, false);
            }
        });
    }

//    private class DownloadFile extends AsyncTask<String, Void, Bitmap> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... URL) {
//
//            String imageURL = URL[0];
//
//            Bitmap bitmap = null;
//            try {
//                // Download Image from URL
//                InputStream input = new java.net.URL(URL).openStream();
//                // Decode Bitmap
//                bitmap = BitmapFactory.decodeStream(input);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap result) {
//
//            if (result != null) {
//                File destination = new File(Context.getFilesDir(),
//                        "/DownloadTestFolder/"+file_name + ".jpg");
//                try {
//                    destination.createNewFile();
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    result.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
//                    byte[] bitmapdata = bos.toByteArray();
//
//                    FileOutputStream fos = new FileOutputStream(destination);
//                    fos.write(bitmapdata);
//                    fos.flush();
//                    fos.close();
//                    selectedFile = destination;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                QueryPreferences.setStoredQuery(getActivity(), null);
                updateItems();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateItems() {
        String query = QueryPreferences.getStoredQuery(getActivity());
        new FetchItemsTask(query).execute();
    }

    private void setupAdapter() {
        if (isAdded()) {
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder {
        private ImageView mItemImageView;

        public PhotoHolder(View itemView) {
            super(itemView);

            mItemImageView =  itemView.findViewById(R.id.item_image_view);
        }

        public void bindDrawable(Drawable drawable) {
            mItemImageView.setImageDrawable(drawable);
        }

        //The code is taken from https://blog.cindypotvin.com/toast-specific-duration-android/
        public void setListnr(final int position, final String url, final String caption, final String id) {
            mItemImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sent(url);
//                    DownloadTask downloadTask = new DownloadTask(Options.this);
//                    FlickrFetchr flickrFetchr = new FlickrFetchr();
//                    try {
//                        flickrFetchr.getUrlBytes(url);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    Log.i("PhotoGalleryFragment: ", "Image_Position: " + position + "\nUrl: " + url + "\nCaption: " + caption + "\nId: " + id);
                    if(flickrImageSet.addUrl(url)) {
                        new MyAsnyc().execute();

                        Bitmap bitmap = null;
//                        try {
//                            InputStream inputStream = new URL(url).openStream();
//                            bitmap = BitmapFactory.decodeStream(inputStream);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }


//                        try {
////                            URL surl = new URL(url);
////                            HttpURLConnection urlConnection = (HttpURLConnection) surl.openConnection();
////                            urlConnection.setRequestMethod("GET");
////                            urlConnection.setDoOutput(true);
////                            urlConnection.connect();
////
////                            File sdcard = Environment.getExternalStorageDirectory();
////                            File file = new File(sdcard, "filename.ext");
////
////                            FileOutputStream fileOutput = new FileOutputStream(file);
////                            InputStream inputStream = urlConnection.getInputStream();
////
////                            byte[] buffer = new byte[1024];
////                            int bufferLength = 0;
////
////                            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
////                                fileOutput.write(buffer, 0, bufferLength);
////                            }
////                            fileOutput.close();
////
////                        } catch (MalformedURLException e) {
////                            e.printStackTrace();
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }


                        toast=Toast.makeText(getActivity(), "New Image added!", Toast.LENGTH_SHORT);
                        CountDownTimer toastCountDown;
                        toastCountDown = new CountDownTimer(500, 1000 /*Tick duration*/) {
                            public void onTick(long millisUntilFinished) {
                                toast.show();
                            }
                            public void onFinish() {
                                toast.cancel();
                            }

                        };
                        // Show the toast and starts the countdown
                        toast.show();
                        toastCountDown.start();
                    }
                    else{
                        toast=Toast.makeText(getActivity(), "Image already added!", Toast.LENGTH_SHORT);
                        CountDownTimer toastCountDown;
                        toastCountDown = new CountDownTimer(500, 1000 /*Tick duration*/) {
                            public void onTick(long millisUntilFinished) {
                                toast.show();
                            }
                            public void onFinish() {
                                toast.cancel();
                            }

                        };
                        // Show the toast and starts the countdown
                        toast.show();
                        toastCountDown.start();
                    }
                    try {
                        flickrImageSet.printToConsole();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void sent(String url) {

    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<GalleryItem> mGalleryItems;

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_gallery, viewGroup, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            Drawable placeholder = getResources().getDrawable(R.drawable.bill_up_close);
            photoHolder.bindDrawable(placeholder);
            mThumbnailDownloader.queueThumbnail(photoHolder, galleryItem.getUrl());
            photoHolder.setListnr(position, galleryItem.getUrl(), galleryItem.getCaption(), galleryItem.getId());
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,List<GalleryItem>> {

        private String mQuery;

        public FetchItemsTask(String query) {
            mQuery = query;
        }

        @Override
        protected List<GalleryItem> doInBackground(Void... params) {

            if (mQuery == null) {
                return new FlickrFetchr().fetchRecentPhotos();
            } else {
                return new FlickrFetchr().searchPhotos(mQuery);
            }
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            mItems = items;
            setupAdapter();
        }

    }

//    private class DownloadFile extends AsyncTask<String, Void, Bitmap> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }

//        @Override
//        protected Bitmap doInBackground(String... URL) {
//
//            String imageURL = URL[0];
//
//            Bitmap bitmap = null;
//            try {
//                // Download Image from URL
//                InputStream input = new java.net.URL(URL).openStream();
//                // Decode Bitmap
//                bitmap = BitmapFactory.decodeStream(input);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return bitmap;
//        }

//        @Override
//        protected void onPostExecute(Bitmap result) {
//
//            if (result != null) {
//                File destination = new File(Context.getFilesDir(),
//                        "/DownloadTestFolder/"+file_name + ".jpg");
//                try {
//                    destination.createNewFile();
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    result.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
//                    byte[] bitmapdata = bos.toByteArray();
//
//                    FileOutputStream fos = new FileOutputStream(destination);
//                    fos.write(bitmapdata);
//                    fos.flush();
//                    fos.close();
//                    selectedFile = destination;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//
//        }
//public class MyAsnyc extends AsyncTask<Void, Void, Void> {
//    public static File file;
//    InputStream is;
//
//    protected void doInBackground() throws IOException {
//
//        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        file = new File(path, "DemoPicture.jpg");
//        try {
//            // Make sure the Pictures directory exists.
//            path.mkdirs();
//
//            URL url = new URL("http://androidsaveitem.appspot.com/downloadjpg");
//            /* Open a connection to that URL. */
//            URLConnection ucon = url.openConnection();
//
//            /*
//             * Define InputStreams to read from the URLConnection.
//             */
//            is = ucon.getInputStream();
//
//            OutputStream os = new FileOutputStream(file);
//            byte[] data = new byte[is.available()];
//            is.read(data);
//            os.write(data);
//            is.close();
//            os.close();
//
//        } catch (IOException e) {
//            Log.d("ImageManager", "Error: " + e);
//        }
//    }
//
//    @Override
//    protected Void doInBackground(Void... params) {
//        // TODO Auto-generated method stub
//        try {
//            doInBackground();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    protected void onPostExecute() {
//        // Tell the media scanner about the new file so that it is
//        // immediately available to the user.
//        MediaScannerConnection.scanFile(null,
//                new String[]{file.toString()}, null,
//                new MediaScannerConnection.OnScanCompletedListener() {
//                    public void onScanCompleted(String path, Uri uri) {
//                        Log.i("ExternalStorage", "Scanned " + path + ":");
//                        Log.i("ExternalStorage", "-> uri=" + uri);
//                    }
//                });
//
//    }
//}
    }


