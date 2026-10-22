package com.nexusteam.blacklogics.sdk.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CollectionFolder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String description;
    private String parentId;
    private ArrayList<String> blueprintIds;
    private ArrayList<CollectionFolder> children;
    private boolean favorite;
    private ArrayList<String> tags;
    private String thumbnailPath;
    private long createdAt;
    private long updatedAt;
    private int sortOrder;

    public CollectionFolder() {
        this(UUID.randomUUID().toString(), "New Folder");
    }

    public CollectionFolder(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public CollectionFolder(String id, String name) {
        this.id = (id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id;
        this.name = name != null ? name : "New Folder";
        this.description = "";
        this.parentId = null;
        this.blueprintIds = new ArrayList<String>();
        this.children = new ArrayList<CollectionFolder>();
        this.favorite = false;
        this.tags = new ArrayList<String>();
        this.thumbnailPath = null;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
        this.sortOrder = 0;
    }


    public String getId() { return id; }
    public void setId(String id) {
        if (id != null && !id.isEmpty()) this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name != null ? name.trim() : "New Folder";
        touch();
    }

    public String getDescription() {
        return description != null ? description : "";
    }
    public void setDescription(String description) {
        this.description = description != null ? description : "";
        touch();
    }

    public String getParentId() { return parentId; }
    public void setParentId(String parentId) {
        this.parentId = (parentId != null && parentId.isEmpty()) ? null : parentId;
    }
    public boolean isRoot() {
        return parentId == null || parentId.isEmpty();
    }


    public List<String> getBlueprintIds() {
        return blueprintIds != null
                ? Collections.unmodifiableList(new ArrayList<String>(blueprintIds))
                : Collections.<String>emptyList();
    }

    public ArrayList<String> getBlueprintIdsRaw() {
        if (blueprintIds == null) blueprintIds = new ArrayList<String>();
        return blueprintIds;
    }

    public void setBlueprintIds(List<String> ids) {
        this.blueprintIds = ids != null ? new ArrayList<String>(ids) : new ArrayList<String>();
        touch();
    }

    public void addBlueprintId(String blueprintId) {
        if (blueprintId == null || blueprintId.isEmpty()) return;
        ArrayList<String> list = getBlueprintIdsRaw();
        if (!list.contains(blueprintId)) {
            list.add(blueprintId);
            touch();
        }
    }

    public void addBlueprintId(int index, String blueprintId) {
        if (blueprintId == null || blueprintId.isEmpty()) return;
        ArrayList<String> list = getBlueprintIdsRaw();
        if (list.contains(blueprintId)) return;
        if (index < 0 || index > list.size()) list.add(blueprintId);
        else list.add(index, blueprintId);
        touch();
    }

    public boolean removeBlueprintId(String blueprintId) {
        if (blueprintIds == null || blueprintId == null) return false;
        boolean r = blueprintIds.remove(blueprintId);
        if (r) touch();
        return r;
    }

    public boolean containsBlueprintId(String blueprintId) {
        return blueprintIds != null && blueprintIds.contains(blueprintId);
    }

    public int getBlueprintCount() {
        return blueprintIds != null ? blueprintIds.size() : 0;
    }

    public boolean hasBlueprints() { return getBlueprintCount() > 0; }


    public List<CollectionFolder> getChildren() {
        return children != null
                ? Collections.unmodifiableList(new ArrayList<CollectionFolder>(children))
                : Collections.<CollectionFolder>emptyList();
    }

    public ArrayList<CollectionFolder> getChildrenRaw() {
        if (children == null) children = new ArrayList<CollectionFolder>();
        return children;
    }

    public void setChildren(List<CollectionFolder> children) {
        this.children = children != null
                ? new ArrayList<CollectionFolder>(children)
                : new ArrayList<CollectionFolder>();
        if (this.children != null) {
            for (CollectionFolder c : this.children) {
                if (c != null) c.setParentId(this.id);
            }
        }
        touch();
    }

    public void addChild(CollectionFolder child) {
        if (child == null) return;
        child.setParentId(this.id);
        getChildrenRaw().add(child);
        touch();
    }

    public boolean removeChild(CollectionFolder child) {
        if (children == null || child == null) return false;
        boolean r = children.remove(child);
        if (r) { child.setParentId(null); touch(); }
        return r;
    }

    public boolean removeChildById(String childId) {
        if (children == null || childId == null) return false;
        for (int i = 0; i < children.size(); i++) {
            CollectionFolder c = children.get(i);
            if (c != null && childId.equals(c.getId())) {
                children.remove(i);
                c.setParentId(null);
                touch();
                return true;
            }
        }
        return false;
    }

    public int getChildCount() {
        return children != null ? children.size() : 0;
    }

    public boolean hasChildren() { return getChildCount() > 0; }

    public boolean isEmptyFolder() {
        return !hasBlueprints() && !hasChildren();
    }


    public CollectionFolder findById(String targetId) {
        if (targetId == null) return null;
        if (targetId.equals(this.id)) return this;
        if (children == null) return null;
        for (CollectionFolder child : children) {
            if (child == null) continue;
            CollectionFolder found = child.findById(targetId);
            if (found != null) return found;
        }
        return null;
    }

    public CollectionFolder findFolderContaining(String blueprintId) {
        if (blueprintId == null) return null;
        if (containsBlueprintId(blueprintId)) return this;
        if (children == null) return null;
        for (CollectionFolder child : children) {
            if (child == null) continue;
            CollectionFolder found = child.findFolderContaining(blueprintId);
            if (found != null) return found;
        }
        return null;
    }

    public List<CollectionFolder> flatten() {
        List<CollectionFolder> out = new ArrayList<CollectionFolder>();
        flattenInto(out);
        return out;
    }

    private void flattenInto(List<CollectionFolder> out) {
        out.add(this);
        if (children == null) return;
        for (CollectionFolder child : children) {
            if (child != null) child.flattenInto(out);
        }
    }

    public List<String> collectAllBlueprintIds() {
        List<String> out = new ArrayList<String>();
        collectIdsInto(out);
        return out;
    }

    private void collectIdsInto(List<String> out) {
        if (blueprintIds != null) out.addAll(blueprintIds);
        if (children != null) {
            for (CollectionFolder child : children) {
                if (child != null) child.collectIdsInto(out);
            }
        }
    }

    public int getTotalBlueprintCount() {
        return collectAllBlueprintIds().size();
    }


    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
        touch();
    }

    public List<String> getTags() {
        return tags != null
                ? Collections.unmodifiableList(new ArrayList<String>(tags))
                : Collections.<String>emptyList();
    }
    public ArrayList<String> getTagsRaw() {
        if (tags == null) tags = new ArrayList<String>();
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags != null ? new ArrayList<String>(tags) : new ArrayList<String>();
        touch();
    }
    public void addTag(String tag) {
        if (tag == null || tag.trim().isEmpty()) return;
        ArrayList<String> t = getTagsRaw();
        String s = tag.trim();
        if (!t.contains(s)) { t.add(s); touch(); }
    }

    public String getThumbnailPath() { return thumbnailPath; }
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
        touch();
    }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
        touch();
    }

    public void touch() { this.updatedAt = System.currentTimeMillis(); }

    public String getDisplayName() {
        if (name != null && !name.trim().isEmpty()) return name.trim();
        return "Folder";
    }

    public String getSubtitle() {
        int b = getBlueprintCount();
        int f = getChildCount();
        if (f > 0 && b > 0) return b + " widgets · " + f + " folders";
        if (f > 0) return f + (f == 1 ? " folder" : " folders");
        return b + (b == 1 ? " widget" : " widgets");
    }

    public CollectionFolder deepCopy(boolean newIds) {
        CollectionFolder copy = new CollectionFolder(
                newIds ? UUID.randomUUID().toString() : this.id,
                this.name
        );
        copy.description = this.description;
        copy.parentId = this.parentId;
        copy.blueprintIds = this.blueprintIds != null
                ? new ArrayList<String>(this.blueprintIds) : new ArrayList<String>();
        copy.favorite = this.favorite;
        copy.tags = this.tags != null ? new ArrayList<String>(this.tags) : new ArrayList<String>();
        copy.thumbnailPath = this.thumbnailPath;
        copy.createdAt = this.createdAt;
        copy.updatedAt = this.updatedAt;
        copy.sortOrder = this.sortOrder;
        if (this.children != null) {
            for (CollectionFolder child : this.children) {
                if (child != null) {
                    CollectionFolder cc = child.deepCopy(newIds);
                    cc.setParentId(copy.getId());
                    copy.getChildrenRaw().add(cc);
                }
            }
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectionFolder)) return false;
        CollectionFolder that = (CollectionFolder) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CollectionFolder{id='" + id + "', name='" + name
                + "', blueprints=" + getBlueprintCount()
                + ", children=" + getChildCount() + "}";
    }


    public static ArrayList<CollectionFolder> buildTree(List<CollectionFolder> flat) {
        ArrayList<CollectionFolder> roots = new ArrayList<CollectionFolder>();
        if (flat == null || flat.isEmpty()) return roots;
        Map<String, CollectionFolder> byId = new LinkedHashMap<String, CollectionFolder>();
        for (CollectionFolder f : flat) {
            if (f == null || f.getId() == null) continue;
            f.getChildrenRaw().clear();
            byId.put(f.getId(), f);
        }
        for (CollectionFolder f : byId.values()) {
            String pid = f.getParentId();
            if (pid != null && byId.containsKey(pid)) {
                byId.get(pid).getChildrenRaw().add(f);
            } else {
                f.setParentId(null);
                roots.add(f);
            }
        }
        return roots;
    }

    public static ArrayList<CollectionFolder> flattenForest(List<CollectionFolder> roots) {
        ArrayList<CollectionFolder> out = new ArrayList<CollectionFolder>();
        if (roots == null) return out;
        for (CollectionFolder root : roots) {
            if (root != null) out.addAll(root.flatten());
        }
        return out;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String id;
        private String name = "New Folder";
        private String description = "";
        private String parentId;
        private ArrayList<String> blueprintIds = new ArrayList<String>();
        private ArrayList<CollectionFolder> children = new ArrayList<CollectionFolder>();
        private boolean favorite;
        private ArrayList<String> tags = new ArrayList<String>();
        private int sortOrder;

        public Builder id(String id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String d) { this.description = d; return this; }
        public Builder parentId(String parentId) { this.parentId = parentId; return this; }
        public Builder blueprintIds(List<String> ids) {
            this.blueprintIds = ids != null ? new ArrayList<String>(ids) : new ArrayList<String>();
            return this;
        }
        public Builder addBlueprintId(String id) {
            if (id != null) this.blueprintIds.add(id);
            return this;
        }
        public Builder children(List<CollectionFolder> children) {
            this.children = children != null
                    ? new ArrayList<CollectionFolder>(children) : new ArrayList<CollectionFolder>();
            return this;
        }
        public Builder addChild(CollectionFolder child) {
            if (child != null) this.children.add(child);
            return this;
        }
        public Builder favorite(boolean favorite) { this.favorite = favorite; return this; }
        public Builder tags(List<String> tags) {
            this.tags = tags != null ? new ArrayList<String>(tags) : new ArrayList<String>();
            return this;
        }
        public Builder sortOrder(int sortOrder) { this.sortOrder = sortOrder; return this; }

        public CollectionFolder build() {
            CollectionFolder f = new CollectionFolder(id, name);
            f.setDescription(description);
            f.setParentId(parentId);
            f.setBlueprintIds(blueprintIds);
            f.setChildren(children);
            f.setFavorite(favorite);
            f.setTags(tags);
            f.setSortOrder(sortOrder);
            return f;
        }
    }
}
