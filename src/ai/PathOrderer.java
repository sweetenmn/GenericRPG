package ai;

public interface PathOrderer {
	public void put(Path p);
	public Path peek();
	public Path remove();
	public boolean isEmpty();
}
