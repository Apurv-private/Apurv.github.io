package Assignment1.src;

public class text {
    private String menuTitle;
    private int noOfOption;
    private String[] menuOption = new String[7];

    //Constructors
    public text(String menuTitle, int noOfOption) {
        this.menuTitle = menuTitle;
        this.noOfOption = noOfOption; }

    //Setters
    public void setMenuOption(int option,String menuOption) {
        this.menuOption[option] = menuOption;
    }

    public void setTitle(String title) {this.menuTitle = title;}

    //Methods
    public void show(){
        for (int i = 0; i < menuTitle.length()+9; i++)
            System.out.print("*");
        System.out.println();

        System.out.print("\t"+menuTitle +"\n");
        for (int i = 0; i < menuTitle.length()+9; i++)
            System.out.print("*");
        System.out.println();

        for(int i = 0 ; i < 7;i++)
            System.out.println(String.format( (i+1)+":"+menuOption[i]));
    }
}
