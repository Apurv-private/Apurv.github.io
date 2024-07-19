package cmpt276.project.Model;

import java.util.ArrayList;

public class ImageSet {
    private ArrayList<String> fileNames;

    public ImageSet() {
        this.fileNames = new ArrayList<>();
    }

    public String getFileName(int index) {
        if (index <= this.fileNames.size()) {
            return this.fileNames.get(index -1);
        } else {
            return "IMG NOT FOUND";
        }
    }

    public void addFileName(String name) {
        this.fileNames.add(name);
    }

    public void clearFileNames() {
        this.fileNames.clear();
    }

    public int getSize() {
        return this.fileNames.size();
    }
}
