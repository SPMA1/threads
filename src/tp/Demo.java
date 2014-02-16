package tp;

public class Demo {
	
	
	public static void main(String[] arg){
		
		Thread t1 = new Thread(){
			public void run(){
				System.out.println("Je suis exécuté par le thread 1");
			};
		};
		
		Thread t2 = new Thread(){
			public void run(){
				System.out.println("Je suis exécuté par le thread 2");
			};
		};
		
		MyRunnable r1 = new MyRunnable(2);
		MyRunnable r2 = new MyRunnable(4);
		Thread t3 = new Thread(r1);
		Thread t4 = new Thread(r2);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		System.out.println("Je suis le thread principal");
		
	}
	

}


class MyRunnable implements Runnable{

	int max;
	 
	
	public MyRunnable(int max){
		super();
		this.max=max;
	}
	
	
	@Override
	public void run() {
		
		System.out.println("Je suis exécuté par un Runnable " + max);
	}
	
	class SuperRunnable extends MyRunnable {
		public SuperRunnable(int max, int supeVariable){
			super(max);
		}
	}
	
	
}