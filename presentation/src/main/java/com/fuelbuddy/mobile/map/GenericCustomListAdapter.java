package com.fuelbuddy.mobile.map;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GenericCustomListAdapter<T> extends BaseAdapter implements Filterable {

    public static final int POSITION_FLAG_BETWEEN = 0;
    public static final int POSITION_FLAG_FIRST = 1;

    public static final int POSITION_FLAG_LAST = 2;

    public static final int POSITION_FLAG_BOTH = POSITION_FLAG_FIRST + POSITION_FLAG_LAST;

    public String selectedItem;

    /**
     * <p>
     * An array filter constrains the content of the array adapter with a
     * prefix. Each item that does not start with the supplied prefix is removed
     * from the list.
     * </p>
     */




    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(final CharSequence prefix) {
            final FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<T>(mObjects);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<T> list;
                synchronized (mLock) {
                    list = new ArrayList<T>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                ArrayList<T> values;
                synchronized (mLock) {
                    values = new ArrayList<T>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<T> newValues = new ArrayList<T>();

                for (int i = 0; i < count; i++) {
                    final T value = values.get(i);
                    final String valueText = value.toString().toLowerCase();

                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixString)) {
                        newValues.add(value);
                    } else {
                        final String[] words = valueText.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with
                        // space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(final CharSequence constraint, final FilterResults results) {
            // noinspection unchecked
            mObjects = (List<T>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }


    public void setSelectedId(String selectedItem) {
        this.selectedItem = selectedItem;
    }


    public interface OnClickItemRefreshListener {
        void onItemRefresh(String gasStationId);
    }



    public interface ListItemInflater<T> {

        View getDropDownView(T item, View convertView, ViewGroup parent, int positionFlag, int position);

        View getView(T item, View convertView, ViewGroup parent, String selectedItem, int positionFlag, int position);

        boolean isEnabled(T item, int position);
    }

    protected ListItemInflater<T> listItemInflater;
    protected Context context;


    /**
     * Contains the list of objects that represent the data of this
     * ArrayAdapter. The content of this list is referred to as "the array" in
     * the documentation.
     */
    protected List<T> mObjects;

    // A copy of the original mObjects array, initialized from and then used
    // instead as soon as
    // the mFilter ArrayFilter is used. mObjects will then only contain the
    // filtered values.
    protected ArrayList<T> mOriginalValues;

    /**
     * Lock used to modify the content of {@link #mObjects}. Any write operation
     * performed on the array should be synchronized on this lock. This lock is
     * also used by the filter (see {@link #getFilter()} to make a synchronized
     * copy of the original array of data.
     */
    protected final Object mLock = new Object();
    /**
     * Indicates whether or not {@link #notifyDataSetChanged()} must be called
     * whenever {@link #mObjects} is modified.
     */
    private boolean mNotifyOnChange = true;

    private Context mContext;

    private ArrayFilter mFilter;

    public GenericCustomListAdapter(final ListItemInflater<T> listItemInflater, final Context context, final List<T> items) {
        this.context = context;
        this.listItemInflater = listItemInflater;
        init(context, 0, 0, items);
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param object The object to add at the end of the array.
     */
    public void add(final T object) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.add(object);
            } else {
                mObjects.add(object);
            }
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * Adds the specified Collection at the end of the array.
     *
     * @param collection The Collection to add at the end of the array.
     */
    public void addAll(final Collection<? extends T> collection) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.addAll(collection);
            } else {
                mObjects.addAll(collection);
            }
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * Adds the specified items at the end of the array.
     *
     * @param items The items to add at the end of the array.
     */
    public void addAll(final T... items) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Collections.addAll(mOriginalValues, items);
            } else {
                Collections.addAll(mObjects, items);
            }
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.clear();
            } else {
                mObjects.clear();
            }
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * Returns the context associated with this array adapter. The context is
     * used to create views from the resource passed to the constructor.
     *
     * @return The Context associated with this adapter.
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        return mObjects.size();
    }

    public View getDropDownView(final int position, final View convertView, final ViewGroup parent) {
        int positionFlag = 0;
        if (position == 0) {
            positionFlag += POSITION_FLAG_FIRST;
        }
        if (position + 1 == getCount()) {
            positionFlag += POSITION_FLAG_LAST;
        }
        return listItemInflater.getDropDownView(getItem(position), convertView, parent, positionFlag, position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getItem(final int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return 0;
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(final T item) {
        return mObjects.indexOf(item);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        int positionFlag = 0;
        if (position == 0) {
            positionFlag += POSITION_FLAG_FIRST;
        }
        if (position + 1 == getCount()) {
            positionFlag += POSITION_FLAG_LAST;
        }
        return listItemInflater.getView(getItem(position), convertView, parent,selectedItem, positionFlag, position);
    }

    private void init(final Context context, final int resource, final int textViewResourceId, final List<T> objects) {
        mContext = context;
        mObjects = objects;
    }

    /**
     * Inserts the specified object at the specified index in the array.
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insert(final T object, final int index) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.add(index, object);
            } else {
                mObjects.add(index, object);
            }
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean isEnabled(final int position) {
        return listItemInflater.isEnabled(getItem(position), position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mNotifyOnChange = true;
    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    public void remove(final T object) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.remove(object);
            } else {
                mObjects.remove(object);
            }
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * Control whether methods that change the list ({@link #add},
     * {@link #insert}, {@link #remove}, {@link #clear}) automatically call
     * {@link #notifyDataSetChanged}. If set to false, caller must manually call
     * notifyDataSetChanged() to have the changes reflected in the attached
     * view.
     * <p/>
     * The default is true, and calling notifyDataSetChanged() resets the flag
     * to true.
     *
     * @param notifyOnChange if true, modifications to the list will automatically call
     *                       {@link #notifyDataSetChanged}
     */
    public void setNotifyOnChange(final boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained in this
     *                   adapter.
     */
    public void sort(final Comparator<? super T> comparator) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Collections.sort(mOriginalValues, comparator);
            } else {
                Collections.sort(mObjects, comparator);
            }
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

}
