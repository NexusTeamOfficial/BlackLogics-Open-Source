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
import com.besome.blacklogics.*;
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
        
        // Clear previous image while loading new one
        holder.imageView.setImageResource(R.drawable.ic_image_placeholder);
        
        // Load actual image thumbnail
        loadImageThumbnail(holder.imageView, item.getPath());

        return convertView;
    }

    private void loadImageThumbnail(ImageView imageView, String imagePath) {
        executorService.execute(() -> {
            try {
                File imageFile = new File(imagePath);
                if (!imageFile.exists()) {
                    throw new Exception("File not found");
                }

                // First decode with bounds to get dimensions
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imagePath, options);

                // Calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(options, thumbnailSize, thumbnailSize);
                
                // Decode actual bitmap
                options.inJustDecodeBounds = false;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
                
                if (bitmap != null) {
                    // Create square thumbnail
                    Bitmap thumbnail = ThumbnailUtils.extractThumbnail(
                        bitmap, 
                        thumbnailSize, 
                        thumbnailSize,
                        ThumbnailUtils.OPTIONS_RECYCLE_INPUT
                    );
                    
                    // Set bitmap on UI thread
                    imageView.post(() -> {
                        if (imageView.getTag() != null && imageView.getTag().equals(imagePath)) {
                            imageView.setImageBitmap(thumbnail);
                        }
                    });
                    
                    // Recycle the original bitmap if it's different from thumbnail
                    if (bitmap != thumbnail) {
                        bitmap.recycle();
                    }
                }
            } catch (Exception e) {
                imageView.post(() -> imageView.setImageResource(R.drawable.ic_broken_image));
            }
        });
        
        // Set current path as tag to prevent wrong image being set
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