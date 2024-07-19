package cmpt276.as2.assignment2.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LensManager implements Iterable<Lens> {
    private List<Lens> lensList = new ArrayList<Lens>();


    private static LensManager instance;
    private LensManager(){}


    public int length() {
        return lensList.size(); }

    public Lens getIndex(int position) {
        return lensList.get(position); }

    public void add(Lens lens) {
    lensList.add(lens);
    }

    public static LensManager getInstance(){
        if(instance == null)
            instance = new LensManager();
        return  instance; }

    @Override
    public Iterator<Lens> iterator() {
        return lensList.iterator();
    }

    public void delete(int position) {
            lensList.remove(position); }
}