package com.nexusteam.internal.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WidgetCollectionBean extends SelectableBean implements Serializable, Cloneable {

    private static final long serialVersionUID = 2L;

    public String name;
    public ArrayList<ViewBean> widgets;

    public String id;
    public String description;
    public ArrayList<WidgetCollectionBean> children;
    public String parentId;
    public String thumbnailPath;
    public boolean favorite;
    public ArrayList<String> tags;
    public long createdAt;
    public long updatedAt;
    public int sortOrder;

    public WidgetCollectionBean() {
        this.name = "Untitled";
        this.widgets = new ArrayList<ViewBean>();
        initNewFields(null);
    }

    public WidgetCollectionBean(String name) {
        this(name, new ArrayList<ViewBean>());
    }

    public WidgetCollectionBean(String str, ArrayList<ViewBean> arrayList) {
        this.name = str;
        this.widgets = arrayList != null ? arrayList : new ArrayList<ViewBean>();
        initNewFields(null);
    }

    public WidgetCollectionBean(String id, String name, ArrayList<ViewBean> widgets) {
        this.name = name;
        this.widgets = widgets != null ? widgets : new ArrayList<ViewBean>();
        initNewFields(id);
    }

    private void initNewFields(String preferredId) {
        this.id = (preferredId != null && preferredId.length() > 0)
                ? preferredId
                : UUID.randomUUID().toString();
        if (this.name == null) this.name = "Untitled";
        if (this.widgets == null) this.widgets = new ArrayList<ViewBean>();
        this.description = "";
        this.children = new ArrayList<WidgetCollectionBean>();
        this.parentId = null;
        this.thumbnailPath = null;
        this.favorite = false;
        this.tags = new ArrayList<String>();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
        this.sortOrder = 0;
    }

    public String getName() {
        return name != null ? name : "Untitled";
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : "Untitled";
        touch();
    }

    public ArrayList<ViewBean> getWidgets() {
        if (widgets == null) widgets = new ArrayList<ViewBean>();
        return widgets;
    }

    public void setWidgets(ArrayList<ViewBean> widgets) {
        this.widgets = widgets != null ? widgets : new ArrayList<ViewBean>();
        touch();
    }

    public ArrayList<ViewBean> getWidgetsRaw() {
        return getWidgets();
    }

    public String getId() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }

    public void setId(String id) {
        if (id != null && !id.isEmpty()) this.id = id;
    }

    public String getDescription() {
        return description != null ? description : "";
    }

    public void setDescription(String description) {
        this.description = description != null ? description : "";
        touch();
    }

    public ArrayList<WidgetCollectionBean> getChildren() {
        if (children == null) children = new ArrayList<WidgetCollectionBean>();
        return children;
    }

    public ArrayList<WidgetCollectionBean> getChildrenRaw() {
        return getChildren();
    }

    public void setChildren(ArrayList<WidgetCollectionBean> children) {
        this.children = children != null ? children : new ArrayList<WidgetCollectionBean>();
        for (WidgetCollectionBean c : this.children) {
            if (c != null) c.parentId = this.getId();
        }
        touch();
    }

    public void addChild(WidgetCollectionBean child) {
        if (child == null) return;
        child.parentId = getId();
        getChildren().add(child);
        touch();
    }

    public boolean removeChildById(String childId) {
        if (children == null || childId == null) return false;
        for (int i = 0; i < children.size(); i++) {
            WidgetCollectionBean c = children.get(i);
            if (c != null && childId.equals(c.getId())) {
                children.remove(i);
                c.parentId = null;
                touch();
                return true;
            }
        }
        return false;
    }

    public int getChildCount() {
        return children != null ? children.size() : 0;
    }

    public boolean hasChildren() {
        return getChildCount() > 0;
    }

    public int getWidgetCount() {
        return widgets != null ? widgets.size() : 0;
    }

    public boolean hasWidgets() {
        return getWidgetCount() > 0;
    }

    public boolean isFolder() {
        return !hasWidgets() && hasChildren();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = (parentId != null && parentId.isEmpty()) ? null : parentId;
    }

    public boolean isRoot() {
        return parentId == null || parentId.isEmpty();
    }

    public WidgetCollectionBean findById(String targetId) {
        if (targetId == null) return null;
        if (targetId.equals(getId())) return this;
        if (children == null) return null;
        for (WidgetCollectionBean child : children) {
            if (child == null) continue;
            WidgetCollectionBean found = child.findById(targetId);
            if (found != null) return found;
        }
        return null;
    }

    public List<WidgetCollectionBean> flatten() {
        List<WidgetCollectionBean> out = new ArrayList<WidgetCollectionBean>();
        flattenInto(out);
        return out;
    }

    private void flattenInto(List<WidgetCollectionBean> out) {
        out.add(this);
        if (children == null) return;
        for (WidgetCollectionBean child : children) {
            if (child != null) child.flattenInto(out);
        }
    }

    public int getTotalWidgetCount() {
        int count = getWidgetCount();
        if (children != null) {
            for (WidgetCollectionBean child : children) {
                if (child != null) count += child.getTotalWidgetCount();
            }
        }
        return count;
    }

    public String getThumbnailPath() { return thumbnailPath; }
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
        touch();
    }

    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
        touch();
    }

    public void toggleFavorite() {
        this.favorite = !this.favorite;
        touch();
    }

    public List<String> getTags() {
        if (tags == null) return Collections.emptyList();
        return Collections.unmodifiableList(new ArrayList<String>(tags));
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
        if (!t.contains(s)) {
            t.add(s);
            touch();
        }
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

    public void touch() {
        this.updatedAt = System.currentTimeMillis();
    }

    public String getDisplayName() {
        if (name != null && name.trim().length() > 0) return name.trim();
        return isFolder() ? "Folder" : "Collection";
    }

    public String getSubtitle() {
        if (isFolder()) {
            int n = getChildCount();
            return n + (n == 1 ? " collection" : " collections");
        }
        int w = getWidgetCount();
        if (hasChildren()) {
            return w + " widgets · " + getChildCount() + " folders";
        }
        return w + (w == 1 ? " widget" : " widgets");
    }

    public void addWidget(ViewBean bean) {
        if (bean == null) return;
        getWidgets().add(bean);
        touch();
    }

    public void addWidgets(List<ViewBean> beans) {
        if (beans == null || beans.isEmpty()) return;
        getWidgets().addAll(beans);
        touch();
    }

    public boolean removeWidget(ViewBean bean) {
        if (widgets == null || bean == null) return false;
        boolean r = widgets.remove(bean);
        if (r) touch();
        return r;
    }

    public void clearWidgets() {
        if (widgets != null) {
            widgets.clear();
            touch();
        }
    }

    public WidgetCollectionBean deepCopy(boolean newIds) {
        ArrayList<ViewBean> widgetCopy = new ArrayList<ViewBean>();
        if (widgets != null) {
            for (ViewBean b : widgets) {
                if (b != null) {
                    try {
                        widgetCopy.add(b.clone());
                    } catch (Exception e) {
                        widgetCopy.add(b);
                    }
                }
            }
        }
        WidgetCollectionBean copy = new WidgetCollectionBean(
                newIds ? UUID.randomUUID().toString() : getId(),
                this.name,
                widgetCopy
        );
        copy.description = this.description;
        copy.parentId = this.parentId;
        copy.thumbnailPath = this.thumbnailPath;
        copy.favorite = this.favorite;
        copy.tags = this.tags != null ? new ArrayList<String>(this.tags) : new ArrayList<String>();
        copy.createdAt = this.createdAt;
        copy.updatedAt = this.updatedAt;
        copy.sortOrder = this.sortOrder;
        if (this.children != null) {
            for (WidgetCollectionBean child : this.children) {
                if (child != null) {
                    WidgetCollectionBean cc = child.deepCopy(newIds);
                    cc.parentId = copy.getId();
                    copy.getChildren().add(cc);
                }
            }
        }
        return copy;
    }

    @Override
    public WidgetCollectionBean clone() {
        return deepCopy(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WidgetCollectionBean)) return false;
        WidgetCollectionBean that = (WidgetCollectionBean) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "WidgetCollectionBean{id='" + id + "', name='" + name
                + "', widgets=" + getWidgetCount()
                + ", children=" + getChildCount() + "}";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name = "Untitled";
        private String description = "";
        private ArrayList<ViewBean> widgets = new ArrayList<ViewBean>();
        private ArrayList<WidgetCollectionBean> children = new ArrayList<WidgetCollectionBean>();
        private String parentId;
        private String thumbnailPath;
        private boolean favorite;
        private ArrayList<String> tags = new ArrayList<String>();
        private int sortOrder;

        public Builder id(String id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder widgets(ArrayList<ViewBean> widgets) {
            this.widgets = widgets != null ? widgets : new ArrayList<ViewBean>();
            return this;
        }
        public Builder addWidget(ViewBean bean) {
            if (bean != null) this.widgets.add(bean);
            return this;
        }
        public Builder children(ArrayList<WidgetCollectionBean> children) {
            this.children = children != null ? children : new ArrayList<WidgetCollectionBean>();
            return this;
        }
        public Builder addChild(WidgetCollectionBean child) {
            if (child != null) this.children.add(child);
            return this;
        }
        public Builder parentId(String parentId) { this.parentId = parentId; return this; }
        public Builder thumbnailPath(String path) { this.thumbnailPath = path; return this; }
        public Builder favorite(boolean favorite) { this.favorite = favorite; return this; }
        public Builder tags(List<String> tags) {
            this.tags = tags != null ? new ArrayList<String>(tags) : new ArrayList<String>();
            return this;
        }
        public Builder sortOrder(int sortOrder) { this.sortOrder = sortOrder; return this; }

        public WidgetCollectionBean build() {
            WidgetCollectionBean bean = new WidgetCollectionBean(id, name, widgets);
            bean.description = description;
            bean.parentId = parentId;
            bean.thumbnailPath = thumbnailPath;
            bean.favorite = favorite;
            bean.tags = tags;
            bean.sortOrder = sortOrder;
            bean.setChildren(children);
            return bean;
        }
    }

    public static ArrayList<WidgetCollectionBean> buildTree(List<WidgetCollectionBean> flat) {
        ArrayList<WidgetCollectionBean> roots = new ArrayList<WidgetCollectionBean>();
        if (flat == null || flat.isEmpty()) return roots;
        Map<String, WidgetCollectionBean> byId = new LinkedHashMap<String, WidgetCollectionBean>();
        for (WidgetCollectionBean b : flat) {
            if (b == null) continue;
            String bid = b.getId();
            b.getChildren().clear();
            byId.put(bid, b);
        }
        for (WidgetCollectionBean b : byId.values()) {
            String pid = b.parentId;
            if (pid != null && byId.containsKey(pid)) {
                byId.get(pid).getChildren().add(b);
            } else {
                b.parentId = null;
                roots.add(b);
            }
        }
        return roots;
    }

    public static ArrayList<WidgetCollectionBean> flattenForest(List<WidgetCollectionBean> roots) {
        ArrayList<WidgetCollectionBean> out = new ArrayList<WidgetCollectionBean>();
        if (roots == null) return out;
        for (WidgetCollectionBean root : roots) {
            if (root != null) out.addAll(root.flatten());
        }
        return out;
    }
}
