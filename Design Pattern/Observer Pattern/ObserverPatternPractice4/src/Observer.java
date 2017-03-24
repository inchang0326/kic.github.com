import java.util.ArrayList;

public interface Observer {
	public void update(ArrayList<String> news);
	public int getInterval();
}
