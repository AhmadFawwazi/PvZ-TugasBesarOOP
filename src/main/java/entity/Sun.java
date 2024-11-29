package entity;

public class Sun {
    private static int totalSun;

    public Sun ()
    {
        totalSun = 50;
    }

    public static void addSun(){
        totalSun += 25;
    }

    public void addSun(int sun){
        totalSun += sun;
    }
    
    public void reduceSun(int sun){
        if (totalSun > sun)
        {
            totalSun -= sun;
        }
        else {
            return;
        }

    }

    public int getTotalSun(){
        return Sun.totalSun;
    }

    public void setTotalSun(int sun){
        Sun.totalSun = sun;
    }
}