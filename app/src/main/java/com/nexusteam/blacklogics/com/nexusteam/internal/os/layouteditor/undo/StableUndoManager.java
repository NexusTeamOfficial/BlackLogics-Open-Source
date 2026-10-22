
package com.nexusteam.internal.os.layouteditor.undo;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StableUndoManager {

    private static final String TAG = "StableUndoManager";


    private static final class BoundedDeque<T> {
        private final int maxSize;
        private final ArrayDeque<T> deque;

        BoundedDeque(int maxSize) {
            this.maxSize = maxSize;
            this.deque   = new ArrayDeque<>(maxSize);
        }

        void push(T item) {
            deque.push(item);
            while (deque.size() > maxSize) {
                deque.removeLast();
            }
        }

        T pop()           { return deque.isEmpty() ? null : deque.pop(); }
        T peek()          { return deque.peek(); }
        boolean isEmpty() { return deque.isEmpty(); }
        int size()        { return deque.size(); }
        void clear()      { deque.clear(); }
    }


    public interface EditorCommand {
        void execute();
        void undo();
        String getDescription();
    }


    public static class AddViewCommand implements EditorCommand {
        private final WeakReference<View>      viewRef;
        private final WeakReference<ViewGroup> parentRef;
        private final int    index;
        private final String description;

        public AddViewCommand(View view, ViewGroup parent, int index) {
            this.viewRef     = new WeakReference<>(view);
            this.parentRef   = new WeakReference<>(parent);
            this.index       = index;
            this.description = "Add " + view.getClass().getSimpleName();
        }

        @Override
        public void execute() {
            View v      = viewRef.get();
            ViewGroup p = parentRef.get();
            if (v == null || p == null) return;
            if (v.getParent() != null) return;
            int safe = Math.min(index, p.getChildCount());
            p.addView(v, safe);
        }

        @Override
        public void undo() {
            View v      = viewRef.get();
            ViewGroup p = parentRef.get();
            if (v == null || p == null) return;
            if (v.getParent() == p) p.removeView(v);
        }

        @Override public String getDescription() { return description; }
    }


    public static class RemoveViewCommand implements EditorCommand {
        private final WeakReference<View>      viewRef;
        private final WeakReference<ViewGroup> oldParentRef;
        private final int    oldIndex;
        private final String description;

        public RemoveViewCommand(View view, ViewGroup oldParent, int oldIndex) {
            this.viewRef      = new WeakReference<>(view);
            this.oldParentRef = new WeakReference<>(oldParent);
            this.oldIndex     = oldIndex;
            this.description  = "Remove " + view.getClass().getSimpleName();
        }

        @Override
        public void execute() {
            View v      = viewRef.get();
            ViewGroup p = oldParentRef.get();
            if (v == null || p == null) return;
            if (v.getParent() == p) p.removeView(v);
        }

        @Override
        public void undo() {
            View v      = viewRef.get();
            ViewGroup p = oldParentRef.get();
            if (v == null || p == null) return;
            if (v.getParent() != null) return;
            int safe = Math.min(oldIndex, p.getChildCount());
            p.addView(v, safe);
        }

        @Override public String getDescription() { return description; }
    }


    public static class MoveViewCommand implements EditorCommand {
        private final WeakReference<View>      viewRef;
        private final WeakReference<ViewGroup> oldParentRef;
        private final WeakReference<ViewGroup> newParentRef;
        private final int oldIndex;
        private final int newIndex;

        public MoveViewCommand(View view,
                               ViewGroup oldParent, int oldIndex,
                               ViewGroup newParent, int newIndex) {
            this.viewRef      = new WeakReference<>(view);
            this.oldParentRef = new WeakReference<>(oldParent);
            this.newParentRef = new WeakReference<>(newParent);
            this.oldIndex     = oldIndex;
            this.newIndex     = newIndex;
        }

        @Override
        public void execute() {
            View v         = viewRef.get();
            ViewGroup newP = newParentRef.get();
            if (v == null || newP == null) return;
            if (v.getParent() instanceof ViewGroup) ((ViewGroup) v.getParent()).removeView(v);
            newP.addView(v, Math.min(newIndex, newP.getChildCount()));
        }

        @Override
        public void undo() {
            View v         = viewRef.get();
            ViewGroup oldP = oldParentRef.get();
            if (v == null || oldP == null) return;
            if (v.getParent() instanceof ViewGroup) ((ViewGroup) v.getParent()).removeView(v);
            oldP.addView(v, Math.min(oldIndex, oldP.getChildCount()));
        }

        @Override
        public String getDescription() {
            View v = viewRef.get();
            return "Move " + (v != null ? v.getClass().getSimpleName() : "View");
        }
    }


    public static class AttributeChangeCommand implements EditorCommand {

        public interface AttributeApplier {
            void apply(View view, String attrName, String attrValue);
        }

        private final WeakReference<View> viewRef;
        private final String attrName;
        private final String oldValue;
        private final String newValue;
        private final AttributeApplier applier;

        public AttributeChangeCommand(View view, String attrName,
                                      String oldValue, String newValue,
                                      AttributeApplier applier) {
            this.viewRef  = new WeakReference<>(view);
            this.attrName = attrName;
            this.oldValue = oldValue;
            this.newValue = newValue;
            this.applier  = applier;
        }

        @Override
        public void execute() {
            View v = viewRef.get();
            if (v == null || newValue == null) return;
            applier.apply(v, attrName, newValue);
        }

        @Override
        public void undo() {
            View v = viewRef.get();
            if (v == null || oldValue == null) return;
            applier.apply(v, attrName, oldValue);
        }

        @Override
        public String getDescription() { return "Change " + attrName; }
    }


    public static class IdChangeCommand implements EditorCommand {
        private final WeakReference<View> viewRef;
        private final String oldId;
        private final String newId;
        private final IdManagerProxy idManager;

        public interface IdManagerProxy {
            void updateId(View view, String id);
        }

        public IdChangeCommand(View view, String oldId, String newId,
                               IdManagerProxy idManager) {
            this.viewRef   = new WeakReference<>(view);
            this.oldId     = oldId;
            this.newId     = newId;
            this.idManager = idManager;
        }

        @Override
        public void execute() {
            View v = viewRef.get();
            if (v == null) return;
            idManager.updateId(v, newId);
        }

        @Override
        public void undo() {
            View v = viewRef.get();
            if (v == null) return;
            idManager.updateId(v, oldId);
        }

        @Override
        public String getDescription() { return "Change ID: " + oldId + " → " + newId; }
    }


    public static class ConvertWidgetCommand implements EditorCommand {
        private final WeakReference<View> viewRef;
        private final String oldClassName;
        private final String newClassName;
        private final ConvertApplier applier;

        public interface ConvertApplier {
            void applyConversion(View view, String className);
        }

        public ConvertWidgetCommand(View view, String oldClassName,
                                    String newClassName, ConvertApplier applier) {
            this.viewRef      = new WeakReference<>(view);
            this.oldClassName = oldClassName;
            this.newClassName = newClassName;
            this.applier      = applier;
        }

        @Override
        public void execute() {
            View v = viewRef.get();
            if (v == null) return;
            applier.applyConversion(v, newClassName);
        }

        @Override
        public void undo() {
            View v = viewRef.get();
            if (v == null) return;
            applier.applyConversion(v, oldClassName);
        }

        @Override
        public String getDescription() {
            return "Convert: " + oldClassName + " → " + newClassName;
        }
    }


    public static class WrapViewCommand implements EditorCommand {
        private final WeakReference<View>      childRef;
        private final WeakReference<ViewGroup> wrapperRef;
        private final WeakReference<ViewGroup> parentRef;
        private final int    originalIndex;
        private final String description;

        public WrapViewCommand(View child, ViewGroup wrapper,
                               ViewGroup parent, int originalIndex) {
            this.childRef      = new WeakReference<>(child);
            this.wrapperRef    = new WeakReference<>(wrapper);
            this.parentRef     = new WeakReference<>(parent);
            this.originalIndex = originalIndex;
            this.description   = "Wrap " + (child != null
                    ? child.getClass().getSimpleName() : "View");
        }

        @Override
        public void execute() {
            View      child   = childRef.get();
            ViewGroup wrapper = wrapperRef.get();
            ViewGroup parent  = parentRef.get();
            if (child == null || wrapper == null || parent == null) return;

            if (child.getParent() instanceof ViewGroup
                    && child.getParent() != wrapper) {
                ((ViewGroup) child.getParent()).removeView(child);
            }
            if (child.getParent() == null) {
                wrapper.addView(child);
            }
            if (wrapper.getParent() == null) {
                int safe = Math.min(originalIndex, parent.getChildCount());
                parent.addView(wrapper, safe);
            }
        }

        @Override
        public void undo() {
            View      child   = childRef.get();
            ViewGroup wrapper = wrapperRef.get();
            ViewGroup parent  = parentRef.get();
            if (child == null || wrapper == null || parent == null) return;

            if (child.getParent() == wrapper) {
                wrapper.removeView(child);
            }
            if (wrapper.getParent() == parent) {
                parent.removeView(wrapper);
            }
            if (child.getParent() == null) {
                int safe = Math.min(originalIndex, parent.getChildCount());
                parent.addView(child, safe);
            }
        }

        @Override public String getDescription() { return description; }
    }


    public static class DuplicateViewCommand implements EditorCommand {
        private final WeakReference<View>      copyRef;
        private final WeakReference<ViewGroup> parentRef;
        private final int    index;
        private final String description;

        public DuplicateViewCommand(View copy, ViewGroup parent, int index) {
            this.copyRef    = new WeakReference<>(copy);
            this.parentRef  = new WeakReference<>(parent);
            this.index      = index;
            this.description = "Duplicate " + (copy != null
                    ? copy.getClass().getSimpleName() : "View");
        }

        @Override
        public void execute() {
            View      v = copyRef.get();
            ViewGroup p = parentRef.get();
            if (v == null || p == null) return;
            if (v.getParent() != null) return;
            int safe = Math.min(index, p.getChildCount());
            p.addView(v, safe);
        }

        @Override
        public void undo() {
            View      v = copyRef.get();
            ViewGroup p = parentRef.get();
            if (v == null || p == null) return;
            if (v.getParent() == p) p.removeView(v);
        }

        @Override public String getDescription() { return description; }
    }


    public static class ChangeParentCommand implements EditorCommand {
        private final WeakReference<View>      viewRef;
        private final WeakReference<ViewGroup> oldParentRef;
        private final WeakReference<ViewGroup> newParentRef;
        private final int oldIndex;
        private final int newIndex;

        public ChangeParentCommand(View view,
                                   ViewGroup oldParent, int oldIndex,
                                   ViewGroup newParent, int newIndex) {
            this.viewRef      = new WeakReference<>(view);
            this.oldParentRef = new WeakReference<>(oldParent);
            this.newParentRef = new WeakReference<>(newParent);
            this.oldIndex     = oldIndex;
            this.newIndex     = newIndex;
        }

        @Override
        public void execute() {
            View      v    = viewRef.get();
            ViewGroup newP = newParentRef.get();
            if (v == null || newP == null) return;
            if (v.getParent() instanceof ViewGroup) ((ViewGroup) v.getParent()).removeView(v);
            int safe = Math.min(newIndex, newP.getChildCount());
            newP.addView(v, safe);
        }

        @Override
        public void undo() {
            View      v    = viewRef.get();
            ViewGroup oldP = oldParentRef.get();
            if (v == null || oldP == null) return;
            if (v.getParent() instanceof ViewGroup) ((ViewGroup) v.getParent()).removeView(v);
            int safe = Math.min(oldIndex, oldP.getChildCount());
            oldP.addView(v, safe);
        }

        @Override
        public String getDescription() {
            View v = viewRef.get();
            return "Change parent: " + (v != null ? v.getClass().getSimpleName() : "View");
        }
    }


    public static class FlattenCommand implements EditorCommand {
        private final WeakReference<ViewGroup> wrapperRef;
        private final WeakReference<ViewGroup> parentRef;
        private final int wrapperIndex;
        private final List<WeakReference<View>> childRefs;
        private final String description;

        public FlattenCommand(ViewGroup wrapper, ViewGroup parent, int wrapperIndex) {
            this.wrapperRef   = new WeakReference<>(wrapper);
            this.parentRef    = new WeakReference<>(parent);
            this.wrapperIndex = wrapperIndex;
            this.childRefs    = new ArrayList<>();
            if (wrapper != null) {
                for (int i = 0; i < wrapper.getChildCount(); i++) {
                    childRefs.add(new WeakReference<>(wrapper.getChildAt(i)));
                }
            }
            this.description = "Flatten " + (wrapper != null
                    ? wrapper.getClass().getSimpleName() : "Container");
        }

        @Override
        public void execute() {
            ViewGroup wrapper = wrapperRef.get();
            ViewGroup parent  = parentRef.get();
            if (wrapper == null || parent == null) return;
            if (wrapper.getParent() != parent) return;

            int insertAt = parent.indexOfChild(wrapper);
            if (insertAt < 0) return;
            parent.removeView(wrapper);

            int offset = 0;
            for (WeakReference<View> ref : childRefs) {
                View child = ref.get();
                if (child == null) continue;
                if (child.getParent() instanceof ViewGroup) {
                    ((ViewGroup) child.getParent()).removeView(child);
                }
                int safe = Math.min(insertAt + offset, parent.getChildCount());
                parent.addView(child, safe);
                offset++;
            }
        }

        @Override
        public void undo() {
            ViewGroup wrapper = wrapperRef.get();
            ViewGroup parent  = parentRef.get();
            if (wrapper == null || parent == null) return;

            for (WeakReference<View> ref : childRefs) {
                View child = ref.get();
                if (child == null) continue;
                if (child.getParent() instanceof ViewGroup) {
                    ((ViewGroup) child.getParent()).removeView(child);
                }
                wrapper.addView(child);
            }

            if (wrapper.getParent() == null) {
                int safe = Math.min(wrapperIndex, parent.getChildCount());
                parent.addView(wrapper, safe);
            }
        }

        @Override public String getDescription() { return description; }
    }


    private static volatile StableUndoManager instance;

    public static StableUndoManager getInstance() {
        if (instance == null) {
            synchronized (StableUndoManager.class) {
                if (instance == null) instance = new StableUndoManager();
            }
        }
        return instance;
    }

    private StableUndoManager() {}


    private static final int MAX_STACK_SIZE = 50;

    private final Map<String, BoundedDeque<EditorCommand>> undoStacks = new HashMap<>();
    private final Map<String, BoundedDeque<EditorCommand>> redoStacks = new HashMap<>();

    private BoundedDeque<EditorCommand> undoStack(String activity) {
        if (!undoStacks.containsKey(activity)) {
            undoStacks.put(activity, new BoundedDeque<EditorCommand>(MAX_STACK_SIZE));
        }
        return undoStacks.get(activity);
    }

    private BoundedDeque<EditorCommand> redoStack(String activity) {
        if (!redoStacks.containsKey(activity)) {
            redoStacks.put(activity, new BoundedDeque<EditorCommand>(MAX_STACK_SIZE));
        }
        return redoStacks.get(activity);
    }


    public synchronized void push(String activityName, EditorCommand command) {
        undoStack(activityName).push(command);
        redoStack(activityName).clear();
    }

    public synchronized boolean undo(String activityName) {
        BoundedDeque<EditorCommand> uStack = undoStack(activityName);
        BoundedDeque<EditorCommand> rStack = redoStack(activityName);
        if (uStack.isEmpty()) return false;

        EditorCommand cmd = uStack.pop();
        if (cmd == null) return false;
        try {
            cmd.undo();
        } catch (Exception e) {
            Log.e(TAG, "undo() failed [" + cmd.getDescription() + "]: " + e.getMessage(), e);
        }
        rStack.push(cmd);
        return true;
    }

    public synchronized boolean redo(String activityName) {
        BoundedDeque<EditorCommand> uStack = undoStack(activityName);
        BoundedDeque<EditorCommand> rStack = redoStack(activityName);
        if (rStack.isEmpty()) return false;

        EditorCommand cmd = rStack.pop();
        if (cmd == null) return false;
        try {
            cmd.execute();
        } catch (Exception e) {
            Log.e(TAG, "redo() failed [" + cmd.getDescription() + "]: " + e.getMessage(), e);
        }
        uStack.push(cmd);
        return true;
    }

    public synchronized boolean canUndo(String activityName) {
        return !undoStack(activityName).isEmpty();
    }

    public synchronized boolean canRedo(String activityName) {
        return !redoStack(activityName).isEmpty();
    }

    public synchronized int undoCount(String activityName) {
        return undoStack(activityName).size();
    }

    public synchronized int redoCount(String activityName) {
        return redoStack(activityName).size();
    }

    public synchronized void clearHistory(String activityName) {
        undoStack(activityName).clear();
        redoStack(activityName).clear();
    }

    public synchronized void clearAll() {
        undoStacks.clear();
        redoStacks.clear();
    }

    public synchronized String getUndoDescription(String activityName) {
        EditorCommand cmd = undoStack(activityName).peek();
        return cmd != null ? "Undo: " + cmd.getDescription() : "Nothing to undo";
    }

    public synchronized String getRedoDescription(String activityName) {
        EditorCommand cmd = redoStack(activityName).peek();
        return cmd != null ? "Redo: " + cmd.getDescription() : "Nothing to redo";
    }
}
