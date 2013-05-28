package cn.carcheck.picture;

public interface Task extends Runnable {

	boolean isFinish();

	Exception getException();

	String getOutput();

}
