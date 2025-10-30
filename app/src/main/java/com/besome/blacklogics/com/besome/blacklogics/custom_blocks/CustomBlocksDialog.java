/**
 * CustomBlocksDialog is a dialog fragment for selecting and importing custom blocks in the BlackLogics application.
 * This class manages the display and interaction with custom blocks stored in a project-specific JSON file.
 * It provides functionality to load blocks, display them in a RecyclerView, and import selected blocks to a central block repository.
 *
 * <p>Copyright (c) 2025 NexusTeam & SmartIndiaGaming. All rights reserved.</p>
 *
 * <p>This software is the confidential and proprietary information of NexusTeam & SmartIndiaGaming.
 * You may not disclose, reproduce, or distribute this software without prior written permission
 * from YourCompanyName. Unauthorized use or distribution is strictly prohibited.</p>
 *
 * @author NexusTeam & SmartIndiaGaming
 * @version 1.1
 * @since 2025-04-15
 */
package com.besome.blacklogics.custom_blocks;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.besome.blacklogics.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomBlocksDialog extends DialogFragment {

    private List<Block> blocks = new ArrayList<>();
    private BlocksAdapter adapter;
    private OnBlocksSelectedListener listener;
    private String sc_id;
    private static final String BLOCKS_PATH = Environment.getExternalStorageDirectory() + "/.blacklogics/resources/My Block/blocks.json";
    private static final String PROJECT_PATH = Environment.getExternalStorageDirectory() + "/.blacklogics/data/";
    private static final String CUSTOM_BLOCK_PATH = Environment.getExternalStorageDirectory() + "/.blacklogics/data/";

    // Utility instances
    private FileUtils fileUtils;
    private JsonUtils jsonUtils;
    private BlockUtils blockUtils;
    private ToastUtils toastUtils;

    public static CustomBlocksDialog newInstance(String sc_id) {
        CustomBlocksDialog dialog = new CustomBlocksDialog();
        Bundle args = new Bundle();
        args.putString("sc_id", sc_id);
        dialog.setArguments(args);
        return dialog;
    }

    public interface OnBlocksSelectedListener {
        void onBlocksSelected(List<Block> selectedBlocks);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof OnBlocksSelectedListener)) {
            throw new ClassCastException(context + " must implement OnBlocksSelectedListener");
        }
        listener = (OnBlocksSelectedListener) context;
        // Initialize utilities
        fileUtils = new FileUtils(context);
        jsonUtils = new JsonUtils();
        blockUtils = new BlockUtils();
        toastUtils = new ToastUtils(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sc_id = getArguments().getString("sc_id");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        loadBlocks();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Select Custom Blocks");

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_custom_blocks, null);
        RecyclerView recyclerView = view.findViewById(R.id.blocks_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BlocksAdapter(blocks);
        recyclerView.setAdapter(adapter);

        builder.setView(view)
                .setPositiveButton("Import", (dialog, which) -> {
                    List<Block> selected = blockUtils.getSelectedBlocks(blocks);
                    if (selected.isEmpty()) {
                        toastUtils.showShort("Please select at least one block.");
                    } else {
                        if (importBlocks(selected)) {
                            toastUtils.showShort("Blocks imported successfully!");
                            listener.onBlocksSelected(selected);
                        } else {
                            toastUtils.showShort("Import failed. Check storage permissions.");
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .setNeutralButton("Select All", (dialog, which) -> {
                    blockUtils.toggleAllBlocksSelection(blocks, true);
                    adapter.notifyDataSetChanged();
                });

        return builder.create();
    }

    private void loadBlocks() {
        blocks.clear();
        String path = fileUtils.getCustomBlocksFilePath(sc_id);
        try {
            if (!fileUtils.fileExists(path)) {
                toastUtils.showShort("No blocks found for this project.");
                return;
            }
            String json = fileUtils.readFile(path);
            if (json.isEmpty()) {
                toastUtils.showShort("Block file is empty.");
                return;
            }
            JSONObject jsonObject = jsonUtils.parseJsonObject(json);
            Iterator<String> keys = jsonObject.keys();
            Set<String> blockIds = new HashSet<>();

            while (keys.hasNext()) {
                String key = keys.next();
                try {
                    JSONArray blockArray = jsonUtils.getJsonArray(jsonObject, key);
                    for (int i = 0; i < blockArray.length(); i++) {
                        JSONObject blockObj = blockArray.getJSONObject(i);
                        Block block = blockUtils.createBlockFromJson(blockObj);
                        if (block.id.isEmpty() || blockIds.contains(block.id)) {
                            block.id = blockUtils.generateUniqueId();
                        }
                        blockIds.add(block.id);
                        block.name = blockUtils.getBlockName(block);
                        block.isSelected = false;

                        if (blockUtils.isValidBlock(block)) {
                            blocks.add(block);
                        }
                    }
                } catch (Exception e) {
                    toastUtils.showShort("Error parsing blocks for " + key + ": " + e.getMessage());
                }
            }
            if (blocks.isEmpty()) {
                toastUtils.showShort("No valid blocks found.");
            }
        } catch (Exception e) {
            toastUtils.showShort("Error loading blocks: " + e.getMessage());
        }
    }

    private boolean importBlocks(List<Block> selectedBlocks) {
        try {
            String customBlockPath = fileUtils.getCustomBlocksFilePath(sc_id);
            File dir = new File(customBlockPath).getParentFile();
            if (!fileUtils.ensureDirectoryExists(dir)) {
                toastUtils.showShort("Cannot create directory: " + dir.getPath());
                return false;
            }

            List<HashMap<String, Object>> categories = fileUtils.loadCategories(customBlockPath);
            HashMap<String, Object> myBlocks = blockUtils.findOrCreateMyBlocksCategory(categories);

            List<HashMap<String, Object>> blockList = blockUtils.getBlockList(myBlocks);
            blockUtils.addOrUpdateBlocks(blockList, selectedBlocks);

            String jsonOutput = jsonUtils.toJson(categories);
            return fileUtils.writeFile(customBlockPath, jsonOutput);
        } catch (Exception e) {
            toastUtils.showShort("Import error: " + e.getMessage());
            return false;
        }
    }

    public boolean setCustomBlocks(List<Block> blocksToSave) {
        try {
            String customBlockPath = fileUtils.getCustomBlocksFilePath(sc_id);
            File dir = new File(customBlockPath).getParentFile();
            if (!fileUtils.ensureDirectoryExists(dir)) {
                toastUtils.showShort("Cannot create directory: " + dir.getPath());
                return false;
            }

            // Load existing categories
            List<HashMap<String, Object>> categories = fileUtils.loadCategories(customBlockPath);
            HashMap<String, Object> myBlocks = blockUtils.findOrCreateMyBlocksCategory(categories);
            List<HashMap<String, Object>> blockList = blockUtils.getBlockList(myBlocks);

            // Track existing block IDs and names to avoid duplicates
            Set<String> existingIds = new HashSet<>();
            Set<String> existingNames = new HashSet<>();
            for (HashMap<String, Object> blockMap : blockList) {
                existingIds.add((String) blockMap.get("id"));
                existingNames.add((String) blockMap.get("name"));
            }

            // Add new blocks, skipping duplicates
            for (Block block : blocksToSave) {
                if (!blockUtils.isValidBlock(block)) {
                    continue; // Skip invalid blocks
                }
                if (existingIds.contains(block.id) || existingNames.contains(block.name)) {
                    continue; // Skip duplicates
                }
                HashMap<String, Object> blockMap = new HashMap<>();
                blockMap.put("id", block.id);
                blockMap.put("type", block.type);
                blockMap.put("code", block.code.isEmpty() ? block.views : block.code);
                blockMap.put("views", block.views);
                blockMap.put("color", block.color);
                blockMap.put("name", block.name);
                if (!block.tty.isEmpty()) {
                    blockMap.put("tty", block.tty);
                }
                blockList.add(blockMap);
                existingIds.add(block.id);
                existingNames.add(block.name);
            }

            // Save updated categories
            String jsonOutput = jsonUtils.toJson(categories);
            return fileUtils.writeFile(customBlockPath, jsonOutput);
        } catch (Exception e) {
            toastUtils.showShort("Error saving custom blocks: " + e.getMessage());
            return false;
        }
    }

    // Utility class for file operations
    private static class FileUtils {
        private final Context context;

        public FileUtils(Context context) {
            this.context = context;
        }

        public String getBlocksFilePath(String sc_id) {
            return PROJECT_PATH + sc_id + "/blocks.json";
        }

        public String getCustomBlocksFilePath(String sc_id) {
            return CUSTOM_BLOCK_PATH + sc_id + "/cusblock.json";
        }

        public boolean fileExists(String path) {
            return new File(path).exists();
        }

        public boolean ensureDirectoryExists(File dir) {
            return dir.exists() || dir.mkdirs();
        }

        public String readFile(String path) {
            try (FileInputStream fis = new FileInputStream(path)) {
                byte[] data = new byte[(int) new File(path).length()];
                fis.read(data);
                return new String(data, StandardCharsets.UTF_8);
            } catch (IOException e) {
                return "";
            }
        }

        public boolean writeFile(String path, String content) {
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(content.getBytes(StandardCharsets.UTF_8));
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        public List<HashMap<String, Object>> loadCategories(String path) {
            List<HashMap<String, Object>> categories = new ArrayList<>();
            if (fileExists(path)) {
                String json = readFile(path);
                if (!json.isEmpty()) {
                    try {
                        categories = new Gson().fromJson(json, new TypeToken<List<HashMap<String, Object>>>(){}.getType());
                    } catch (Exception ignored) {}
                }
            }
            return categories;
        }
    }

    // Utility class for JSON operations
    private static class JsonUtils {
        public JSONObject parseJsonObject(String json) throws Exception {
            return new JSONObject(json);
        }

        public JSONArray getJsonArray(JSONObject jsonObject, String key) throws Exception {
            String data = jsonObject.getString(key);
            return data.trim().startsWith("[") ? new JSONArray(data) : new JSONArray();
        }

        public String toJson(Object object) {
            return new Gson().toJson(object);
        }

        public boolean isValidJson(String json) {
            try {
                new JSONObject(json);
                return true;
            } catch (Exception e) {
                try {
                    new JSONArray(json);
                    return true;
                } catch (Exception ignored) {
                    return false;
                }
            }
        }
    }

    // Utility class for block operations
    private static class BlockUtils {
        public Block createBlockFromJson(JSONObject blockObj) {
            Block block = new Block();
            block.id = blockObj.optString("id", "");
            block.type = blockObj.optString("type", "unknown");
            block.views = blockObj.optString("views", "");
            block.code = blockObj.optString("code", "");
            block.color = blockObj.optString("color", "#2196F3");
            block.tty = blockObj.optString("tty", "");
            return block;
        }

        public String generateUniqueId() {
            return "blk_" + UUID.randomUUID().toString();
        }

        public boolean isValidBlock(Block block) {
            return !block.type.equals("unknown") && !block.name.isEmpty();
        }

        public List<Block> getSelectedBlocks(List<Block> blocks) {
            List<Block> selected = new ArrayList<>();
            for (Block block : blocks) {
                if (block.isSelected) {
                    selected.add(block);
                }
            }
            return selected;
        }

        public void toggleAllBlocksSelection(List<Block> blocks, boolean select) {
            for (Block block : blocks) {
                block.isSelected = select;
            }
        }

        public String getBlockName(Block block) {
            // Priority: TextView views, JSON views, type-based
            if ("TextView".equals(block.type) && !block.views.trim().isEmpty()) {
                return block.views.trim();
            }
            if (!block.views.isEmpty() && block.views.trim().startsWith("[")) {
                try {
                    JSONArray viewsArray = new JSONArray(block.views);
                    for (int i = 0; i < viewsArray.length(); i++) {
                        JSONObject viewObj = viewsArray.getJSONObject(i);
                        if ("TextView".equals(viewObj.optString("type")) && !viewObj.optString("code", "").isEmpty()) {
                            return viewObj.optString("code").trim();
                        }
                    }
                } catch (Exception ignored) {}
                String cleaned = cleanViews(block.views);
                if (!cleaned.isEmpty()) {
                    return cleaned;
                }
            }
            return getTypeBasedName(block.type);
        }

        private String cleanViews(String views) {
            String cleaned = views.replaceAll("%[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+\\s*", "").trim();
            Pattern pattern = Pattern.compile("[a-zA-Z0-9\\s]+");
            Matcher matcher = pattern.matcher(cleaned);
            return matcher.find() ? matcher.group().trim() : "";
        }

        private String getTypeBasedName(String type) {
            switch (type) {
                case "if_block": return "If Block";
                case "boolean_block": return "Boolean Block";
                case "component_type": return "Component";
                case "component_typeN": return "Number Component";
                case "TextViewT": return "Text Input";
                case "TextViewN": return "Number Input";
                case "boolean_field": return "Checkbox";
                case "regular_block": return "Regular Block";
                default:
                    if (type.startsWith("%list.")) {
                        return "List " + type.replace("%list.", "").replace("_", " ");
                    }
                    return type.replace("_", " ").trim();
            }
        }

        public HashMap<String, Object> findOrCreateMyBlocksCategory(List<HashMap<String, Object>> categories) {
            for (HashMap<String, Object> cat : categories) {
                if ("My Blocks".equals(cat.get("name"))) {
                    return cat;
                }
            }
            HashMap<String, Object> myBlocks = new HashMap<>();
            myBlocks.put("name", "My Blocks");
            myBlocks.put("color", "#2196F3");
            myBlocks.put("blocks", new ArrayList<HashMap<String, Object>>());
            categories.add(myBlocks);
            return myBlocks;
        }

        public List<HashMap<String, Object>> getBlockList(HashMap<String, Object> category) {
            return (List<HashMap<String, Object>>) category.computeIfAbsent("blocks", k -> new ArrayList<>());
        }

        public void addOrUpdateBlocks(List<HashMap<String, Object>> blockList, List<Block> selectedBlocks) {
            for (Block block : selectedBlocks) {
                HashMap<String, Object> blockMap = new HashMap<>();
                blockMap.put("id", block.id);
                blockMap.put("type", block.type);
                blockMap.put("code", block.code.isEmpty() ? block.views : block.code);
                blockMap.put("views", block.views);
                blockMap.put("color", block.color);
                blockMap.put("name", block.name);
                if (!block.tty.isEmpty()) {
                    blockMap.put("tty", block.tty);
                }
                // Replace or add
                boolean replaced = false;
                for (int i = 0; i < blockList.size(); i++) {
                    if (block.name.equals(blockList.get(i).get("name"))) {
                        blockList.set(i, blockMap);
                        replaced = true;
                        break;
                    }
                }
                if (!replaced) {
                    blockList.add(blockMap);
                }
            }
        }
    }

    // Utility class for toast messages
    private static class ToastUtils {
        private final Context context;

        public ToastUtils(Context context) {
            this.context = context;
        }

        public void showShort(String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }

        public void showLong(String message) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    private class BlocksAdapter extends RecyclerView.Adapter<BlocksAdapter.ViewHolder> {
        private final List<Block> blocks;

        public BlocksAdapter(List<Block> blocks) {
            this.blocks = blocks;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_block_category, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Block block = blocks.get(position);
            holder.name.setText(block.name);
            holder.checkBox.setChecked(block.isSelected);
            holder.checkBox.setOnCheckedChangeListener((button, isChecked) -> block.isSelected = isChecked);
        }

        @Override
        public int getItemCount() {
            return blocks.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBox;
            TextView name;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.category_checkbox);
                name = itemView.findViewById(R.id.category_name);
            }
        }
    }

    public static class Block {
        public String id;
        public String type;
        public String views;
        public String code;
        public String color;
        public String name;
        public String tty;
        public boolean isSelected;
    }
}