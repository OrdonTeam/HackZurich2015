package com.hackzurich.model;

import com.dropbox.client2.DropboxAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DropBoxFiles {
    private Map<String, DropboxAPI.Entry> files;
    private Set<String> selectedNames = new HashSet<>();

    public DropBoxFiles(Map<String, DropboxAPI.Entry> files) {
        this.files = files;
    }

    public List<String> getNames() {
        return new ArrayList<>(files.keySet());
    }

    public Collection<DropboxAPI.Entry> getSelected() {
        ArrayList<DropboxAPI.Entry> entries = new ArrayList<>();
        for (Map.Entry<String, DropboxAPI.Entry> entry : files.entrySet()) {
            if (selectedNames.contains(entry.getKey())) {
                entries.add(entry.getValue());
            }
        }
        return entries;
    }

    public void setSelected(String name, boolean select) {
        if (select) {
            selectedNames.add(name);
        } else {
            selectedNames.remove(name);
        }
    }
}
