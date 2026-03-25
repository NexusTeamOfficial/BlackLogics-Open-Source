package com.nexusteam.internal.os.layouteditor.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.os.layouteditor.model.FileItem;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileListAdapter extends ArrayAdapter<FileItem> {
    private final Context context;
    private final ArrayList<FileItem> items;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final int thumbnailSize;
    
    public FileListAdapter(Context context, ArrayList<FileItem> items) {
        super(context, R.layout.list_item_file, items);
        this.context = context;
        this.items = items;
        this.thumbnailSize = (int) (context.getResources().getDisplayMetrics().density * 64); // 64dp
    }
    
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_file, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.item_icon);
            holder.textView = convertView.findViewById(R.id.item_text);
            holder.checkBox = convertView.findViewById(R.id.item_checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        FileItem item = items.get(position);
        holder.textView.setText(item.getName());
        holder.checkBox.setChecked(((ListView) parent).isItemChecked(position));
        

        holder.imageView.setImageResource(R.drawable.ic_image_placeholder);
        

        loadImageThumbnail(holder.imageView, item.getPath());
        
        return convertView;
    }
    
    private void loadImageThumbnail(final ImageView imageView, final String imagePath) {
        
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    File imageFile = new File(imagePath);
                    if (!imageFile.exists()) {
                        throw new Exception("File not found");
                    }
                    

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(imagePath, options);
                    

                    options.inSampleSize = calculateInSampleSize(
                    options,
                    thumbnailSize,
                    thumbnailSize
                    );
                    

                    options.inJustDecodeBounds = false;
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    final Bitmap bitmap =
                    BitmapFactory.decodeFile(imagePath, options);
                    
                    if (bitmap != null) {
                        final Bitmap thumbnail = ThumbnailUtils.extractThumbnail(
                        bitmap,
                        thumbnailSize,
                        thumbnailSize,
                        ThumbnailUtils.OPTIONS_RECYCLE_INPUT
                        );
                        

                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                Object tag = imageView.getTag();
                                if (tag != null && tag.equals(imagePath)) {
                                    imageView.setImageBitmap(thumbnail);
                                }
                            }
                        });
                        
                        if (bitmap != thumbnail) {
                            bitmap.recycle();
                        }
                    }
                    
                } catch (Exception e) {
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageResource(
                            R.drawable.ic_broken_image
                            );
                        }
                    });
                }
            }
        });
        

        imageView.setTag(imagePath);
    }
    
    
    private int calculateInSampleSize(BitmapFactory.Options options, 
    int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            
            while ((halfHeight / inSampleSize) >= reqHeight
            && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    
    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
        CheckBox checkBox;
    }
    
    public void shutdown() {
        executorService.shutdown();
    }
}
