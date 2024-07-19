package cmpt276.as1.assignment1.Model;

import java.util.ArrayList;
import java.util.List;

public class LensManager {
    private List<Lens> lensList = new ArrayList<>();

    //Constructors
    public LensManager()
    {}

    //Methods
    public void add(String lens, double maximum_aperture, double focal_length)
    {
       lensList.add(new Lens(lens,maximum_aperture,focal_length));
    }

    public String getIndex(int index)
    {
        return lensList.get(index).toString(index);
    }

    public Lens getLens(int index)
    {
        return lensList.get(index);
    }
}
