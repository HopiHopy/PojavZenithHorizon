/*
 * Copyright (C) 2012 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ipaulpro.afilechooser;

import static net.kdt.pojavlaunch.PojavZHTools.getIcon;
import static net.kdt.pojavlaunch.PojavZHTools.isImage;

import android.content.*;
import android.graphics.drawable.Drawable;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import net.kdt.pojavlaunch.*;

/**
 * List adapter for Files.
 * 
 * @version 2013-12-11
 * @author paulburke (ipaulpro)
 *
 * @addDate 2018-08-08
 * @addToMyProject khanhduy032
 */
public class FileListAdapter extends BaseAdapter {

    private final static int ICON_FOLDER = R.drawable.ic_folder;
    private final static int ICON_FILE = R.drawable.ic_file;
    private final static int ICON_CONTROL = R.drawable.ic_menu_custom_controls;
    private final static int ICON_JAR = R.drawable.ic_java;

    private final LayoutInflater mInflater;

    private List<File> mData = new ArrayList<>();
    private final FileIcon iconType; //图标类型

    public FileListAdapter(Context context, FileIcon iconType) {
        mInflater = LayoutInflater.from(context);
        this.iconType = iconType;
    }

    public void add(File file) {
        mData.add(file);
        notifyDataSetChanged();
    }

    public void remove(File file) {
        mData.remove(file);
        notifyDataSetChanged();
    }

    public void insert(File file, int index) {
        mData.add(index, file);
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public File getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public List<File> getListItems() {
        return mData;
    }

    /**
     * Set the list items without notifying on the clear. This prevents loss of
     * scroll position.
     *
     * @param data
     */
    public void setListItems(List<File> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null)
            row = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        TextView view = (TextView) row;

        // Get the file at the current position
        final File file = getItem(position);

        // Set the TextView as the file name
        view.setText(file.getName());

        // 根据图标类别来设置图标
        int icon = file.isDirectory() ? ICON_FOLDER : ICON_FILE;
        switch (this.iconType) {
            case MOD:
                if (file.getName().endsWith(".jar")) {
                    icon = ICON_JAR;
                }
                view.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
                break;
            case CONTROL:
                if (file.getName().endsWith(".json")) {
                    icon = ICON_CONTROL;
                }
                view.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
                break;
            case MOUSE:
                if (isImage(file)) { //判断是不是一张图片
                    try {
                        Drawable mouse = getIcon(file.getAbsolutePath(), view.getContext());
                        view.setCompoundDrawablesWithIntrinsicBounds(mouse, null, null, null);
                    } catch (Exception e) {
                        view.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
                    }
                } else {
                    view.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
                }
                break;
            default:
                view.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
        }

        view.setCompoundDrawablePadding(20);
        return row;
    }
}
